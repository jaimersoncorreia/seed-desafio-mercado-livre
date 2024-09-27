package tech.bacuri.mecadolivre.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class SenhaLimpaTest {

    @DisplayName("só aceita senha com 6 ou mais caracteres")
    @ParameterizedTest
    @CsvSource({"123456", "123456", "123456789123456"})
    void teste1(String senhaLimpa) {
        new SenhaLimpa(senhaLimpa);
    }

    @DisplayName("não aceita senha com menos de 6 caracteres")
    @ParameterizedTest
    @CsvSource({"1235", "126"})
    void teste2(String senhaLimpa) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new SenhaLimpa(senhaLimpa));
    }
}