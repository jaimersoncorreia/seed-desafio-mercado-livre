package tech.bacuri.mecadolivre.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import tech.bacuri.mecadolivre.entity.Compra;
import tech.bacuri.mecadolivre.entity.Transacao;
import tech.bacuri.mecadolivre.enums.StatusTransacao;
import tech.bacuri.mecadolivre.interfaces.RetornoGatewayPagamento;

@Getter
@AllArgsConstructor
public class RetornoPaypalForm implements RetornoGatewayPagamento {

    @Min(0)
    @Max(1)
    @NotNull
    private Integer status;

    @NotBlank
    private String idTransacao;

    public Transacao toTransacao(Compra compra) {
        var statusNormalizado = this.status == 0 ? StatusTransacao.ERRO : StatusTransacao.SUCESSO;
        return new Transacao(statusNormalizado, this.idTransacao, compra);
    }
}
