package tech.bacuri.mecadolivre.entity.sumula;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(onConstructor_ = @Deprecated)
@Getter
@Entity
@SequenceGenerator(name = "participante", allocationSize = 20, sequenceName = "SQ_PARTICIPANTE")
public class Participante {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "participante")
    private Long id;

    private String nome;

    public Participante(String nome) {
        this.nome = nome;
    }
}
