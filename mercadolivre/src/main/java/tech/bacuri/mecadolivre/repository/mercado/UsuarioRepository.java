package tech.bacuri.mecadolivre.repository.mercado;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.repository.CrudRepository;
import tech.bacuri.mecadolivre.entity.mercado.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, String> {
    boolean existsByEmail(@NotBlank @Email String email);

    Usuario getByEmail(@NotBlank @Email String email);
}
