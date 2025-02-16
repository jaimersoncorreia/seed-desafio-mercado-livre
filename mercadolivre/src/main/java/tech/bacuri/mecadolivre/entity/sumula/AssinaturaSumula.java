package tech.bacuri.mecadolivre.entity.sumula;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@NoArgsConstructor(onConstructor_ = @Deprecated)
@Getter
@Entity
@SequenceGenerator(name = "assinaturaSumula", allocationSize = 20, sequenceName = "SQ_ASSINATURA_SUMULA")
public class AssinaturaSumula {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "assinaturaSumula")
    private Long id;

    @ManyToOne
    private Participante participante;

    @ManyToOne
    private Sumula sumula;

    private LocalDateTime tsAssinatura;

    public AssinaturaSumula(Participante participante) {
        this.participante = participante;
    }

    public void assinar() {
        this.tsAssinatura = LocalDateTime.now();
    }

    public void associar(Sumula sumula) {
        this.sumula = sumula;
    }

    public void removerAssinatura() {
        this.tsAssinatura = null;
    }

    public AssinaturaSumula copia() {
        return new AssinaturaSumula(this.getParticipante());
    }

    public boolean ehIgual(String cpf) {
        return Objects.equals(this.participante.getCpf(), cpf);
    }

    public boolean assinado() {
        return Objects.nonNull(this.tsAssinatura);
    }
}
