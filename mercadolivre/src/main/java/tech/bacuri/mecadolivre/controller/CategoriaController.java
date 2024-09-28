package tech.bacuri.mecadolivre.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.bacuri.mecadolivre.dto.NovaCategoriaForm;
import tech.bacuri.mecadolivre.entity.Categoria;
import tech.bacuri.mecadolivre.repository.CategoriaRepository;

@RequiredArgsConstructor
@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaRepository categoriaRepository;

    @PostMapping
    @Transactional
    public String novaCategoria(@RequestBody @Valid NovaCategoriaForm form) {
        Categoria categoria = form.toCategoria(categoriaRepository);
        return categoriaRepository.save(categoria).toString();
    }
}
