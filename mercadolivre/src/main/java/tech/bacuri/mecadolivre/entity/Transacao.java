package tech.bacuri.mecadolivre.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tech.bacuri.mecadolivre.enums.StatusTransacao;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@NoArgsConstructor(onConstructor_ = @Deprecated)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@SequenceGenerator(name = "transacaoSeq", sequenceName = "SQ_TRANSACAO")
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transacaoSeq")
    private Long id;

    @NotNull
    private StatusTransacao status;

    @EqualsAndHashCode.Include
    @NotBlank
    private String idTransacaoGateway;

    @JsonIgnore
    @NotNull
    @Valid
    @ManyToOne
    private Compra compra;

    @NotNull
    private LocalDateTime instante;

    public Transacao(StatusTransacao status, @NotBlank String idTransacaoGateway, Compra compra) {
        this.status = status;
        this.idTransacaoGateway = idTransacaoGateway;
        this.compra = compra;
        this.instante = LocalDateTime.now();
    }

    public boolean concluidaComSucesso() {
        return Objects.equals(this.status, StatusTransacao.SUCESSO);
    }
}
