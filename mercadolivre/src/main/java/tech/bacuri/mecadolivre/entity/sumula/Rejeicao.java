package tech.bacuri.mecadolivre.entity.sumula;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor(onConstructor_ = @Deprecated)
@Getter
@Entity
@SequenceGenerator(name = "rejeicao", allocationSize = 20, sequenceName = "SQ_REJEICAO")
public class Rejeicao {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rejeicao")
    private Long id;

    @ManyToOne
    private Participante participante;

    @ManyToOne
    private Sumula sumula;

    private String pauta;

    private LocalDateTime tsRejeicao;

    private String codigoVerificador;

    public Rejeicao(Participante participante, Sumula sumula, String pauta,String codigoVerificador) {
        this.participante = participante;
        this.sumula = sumula;
        this.pauta = pauta;
        this.tsRejeicao = LocalDateTime.now();
        this.codigoVerificador = codigoVerificador;
    }

    public static Rejeicao criar(Sumula sumula, String cpf, @NotBlank String pauta, String codigoVerificador) {
        Assert.isTrue(sumula.pendenteAssinatura(), "não pode rejeitar uma súmula [" + sumula.getStatus() + "]");
        return new Rejeicao(sumula.getParticipante(cpf), sumula, pauta, codigoVerificador);
    }

    public void assinar() {
        this.codigoVerificador = String.valueOf(UUID.randomUUID());
    }
}
