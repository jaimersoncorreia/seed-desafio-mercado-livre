package tech.bacuri.mecadolivre.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import tech.bacuri.mecadolivre.dto.NovoUsuarioForm;
import tech.bacuri.mecadolivre.repository.UsuarioRepository;

@RequiredArgsConstructor
@Component
public class ProibeUsuarioComEmailDuplicadoValidator implements Validator {

    private final UsuarioRepository usuarioRepository;

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return NovoUsuarioForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NonNull Object target, Errors errors) {
        if (errors.hasErrors()) return;
        var form = (NovoUsuarioForm) target;
        if (usuarioRepository.existsByEmail(form.getEmail()))
            errors.rejectValue("email", null, "já existe esse email no sistema");
    }
}
