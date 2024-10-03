package tech.bacuri.mecadolivre.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@NoArgsConstructor(onConstructor_ = @Deprecated)
@Entity
@SequenceGenerator(name = "caracteristicaProduto", allocationSize = 1, sequenceName = "SQ_CARACTERISTICA_PRODUTO")
public class CaracteristicaProduto {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "caracteristicaProduto")
    private Long id;

    @Getter
    @NotBlank
    @NonNull
    private String nome;

    @Getter
    @NotBlank
    @NonNull
    private String descricao;

    @JsonIgnore
    @NonNull
    @NotNull
    @ManyToOne
    private Produto produto;

    public CaracteristicaProduto(@NonNull @NotBlank @NotBlank String nome,
                                 @NonNull @NotBlank @NotBlank String descricao,
                                 @NotNull @Valid @NotNull Produto produto) {
        this.nome = nome;
        this.descricao = descricao;
        this.produto = produto;
    }
}
