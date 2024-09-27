package tech.bacuri.mecadolivre.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.bacuri.mecadolivre.dto.NovoUsuarioForm;
import tech.bacuri.mecadolivre.repository.UsuarioRepository;

@RequiredArgsConstructor
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioRepository usuarioRepository;

    @PostMapping
    public void create(@RequestBody @Valid NovoUsuarioForm form) {
        usuarioRepository.save(form.toUsuario());
    }
}
