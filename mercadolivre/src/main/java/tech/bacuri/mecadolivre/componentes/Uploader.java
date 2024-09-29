package tech.bacuri.mecadolivre.componentes;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface Uploader {
    Set<String> envia(@Size(min = 1) @NotNull List<MultipartFile> imagens);
}
