package tech.bacuri.mecadolivre.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import tech.bacuri.mecadolivre.entity.ImagemProduto;
import tech.bacuri.mecadolivre.entity.Opiniao;
import tech.bacuri.mecadolivre.entity.Pergunta;
import tech.bacuri.mecadolivre.entity.Produto;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

@Getter
public class DetalheProdutoView {

    @NotBlank
    @Length(max = 1000)
    private String descricao;

    @NotBlank
    private String nome;

    @NotNull
    @Positive
    private BigDecimal preco;

    private Set<DetalheProdutoCaracteristica> caracteristicas = new HashSet<>();

    private Set<String> linksImagens;

    private SortedSet<String> perguntas;

    private Set<Map<String, String>> opinioes;

    private Double mediaNotas;

    public DetalheProdutoView(Produto produto) {
        this.descricao = produto.getDescricao();
        this.nome = produto.getNome();
        this.preco = produto.getValor();
        this.caracteristicas.addAll(produto.mapeiaCaracteristicas(DetalheProdutoCaracteristica::new));
        this.linksImagens = produto.mapeiaImagens(ImagemProduto::getLink);
        this.perguntas = produto.mapeiaPerguntas(Pergunta::getTitulo);
        this.opinioes = produto.mapeiaOpinioes(DetalheProdutoView::construirOpiniaoView);
        this.mediaNotas = produto.mapeiaOpinioes(Opiniao::getNota).stream()
                .mapToInt(nota -> nota)
                .average()
                .orElse(0.0);
    }

    private static Map<String, String> construirOpiniaoView(Opiniao opiniao) {
        return Map.of("titulo", opiniao.getTitulo(), "descricao", opiniao.getDescricao());
    }
}
