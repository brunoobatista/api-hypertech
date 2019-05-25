package com.rancotech.tendtudo.validation.validator;

import com.rancotech.tendtudo.model.enumerated.TipoPessoa;
import com.rancotech.tendtudo.repository.ClienteRepository;
import com.rancotech.tendtudo.validation.CpfCnpjUnique;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CpfCnpjUniqueValidator implements ConstraintValidator<CpfCnpjUnique, Object> {

    private String cpfCnpj;
    private String id;

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public void initialize(CpfCnpjUnique unique) {
        unique.message();
        this.cpfCnpj = unique.cpfCnpj();
        this.id = unique.id();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        try {
            Object cpfCnpj = TipoPessoa.removerFormatacao(BeanUtils.getProperty(object, this.cpfCnpj));
            Object id = BeanUtils.getProperty(object, this.id);


        } catch (Exception e) {
            throw new RuntimeException("Erro recuperando valores dos atributos", e);
        }

        return true;
    }
}
