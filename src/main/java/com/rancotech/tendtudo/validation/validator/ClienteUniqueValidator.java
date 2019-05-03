package com.rancotech.tendtudo.validation.validator;

import com.rancotech.tendtudo.repository.ClienteRepository;
import com.rancotech.tendtudo.validation.Unique;
import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ClienteUniqueValidator implements ConstraintValidator<Unique,String> {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public void initialize(Unique unique) {
        unique.message();
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (clienteRepository != null && clienteRepository.existsByEmail(email)) {
            return false;
        }
        return true;
    }
}
