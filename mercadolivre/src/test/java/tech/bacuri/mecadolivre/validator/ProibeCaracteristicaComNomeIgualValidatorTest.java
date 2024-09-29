package tech.bacuri.mecadolivre.validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.validation.BeanPropertyBindingResult;
import tech.bacuri.mecadolivre.dto.NovaCaracteristicaForm;
import tech.bacuri.mecadolivre.dto.NovoProdutoForm;

import java.util.List;
import java.util.stream.Stream;

class ProibeCaracteristicaComNomeIgualValidatorTest {

    public static Stream<Arguments> gerador() {
        return Stream.of(
                Arguments.of(false, List.of(new NovaCaracteristicaForm("key", "value"))),
                Arguments.of(true, List.of(new NovaCaracteristicaForm("key", "value"), new NovaCaracteristicaForm("key", "value")))
        );
    }

    @DisplayName("não aceita produto com característica igual")
    @ParameterizedTest
    @MethodSource("gerador")
    void validate(boolean resultadoEsperado, List<NovaCaracteristicaForm> formList) {
        NovoProdutoForm form = new NovoProdutoForm("nome", 10L, "descrição", formList);
        var validator = new ProibeCaracteristicaComNomeIgualValidator();
        var errors = new BeanPropertyBindingResult(form, "teste");

        validator.validate(form, errors);

        Assertions.assertEquals(resultadoEsperado, errors.hasFieldErrors("caracteristicaList"));
    }
}