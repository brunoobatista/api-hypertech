package com.rancotech.tendtudo.validation;

import com.rancotech.tendtudo.validation.validator.CpfCnpjUniqueValidator;

import java.lang.annotation.*;
import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;


@Target({ ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CpfCnpjUniqueValidator.class)
public @interface CpfCnpjUnique {

    @OverridesAttribute(constraint = Pattern.class, name = "message")
    String message() default "Atributos não conferem";

    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };

    String cpfCnpj();
    String id();
}
