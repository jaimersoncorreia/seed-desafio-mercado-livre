package tech.bacuri.mecadolivre.entity.sumula;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(onConstructor_ = @Deprecated)
@Getter
@Entity
@SequenceGenerator(name = "atividade", allocationSize = 20, sequenceName = "SQ_ATIVIDADE")
public class Atividade {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "atividade")
    private Long id;

    private Integer exercicio;

    public Atividade(Integer exercicio) {
        this.exercicio = exercicio;
    }
}
