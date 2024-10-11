package tech.bacuri.mecadolivre.interfaces;

import tech.bacuri.mecadolivre.entity.Compra;
import tech.bacuri.mecadolivre.entity.Transacao;

public interface RetornoGatewayPagamento {
    /**
     * @param compra
     * @return uma nova transação em função do gateway específico
     */
    Transacao toTransacao(Compra compra);
}
