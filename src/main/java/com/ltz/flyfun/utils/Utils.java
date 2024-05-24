package com.ltz.flyfun.utils;

import java.util.List;

public class Utils {
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean isNullOrEmpty(Object obj) {
        if (obj == null)
            return true;
        if (obj instanceof List<?>)
            return ((List<?>) obj).isEmpty();
        return false;
    }
}
