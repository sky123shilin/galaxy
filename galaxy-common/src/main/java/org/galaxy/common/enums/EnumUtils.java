package org.galaxy.common.enums;

public class EnumUtils {

    private EnumUtils() {}

    public static <T extends Enum<T>> T findEnumInsensitiveCase(Class<T> enumType, String name) {
        Enum[] arr = enumType.getEnumConstants();

        for (Enum var : arr) {
            T constant = (T) var;
            if (constant.name().compareToIgnoreCase(name) == 0) {
                return constant;
            }
        }
        throw new IllegalArgumentException("No enum constant " + enumType.getCanonicalName() + "." + name);
    }
}
