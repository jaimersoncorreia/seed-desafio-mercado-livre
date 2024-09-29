package tech.bacuri.mecadolivre.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.Assert;
import tech.bacuri.mecadolivre.anotation.ExistsId;
import tech.bacuri.mecadolivre.entity.Categoria;
import tech.bacuri.mecadolivre.entity.Produto;
import tech.bacuri.mecadolivre.entity.Usuario;
import tech.bacuri.mecadolivre.repository.CategoriaRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor(onConstructor_ = @Deprecated)
@ToString
public class NovoProdutoForm {
    @NotBlank
    private String nome;

    @Positive
    private BigDecimal valor;

    @Positive
    private Long quantidade;

    @NotBlank
    @Length(max = 1000)
    private String descricao;

    @NotNull
    @ExistsId(domainClass = Categoria.class, fieldName = "id")
    private Long idCategoria;

    @Size(min = 3)
    @Valid
    private List<NovaCaracteristicaForm> caracteristicaList = new ArrayList<>();

    public NovoProdutoForm(String nome, Long quantidade, String descricao, List<NovaCaracteristicaForm> novaCaracteristicaFormList) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.descricao = descricao;
        this.caracteristicaList.addAll(novaCaracteristicaFormList);
    }

    public Produto toProduto(CategoriaRepository repository, Usuario dono) {
        Categoria categoria = repository.getCategoriaById(idCategoria);
        Assert.notNull(categoria, "categoria não pode está nulo");
        return new Produto(nome, valor, quantidade, descricao, categoria, dono, caracteristicaList);
    }

    public List<String> buscaCaracteristicasRepetidas() {
        Map<String, Integer> contagem = new HashMap<>();

        List<String> lista = new ArrayList<>(caracteristicaList
                .stream()
                .map(NovaCaracteristicaForm::getNome)
                .toList());

        lista.forEach(elemento -> contagem.merge(elemento, 1, Integer::sum));

        return contagem.entrySet()
                .stream()
                .filter(entry -> entry.getValue() > 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
