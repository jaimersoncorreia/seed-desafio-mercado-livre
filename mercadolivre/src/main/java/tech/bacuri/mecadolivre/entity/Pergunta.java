package tech.bacuri.mecadolivre.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(onConstructor_ = @Deprecated)
@RequiredArgsConstructor
@Entity
@SequenceGenerator(name = "perguntaSeq", sequenceName = "SQ_PERGUNTA")
public class Pergunta {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "perguntaSeq")
    private Long id;

    @Getter
    @NotBlank
    @NonNull
    private String titulo;

    @Getter
    @NotNull
    @NonNull
    @ManyToOne
    private Usuario interessado;

    @NotNull
    @NonNull
    @ManyToOne
    private Produto produto;

    @NotNull
    @NonNull
    private LocalDateTime instante;

    public Usuario getDonoProduto() {
        return produto.getDono();
    }
}
