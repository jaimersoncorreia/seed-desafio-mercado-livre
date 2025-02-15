package tech.bacuri.mecadolivre.repository.mercado;

import org.springframework.data.repository.CrudRepository;
import tech.bacuri.mecadolivre.entity.mercado.Categoria;

public interface CategoriaRepository extends CrudRepository<Categoria, Long> {
    Categoria getCategoriaById(Long idCategoriaMae);
}
