package com.rancotech.tendtudo.model;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class Endereco implements Serializable {

    private static final long serialVersionUID = 1L;

    private String endereco;

    private String numero;

    private String complemento;

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Endereco endereco1 = (Endereco) o;
        return Objects.equals(endereco, endereco1.endereco) &&
                Objects.equals(numero, endereco1.numero) &&
                Objects.equals(complemento, endereco1.complemento);
    }

    @Override
    public int hashCode() {

        return Objects.hash(endereco, numero, complemento);
    }

}
