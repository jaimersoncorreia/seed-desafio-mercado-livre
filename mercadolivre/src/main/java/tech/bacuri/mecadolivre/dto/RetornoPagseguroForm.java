package tech.bacuri.mecadolivre.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import tech.bacuri.mecadolivre.entity.Compra;
import tech.bacuri.mecadolivre.entity.Transacao;
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
