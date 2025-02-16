package tech.bacuri.mecadolivre.entity.sumula;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;
import tech.bacuri.mecadolivre.converter.AssinaturasConverter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

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

    @NotNull
    @Column(columnDefinition = "text")
    @Convert(converter = AssinaturasConverter.class)
    private Set<AssinaturaJson> assinaturasJson = new HashSet<>();

    @JsonIgnore
    @Size(min = 1)
    @OneToMany(mappedBy = "sumula", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<AssinaturaSumula> assinaturas = new HashSet<>();

    private Sumula(Reuniao reuniao, String pauta, Sumula sumulaAnterior, Set<AssinaturaSumula> assinaturas, Set<AssinaturaJson> assinaturasJson) {
        this.reuniao = reuniao;
        this.pauta = pauta;
        this.sumulaAnterior = sumulaAnterior;
        this.assinaturasJson.addAll(assinaturasJson);
        this.assinaturas.addAll(assinaturas.stream().peek(assinaturaConsumer()).collect(Collectors.toSet()));
    }

    private Consumer<AssinaturaSumula> assinaturaConsumer() {
        return assinaturaSumula -> assinaturaSumula.associar(this);
    }

    public static Sumula criarSumulaRejeicao(Sumula sumula) {
        Set<AssinaturaSumula> assinaturaSumulas = sumula.getAssinaturas().stream()
                .map(AssinaturaSumula::copia)
                .peek(AssinaturaSumula::removerAssinatura)
                .collect(Collectors.toSet());

        Set<AssinaturaJson> assinaturasJson = sumula.getAssinaturasJson()
                .stream()
                .map(AssinaturaJson::copia)
                .peek(AssinaturaJson::removerAssinatura)
                .collect(Collectors.toSet());

        return new Sumula(sumula.reuniao, sumula.pauta, sumula, assinaturaSumulas, assinaturasJson);
    }

    public static Sumula criarSumulaInicial(Reuniao reuniao,
                                            String pauta,
                                            List<AssinaturaJson> assinaturasJson,
                                            List<AssinaturaSumula> assinaturas) {
        Set<AssinaturaSumula> assinaturaSumulas = new HashSet<>(assinaturas);
        return new Sumula(reuniao, pauta, null, assinaturaSumulas, new HashSet<>(assinaturasJson));
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
}
