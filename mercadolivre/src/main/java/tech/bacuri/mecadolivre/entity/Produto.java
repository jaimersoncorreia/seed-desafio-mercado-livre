package tech.bacuri.mecadolivre.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.Assert;
import tech.bacuri.mecadolivre.dto.NovaCaracteristicaForm;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    private String nome;

    @NotNull
    @Positive
    private BigDecimal valor;

    @NotNull
    @Positive
    private Long quantidade;

    @NotBlank
    @Length(max = 1000)
    private String descricao;

    @NotNull
    @ManyToOne
    private Categoria categoria;

    @NotNull
    @ManyToOne
    private Usuario dono;

    @NotNull
    @OneToMany(mappedBy = "produto", cascade = CascadeType.PERSIST)
    private Set<CaracteristicaProduto> caracteristicaProdutoList = new HashSet<>();

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
        Set<CaracteristicaProduto> novasCaracteristicas = caracteristicaList.stream()
                .map(novaCaracteristica -> novaCaracteristica.toCaracteristica(this))
                .collect(Collectors.toSet());
        this.caracteristicaProdutoList.addAll(novasCaracteristicas);

        Assert.isTrue(this.caracteristicaProdutoList.size() >= 3, "todo produto precisa ter no mínimo 3 ou mais características");
    }
}
