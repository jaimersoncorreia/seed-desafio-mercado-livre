package tech.bacuri.mecadolivre;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import tech.bacuri.mecadolivre.entity.documento.AssinaturaParticipante;
import tech.bacuri.mecadolivre.entity.documento.Documento;
import tech.bacuri.mecadolivre.entity.sumula.*;
import tech.bacuri.mecadolivre.repository.documento.DocumentoRepository;
import tech.bacuri.mecadolivre.repository.sumula.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class MercadolivreApplication implements CommandLineRunner {

    private final AtividadeRepository atividadeRepository;
    private final MemorandoRepository memorandoRepository;
    private final ReuniaoRepository reuniaoRepository;
    private final ParticipanteRepository participanteRepository;
    private final SumulaRepository sumulaRepository;
    private final ReuniaoParticipanteRepository reuniaoParticipanteRepository;
    private final RejeicaoRepository rejeicaoRepository;
    private final DocumentoRepository documentoRepository;

    public static void main(String[] args) {
        SpringApplication.run(MercadolivreApplication.class, args);
    }

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        /*
        sumula();
        documento();
         */
        preparacaoSumulaController();

    }

    private void documento() {
        AssinaturaParticipante jaimerson = new AssinaturaParticipante("00000000001", "Jaimerson");
        AssinaturaParticipante gabriela = new AssinaturaParticipante("00000000002", "Gabriela");
        Documento documentoSalvo = documentoRepository.save(new Documento("texto", List.of(jaimerson, gabriela)));
        documentoSalvo.assinar("00000000001");
    }

    private void sumula() {
        Participante jaimerson = participanteRepository.save(new Participante("Jaimerson", "12345678900"));
        Participante gabriela = participanteRepository.save(new Participante("Gabriela", "12345678901"));

        Atividade atividade = atividadeRepository.save(new Atividade(2023));
        Memorando memorando = memorandoRepository.save(new Memorando(atividade, "Objeto"));
        Reuniao selecionada = reuniaoRepository.save(new Reuniao(memorando, LocalDateTime.now()));

        ReuniaoParticipante participante1 = reuniaoParticipanteRepository.save(new ReuniaoParticipante(selecionada, jaimerson));
        ReuniaoParticipante participante2 = reuniaoParticipanteRepository.save(new ReuniaoParticipante(selecionada, gabriela));

        AssinaturaSumula assinaturaSumula1 = new AssinaturaSumula(participante1.getParticipante());
        AssinaturaSumula assinaturaSumula2 = new AssinaturaSumula(participante2.getParticipante());

        AssinaturaJson assinaturaJson1 = new AssinaturaJson(jaimerson.getNome(), jaimerson.getCpf());
        AssinaturaJson assinaturaJson2 = new AssinaturaJson(gabriela.getNome(), gabriela.getCpf());

        List<AssinaturaJson> assinaturasJson = Arrays.asList(assinaturaJson1, assinaturaJson2);

        Sumula sumula = sumulaRepository.save(Sumula.criarSumulaInicial(selecionada, "redesenhando a súmula", assinaturasJson, List.of(assinaturaSumula1, assinaturaSumula2)));
        sumula.assinar(gabriela.getCpf());
        sumula.assinar(jaimerson.getCpf());

        Rejeicao rejeicao = rejeicaoRepository.save(new Rejeicao(jaimerson, sumula, "esse redesenhando a súmula"));
        rejeicao.assinar();

        Sumula reelaboracao = sumulaRepository.save(Sumula.criarSumulaRejeicao(sumula));
        reelaboracao.assinar(gabriela.getCpf());
        reelaboracao.assinar(jaimerson.getCpf());
    }

    private void preparacaoSumulaController() {
        Participante jaimerson = participanteRepository.save(new Participante("Jaimerson", "00000000001"));
        Participante gabriela = participanteRepository.save(new Participante("Gabriela", "00000000002"));
        Participante sofia = participanteRepository.save(new Participante("Sofia", "00000000003"));
        Participante rebeca = participanteRepository.save(new Participante("Rebeca", "00000000004"));

        Atividade atividade = atividadeRepository.save(new Atividade(2023));
        Memorando memorando = memorandoRepository.save(new Memorando(atividade, "Objeto"));
        Reuniao selecionada = reuniaoRepository.save(new Reuniao(memorando, LocalDateTime.now()));

        ReuniaoParticipante participante1 = reuniaoParticipanteRepository.save(new ReuniaoParticipante(selecionada, jaimerson));
        ReuniaoParticipante participante2 = reuniaoParticipanteRepository.save(new ReuniaoParticipante(selecionada, gabriela));
        ReuniaoParticipante participante3 = reuniaoParticipanteRepository.save(new ReuniaoParticipante(selecionada, sofia));
        ReuniaoParticipante participante4 = reuniaoParticipanteRepository.save(new ReuniaoParticipante(selecionada, rebeca));

//        AssinaturaSumula assinaturaSumula1 = new AssinaturaSumula(participante1.getParticipante());
//        AssinaturaSumula assinaturaSumula2 = new AssinaturaSumula(participante2.getParticipante());

//        AssinaturaJson assinaturaJson1 = new AssinaturaJson(jaimerson.getNome(), jaimerson.getCpf());
//        AssinaturaJson assinaturaJson2 = new AssinaturaJson(gabriela.getNome(), gabriela.getCpf());

//        List<AssinaturaJson> assinaturasJson = Arrays.asList(assinaturaJson1, assinaturaJson2);
    }
}
