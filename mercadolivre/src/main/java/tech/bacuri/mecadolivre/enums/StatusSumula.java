package tech.bacuri.mecadolivre.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusSumula {
    RASCUNHO(1, "Rascunho"),
    TEMPORARIO(2, "Temporário no X"),
    EM_ASSINATURA(3, "Em processo de assinatura"),
    CONTESTADA(4, "Contestada"),
    APROVADA(5, "Aprovada");

    private final Integer id;
    private final String descricao;
}
