package tech.bacuri.mecadolivre.enums;

import tech.bacuri.mecadolivre.entity.documento.Documento;

public enum StatusDocumento {

    RASCUNHO {
        @Override
        public void assinar(Documento documento) {
            System.out.println("Documento em rascunho não pode ser assinado diretamente. Envie para assinatura.");
            throw new IllegalStateException("Documento em rascunho não pode ser assinado diretamente. Envie para assinatura.");
        }

        @Override
        public void colocarEmAssinatura(Documento documento) {
            System.out.println("Documento enviado para assinatura.");
            documento.setStatus(EM_ASSINATURA);
        }

        @Override
        public void rascunhar(Documento documento) {
            System.out.println("O documento já está em rascunho.");
            throw new IllegalStateException("O documento já está em rascunho.");
        }
    },
    EM_ASSINATURA {
        @Override
        public void assinar(Documento documento) {
            System.out.println("Documento assinado");
            documento.setStatus(ASSINADO);
        }

        @Override
        public void colocarEmAssinatura(Documento documento) {
            System.out.println("O documento já está em assinatura.");
            throw new IllegalStateException("O documento já está em assinatura.");
        }

        @Override
        public void rascunhar(Documento documento) {
            System.out.println("Documento rejeitado na assinatura. Voltando para rascunho.");
            documento.setStatus(RASCUNHO);
        }
    },
    ASSINADO {
        @Override
        public void assinar(Documento documento) {
            System.out.println("O documento já está assinado.");
            throw new IllegalStateException("O documento já está assinado.");
        }

        @Override
        public void colocarEmAssinatura(Documento documento) {
            System.out.println("Documento assinado não pode voltar para em assinatura.");
            throw new IllegalStateException("Documento assinado não pode voltar para em assinatura.");
        }

        @Override
        public void rascunhar(Documento documento) {
            System.out.println("Documento assinado não pode voltar para rascunho.");
            throw new IllegalStateException("Documento assinado não pode voltar para rascunho.");
        }
    };

    public abstract void assinar(Documento documento);

    public abstract void colocarEmAssinatura(Documento documento);

    public abstract void rascunhar(Documento documento);
}
