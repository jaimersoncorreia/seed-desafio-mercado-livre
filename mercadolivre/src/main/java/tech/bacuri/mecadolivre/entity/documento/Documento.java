package tech.bacuri.mecadolivre.entity.documento;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tech.bacuri.mecadolivre.converter.AssinaturaParticipanteConverter;
import tech.bacuri.mecadolivre.enums.StatusDocumento;

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

    @Setter
    @Enumerated(EnumType.STRING)
    private StatusDocumento status;

    @Size(min = 1)
    @Convert(converter = AssinaturaParticipanteConverter.class)
    private final Set<AssinaturaParticipante> assinaturasDocumento = new HashSet<>();

    public Documento(String texto, List<AssinaturaParticipante> participantes) {
        this.texto = texto;
        this.assinaturasDocumento.addAll(participantes);
        this.status = StatusDocumento.RASCUNHO;
    }

    public void assinar(String cpf) {

        if (!status.equals(StatusDocumento.EM_ASSINATURA)) {
            throw new IllegalStateException("Deveria está no status em assinatura.");
        }

        this.assinaturasDocumento.stream()
                .filter(participante -> participante.ehIgual(cpf))
                .findFirst()
                .ifPresentOrElse(AssinaturaParticipante::assinar, () -> {
                    throw new EntityNotFoundException("participante não encontrado");
                });

        if (todosAssinaram()) {
            status.assinar(this);
        }
    }

    public boolean todosAssinaram() {
        int size = this.assinaturasDocumento.size();
        long count = this.assinaturasDocumento.stream().filter(AssinaturaParticipante::assinado).count();
        return size == count;
    }

    public boolean assinadoPor(String cpf) {
        return this.assinaturasDocumento.stream()
                .filter(participante -> participante.ehIgual(cpf))
                .anyMatch(AssinaturaParticipante::assinado);
    }

    public void colocarEmAssinatura() {
        status.colocarEmAssinatura(this);
    }

    public void rascunhar() {
        status.rascunhar(this);
    }

    public boolean assinaturaEstaEmAndamento() {
        return status.equals(StatusDocumento.EM_ASSINATURA);
    }
}
