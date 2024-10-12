package tech.bacuri.mecadolivre.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import tech.bacuri.mecadolivre.dto.NovaCaracteristicaForm;
import tech.bacuri.mecadolivre.dto.SenhaLimpa;
import tech.bacuri.mecadolivre.enums.GatewayPagamento;
import tech.bacuri.mecadolivre.enums.StatusTransacao;
import tech.bacuri.mecadolivre.interfaces.RetornoGatewayPagamento;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

class CompraTest {

    @Test
    @DisplayName("deveria adicionar uma transação")
    void teste1() {
        Compra novaCompra = novaCompra();
        RetornoGatewayPagamento retornoGatewayPagamento = compra -> new Transacao(StatusTransacao.SUCESSO, "1", compra);
        novaCompra.adicionaTransacao(retornoGatewayPagamento);
    }

    @Test
    @DisplayName("não pode aceitar uma transação igual")
    void teste2() {
        Compra novaCompra = novaCompra();
        RetornoGatewayPagamento retornoGatewayPagamento = compra -> new Transacao(StatusTransacao.ERRO, "1", compra);
        novaCompra.adicionaTransacao(retornoGatewayPagamento);
        RetornoGatewayPagamento retornoGatewayPagamento1 = compra -> new Transacao(StatusTransacao.ERRO, "1", compra);
        Assertions.assertThrows(IllegalStateException.class, () -> novaCompra.adicionaTransacao(retornoGatewayPagamento1));
    }

    @Test
    @DisplayName("não pode aceitar uma transação se a compra já foi concluída com sucesso")
    void teste3() {
        Compra novaCompra = novaCompra();
        RetornoGatewayPagamento retornoGatewayPagamento = compra -> new Transacao(StatusTransacao.SUCESSO, "1", compra);
        novaCompra.adicionaTransacao(retornoGatewayPagamento);
        RetornoGatewayPagamento retornoGatewayPagamento1 = compra -> new Transacao(StatusTransacao.SUCESSO, "2", compra);
        Assertions.assertThrows(IllegalStateException.class, () -> novaCompra.adicionaTransacao(retornoGatewayPagamento1));
    }

    @DisplayName("deveria verificar se a compra foi concluída com sucesso")
    @ParameterizedTest
    @MethodSource("geradorTeste4")
    void teste4(boolean resultadoEsperado, List<RetornoGatewayPagamento> retornos) {
        Compra novaCompra = novaCompra();
        retornos.forEach(novaCompra::adicionaTransacao);
        Assertions.assertEquals(resultadoEsperado, novaCompra.processadaComSucesso());

    }

    public static Stream<Arguments> geradorTeste4() {
        RetornoGatewayPagamento retornoSecesso = compra -> new Transacao(StatusTransacao.SUCESSO, "1", compra);
        RetornoGatewayPagamento retornoFalha = compra -> new Transacao(StatusTransacao.ERRO, "1", compra);
        return Stream.of(
                Arguments.of(true, List.of(retornoSecesso)),
                Arguments.of(false, List.of(retornoFalha)),
                Arguments.of(false, List.of())
        );
    }

    private Compra novaCompra() {
        Categoria categoria = new Categoria("teste");
        Usuario dono = new Usuario("dono@teste.com", new SenhaLimpa("dono@@"));
        var caracteristicas = List.of(
                new NovaCaracteristicaForm("nome", "descricao"),
                new NovaCaracteristicaForm("nome1", "descricao1"),
                new NovaCaracteristicaForm("nome2", "descricao2")
        );
        var produto = new Produto("nome", BigDecimal.TEN, 100, "descrição", categoria, dono, caracteristicas);
        Usuario comprador = new Usuario("comprador@teste.com", new SenhaLimpa("comprador@@"));
        GatewayPagamento gatewayPagamento = GatewayPagamento.PAGSEGURO;
        return new Compra(produto, 50, comprador, gatewayPagamento);
    }
}