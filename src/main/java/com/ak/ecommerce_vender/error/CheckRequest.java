package com.ak.ecommerce_vender.error;
import java.util.regex.Pattern;

public class CheckRequest {
    
private static Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

public static boolean isNumeric(String strNum) {
    if (strNum == null) {
        return false; 
    }
    return pattern.matcher(strNum).matches();
}
}
