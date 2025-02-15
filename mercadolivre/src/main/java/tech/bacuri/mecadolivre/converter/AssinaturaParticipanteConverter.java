package tech.bacuri.mecadolivre.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import tech.bacuri.mecadolivre.entity.documento.AssinaturaParticipante;

import java.util.Objects;

@Converter(autoApply = true)
public class AssinaturaParticipanteConverter implements AttributeConverter<AssinaturaParticipante, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(AssinaturaParticipante attribute) {
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
    public AssinaturaParticipante convertToEntityAttribute(String json) {
        if (Objects.isNull(json) || json.isEmpty()) {
            return null;
        }

        try {
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.readValue(json, AssinaturaParticipante.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("erro ao converter json assinatura para objeto");
        }

    }
}
