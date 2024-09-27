package tech.bacuri.mecadolivre.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;

/**
 * Representa uma senha limpa no sistema
 */
public class SenhaLimpa {
    @NotBlank
    @Size(min = 6, message = "senha não pode ter menos de 6 caracteres")
    private String senha;

    public SenhaLimpa(@NotBlank @Length(min = 6) String senha) {
        Assert.hasLength(senha, "senha não pode ser em branco");
        Assert.isTrue(senha.length() >= 6, "senha tem que ter no mínimo 6 caracteres");
        this.senha = senha;
    }

    public @NotBlank String encoder() {
        return new BCryptPasswordEncoder().encode(senha);
    }

}
