package tech.bacuri.mecadolivre.repository.mercado;

import org.springframework.data.repository.CrudRepository;
import tech.bacuri.mecadolivre.entity.mercado.Compra;

public interface CompraRepository extends CrudRepository<Compra, Long> {
    Compra getCompraById(Long idCompra);
}
