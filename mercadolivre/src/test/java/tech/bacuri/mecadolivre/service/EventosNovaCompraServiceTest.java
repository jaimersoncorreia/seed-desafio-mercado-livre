package tech.bacuri.mecadolivre.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import tech.bacuri.mecadolivre.entity.Compra;
import tech.bacuri.mecadolivre.interfaces.EventoCompraSucesso;

import java.util.Set;

class EventosNovaCompraServiceTest {

    @Test
    @DisplayName("deveria disparar eventos de sucesso")
    void teste1() {
        Compra compraConcluida = TestBuilders.novaCompra().concluida();
        EventoCompraSucesso eventoSucesso = Mockito.mock(EventoCompraSucesso.class);
        Set<EventoCompraSucesso> eventos = Set.of(eventoSucesso);
        EventosNovaCompraService eventosNovaCompra = new EventosNovaCompraService(eventos);

        eventosNovaCompra.processa(compraConcluida);

        Mockito.verify(eventoSucesso).processa(compraConcluida);
    }

    @Test
    @DisplayName("deveria disparar eventos de falha")
    void teste2() {
        Compra compraConcluida = TestBuilders.novaCompra().queNaoFoiPagaComSucesso();
        EventoCompraSucesso eventoSucesso = Mockito.mock(EventoCompraSucesso.class);
        Set<EventoCompraSucesso> eventos = Set.of(eventoSucesso);
        EventosNovaCompraService eventosNovaCompra = new EventosNovaCompraService(eventos);

        eventosNovaCompra.processa(compraConcluida);

        Mockito.verify(eventoSucesso, Mockito.never()).processa(compraConcluida);
    }
}