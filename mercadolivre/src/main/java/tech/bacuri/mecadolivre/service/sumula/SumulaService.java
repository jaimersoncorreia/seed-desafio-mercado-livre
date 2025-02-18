package tech.bacuri.mecadolivre.service.sumula;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import tech.bacuri.mecadolivre.dto.sumula.NovaRejeicaoForm;
import tech.bacuri.mecadolivre.dto.sumula.NovaSumulaForm;
import tech.bacuri.mecadolivre.entity.sumula.*;
import tech.bacuri.mecadolivre.repository.sumula.RejeicaoRepository;
import tech.bacuri.mecadolivre.repository.sumula.ReuniaoRepository;
import tech.bacuri.mecadolivre.repository.sumula.SumulaRepository;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SumulaService {
    private final ReuniaoRepository reuniaoRepository;
    private final SumulaRepository sumulaRepository;
    private final RejeicaoRepository rejeicaoRepository;

    @Transactional
    public Sumula criarSumulaInicial(@Valid NovaSumulaForm form) {
        Reuniao reuniao = reuniaoRepository.findById(form.getIdRuniao()).orElseThrow();

        if (sumulaRepository.existsSumulasByReuniao(reuniao)) {
            throw new IllegalStateException("já existe súmula inicial para essa reunião");
        }

        String pauta = form.getPauta();
        Set<ReuniaoParticipante> participantes = reuniao.getParticipantes();

        List<AssinaturaSumula> sumulas = participantes.stream()
                .map(reuniaoParticipante -> new AssinaturaSumula(reuniaoParticipante.getParticipante()))
                .collect(Collectors.toList());

        List<AssinaturaJson> assinaturasJson = participantes.stream()
                .map(reuniaoParticipante -> new AssinaturaJson(reuniaoParticipante.getParticipante()))
                .collect(Collectors.toList());

        Sumula sumula = Sumula.criarSumulaInicial(reuniao, pauta, assinaturasJson, sumulas);
        return sumulaRepository.save(sumula);
    }

    @Transactional
    public Sumula rejeitarSumula(Sumula sumula, String cpf, @Valid NovaRejeicaoForm form) {
        Assert.notNull(sumula, "sumula não deveria está nula");
        Assert.isTrue(sumula.naoAprovada(), "sumula [" + sumula.getPauta().toUpperCase() + "] aprovada");
        rejeicaoRepository.save(Rejeicao.criar(sumula, cpf, form.getPauta(), UUID.randomUUID().toString()));
        Assert.isTrue(sumula.pendenteAssinatura(), "não pode rejeitar súmula que está em situação [" + sumula.getStatus() + "]");
        sumula.contestar();
        sumulaRepository.save(sumula);
        return sumulaRepository.save(Sumula.criarSumulaRascunho(sumula));
    }
}
