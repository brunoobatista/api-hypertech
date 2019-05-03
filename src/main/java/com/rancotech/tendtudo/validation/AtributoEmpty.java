package com.rancotech.tendtudo.validation;

import com.rancotech.tendtudo.validation.validator.AtributoEmptyValidator;

import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { AtributoEmptyValidator.class })
public @interface AtributoEmpty {

    @OverridesAttribute(constraint = Pattern.class, name = "message")
    String message() default "Atributos não conferem";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String atributo();
    String id();

}
