package tech.bacuri.mecadolivre.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import tech.bacuri.mecadolivre.entity.Usuario;

@RequiredArgsConstructor
@NoArgsConstructor(onConstructor_ = @Deprecated)
@Data
public class NovoUsuarioForm {
    @NonNull
    @NotBlank
    @Email
    private String email;

    @NonNull
    @NotBlank
    @Size(min = 6, message = "senha não pode ter menos de 6 caracteres")
    private String senha;

    public Usuario toUsuario() {
        return new Usuario(email, new SenhaLimpa(senha));
    }

}
