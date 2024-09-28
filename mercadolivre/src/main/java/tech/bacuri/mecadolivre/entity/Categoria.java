package tech.bacuri.mecadolivre.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@RequiredArgsConstructor
@NoArgsConstructor(onConstructor_ = @Deprecated)
@Entity
@SequenceGenerator(name = "categoria", allocationSize = 1, sequenceName = "SQ_CATEGORIA")
public class Categoria {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "categoria")
    private Long id;

    @NotBlank
    @NonNull
    @Column(unique = true)
    private String nome;

    @Setter
    @ManyToOne
    private Categoria categoriaMae;
}
