package tech.bacuri.mecadolivre.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import tech.bacuri.mecadolivre.entity.sumula.AssinaturaJson;

import java.util.Objects;

@Converter(autoApply = true)
public class AssinaturaConverter implements AttributeConverter<AssinaturaJson, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(AssinaturaJson attribute) {
        if (Objects.isNull(attribute)) {
            return null;
        }

        try {
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("erro ao converter assinatura para json" + e);
        }
    }

    @Override
    public AssinaturaJson convertToEntityAttribute(String json) {
        if (Objects.isNull(json) || json.isEmpty()) {
            return null;
        }

        try {
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.readValue(json, AssinaturaJson.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("erro ao converter json assinatura para objeto");
        }
    }
}
