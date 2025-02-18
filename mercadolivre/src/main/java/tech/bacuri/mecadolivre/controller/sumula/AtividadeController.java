package tech.bacuri.mecadolivre.controller.sumula;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.bacuri.mecadolivre.entity.sumula.Atividade;
import tech.bacuri.mecadolivre.repository.sumula.MemorandoRepository;
import tech.bacuri.mecadolivre.repository.sumula.SumulaRepository;

@RequiredArgsConstructor
@RestController
@RequestMapping("/atividades")
public class AtividadeController {
    private final SumulaRepository sumulaRepository;
    private final MemorandoRepository memorandoRepository;

    /*TODO: criar um dto para essa listagem e também uma consulta específica para essa listagem*/
    @GetMapping("/{idAtividade}/sumulas")
    public ResponseEntity<?> buscarSumulaPorAtividade(@PathVariable(name = "idAtividade") Atividade atividade) {
        return ResponseEntity.ok(sumulaRepository.buscarSumulasPorAtividade(atividade));
    }
}
