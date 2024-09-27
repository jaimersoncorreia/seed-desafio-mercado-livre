package tech.bacuri.mecadolivre.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import tech.bacuri.mecadolivre.entity.Usuario;

import java.time.LocalDateTime;

@Data
public class NovoUsuarioForm {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, message = "senha não pode ter menos de 6 caracteres")
    private String senha;

    public Usuario toUsuario() {
        return new Usuario(email, encoder(), LocalDateTime.now());
    }

    private String encoder() {
        return new BCryptPasswordEncoder().encode(senha);
    }
}
