package tech.bacuri.mecadolivre.entity.sumula;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(onConstructor_ = @Deprecated)
@Getter
@Entity
@SequenceGenerator(name = "reuniaoParticipante", allocationSize = 20, sequenceName = "SQ_REUNIAO_PARTICIPANTE")
public class ReuniaoParticipante {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reuniaoParticipante")
    private Long id;

    @ManyToOne
    private Reuniao reuniao;

    @ManyToOne
    private Participante participante;

    public ReuniaoParticipante(Reuniao reuniao, Participante participante) {
        this.reuniao = reuniao;
        this.participante = participante;
    }
}
