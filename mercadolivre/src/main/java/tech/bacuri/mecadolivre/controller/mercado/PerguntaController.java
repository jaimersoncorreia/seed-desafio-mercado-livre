package tech.bacuri.mecadolivre.controller.mercado;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.bacuri.mecadolivre.dto.mercado.NovaPerguntaForm;
import tech.bacuri.mecadolivre.infra.EmailsService;
import tech.bacuri.mecadolivre.repository.mercado.PerguntaRepository;
import tech.bacuri.mecadolivre.repository.mercado.ProdutoRepository;
import tech.bacuri.mecadolivre.repository.mercado.UsuarioRepository;

@RequiredArgsConstructor
@RestController
@RequestMapping("/produtos/{id}/perguntas")
public class PerguntaController {

    private final ProdutoRepository produtoRepository;
    private final UsuarioRepository usuarioRepository;
    private final PerguntaRepository perguntaRepository;
    private final EmailsService emailsService;

    @Transactional
    @PostMapping
    public ResponseEntity<?> cria(@PathVariable Long id, @RequestBody @Valid NovaPerguntaForm form) {
        var produto = produtoRepository.getProdutoById(id);
        var interessada = usuarioRepository.getByEmail("interessado@bacuri.tech");
        var novaPergunta = form.toPergunta(produto, interessada);

        var pergunta = perguntaRepository.save(novaPergunta);

        emailsService.novaPergunta(novaPergunta);

        return ResponseEntity.ok(pergunta);
    }
}
