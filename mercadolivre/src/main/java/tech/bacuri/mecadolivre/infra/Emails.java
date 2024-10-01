package tech.bacuri.mecadolivre.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.bacuri.mecadolivre.entity.Pergunta;

@Service
@RequiredArgsConstructor
public class Emails {
    private final Mailer mailer;

    public void novaPergunta(Pergunta pergunta) {
//        new RestTemplate().postForEntity("", "", String.class);
        mailer.send("<html>***</html>",
                "Nova pergunta...",
                pergunta.getInteressado().getEmail(),
                "pergunta@bacuri.tech",
                pergunta.getDonoProduto().getEmail());
    }
}
