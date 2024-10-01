package tech.bacuri.mecadolivre.infra;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public interface Mailer {
    /**
     * @param body     corpo do email
     * @param subject  assunto do email
     * @param nameFrom nome para aparecer no provedor de email
     * @param from     email de origem
     * @param to       email de destino
     */
    void send(String body, String subject, @NotBlank @Email String nameFrom, String from, @NotBlank @Email String to);
}
