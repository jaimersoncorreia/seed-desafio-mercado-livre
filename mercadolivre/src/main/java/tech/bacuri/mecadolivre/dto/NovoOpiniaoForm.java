package tech.bacuri.mecadolivre.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import tech.bacuri.mecadolivre.entity.Opiniao;
import tech.bacuri.mecadolivre.entity.Produto;
import tech.bacuri.mecadolivre.entity.Usuario;

@RequiredArgsConstructor
public class NovoOpiniaoForm {

    @Getter
    @Min(1)
    @Max(5)
    @NonNull
    private Integer nota;

    @NotBlank
    @NonNull
    private String titulo;

    @NotBlank
    @NonNull
    @Size(max = 500)
    private String descricao;

    public Opiniao toOpiniao(Produto produto, Usuario consumidor) {
        return new Opiniao(nota, titulo, descricao, produto, consumidor);
    }
}
