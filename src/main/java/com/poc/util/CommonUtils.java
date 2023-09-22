package com.poc.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CommonUtils {

    public static String masked(String val){

        return val.replaceAll("\\.", "*");
    }

}
