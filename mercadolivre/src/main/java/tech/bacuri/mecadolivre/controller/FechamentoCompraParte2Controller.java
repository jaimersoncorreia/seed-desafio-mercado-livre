package tech.bacuri.mecadolivre.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.bacuri.mecadolivre.dto.RetornoPagseguroForm;
import tech.bacuri.mecadolivre.dto.RetornoPaypalForm;
import tech.bacuri.mecadolivre.entity.Compra;
import tech.bacuri.mecadolivre.interfaces.RetornoGatewayPagamento;
import tech.bacuri.mecadolivre.repository.CompraRepository;

@RequiredArgsConstructor
@RestController
public class FechamentoCompraParte2Controller {
    private final CompraRepository compraRepository;

    @Transactional
    @PostMapping("/retorno-pagseguro/{idCompra}")
    public ResponseEntity<?> processamentoPagSeguro(@PathVariable Long idCompra, @Valid RetornoPagseguroForm form) {
        return ResponseEntity.ok(processa(idCompra, form));
    }

    @Transactional
    @PostMapping("/retorno-paypal/{idCompra}")
    public ResponseEntity<?> processamentoPagPal(@PathVariable Long idCompra, @Valid RetornoPaypalForm form) {
        return ResponseEntity.ok(processa(idCompra, form));
    }

    private Compra processa(Long idCompra, RetornoGatewayPagamento form) {
        var compra = compraRepository.getCompraById(idCompra);
        compra.adicionaTransacao(form);
        return compraRepository.save(compra);
    }

}
