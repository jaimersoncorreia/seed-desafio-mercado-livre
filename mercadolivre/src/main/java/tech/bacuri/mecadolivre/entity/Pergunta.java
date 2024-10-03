package tech.bacuri.mecadolivre.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor(onConstructor_ = @Deprecated)
@RequiredArgsConstructor
@Entity
@SequenceGenerator(name = "perguntaSeq", sequenceName = "SQ_PERGUNTA")
public class Pergunta implements Comparable<Pergunta> {
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

    @JsonIgnore
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

    @Override
    public int compareTo(Pergunta o) {
        return this.titulo.compareTo(o.getTitulo());
    }
}
