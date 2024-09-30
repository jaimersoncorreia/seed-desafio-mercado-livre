package tech.bacuri.mecadolivre.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor(onConstructor_ = @Deprecated)
@RequiredArgsConstructor
@Entity
@SequenceGenerator(name = "opiniao", allocationSize = 1, sequenceName = "SQ_OPINIAO")
public class Opiniao {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "opiniao")
    private Long id;

    @Min(1)
    @Max(5)
    @NonNull
    private Integer nota;

    @NonNull
    @NotBlank
    private String titulo;

    @NotBlank
    @NonNull
    @Size(max = 500)
    private String descricao;

    @NotNull
    @NonNull
    @ManyToOne
    private Produto produto;

    @NotNull
    @NonNull
    @ManyToOne
    private Usuario consumidor;
}
