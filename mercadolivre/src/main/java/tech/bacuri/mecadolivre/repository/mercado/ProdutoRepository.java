package tech.bacuri.mecadolivre.repository.mercado;

import org.springframework.data.repository.CrudRepository;
import tech.bacuri.mecadolivre.entity.mercado.Produto;

public interface ProdutoRepository extends CrudRepository<Produto, Long> {
    Produto getProdutoById(Long id);
}
