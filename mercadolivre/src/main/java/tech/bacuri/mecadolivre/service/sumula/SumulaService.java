package tech.bacuri.mecadolivre.service.sumula;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.bacuri.mecadolivre.dto.sumula.NovaSumulaForm;
import tech.bacuri.mecadolivre.entity.sumula.*;
import tech.bacuri.mecadolivre.repository.sumula.ReuniaoRepository;
import tech.bacuri.mecadolivre.repository.sumula.SumulaRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SumulaService {
    private final ReuniaoRepository reuniaoRepository;
    private final SumulaRepository sumulaRepository;

    @Transactional
    public Sumula criarSumulaInicial(@Valid NovaSumulaForm form) {
        Reuniao reuniao = reuniaoRepository.findById(form.getIdRuniao()).orElseThrow();
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
}
