package tech.bacuri.mecadolivre.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.util.UriComponentsBuilder;
import tech.bacuri.mecadolivre.entity.Compra;

import java.util.Arrays;
import java.util.Objects;

@Getter
@AllArgsConstructor
public enum GatewayPagamento {
    PAGSEGURO(1, "PagSeguro") {
        @Override
        public String criarUrlRetorno(Compra compra, UriComponentsBuilder uriComponentsBuilder) {
            String url = uriComponentsBuilder.path("/retorno-pagseguro/{id}").buildAndExpand(compra.getId()).toString();
            return "pagseguro.com/%d?redirectUrl=%s".formatted(compra.getId(), url);
        }
    },
    PAYPAL(2, "PayPal") {
        @Override
        public String criarUrlRetorno(Compra compra, UriComponentsBuilder uriComponentsBuilder) {
            var url = uriComponentsBuilder.path("/retorno-paypal/{id}").buildAndExpand(compra.getId()).toString();
            return "paypal.com/%d?redirectUrl=%s".formatted(compra.getId(), url);
        }
    };
    private final Integer id;
    private final String descricao;

    public abstract String criarUrlRetorno(Compra compra, UriComponentsBuilder uriComponentsBuilder);

    public static GatewayPagamento obter(Integer id) {
        return Arrays.stream(GatewayPagamento.values())
                .filter(e -> Objects.equals(e.getId(), id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Valor Ativo inválido"));
    }
}
