package tech.bacuri.mecadolivre.dto.sumula;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(onConstructor_ = @Deprecated)
@AllArgsConstructor
public class NovaRejeicaoForm {
    @NotBlank
    private String pauta;
}
