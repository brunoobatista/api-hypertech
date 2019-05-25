package com.rancotech.tendtudo.model.enumerated;


public enum StatusVenda {
    ABERTA("Em aberto"),
    FINALIZADA("Finalizado"),
    CANCELADA("Cancelado"),
    ESTORNADA("Estornada")
    ;

    private String descricao;

    StatusVenda(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
