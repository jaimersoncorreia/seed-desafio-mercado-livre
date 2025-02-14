package tech.bacuri.mecadolivre.entity.sumula;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
}
