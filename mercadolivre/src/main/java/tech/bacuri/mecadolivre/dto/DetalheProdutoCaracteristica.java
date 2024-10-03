package tech.bacuri.mecadolivre.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import tech.bacuri.mecadolivre.entity.CaracteristicaProduto;

@Getter
public class DetalheProdutoCaracteristica {

    @NotBlank
    private String descricao;

    @NotBlank
    private String nome;

    public DetalheProdutoCaracteristica(CaracteristicaProduto c) {
        this.nome = c.getNome();
        this.descricao = c.getDescricao();
    }
}
