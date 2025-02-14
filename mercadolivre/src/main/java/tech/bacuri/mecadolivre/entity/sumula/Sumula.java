package tech.bacuri.mecadolivre.entity.sumula;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.HashSet;
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

    @Size(min = 1)
    @OneToMany(mappedBy = "sumula", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<AssinaturaSumula> assinaturas = new HashSet<>();

    private Sumula(Reuniao reuniao, String pauta, Sumula sumulaAnterior, Set<AssinaturaSumula> assinaturas) {
        this.reuniao = reuniao;
        this.pauta = pauta;
        this.sumulaAnterior = sumulaAnterior;
        this.assinaturas.addAll(assinaturas.stream().peek(assinaturaConsumer()).collect(Collectors.toSet()));
    }

    private Consumer<AssinaturaSumula> assinaturaConsumer() {
        return assinaturaSumula -> assinaturaSumula.associar(this);
    }

    public static Sumula criarSumulaInicial(Reuniao reuniao, String pauta, AssinaturaSumula... assinaturas) {
        Set<AssinaturaSumula> collect = Arrays.stream(assinaturas).collect(Collectors.toSet());
        return new Sumula(reuniao, pauta, null, collect);
    }

    public void assinar(Participante participante) {
        this.getAssinaturas().stream()
                .filter(assinatura -> assinatura.getParticipante().getId().equals(participante.getId()))
                .findFirst()
                .ifPresent(AssinaturaSumula::assinar);
    }
}
