package tech.bacuri.mecadolivre.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.Assert;
import tech.bacuri.mecadolivre.anotation.ExistsId;
import tech.bacuri.mecadolivre.entity.Categoria;
import tech.bacuri.mecadolivre.entity.Produto;
import tech.bacuri.mecadolivre.entity.Usuario;
import tech.bacuri.mecadolivre.repository.CategoriaRepository;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class NovoProdutoForm {
    @NotBlank
    private String nome;

    @Positive
    private BigDecimal valor;

    @Positive
    private Long quantidade;

    private String caracteristicas;

    @NotBlank
    @Length(max = 1000)
    private String descricao;

    @NotNull
    @ExistsId(domainClass = Categoria.class, fieldName = "id")
    private Long idCategoria;

    public Produto toProduto(CategoriaRepository repository, Usuario dono) {
        Categoria categoria = repository.getCategoriaById(idCategoria);
        Assert.notNull(categoria, "categoria não pode está nulo");
        return new Produto(nome, valor, quantidade, caracteristicas, descricao, categoria, dono);
    }
}
