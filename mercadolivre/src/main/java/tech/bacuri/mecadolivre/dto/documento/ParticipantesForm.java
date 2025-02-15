package tech.bacuri.mecadolivre.dto.documento;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tech.bacuri.mecadolivre.entity.documento.AssinaturaParticipante;

@Getter
@NoArgsConstructor(onConstructor_ = @Deprecated)
@AllArgsConstructor
public class ParticipantesForm {
    @NotBlank
    private String cpf;

    @NotBlank
    private String nome;

    public AssinaturaParticipante toEntity() {
        return new AssinaturaParticipante(this.getCpf(), this.getNome());
    }
}
