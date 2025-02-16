package tech.bacuri.mecadolivre.controller.sumula;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.bacuri.mecadolivre.dto.sumula.NovaSumulaForm;
import tech.bacuri.mecadolivre.entity.sumula.Sumula;
import tech.bacuri.mecadolivre.repository.sumula.SumulaRepository;
import tech.bacuri.mecadolivre.service.sumula.SumulaService;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/sumulas")
public class SumulaController {
    private final SumulaService sumulaService;
    private final SumulaRepository sumulaRepository;

    @Transactional
    @PostMapping
    public Sumula novo(@RequestBody @Valid NovaSumulaForm form) {
        return sumulaService.criarSumulaInicial(form);
    }

    @Transactional
    @PostMapping("/{idsumula}/participantes/{cpf}/assinar")
    public ResponseEntity<?> assinar(@PathVariable(name = "idsumula") Sumula sumula,
                                     @PathVariable String cpf) {

        if (sumula.assinadoPor(cpf)) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of("mensagem", "sumula [" + sumula.getPauta().toUpperCase() + "] assinada"));
        }

        sumula.assinar(cpf);
        return ResponseEntity.ok(sumulaRepository.save(sumula));
    }

    @Transactional
    @PostMapping("/{idsumula}/participantes/{cpf}/rejeitar")
    public ResponseEntity<?> rejeitar(@PathVariable(name = "idsumula") Sumula sumula,
                                      @PathVariable String cpf) {

        if (sumula.assinadoPor(cpf)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("mensagem", "sumula assinada"));
        }

        sumula.assinar(cpf);
        return ResponseEntity.ok(sumulaRepository.save(sumula));
    }
}
