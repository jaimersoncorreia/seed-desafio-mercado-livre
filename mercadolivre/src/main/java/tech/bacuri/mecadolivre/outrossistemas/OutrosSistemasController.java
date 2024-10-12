package tech.bacuri.mecadolivre.outrossistemas;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OutrosSistemasController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping("/notas-fiscais")
    public void criaNota(@Valid @RequestBody NovaCompraNFForm form) throws InterruptedException {
        logger.info("criando nota para compra: {} do comprador: {}", form.getIdCompra(), form.getIdComprador());
        Thread.sleep(1000);
    }

    @PostMapping("/ranking")
    public void ranking(@Valid @RequestBody RankingNovaCompraForm form) throws InterruptedException {
        logger.info("criando ranking para compra: {} do dono do produto: {}", form.getIdCompra(), form.getIdDonoProduto());
        Thread.sleep(1000);
    }
}
