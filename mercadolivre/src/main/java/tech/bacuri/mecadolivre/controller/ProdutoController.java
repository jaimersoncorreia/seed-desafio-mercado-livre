package tech.bacuri.mecadolivre.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tech.bacuri.mecadolivre.componentes.Uploader;
import tech.bacuri.mecadolivre.dto.NovasImagensForm;
import tech.bacuri.mecadolivre.dto.NovoProdutoForm;
import tech.bacuri.mecadolivre.entity.Produto;
import tech.bacuri.mecadolivre.entity.Usuario;
import tech.bacuri.mecadolivre.repository.CategoriaRepository;
import tech.bacuri.mecadolivre.repository.ProdutoRepository;
import tech.bacuri.mecadolivre.repository.UsuarioRepository;
import tech.bacuri.mecadolivre.validator.ProibeCaracteristicaComNomeIgualValidator;

import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final CategoriaRepository categoriaRepository;
    private final ProdutoRepository produtoRepository;
    private final UsuarioRepository usuarioRepository;
    private final Uploader uploader;

    @InitBinder("novoProdutoForm")
    public void init(WebDataBinder binder) {
        binder.addValidators(new ProibeCaracteristicaComNomeIgualValidator());
    }

    @PostMapping
    public String novoProduto(@RequestBody @Valid NovoProdutoForm form) {
        Usuario dono = usuarioRepository.getByEmail("teste@teste.com");
        Produto produto = form.toProduto(categoriaRepository, dono);
        return produtoRepository.save(produto).toString();
    }

    @Transactional
    @PostMapping("/{id}/imagens")
    public String adicionarImagens(@PathVariable Long id, @Valid NovasImagensForm form) {

        Usuario dono = usuarioRepository.getByEmail("teste@teste.com");
//        Usuario dono = usuarioRepository.getByEmail("teste1@teste.com");
        Produto produto = produtoRepository.getProdutoById(id);

        if (produto.naoPertenceAo(dono)) throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        Set<String> links = uploader.envia(form.getImagens());
        produto.associaImagens(links);
        produtoRepository.save(produto);
        return links.toString();
    }
}
