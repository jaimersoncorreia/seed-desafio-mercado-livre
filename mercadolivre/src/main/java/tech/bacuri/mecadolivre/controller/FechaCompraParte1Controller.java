package tech.bacuri.mecadolivre.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import tech.bacuri.mecadolivre.dto.NovaCompraForm;
import tech.bacuri.mecadolivre.entity.Compra;
import tech.bacuri.mecadolivre.infra.EmailsService;
import tech.bacuri.mecadolivre.repository.CompraRepository;
import tech.bacuri.mecadolivre.repository.ProdutoRepository;
import tech.bacuri.mecadolivre.repository.UsuarioRepository;


@RequiredArgsConstructor
@RestController
public class FechaCompraParte1Controller {

    //1
    private final ProdutoRepository produtoRepository;
    //1
    private final UsuarioRepository usuarioRepository;
    //1
    private final CompraRepository compraRepository;
    private final EmailsService emailsService;

    @Transactional
    @PostMapping("/compras")
    //1
    public ResponseEntity<?> cria(@RequestBody @Valid NovaCompraForm form, UriComponentsBuilder uriComponentsBuilder) throws BindException {

        //1
        var produtoASercomprado = produtoRepository.getProdutoById(form.getIdProduto());
        var quantidade = form.getQuantidade();
        var abateu = produtoASercomprado.abataEstoque(quantidade);

        //1
        if (abateu) {
            var comprador = usuarioRepository.getByEmail("consumidor@bacuri.tech");
            //1
            var gateway = form.getGateway();
            //1
            var novaCompra = compraRepository.save(new Compra(produtoASercomprado, quantidade, comprador, gateway));
            emailsService.novaCompra(novaCompra);

            return ResponseEntity.ok(novaCompra.urlRedirecionamento(uriComponentsBuilder));
        }
        var problemaComEstoque = new BindException(form, "novaCompraForm");
        problemaComEstoque.reject(null, "não foi possível realizar a compra por conta do estoque");

        throw problemaComEstoque;
    }
}
