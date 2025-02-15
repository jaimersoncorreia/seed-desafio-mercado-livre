package tech.bacuri.mecadolivre.entity.documento;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@NoArgsConstructor(onConstructor_ = @Deprecated)
@Getter
@EqualsAndHashCode
public class AssinaturaParticipante {
    @NotBlank
    private String cpf;

    @NotBlank
    private String nome;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime tsAssinatura;

    public AssinaturaParticipante(String cpf, String nome) {
        this.cpf = cpf;
        this.nome = nome;
    }

    public void assinar() {
        this.tsAssinatura = LocalDateTime.now();
    }

    public boolean ehIgual(String cpf) {
        return Objects.equals(this.cpf, cpf);
    }

    public boolean assinado() {
        return Objects.nonNull(this.tsAssinatura);
    }
}
