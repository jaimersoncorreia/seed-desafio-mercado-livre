package tech.bacuri.mecadolivre.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import tech.bacuri.mecadolivre.dto.NovoProdutoForm;
import tech.bacuri.mecadolivre.entity.Produto;
import tech.bacuri.mecadolivre.entity.Usuario;
import tech.bacuri.mecadolivre.repository.CategoriaRepository;
import tech.bacuri.mecadolivre.repository.ProdutoRepository;
import tech.bacuri.mecadolivre.repository.UsuarioRepository;
import tech.bacuri.mecadolivre.validator.ProibeCaracteristicaComNomeIgualValidator;

@RequiredArgsConstructor
@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final CategoriaRepository categoriaRepository;
    private final ProdutoRepository produtoRepository;
    private final UsuarioRepository usuarioRepository;

    @InitBinder
    public void init(WebDataBinder binder) {
        binder.addValidators(new ProibeCaracteristicaComNomeIgualValidator());
    }

    @PostMapping
    public String novoProduto(@RequestBody @Valid NovoProdutoForm form) {
        Usuario dono = usuarioRepository.getByEmail("teste@teste.com");
        Produto produto = form.toProduto(categoriaRepository, dono);
        return produtoRepository.save(produto).toString();
    }
}
