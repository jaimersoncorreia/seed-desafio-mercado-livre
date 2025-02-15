package tech.bacuri.mecadolivre.dto.documento;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tech.bacuri.mecadolivre.entity.documento.AssinaturaParticipante;
import tech.bacuri.mecadolivre.entity.documento.Documento;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(onConstructor_ = @Deprecated)
public class NovoDocumentoForm {
    @NotBlank
    private String texto;

    @Size(min = 1)
    private final List<ParticipantesForm> participantes = new ArrayList<>();


    public Documento toEntity() {
        List<AssinaturaParticipante> participantes = this.participantes.stream()
                .map(ParticipantesForm::toEntity)
                .collect(Collectors.toList());

        return new Documento(this.texto, participantes);
    }
}
