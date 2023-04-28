package org.galaxy.validation.validator;

import org.galaxy.validation.annotation.BankCardNumber;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 银行卡号校验
 */
public class BankCardNumberValidator implements ConstraintValidator<BankCardNumber, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return false;
    }

    @Override
    public void initialize(BankCardNumber constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
}
