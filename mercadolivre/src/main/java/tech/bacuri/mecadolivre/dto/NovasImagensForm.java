package tech.bacuri.mecadolivre.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class NovasImagensForm {

    @Setter
    @Getter
    @Size(min = 1)
    @NotNull
    private List<MultipartFile> imagens = new ArrayList<>();
}
