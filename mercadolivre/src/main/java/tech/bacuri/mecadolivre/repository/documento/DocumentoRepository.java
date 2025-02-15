package tech.bacuri.mecadolivre.repository.documento;

import org.springframework.data.repository.CrudRepository;
import tech.bacuri.mecadolivre.entity.documento.Documento;

public interface DocumentoRepository extends CrudRepository<Documento, Long> {
}
