package tech.bacuri.mecadolivre.outrossistemas;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RankingNovaCompraForm {

    @NotNull
    private Long idCompra;

    @NotNull
    private Long idDonoProduto;
}
