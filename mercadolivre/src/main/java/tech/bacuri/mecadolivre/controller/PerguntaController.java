package tech.bacuri.mecadolivre.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.bacuri.mecadolivre.dto.NovaPerguntaForm;
import tech.bacuri.mecadolivre.entity.Pergunta;
import tech.bacuri.mecadolivre.entity.Produto;
import tech.bacuri.mecadolivre.entity.Usuario;
import tech.bacuri.mecadolivre.repository.PerguntaRepository;
import tech.bacuri.mecadolivre.repository.ProdutoRepository;
import tech.bacuri.mecadolivre.repository.UsuarioRepository;

@RequiredArgsConstructor
@RestController
@RequestMapping("/produtos/{id}/pergunta s")
public class PerguntaController {

    private final ProdutoRepository produtoRepository;
    private final UsuarioRepository usuarioRepository;
    private final PerguntaRepository perguntaRepository;

    @Transactional
    @PostMapping
    public ResponseEntity<?> cria(@PathVariable Long id, @RequestBody @Valid NovaPerguntaForm form) {
        Produto produto = produtoRepository.getProdutoById(id);
        Usuario interessada = usuarioRepository.getByEmail("teste@teste.com");
        Pergunta novaPergunta = form.toPergunta(produto, interessada);
        return ResponseEntity.ok(perguntaRepository.save(novaPergunta));
    }
}
