package com.rancotech.tendtudo.repository.filter;

public class FornecedorFilter {

    private String nome;
    private String nome_fantasia;
    private String cpfOuCnpj;
    private String valorDeBusca;

    public String getValorDeBusca() {
        return valorDeBusca;
    }

    public void setValorDeBusca(String valorDeBusca) {
        this.valorDeBusca = valorDeBusca;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome_fantasia() {
        return nome_fantasia;
    }

    public void setNome_fantasia(String nome_fantasia) {
        this.nome_fantasia = nome_fantasia;
    }

    public String getCpfOuCnpj() {
        return cpfOuCnpj;
    }

    public void setCpfOuCnpj(String cpfOuCnpj) {
        this.cpfOuCnpj = cpfOuCnpj;
    }
}
