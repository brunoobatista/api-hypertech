package com.rancotech.tendtudo.model.enumerated;

import com.rancotech.tendtudo.model.validation.group.CnpjGroup;
import com.rancotech.tendtudo.model.validation.group.CpfGroup;

import javax.validation.constraints.NotEmpty;

public enum TipoPessoa {

    FISICA("Física", "CPF", "000.000.000-00", CpfGroup.class) {
        @Override
        public String formatar(String cpfOuCnpj) {
            if (cpfOuCnpj != null) {
                return !cpfOuCnpj.isEmpty() ? cpfOuCnpj.replaceAll("(\\d{3})(\\d{3})(\\d{3})", "$1.$2.$3-") : null;
            }
            return "";
        }
    },
    JURIDICA("Jurídica", "CNPJ", "00.000.000/0000-00", CnpjGroup.class) {
        @Override
        public String formatar(String cpfOuCnpj) {
            if (cpfOuCnpj != null) {
                return !cpfOuCnpj.isEmpty() ? cpfOuCnpj.replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{4})", "$1.$2.$3/$4-") : null;
            }
            return "";
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

    public static String formatarCpf(String cpf) {
        return TipoPessoa.FISICA.formatar(cpf);
    }

    public static String formatarCnpj(String cnpj) {
        return TipoPessoa.JURIDICA.formatar(cnpj);
    }

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
        if (cpfOuCnpj != null) {
            return cpfOuCnpj.replaceAll("\\.|-|/", "");
        }
        return "";
    }

}
