package tech.bacuri.mecadolivre.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import tech.bacuri.mecadolivre.enums.GatewayPagamento;

import java.util.Objects;

@Converter(autoApply = true)
public class GatewayPagamentoConverter implements AttributeConverter<GatewayPagamento, Integer> {

    @Override
    public Integer convertToDatabaseColumn(GatewayPagamento attribute) {
        return Objects.isNull(attribute) ? null : attribute.getId();
    }

    @Override
    public GatewayPagamento convertToEntityAttribute(Integer id) {
        return Objects.isNull(id) ? null : GatewayPagamento.obter(id);
    }
}
