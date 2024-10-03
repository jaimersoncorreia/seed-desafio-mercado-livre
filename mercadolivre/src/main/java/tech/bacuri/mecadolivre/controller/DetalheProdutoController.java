package tech.bacuri.mecadolivre.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.bacuri.mecadolivre.dto.DetalheProdutoView;
import tech.bacuri.mecadolivre.entity.Produto;
import tech.bacuri.mecadolivre.repository.ProdutoRepository;

@RequiredArgsConstructor
@RequestMapping("/produtos")
@RestController
public class DetalheProdutoController {

    private final ProdutoRepository produtoRepository;

    @GetMapping("/{id}")
    public DetalheProdutoView detalhe(@PathVariable Long id) {
        Produto produtoEscolhido = produtoRepository.getProdutoById(id);
        return new DetalheProdutoView(produtoEscolhido);
    }
}
