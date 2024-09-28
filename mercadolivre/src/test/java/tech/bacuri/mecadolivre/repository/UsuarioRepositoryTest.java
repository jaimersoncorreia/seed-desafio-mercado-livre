package tech.bacuri.mecadolivre.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import tech.bacuri.mecadolivre.dto.SenhaLimpa;
import tech.bacuri.mecadolivre.entity.Usuario;

import java.util.stream.Stream;

class UsuarioRepositoryTest {

    public static Stream<Arguments> geradorTeste1() {
        return Stream.of(Arguments.of(true), Arguments.of(false));
    }

    public static Stream<Arguments> geradorUsuario() {
        Usuario usuario = new Usuario("usuario@gmail.com", new SenhaLimpa("123456"));
        return Stream.of(Arguments.of(usuario), Arguments.of((Object) null));
    }

    @DisplayName("deveria lidar com email duplicado")
    @ParameterizedTest
    @MethodSource("geradorTeste1")
    void existsByEmail(boolean esperado) {
        UsuarioRepository usuarioRepository = Mockito.mock(UsuarioRepository.class);
        Mockito.when(usuarioRepository.existsByEmail("teste1@gmail.com")).thenReturn(esperado);
        Assertions.assertEquals(esperado, usuarioRepository.existsByEmail("teste1@gmail.com"));
    }

    @DisplayName("o usuário por email")
    @ParameterizedTest
    @MethodSource("geradorUsuario")
    void getByEmail(Usuario usuarioEsperado) {
        UsuarioRepository usuarioRepository = Mockito.mock(UsuarioRepository.class);
        Mockito.when(usuarioRepository.getByEmail("usuario@gmail.com")).thenReturn(usuarioEsperado);
        Assertions.assertEquals(usuarioEsperado, usuarioRepository.getByEmail("usuario@gmail.com"));
    }
}