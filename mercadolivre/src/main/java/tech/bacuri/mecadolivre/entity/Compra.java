package tech.bacuri.mecadolivre.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;
import org.springframework.web.util.UriComponentsBuilder;
import tech.bacuri.mecadolivre.converter.GatewayPagamentoConverter;
import tech.bacuri.mecadolivre.enums.GatewayPagamento;
import tech.bacuri.mecadolivre.interfaces.RetornoGatewayPagamento;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor(onConstructor_ = @Deprecated)
@Getter
@Entity
@SequenceGenerator(name = "compraSeq", sequenceName = "SQ_COMPRA", allocationSize = 1)
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "compraSeq")
    private Long id;

    @ManyToOne
    @NotNull
    @Valid
    private Produto produtoASercomprado;

    @Positive
    private Integer quantidade;

    @ManyToOne
    @NotNull
    @Valid
    private Usuario comprador;

    @NotNull
    @Convert(converter = GatewayPagamentoConverter.class)
    private GatewayPagamento gateway;

    @OneToMany(mappedBy = "compra", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set<Transacao> transacoes = new HashSet<>();

    public Compra(Produto produtoASercomprado, Integer quantidade, Usuario comprador, @NotNull GatewayPagamento gateway) {
        this.produtoASercomprado = produtoASercomprado;
        this.quantidade = quantidade;
        this.comprador = comprador;
        this.gateway = gateway;
    }

    public String urlRedirecionamento(UriComponentsBuilder uriComponentsBuilder) {
        return this.gateway.criarUrlRetorno(this, uriComponentsBuilder);
    }

    public Usuario getDonoProduto() {
        return produtoASercomprado.getDono();
    }

    public void adicionaTransacao(@Valid RetornoGatewayPagamento form) {
        var novaTransacao = form.toTransacao(this);
        Assert.isTrue(!this.transacoes.contains(novaTransacao), "Já existe uma transaçao igual a essa processada " + novaTransacao);
        var transacoesConcluidasComSucesso = this.transacoes.stream().filter(Transacao::concluidaComSucesso).collect(Collectors.toSet());
        Assert.isTrue(transacoesConcluidasComSucesso.isEmpty(), "Essa compra já foi concluída com sucesso");
        this.transacoes.add(novaTransacao);
    }
}
