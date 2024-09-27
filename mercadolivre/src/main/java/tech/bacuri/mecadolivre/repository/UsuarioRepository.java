package tech.bacuri.mecadolivre.repository;

import org.springframework.data.repository.CrudRepository;
import tech.bacuri.mecadolivre.entity.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, String> {
}
