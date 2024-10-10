package tech.bacuri.mecadolivre.repository;

import org.springframework.data.repository.CrudRepository;
import tech.bacuri.mecadolivre.entity.Compra;

public interface CompraRepository extends CrudRepository<Compra, Long> {
    Compra getCompraById(Long idCompra);
}
