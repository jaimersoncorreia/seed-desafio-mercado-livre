package tech.bacuri.mecadolivre.repository.sumula;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import tech.bacuri.mecadolivre.entity.sumula.Atividade;
import tech.bacuri.mecadolivre.entity.sumula.Reuniao;
import tech.bacuri.mecadolivre.entity.sumula.Sumula;

import java.util.List;

public interface SumulaRepository extends CrudRepository<Sumula, Long> {
    boolean existsSumulasByReuniao(Reuniao reuniao);

    @Query("select s from Sumula s " +
            "join s.reuniao r " +
            "join r.memorando m " +
            "where r.dataHora is not null and m.atividade = :atividade")
    List<Sumula> buscarSumulasPorAtividade(Atividade atividade);
}
