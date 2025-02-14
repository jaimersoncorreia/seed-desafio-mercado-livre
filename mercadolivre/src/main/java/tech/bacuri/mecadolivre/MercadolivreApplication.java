package tech.bacuri.mecadolivre;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import tech.bacuri.mecadolivre.entity.sumula.*;
import tech.bacuri.mecadolivre.repository.sumula.*;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class MercadolivreApplication implements CommandLineRunner {

    private final AtiviadeRepository ativiadeRepository;
    private final MemorandoRepository memorandoRepository;
    private final ReuniaoRepository reuniaoRepository;
    private final ParticipanteRepository participanteRepository;
    private final SumulaRepository sumulaRepository;
    private final ReuniaoParticipanteRepository reuniaoParticipanteRepository;

    public static void main(String[] args) {
        SpringApplication.run(MercadolivreApplication.class, args);
    }

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        Participante jaimerson = participanteRepository.save(new Participante("Jaimerson"));
        Participante gabriela = participanteRepository.save(new Participante("Gabriela"));

        Atividade atividade = ativiadeRepository.save(new Atividade(2023));
        Memorando memorando = memorandoRepository.save(new Memorando(atividade, "Objeto"));
        Reuniao selecionada = reuniaoRepository.save(new Reuniao(memorando, LocalDateTime.now()));

        ReuniaoParticipante participante1 = reuniaoParticipanteRepository.save(new ReuniaoParticipante(selecionada, jaimerson));
        ReuniaoParticipante participante2 = reuniaoParticipanteRepository.save(new ReuniaoParticipante(selecionada, gabriela));

        AssinaturaSumula assinaturaSumula1 = new AssinaturaSumula(participante1.getParticipante());
        AssinaturaSumula assinaturaSumula2 = new AssinaturaSumula(participante2.getParticipante());

        Sumula su = sumulaRepository.save(Sumula.criarSumulaInicial(selecionada, "redesenhando a súmulo", assinaturaSumula1, assinaturaSumula2));

        su.assinar(gabriela);
    }
}
