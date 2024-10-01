package tech.bacuri.mecadolivre.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.bacuri.mecadolivre.dto.NovaPerguntaForm;
import tech.bacuri.mecadolivre.infra.Emails;
import tech.bacuri.mecadolivre.repository.PerguntaRepository;
import tech.bacuri.mecadolivre.repository.ProdutoRepository;
import tech.bacuri.mecadolivre.repository.UsuarioRepository;

@RequiredArgsConstructor
@RestController
@RequestMapping("/produtos/{id}/perguntas")
public class PerguntaController {

    private final ProdutoRepository produtoRepository;
    private final UsuarioRepository usuarioRepository;
    private final PerguntaRepository perguntaRepository;
    private final Emails emails;

    @Transactional
    @PostMapping
    public ResponseEntity<?> cria(@PathVariable Long id, @RequestBody @Valid NovaPerguntaForm form) {
        var produto = produtoRepository.getProdutoById(id);
        var interessada = usuarioRepository.getByEmail("interessado@bacuri.tech");
        var novaPergunta = form.toPergunta(produto, interessada);

        var pergunta = perguntaRepository.save(novaPergunta);

        emails.novaPergunta(novaPergunta);

        return ResponseEntity.ok(pergunta);
    }
}
