package tech.bacuri.mecadolivre.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import tech.bacuri.mecadolivre.entity.Pergunta;
import tech.bacuri.mecadolivre.entity.Produto;
import tech.bacuri.mecadolivre.entity.Usuario;

import java.time.LocalDateTime;

@Getter
public class NovaPerguntaForm {
    @NotBlank
    private String titulo;

    public Pergunta toPergunta(Produto produto, Usuario interessado) {
        return new Pergunta(titulo, interessado, produto, LocalDateTime.now());
    }
}
