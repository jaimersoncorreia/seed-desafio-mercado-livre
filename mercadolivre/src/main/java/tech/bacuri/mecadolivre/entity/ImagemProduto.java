package tech.bacuri.mecadolivre.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.URL;

@RequiredArgsConstructor
@NoArgsConstructor(onConstructor_ = @Deprecated)
@SequenceGenerator(name = "imagemProduto", allocationSize = 1, sequenceName = "SQ_IMAGEM_PRODUTO")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class ImagemProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "imagemProduto")
    private Long id;

    @NotNull
    @NonNull
    @EqualsAndHashCode.Include
    @Valid
    @ManyToOne
    private Produto produto;

    @URL
    @EqualsAndHashCode.Include
    @NotBlank
    @NonNull
    private String link;
}
