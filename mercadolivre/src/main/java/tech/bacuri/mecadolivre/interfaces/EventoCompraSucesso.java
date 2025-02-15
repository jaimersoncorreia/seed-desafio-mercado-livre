package tech.bacuri.mecadolivre.interfaces;

import tech.bacuri.mecadolivre.entity.mercado.Compra;

/**
 * Todo evento relacionado ao sucesso de uma nova compra deve implementar essa interface
 */
public interface EventoCompraSucesso {
    void processa(Compra compra);
}
