package tech.bacuri.mecadolivre.service;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import tech.bacuri.mecadolivre.entity.Compra;
import tech.bacuri.mecadolivre.interfaces.EventoCompraSucesso;

import java.util.Map;

@Service
public class NotaFiscalService implements EventoCompraSucesso {
    private static final String URL_BASE = "http://localhost:8080/mercadolivre";

    @Override
    public void processa(Compra compra) {
        Assert.isTrue(compra.processadaComSucesso(), "Opa, compra não concluída com sucesso");

        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> request = Map.of("idCompra", compra.getId(),
                "idComprador", compra.getComprador().getId());
        restTemplate.postForEntity(URL_BASE + "/notas-fiscais", request, String.class);
    }
}
