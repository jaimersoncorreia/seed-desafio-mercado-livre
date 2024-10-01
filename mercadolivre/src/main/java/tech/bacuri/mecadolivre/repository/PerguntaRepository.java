package tech.bacuri.mecadolivre.repository;

import org.springframework.data.repository.CrudRepository;
import tech.bacuri.mecadolivre.entity.Pergunta;

public interface PerguntaRepository extends CrudRepository<Pergunta, Long> {
}
