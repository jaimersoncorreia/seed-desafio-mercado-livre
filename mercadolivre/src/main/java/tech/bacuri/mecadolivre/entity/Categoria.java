package tech.bacuri.mecadolivre.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@NoArgsConstructor(onConstructor_ = @Deprecated)
@Entity
@SequenceGenerator(name = "categoria", sequenceName = "SQ_CATEGORIA")
public class Categoria {
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
