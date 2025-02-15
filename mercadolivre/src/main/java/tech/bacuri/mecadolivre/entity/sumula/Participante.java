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

    private String cpf;

    public Participante(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }
}
