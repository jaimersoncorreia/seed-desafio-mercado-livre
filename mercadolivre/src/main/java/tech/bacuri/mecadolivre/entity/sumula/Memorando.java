package tech.bacuri.mecadolivre.entity.sumula;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(onConstructor_ = @Deprecated)
@Getter
@Entity
@SequenceGenerator(name = "memorando", allocationSize = 20, sequenceName = "SQ_MEMORANDO")
public class Memorando {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "memorando")
    private Long id;

    @ManyToOne
    private Atividade atividade;

    private String objeto;

    public Memorando(Atividade atividade, String objeto) {
        this.atividade = atividade;
        this.objeto = objeto;
    }
}
