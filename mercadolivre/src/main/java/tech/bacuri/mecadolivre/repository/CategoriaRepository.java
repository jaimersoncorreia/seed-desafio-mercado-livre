package tech.bacuri.mecadolivre.repository;

import org.springframework.data.repository.CrudRepository;
import tech.bacuri.mecadolivre.entity.Categoria;

public interface CategoriaRepository extends CrudRepository<Categoria, Long> {
    Categoria getCategoriaById(Long idCategoriaMae);
}
