package tech.bacuri.mecadolivre.dto.sumula;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(onConstructor_ = @Deprecated)
public class NovaSumulaForm {
    private Long idRuniao;
    private String pauta;
}
