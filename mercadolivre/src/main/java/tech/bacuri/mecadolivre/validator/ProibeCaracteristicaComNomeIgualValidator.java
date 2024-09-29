package tech.bacuri.mecadolivre.validator;

import org.springframework.lang.NonNull;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import tech.bacuri.mecadolivre.dto.NovoProdutoForm;

import java.util.List;

public class ProibeCaracteristicaComNomeIgualValidator implements Validator {
    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return NovoProdutoForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NonNull Object target, Errors errors) {
        if (errors.hasErrors()) return;

        NovoProdutoForm form = (NovoProdutoForm) target;
        Assert.notNull(form, "novo produto não pode está nulo");

        List<String> strings = form.buscaCaracteristicasRepetidas();
        if (!strings.isEmpty())
            errors.rejectValue("caracteristicaList", null, "você tem as seguintes características repetidas " + strings);
    }
}
