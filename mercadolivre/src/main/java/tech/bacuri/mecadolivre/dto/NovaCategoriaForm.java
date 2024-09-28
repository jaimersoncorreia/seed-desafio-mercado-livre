package tech.bacuri.mecadolivre.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.springframework.util.Assert;
import tech.bacuri.mecadolivre.entity.Categoria;
import tech.bacuri.mecadolivre.repository.CategoriaRepository;

import java.util.Objects;

@Data
public class NovaCategoriaForm {
    @NotBlank
    private String nome;

    @Positive
    private Long idCategoriaMae;

    public Categoria toCategoria(CategoriaRepository repository) {
        Categoria categoria = new Categoria(nome);
        if (Objects.nonNull(idCategoriaMae)) {
            Categoria mae = repository.getCategoriaById(idCategoriaMae);
            Assert.notNull(mae, "o id da categoria mãe precisa ser válido");
            categoria.setCategoriaMae(mae);
        }

        return categoria;
    }
}
