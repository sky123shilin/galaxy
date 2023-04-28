package org.galaxy.validation.annotation;

import org.galaxy.validation.validator.BankCardNumberValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(
        validatedBy = {BankCardNumberValidator.class}
)
public @interface BankCardNumber {

    String message() default "{validate.BankCardNo.failed}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
