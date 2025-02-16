package tech.bacuri.mecadolivre.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import tech.bacuri.mecadolivre.entity.sumula.AssinaturaJson;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Converter(autoApply = true)
public class AssinaturasConverter implements AttributeConverter<Set<AssinaturaJson>, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Set<AssinaturaJson> attribute) {
        if (Objects.isNull(attribute)) {
            return "[]";
        }

        try {
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("erro ao converter assinatura para json" + e);
        }
    }

    @Override
    public Set<AssinaturaJson> convertToEntityAttribute(String json) {
        if (Objects.isNull(json) || json.isEmpty()) {
            return new HashSet<>();
        }

        try {
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.readValue(json, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException("erro ao converter json assinatura para objeto");
        }
    }
}
