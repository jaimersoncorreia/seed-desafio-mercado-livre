package tech.bacuri.mecadolivre.repository;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.repository.CrudRepository;
import tech.bacuri.mecadolivre.entity.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, String> {
    boolean existsByEmail(@NotBlank @Email String email);
}
