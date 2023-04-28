package org.galaxy.validation.annotation;

import org.galaxy.validation.validator.IdCardValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(
        validatedBy = {IdCardValidator.class}
)
public @interface IdCard {

    String message() default "{validate.IdCardNo.failed}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
