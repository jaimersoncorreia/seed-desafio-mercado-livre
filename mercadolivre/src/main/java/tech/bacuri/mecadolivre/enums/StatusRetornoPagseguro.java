package tech.bacuri.mecadolivre.enums;

import java.util.Objects;

public enum StatusRetornoPagseguro {
    SUCESSO, ERRO;

    public StatusTransacao normaliza() {
        return Objects.equals(this, SUCESSO) ? StatusTransacao.SUCESSO : StatusTransacao.ERRO;
    }
}
