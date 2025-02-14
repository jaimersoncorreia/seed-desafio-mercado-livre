package tech.bacuri.mecadolivre.entity.sumula;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(onConstructor_ = @Deprecated)
@Getter
@Entity
@SequenceGenerator(name = "reuniao", allocationSize = 20, sequenceName = "SQ_REUNIAO")
public class Reuniao {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reuniao")
    private Long id;

    @ManyToOne
    private Memorando memorando;

    private LocalDateTime dataHora;

    public Reuniao(Memorando memorando, LocalDateTime dataHora) {
        this.memorando = memorando;
        this.dataHora = dataHora;
    }
}
