package tech.bacuri.mecadolivre.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import tech.bacuri.mecadolivre.dto.NovaCaracteristicaForm;
import tech.bacuri.mecadolivre.dto.SenhaLimpa;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

class ProdutoTest {
    @DisplayName("um produto precisa de no mínimo 3 características")
    @ParameterizedTest
    @MethodSource("geradorTeste1")
    void teste1(List<NovaCaracteristicaForm> novaCaracteristicaFormList) {
        Categoria categoria = new Categoria("categoria");
        Usuario dono = new Usuario("email@email.com", new SenhaLimpa("123456"));

        new Produto("nome", BigDecimal.TEN, 10L, "descrição", categoria, dono, novaCaracteristicaFormList);
    }

    @DisplayName("um produto não pode ser criado com menos de 3 características")
    @ParameterizedTest
    @MethodSource("geradorTeste2")
    void teste2(List<NovaCaracteristicaForm> novaCaracteristicaFormList) {
        Categoria categoria = new Categoria("categoria");
        Usuario dono = new Usuario("email@email.com", new SenhaLimpa("123456"));

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Produto("nome", BigDecimal.TEN, 10L, "descrição", categoria, dono, novaCaracteristicaFormList);
        });
    }

    public static Stream<Arguments> geradorTeste1() {
        return Stream.of(
                Arguments.of(List.of(
                        new NovaCaracteristicaForm("key1", "value1"),
                        new NovaCaracteristicaForm("key2", "value2"),
                        new NovaCaracteristicaForm("key3", "value3")
                )),
                Arguments.of(List.of(
                        new NovaCaracteristicaForm("key1", "value1"),
                        new NovaCaracteristicaForm("key2", "value2"),
                        new NovaCaracteristicaForm("key3", "value3"),
                        new NovaCaracteristicaForm("key4", "value4")
                ))
        );
    }

    public static Stream<Arguments> geradorTeste2() {
        return Stream.of(
                Arguments.of(List.of(
                        new NovaCaracteristicaForm("key1", "value1"),
                        new NovaCaracteristicaForm("key3", "value3")
                )),
                Arguments.of(List.of(
                        new NovaCaracteristicaForm("key4", "value4")
                ))
        );
    }
}