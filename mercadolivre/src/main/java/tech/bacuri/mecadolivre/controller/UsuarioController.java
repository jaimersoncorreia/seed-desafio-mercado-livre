package tech.bacuri.mecadolivre.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import tech.bacuri.mecadolivre.dto.NovoUsuarioForm;
import tech.bacuri.mecadolivre.repository.UsuarioRepository;
import tech.bacuri.mecadolivre.validator.ProibeUsuarioComEmailDuplicadoValidator;

@RequiredArgsConstructor
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioRepository usuarioRepository;
    private final ProibeUsuarioComEmailDuplicadoValidator proibeUsuarioComEmailDuplicadoValidator;

    @InitBinder
    public void init(WebDataBinder binder) {
        binder.addValidators(proibeUsuarioComEmailDuplicadoValidator);
    }

    @PostMapping
    public void create(@RequestBody @Valid NovoUsuarioForm form) {
        usuarioRepository.save(form.toUsuario());
    }
}
