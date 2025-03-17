package tech.bacuri.mecadolivre.controller.documento;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.bacuri.mecadolivre.dto.documento.NovoDocumentoForm;
import tech.bacuri.mecadolivre.entity.documento.Documento;
import tech.bacuri.mecadolivre.enums.StatusDocumento;
import tech.bacuri.mecadolivre.repository.documento.DocumentoRepository;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/documentos")
public class DocumentoController {

    private final DocumentoRepository documentoRepository;

    @Transactional
    @PostMapping
    public Documento novo(@RequestBody @Valid NovoDocumentoForm form) {
        Documento documento = form.toEntity();
        return documentoRepository.save(documento);
    }

    @Transactional
    @PostMapping("/{idDocumento}/diponibizar_assinatura")
    public ResponseEntity<?> diponibizarAssinatura(@PathVariable(name = "idDocumento") Documento documento) {


        try {
            documento.colocarEmAssinatura();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("mensagem", e.getMessage()));
        }

        return ResponseEntity.ok(documentoRepository.save(documento));
    }

    @Transactional
    @PostMapping("/{idDocumento}/participantes/{cpf}")
    public ResponseEntity<?> assinar(@PathVariable(name = "idDocumento") Documento documento,
                                     @PathVariable String cpf) {

        if (!documento.assinaturaEstaEmAndamento()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("mensagem", "Assinatura deve está " + StatusDocumento.EM_ASSINATURA + ", mas está " + documento.getStatus()));
        }

        if (documento.assinadoPor(cpf)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("mensagem", "documento assinado"));
        }

        documento.assinar(cpf);
        return ResponseEntity.ok(documentoRepository.save(documento).getAssinaturasDocumento());
    }
}
