package tech.bacuri.mecadolivre.repository.sumula;

import org.springframework.data.repository.CrudRepository;
import tech.bacuri.mecadolivre.entity.sumula.Memorando;
import tech.bacuri.mecadolivre.entity.sumula.Reuniao;
import tech.bacuri.mecadolivre.entity.sumula.Sumula;

import java.util.List;

public interface SumulaRepository extends CrudRepository<Sumula, Long> {
    boolean existsSumulasByReuniao(Reuniao reuniao);

    List<Sumula> findSumulaByReuniao_Memorando(Memorando reuniaoMemorando);
}
