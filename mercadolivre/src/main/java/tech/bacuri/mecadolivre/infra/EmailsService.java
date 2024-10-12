package tech.bacuri.mecadolivre.infra;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.bacuri.mecadolivre.entity.Compra;
import tech.bacuri.mecadolivre.entity.Pergunta;

@Service
@RequiredArgsConstructor
public class EmailsService {
    private final Mailer mailer;

    public void novaPergunta(Pergunta pergunta) {
//        new RestTemplate().postForEntity("", "", String.class);
        mailer.send("<html>***</html>",
                "Nova pergunta...",
                pergunta.getInteressado().getEmail(),
                "pergunta@bacuri.tech",
                pergunta.getDonoProduto().getEmail());
    }

    public void novaCompra(Compra novaCompra) {
        mailer.send("<html>***</html>",
                "Nova compra...",
                novaCompra.getComprador().getEmail(),
                "compra@bacuri.tech",
                novaCompra.getDonoProduto().getEmail());
    }
}
