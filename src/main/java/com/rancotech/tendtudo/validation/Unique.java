package com.rancotech.tendtudo.validation;

import com.rancotech.tendtudo.validation.validator.ClienteUniqueValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({ElementType.METHOD, ElementType.FIELD})
@Constraint(validatedBy = ClienteUniqueValidator.class)
@Retention(RUNTIME)
public @interface Unique {
    String message();
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}
