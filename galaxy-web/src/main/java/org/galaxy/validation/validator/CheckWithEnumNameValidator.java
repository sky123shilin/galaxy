package org.galaxy.validation.validator;


import org.galaxy.validation.annotation.CheckWithEnumName;

import java.lang.reflect.Method;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckWithEnumNameValidator implements ConstraintValidator<CheckWithEnumName, String> {
    private Class<?> enumClass;

    CheckWithEnumNameValidator() {}

    public void initialize(CheckWithEnumName constraintAnnotation) {
        this.enumClass = constraintAnnotation.enumClass();
    }

    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        try {
            Method method = this.enumClass.getMethod("values");
            Enum[] inter = (Enum[]) method.invoke(null);
            Enum[] var5 = inter;
            int var6 = inter.length;
            for(int var7 = 0; var7 < var6; ++var7) {
                Enum iter = var5[var7];
                if (iter.toString().equals(value)) {
                    return true;
                }
            }
        } catch (Throwable var9) {
            var9.printStackTrace();
        }
        return false;
    }
}
