package tech.bacuri.mecadolivre.entity.sumula;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@NoArgsConstructor(onConstructor_ = @Deprecated)
@Getter
public class AssinaturaJson {

    private String nome;

    private String cpf;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime tsAssinatura;

    public AssinaturaJson(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }

    public AssinaturaJson(Participante participante) {
        this.nome = participante.getNome();
        this.cpf = participante.getCpf();
    }

    public void assinar() {
        if(assinado()) {
            return;
        }

        this.tsAssinatura = LocalDateTime.now();
    }

    public void removerAssinatura() {
        this.tsAssinatura = null;
    }

    public AssinaturaJson copia() {
        return new AssinaturaJson(this.nome, this.cpf);
    }

    public boolean ehIgual(String cpf) {
        return Objects.equals(this.cpf, cpf);
    }

    public boolean assinado() {
        return Objects.nonNull(this.tsAssinatura);
    }
}
