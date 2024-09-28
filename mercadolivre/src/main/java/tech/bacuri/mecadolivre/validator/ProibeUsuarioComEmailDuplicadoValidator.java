package tech.bacuri.mecadolivre.validator;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import tech.bacuri.mecadolivre.dto.NovoUsuarioForm;

import java.util.List;

@Component
public class ProibeUsuarioComEmailDuplicadoValidator implements Validator {
    @PersistenceContext
    private EntityManager manager;

    @Override
    public boolean supports(Class<?> clazz) {
        return NovoUsuarioForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) return;

        NovoUsuarioForm form = (NovoUsuarioForm) target;
        List<?> list = manager.createQuery("select 1 from Usuario u where u.email = :email")
                .setParameter("email", form.getEmail())
                .getResultList();

        if (!list.isEmpty()) {
            errors.rejectValue("email", null, "já existe esse email no sistema");
        }
    }
}
