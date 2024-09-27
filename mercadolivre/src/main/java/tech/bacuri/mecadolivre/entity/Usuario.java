package tech.bacuri.mecadolivre.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.Assert;
import tech.bacuri.mecadolivre.dto.SenhaLimpa;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor(onConstructor_ = @Deprecated)
@Entity
@SequenceGenerator(sequenceName = "SQ_USUARIO", allocationSize = 1, name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario")
    private Long id;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String senha;

    @Past
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime instanteCriacao;

    public Usuario(String email, SenhaLimpa senhaLimpa) {
        Assert.hasLength(email, "email não pode está branco");
        Assert.notNull(senhaLimpa, "o objeto do tipo senha limpa não pode ser nula");
        this.email = email;
        this.senha = senhaLimpa.encoder();
        this.instanteCriacao = LocalDateTime.now();
    }
}
