package org.galaxy.validation.annotation;

import org.galaxy.validation.validator.CheckWithEnumNameValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(
        validatedBy = {CheckWithEnumNameValidator.class}
)
@Documented
public @interface CheckWithEnumName {
    String message() default "传值不合规范";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<?> enumClass();
}