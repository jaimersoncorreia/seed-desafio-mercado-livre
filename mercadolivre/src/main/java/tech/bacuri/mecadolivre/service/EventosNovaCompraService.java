package tech.bacuri.mecadolivre.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.bacuri.mecadolivre.entity.Compra;
import tech.bacuri.mecadolivre.interfaces.EventoCompraSucesso;

import java.util.Set;

@RequiredArgsConstructor
@Service
public class EventosNovaCompraService {
    //1
    private final Set<EventoCompraSucesso> eventosCompraSucesso;
    //1
    public void processa(Compra compra) {
        //1
        if (compra.processadaComSucesso()) {
            //1
            eventosCompraSucesso.forEach(evento -> evento.processa(compra));
        }
    }
}
