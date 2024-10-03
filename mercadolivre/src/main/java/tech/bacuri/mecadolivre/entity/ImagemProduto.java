package tech.bacuri.mecadolivre.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.URL;

@RequiredArgsConstructor
@NoArgsConstructor(onConstructor_ = @Deprecated)
@SequenceGenerator(name = "imagemProduto", allocationSize = 1, sequenceName = "SQ_IMAGEM_PRODUTO")
@Entity
public class ImagemProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "imagemProduto")
    private Long id;

    @JsonIgnore
    @NotNull
    @NonNull
    @Valid
    @ManyToOne
    private Produto produto;

    @Getter
    @URL
    @NotBlank
    @NonNull
    private String link;
}
