package org.unifacisa.model.domain.entities;

public enum Prioridade {
    BAIXA(0, "Baixa"),
    MEDIA(1, "Média"),
    ALTA(2, "Alta");

    private final int codigo;
    private final String descricao;

    Prioridade(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }

    public static Prioridade fromCodigo(int codigo) {
        for (Prioridade prioridade : Prioridade.values()) {
            if (prioridade.getCodigo() == codigo) {
                return prioridade;
            }
        }
        throw new IllegalArgumentException("Código de prioridade inválido: " + codigo);
    }
}
