package tech.bacuri.mecadolivre.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.Assert;
import tech.bacuri.mecadolivre.dto.NovaCaracteristicaForm;
import tech.bacuri.mecadolivre.dto.Opinioes;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@ToString
@NoArgsConstructor(onConstructor_ = @Deprecated)
@Entity
@SequenceGenerator(name = "produto", allocationSize = 1, sequenceName = "SQ_PRODUTO")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "produto")
    private Long id;

    @NotBlank
    @Getter
    private String nome;

    @Getter
    @NotNull
    @Positive
    private BigDecimal valor;

    @NotNull
    @Positive
    private Long quantidade;

    @Getter
    @NotBlank
    @Length(max = 1000)
    private String descricao;

    @NotNull
    @ManyToOne
    private Categoria categoria;

    @Getter
    @NotNull
    @ManyToOne
    private Usuario dono;

    @NotNull
    @OneToMany(mappedBy = "produto", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<CaracteristicaProduto> caracteristicas = new HashSet<>();

    @OneToMany(mappedBy = "produto", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set<ImagemProduto> imagens = new HashSet<>();

    @OneToMany(mappedBy = "produto", fetch = FetchType.EAGER)
    private SortedSet<Pergunta> perguntas = new TreeSet<>();

    @OneToMany(mappedBy = "produto", fetch = FetchType.EAGER)
    private Set<Opiniao> opinioes = new HashSet<>();

    public Produto(@NotBlank String nome,
                   @Positive BigDecimal valor,
                   @Positive Long quantidade,
                   @NotBlank @Length(max = 1000) String descricao,
                   Categoria categoria,
                   Usuario dono,
                   @Size(min = 3) @Valid List<NovaCaracteristicaForm> caracteristicaList) {
        this.nome = nome;
        this.valor = valor;
        this.quantidade = quantidade;
        this.descricao = descricao;
        this.categoria = categoria;
        this.dono = dono;
        this.caracteristicas.addAll(obterNovaCaracteristicasList(caracteristicaList));

        Assert.isTrue(this.caracteristicas.size() >= 3, "todo produto precisa ter no mínimo 3 ou mais características");
    }

    private Set<CaracteristicaProduto> obterNovaCaracteristicasList(List<NovaCaracteristicaForm> caracteristicaList) {
        return caracteristicaList.stream()
                .map(novaCaracteristica -> novaCaracteristica.toCaracteristica(this))
                .collect(Collectors.toSet());
    }

    public void associaImagens(Set<String> links) {
        var imagens = links.stream()
                .map(link -> new ImagemProduto(this, link))
                .collect(Collectors.toSet());

        this.imagens.addAll(imagens);
    }

    public boolean pertenceAo(Usuario dono) {
        return Objects.equals(this.dono.getId(), dono.getId());
    }

    public boolean naoPertenceAo(Usuario dono) {
        return !pertenceAo(dono);
    }

    public <T> Set<T> mapeiaCaracteristicas(Function<CaracteristicaProduto, T> funcaoMapeadora) {
        return this.caracteristicas.stream().map(funcaoMapeadora).collect(Collectors.toSet());
    }

    public <T> Set<T> mapeiaImagens(Function<ImagemProduto, T> funcaoMapeadora) {
        return this.imagens.stream().map(funcaoMapeadora).collect(Collectors.toSet());
    }

    public <T extends Comparable<T>> SortedSet<T> mapeiaPerguntas(Function<Pergunta, T> funcaoMapeadora) {
        return this.perguntas.stream().map(funcaoMapeadora).collect(Collectors.toCollection(TreeSet::new));
    }

    public Opinioes getOpinioes() {
        return new Opinioes(this.opinioes);
    }
}
