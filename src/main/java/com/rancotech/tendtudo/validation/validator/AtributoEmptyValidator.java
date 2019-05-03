package com.rancotech.tendtudo.validation.validator;

import com.rancotech.tendtudo.validation.AtributoEmpty;
import org.apache.commons.beanutils.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AtributoEmptyValidator implements ConstraintValidator<AtributoEmpty, Object> {

    private String atributo;
    private String id;

    @Override
    public void initialize(AtributoEmpty constraintAnnotation) {
        this.atributo = constraintAnnotation.atributo();
        this.id = constraintAnnotation.id();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        boolean valido = false;

        try {
            Object valorAtributo = BeanUtils.getProperty(object, this.atributo);
            Object valorId = BeanUtils.getProperty(object, this.id);

            if (valorId != null && valorAtributo == null) {
                valido = true;
            } else {

            }
        } catch (Exception e) {
            throw new RuntimeException("Erro recuperando valores dos atributos", e);
        }

        if (!valido) {
            context.disableDefaultConstraintViolation();
            String mensagem = context.getDefaultConstraintMessageTemplate();
            ConstraintValidatorContext.ConstraintViolationBuilder violationBuilder = context.buildConstraintViolationWithTemplate(mensagem);
            violationBuilder.addPropertyNode(atributo).addConstraintViolation();
        }

        return valido;
    }

}
