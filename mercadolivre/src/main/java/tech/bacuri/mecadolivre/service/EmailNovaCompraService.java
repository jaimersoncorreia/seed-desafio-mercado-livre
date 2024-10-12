package tech.bacuri.mecadolivre.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import tech.bacuri.mecadolivre.entity.Compra;
import tech.bacuri.mecadolivre.infra.EmailsService;
import tech.bacuri.mecadolivre.interfaces.EventoCompraSucesso;

@RequiredArgsConstructor
@Service
public class EmailNovaCompraService implements EventoCompraSucesso {
    private final EmailsService emailsService;

    @Override
    public void processa(Compra compra) {
        Assert.isTrue(compra.processadaComSucesso(), "Opa, compra não concluída com sucesso");
        emailsService.novaCompra(compra);
    }
}
