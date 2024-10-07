package tech.bacuri.mecadolivre.dto;

import tech.bacuri.mecadolivre.entity.Opiniao;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Opinioes {
    private final Set<Opiniao> opinioes;

    public Opinioes(Set<Opiniao> opinioes) {
        this.opinioes = opinioes;
    }

    public <T> Set<T> mapeiaOpinioes(Function<Opiniao, T> funcaoMapeadora) {
        return this.opinioes.stream().map(funcaoMapeadora).collect(Collectors.toSet());
    }

    public Double media() {
        return this.mapeiaOpinioes(Opiniao::getNota).stream()
                .mapToInt(nota -> nota)
                .average()
                .orElse(0.0);
    }

    public Integer total() {
        return this.opinioes.size();
    }
}
