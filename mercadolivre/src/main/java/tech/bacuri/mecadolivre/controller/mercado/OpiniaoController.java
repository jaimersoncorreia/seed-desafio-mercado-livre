package tech.bacuri.mecadolivre.controller.mercado;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.bacuri.mecadolivre.dto.mercado.NovoOpiniaoForm;
import tech.bacuri.mecadolivre.entity.mercado.Opiniao;
import tech.bacuri.mecadolivre.entity.mercado.Produto;
import tech.bacuri.mecadolivre.entity.mercado.Usuario;
import tech.bacuri.mecadolivre.repository.mercado.OpiniaoRepository;
import tech.bacuri.mecadolivre.repository.mercado.ProdutoRepository;
import tech.bacuri.mecadolivre.repository.mercado.UsuarioRepository;

@RequiredArgsConstructor
@RestController
@RequestMapping("/produtos/{id}/opnioes")
public class OpiniaoController {

    private final ProdutoRepository produtoRepository;
    private final UsuarioRepository usuarioRepository;
    private final OpiniaoRepository opiniaoRepository;

    @Transactional
    @PostMapping
    public ResponseEntity<?> adiciona(@PathVariable Long id, @RequestBody @Valid NovoOpiniaoForm form) {
        Produto produto = produtoRepository.getProdutoById(id);
        Usuario consumidor = usuarioRepository.getByEmail("consumidor@bacuri.tech");
        Opiniao novaOpiniao = form.toOpiniao(produto, consumidor);

        Opiniao opiniao = opiniaoRepository.save(novaOpiniao);
        return ResponseEntity.ok(opiniao.toString());
    }
}
