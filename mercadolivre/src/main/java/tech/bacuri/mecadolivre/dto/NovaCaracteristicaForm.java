package tech.bacuri.mecadolivre.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.lang.NonNull;
import tech.bacuri.mecadolivre.entity.CaracteristicaProduto;
import tech.bacuri.mecadolivre.entity.Produto;

@ToString
@RequiredArgsConstructor
public class NovaCaracteristicaForm {

    @Getter
    @NonNull
    @NotBlank
    private String nome;

    @NonNull
    @NotBlank
    private String descricao;

    public CaracteristicaProduto toCaracteristica(@NotNull @Valid Produto produto) {
        return new CaracteristicaProduto(nome, descricao, produto);
    }
}
