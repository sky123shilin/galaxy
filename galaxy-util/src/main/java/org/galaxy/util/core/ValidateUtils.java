package org.galaxy.util.core;

import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 通用性校验工具类
 * 
 * @author Shilin.Qu
 *
 */
public final class ValidateUtils {

    private ValidateUtils(){}

    public static boolean isNumeric(String str){
        if(!StringUtils.hasText(str)) return false;
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNumeric2(String str){
        for(int i=str.length();--i>=0;){
            int chr=str.charAt(i);
            if(chr < 48 || chr > 57)
                return false;
        }
        try {
            int number = Integer.parseInt(str);
            return number > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isEmail(String email) {
        if (email == null)
            return false;
        String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p;
        Matcher m;
        p = Pattern.compile(regEx1);
        m = p.matcher(email);
        return m.matches();
    }

    public static boolean isPhone(String phone) {
        if (phone == null)
            return false;
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (phone.length() != 11) {
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            return m.matches();
        }
    }

    /**
     * 是否是正数
     */
    public static boolean isPositiveNumber(String number) {
        if (number == null)
            return false;
        String regex = "^(?!0+(?:\\.0+)?$)(?:[1-9]\\d*|0)(?:\\.\\d{1,2})?$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(number);
        return m.matches();
    }

    /**
     * 是否是正整数
     */
    public static boolean isPositiveInteger(String number) {
        if (number == null)
            return false;
        String regex = "^[1-9]\\d*$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(number);
        return m.matches();
    }
}
