package tech.bacuri.mecadolivre.entity.sumula;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.Assert;
import tech.bacuri.mecadolivre.converter.AssinaturasConverter;
import tech.bacuri.mecadolivre.enums.StatusSumula;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static tech.bacuri.mecadolivre.enums.StatusSumula.APROVADA;
import static tech.bacuri.mecadolivre.enums.StatusSumula.CONTESTADA;

@NoArgsConstructor(onConstructor_ = @Deprecated)
@Getter
@Entity
@SequenceGenerator(name = "sumula", allocationSize = 20, sequenceName = "SQ_SUMULA")
public class Sumula {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sumula")
    private Long id;

    @ManyToOne
    private Reuniao reuniao;

    private String pauta;

    @OneToOne
    private Sumula sumulaAnterior;

    @Enumerated(EnumType.STRING)
    private StatusSumula status;

    @Setter
    private String codigoVerificador;


    @NotNull
    @Column(columnDefinition = "text")
    @Convert(converter = AssinaturasConverter.class)
    private Set<AssinaturaJson> assinaturasJson = new HashSet<>();

    @JsonIgnore
    @Size(min = 1)
    @OneToMany(mappedBy = "sumula", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<AssinaturaSumula> assinaturas = new HashSet<>();

    private Sumula(Reuniao reuniao,
                   String pauta,
                   Sumula sumulaAnterior,
                   Set<AssinaturaSumula> assinaturas,
                   Set<AssinaturaJson> assinaturasJson, StatusSumula status) {
        Assert.notNull(status, "não é permitido status null");
        this.reuniao = reuniao;
        this.pauta = pauta;
        this.sumulaAnterior = sumulaAnterior;
        this.assinaturasJson.addAll(assinaturasJson);
        this.assinaturas.addAll(assinaturas.stream().peek(assinaturaConsumer()).collect(Collectors.toSet()));
        this.status = status;
    }

    public static Sumula criarSumulaInicial(Reuniao reuniao,
                                            String pauta,
                                            List<AssinaturaJson> assinaturasJson,
                                            List<AssinaturaSumula> assinaturas) {
        Set<AssinaturaSumula> assinaturaSumulas = new HashSet<>(assinaturas);
        return new Sumula(reuniao,
                pauta,
                null,
                assinaturaSumulas,
                new HashSet<>(assinaturasJson),
                StatusSumula.RASCUNHO);
    }

    private Consumer<AssinaturaSumula> assinaturaConsumer() {
        return assinaturaSumula -> assinaturaSumula.associar(this);
    }

    public static Sumula criarSumulaRascunho(Sumula sumula) {
        Set<AssinaturaSumula> assinaturaSumulas = sumula.getAssinaturas().stream()
                .map(AssinaturaSumula::copia)
                .peek(AssinaturaSumula::removerAssinatura)
                .collect(Collectors.toSet());

        Set<AssinaturaJson> assinaturasJson = sumula.getAssinaturasJson()
                .stream()
                .map(AssinaturaJson::copia)
                .peek(AssinaturaJson::removerAssinatura)
                .collect(Collectors.toSet());


        Assert.isTrue(sumula.pendenteAssinatura(), "não pode rejeitar súmula que está em situação [" + sumula.status + "]");
        sumula.contestar();

        return new Sumula(sumula.reuniao, sumula.pauta, sumula, assinaturaSumulas, assinaturasJson, StatusSumula.RASCUNHO);
    }

    public void assinar(String cpf) {
        this.getAssinaturas().stream()
                .filter(assinatura -> assinatura.getParticipante().getCpf().equals(cpf))
                .findFirst()
                .ifPresent(AssinaturaSumula::assinar);

        this.getAssinaturasJson().stream()
                .filter(assinaturaJson -> assinaturaJson.getCpf().equals(cpf))
                .findFirst()
                .ifPresent(AssinaturaJson::assinar);

        Assert.isTrue(!this.contestada(), "não possível assinar súmula com situação [" + this.status + "]");

        if (!this.pendenteAssinatura()) {
            this.setCodigoVerificador(UUID.randomUUID().toString());
        }

        this.status = todosAssinaram() ? APROVADA : StatusSumula.EM_ASSINATURA;
    }

    public boolean todosAssinaram() {
        return getAssinaturas().size() == getAssinaturas().stream().filter(AssinaturaSumula::assinado).count();
    }

    public boolean assinadoPor(String cpf) {
        List<AssinaturaSumula> assinaturas = this.assinaturas.stream()
                .filter(assinatura -> assinatura.ehIgual(cpf))
                .toList();

        Assert.notEmpty(assinaturas, "participante não encontrada");
        boolean banco = assinaturas.stream().anyMatch(AssinaturaSumula::assinado);

        List<AssinaturaJson> jsons = this.assinaturasJson.stream()
                .filter(assinatura -> assinatura.ehIgual(cpf))
                .toList();

        Assert.notEmpty(jsons, "participante não encontrada");

        boolean json = jsons.stream().anyMatch(AssinaturaJson::assinado);

        return banco && json;
    }

    public boolean aprovada() {
        return Objects.equals(this.status, APROVADA);
    }

    public boolean contestada() {
        return Objects.equals(this.status, CONTESTADA);
    }

    public void contestar() {
        this.status = CONTESTADA;
    }

    public Participante getParticipante(String cpf) {
        return this.assinaturas.stream()
                .filter(assinatura -> assinatura.ehIgual(cpf))
                .map(AssinaturaSumula::getParticipante)
                .findFirst().orElseThrow(() -> new EntityNotFoundException("participante não encontrado"));
    }

    public boolean naoAprovada() {
        return !aprovada();
    }

    public boolean pendenteAssinatura() {
        return Objects.equals(this.status, StatusSumula.EM_ASSINATURA);
    }

    public boolean rascunhada() {
        return Objects.equals(this.status, StatusSumula.RASCUNHO);
    }
}
