package tech.bacuri.mecadolivre.enums;

import org.springframework.web.util.UriComponentsBuilder;
import tech.bacuri.mecadolivre.entity.Compra;

public enum GatewayPagamento {
    PAGSEGURO {
        @Override
        public String criarUrlRetorno(Compra compra, UriComponentsBuilder uriComponentsBuilder) {
            String url = uriComponentsBuilder.path("/retorno-pagseguro/{id}").buildAndExpand(compra.getId()).toString();
            return "pagseguro.com/%d?redirectUrl=%s".formatted(compra.getId(), url);
        }
    },
    PAYPAL {
        @Override
        public String criarUrlRetorno(Compra compra, UriComponentsBuilder uriComponentsBuilder) {
            var url = uriComponentsBuilder.path("/retorno-paypal/{id}").buildAndExpand(compra.getId()).toString();
            return "paypal.com/%d?redirectUrl=%s".formatted(compra.getId(), url);
        }
    };

    public abstract String criarUrlRetorno(Compra compra, UriComponentsBuilder uriComponentsBuilder);
}
