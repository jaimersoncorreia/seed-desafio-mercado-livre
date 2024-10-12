package tech.bacuri.mecadolivre.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

class NovoProdutoFormTest {

    public static Stream<Arguments> gerador() {
        return Stream.of(
                Arguments.of(0, List.of()),
                Arguments.of(0, List.of(new NovaCaracteristicaForm("key4", "value4"))),
                Arguments.of(0, List.of(new NovaCaracteristicaForm("key3", "value3"), new NovaCaracteristicaForm("key4", "value4"))),
                Arguments.of(1, List.of(new NovaCaracteristicaForm("key", "value"), new NovaCaracteristicaForm("key", "value")))
        );
    }

    @DisplayName("um produto não pode ser criado com menos de 3 características")
    @ParameterizedTest
    @MethodSource("gerador")
    void buscaCaracteristicasRepetidas(int esperado, List<NovaCaracteristicaForm> novaCaracteristicaFormList) {
        NovoProdutoForm form = new NovoProdutoForm("nome", 10, "descrição", novaCaracteristicaFormList);
        Assertions.assertEquals(esperado, form.buscaCaracteristicasRepetidas().size());
    }
}