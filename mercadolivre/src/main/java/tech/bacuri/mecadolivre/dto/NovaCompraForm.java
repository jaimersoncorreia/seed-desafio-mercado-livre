package tech.bacuri.mecadolivre.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import tech.bacuri.mecadolivre.enums.GatewayPagamento;

@Getter
@AllArgsConstructor
public class NovaCompraForm {
    @Positive
    private Integer quantidade;

    @NotNull
    private Long idProduto;

    @NotNull
    private GatewayPagamento gateway;
}
