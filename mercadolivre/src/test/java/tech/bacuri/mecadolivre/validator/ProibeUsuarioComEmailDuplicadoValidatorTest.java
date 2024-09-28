package tech.bacuri.mecadolivre.validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import tech.bacuri.mecadolivre.dto.NovoUsuarioForm;
import tech.bacuri.mecadolivre.repository.UsuarioRepository;

import java.util.stream.Stream;

class ProibeUsuarioComEmailDuplicadoValidatorTest {

    public static Stream<Arguments> geradorTeste1() {
        return Stream.of(Arguments.of(true, true), Arguments.of(false, false));
    }

    @DisplayName("deveria lidar com email duplicado")
    @ParameterizedTest
    @MethodSource("geradorTeste1")
    void teste1(boolean esperado1, boolean esperado2) {
        UsuarioRepository usuarioRepository = Mockito.mock(UsuarioRepository.class);
        ProibeUsuarioComEmailDuplicadoValidator validator = new ProibeUsuarioComEmailDuplicadoValidator(usuarioRepository);
        Object target = new NovoUsuarioForm("teste1@gmail.com", "123456");
        Errors errors = new BeanPropertyBindingResult(target, "teste");
        Mockito.when(usuarioRepository.existsByEmail("teste1@gmail.com")).thenReturn(esperado2);

        validator.validate(target, errors);

        Assertions.assertEquals(esperado2, errors.hasFieldErrors("email"));
        Assertions.assertEquals(esperado2, esperado1);
    }

}