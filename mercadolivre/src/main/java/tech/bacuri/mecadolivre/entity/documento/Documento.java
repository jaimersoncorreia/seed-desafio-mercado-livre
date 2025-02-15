package tech.bacuri.mecadolivre.entity.documento;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tech.bacuri.mecadolivre.converter.AssinaturaParticipanteConverter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor(onConstructor_ = @Deprecated)
@Getter
@Entity
@SequenceGenerator(name = "documento", allocationSize = 20, sequenceName = "SQ_DOCUMENTO")
public class Documento {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "documento")
    private Long id;

    private String texto;

    @Size(min = 1)
    @Convert(converter = AssinaturaParticipanteConverter.class)
    private final Set<AssinaturaParticipante> assinaturasDocumento = new HashSet<>();

    public Documento(String texto, List<AssinaturaParticipante> participantes) {
        this.texto = texto;
        this.assinaturasDocumento.addAll(participantes);
    }

    public void assinar(String cpf) {
        this.assinaturasDocumento.stream()
                .filter(participante -> participante.ehIgual(cpf))
                .findFirst()
                .ifPresentOrElse(AssinaturaParticipante::assinar, () -> {
                    throw new EntityNotFoundException("participante não encontrado");
                });
    }

    public boolean assinadoPor(String cpf) {
        return this.assinaturasDocumento.stream()
                .filter(participante -> participante.ehIgual(cpf))
                .anyMatch(AssinaturaParticipante::assinado);
    }
}
