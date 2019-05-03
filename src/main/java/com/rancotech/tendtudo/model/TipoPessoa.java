package com.rancotech.tendtudo.model;

import com.rancotech.tendtudo.model.validation.group.CnpjGroup;
import com.rancotech.tendtudo.model.validation.group.CpfGroup;

import javax.validation.constraints.NotEmpty;

public enum TipoPessoa {

    FISICA("Física", "CPF", "000.000.000-00", CpfGroup.class) {
        @Override
        public String formatar(String cpfOuCnpj) {
            return !cpfOuCnpj.isEmpty() ? cpfOuCnpj.replaceAll("(\\d{3})(\\d{3})(\\d{3})", "$1.$2.$3-") : null;
        }
    },
    JURIDICA("Jurídica", "CNPJ", "00.000.000/0000-00", CnpjGroup.class) {
        @Override
        public String formatar(String cpfOuCnpj) {
            return !cpfOuCnpj.isEmpty() ? cpfOuCnpj.replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{4})", "$1.$2.$3/$4-") : null;
        }
    };

    private String descricao;
    private String documento;
    private String mascara;
    private Class<?> grupo;

    TipoPessoa(String descricao, String documento, String mascara, Class<?> grupo) {
        this.descricao = descricao;
        this.documento = documento;
        this.mascara = mascara;
        this.grupo = grupo;
    }

    public abstract String formatar(String cpfOuCnpj);

    public String getDescricao() {
        return descricao;
    }

    public String getDocumento() {
        return documento;
    }

    public Class<?> getGrupo() {
        return grupo;
    }

    public String getMascara() {
        return mascara;
    }

    public static String removerFormatacao(String cpfOuCnpj) {
        return cpfOuCnpj.replaceAll("\\.|-|/", "");
    }

}
