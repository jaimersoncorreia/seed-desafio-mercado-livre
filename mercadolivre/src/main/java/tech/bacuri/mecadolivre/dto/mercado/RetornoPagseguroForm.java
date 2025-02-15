package tech.bacuri.mecadolivre.dto.mercado;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import tech.bacuri.mecadolivre.entity.mercado.Compra;
import tech.bacuri.mecadolivre.entity.mercado.Transacao;
import tech.bacuri.mecadolivre.enums.StatusRetornoPagseguro;
import tech.bacuri.mecadolivre.interfaces.RetornoGatewayPagamento;

@Getter
@AllArgsConstructor
public class RetornoPagseguroForm implements RetornoGatewayPagamento {

    @NotBlank
    private String idTransacao;

    @NotNull
    private StatusRetornoPagseguro status;

    @Override
    public Transacao toTransacao(Compra compra) {
        return new Transacao(this.status.normaliza(), this.idTransacao, compra);
    }
}
