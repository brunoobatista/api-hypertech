package com.rancotech.tendtudo.repository.filter;

public class ProdutoFilter {

    private String nome;
    private Long tipoId;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getTipoId() {
        return tipoId;
    }

    public void setTipoId(Long tipoId) {
        this.tipoId = tipoId;
    }
}
