package tech.bacuri.mecadolivre.infra;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class FakerMailer implements Mailer {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void send(String body, String subject, String nameFrom, String from, String to) {
        logger.info("\nEnviando email {}", "#".repeat(150));
        logger.info("\nbody: {}\nsubject: {}\nnameFrom: {}\nfrom: {}\nto: {}", body, subject, nameFrom, from, to);
    }
}
