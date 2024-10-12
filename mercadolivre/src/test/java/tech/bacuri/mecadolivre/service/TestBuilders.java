package tech.bacuri.mecadolivre.service;

import tech.bacuri.mecadolivre.dto.NovaCaracteristicaForm;
import tech.bacuri.mecadolivre.dto.SenhaLimpa;
import tech.bacuri.mecadolivre.entity.*;
import tech.bacuri.mecadolivre.enums.GatewayPagamento;
import tech.bacuri.mecadolivre.enums.StatusTransacao;
import tech.bacuri.mecadolivre.interfaces.RetornoGatewayPagamento;

import java.math.BigDecimal;
import java.util.List;

public class TestBuilders {

    public static class CompraFixture {
        private Compra compra;

        public CompraFixture(Compra compra) {
            this.compra = compra;
        }

        public Compra concluida() {
            RetornoGatewayPagamento sucesso = compra -> new Transacao(StatusTransacao.SUCESSO, "1", compra);
            this.compra.adicionaTransacao(sucesso);
            return this.compra;
        }

        public Compra queNaoFoiPagaComSucesso() {
            RetornoGatewayPagamento falho = compra -> new Transacao(StatusTransacao.ERRO, "1", compra);
            this.compra.adicionaTransacao(falho);
            return this.compra;
        }
    }

    public static CompraFixture novaCompra(){
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
        return new CompraFixture(new Compra(produto, 50, comprador, gatewayPagamento));
    }
}
