package tech.bacuri.mecadolivre.dto.mercado;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import tech.bacuri.mecadolivre.entity.mercado.Pergunta;
import tech.bacuri.mecadolivre.entity.mercado.Produto;
import tech.bacuri.mecadolivre.entity.mercado.Usuario;

import java.time.LocalDateTime;

@Getter
public class NovaPerguntaForm {
    @NotBlank
    private String titulo;

    public Pergunta toPergunta(Produto produto, Usuario interessado) {
        return new Pergunta(titulo, interessado, produto, LocalDateTime.now());
    }
}
