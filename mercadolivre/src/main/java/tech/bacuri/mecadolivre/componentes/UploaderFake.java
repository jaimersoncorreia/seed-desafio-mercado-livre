package tech.bacuri.mecadolivre.componentes;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Primary
public class UploaderFake implements Uploader {
    @Override
    public Set<String> envia(@Size(min = 1) @NotNull List<MultipartFile> imagens) {
        return imagens.stream()
                .map(MultipartFile::getOriginalFilename)
                .map(s -> "https://localhost:8080/mercadolivre/" + s)
                .collect(Collectors.toSet());
    }
}
