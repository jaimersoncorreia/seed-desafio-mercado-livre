package tech.bacuri.mecadolivre.repository;

import org.springframework.data.repository.CrudRepository;
import tech.bacuri.mecadolivre.entity.Produto;

public interface ProdutoRepository extends CrudRepository<Produto, Long> {
    Produto getProdutoById(Long id);
}
