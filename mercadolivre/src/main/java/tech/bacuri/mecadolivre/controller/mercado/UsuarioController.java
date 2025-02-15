package tech.bacuri.mecadolivre.controller.mercado;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import tech.bacuri.mecadolivre.dto.mercado.NovoUsuarioForm;
import tech.bacuri.mecadolivre.repository.mercado.UsuarioRepository;
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
