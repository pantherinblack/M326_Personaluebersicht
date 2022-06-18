package ch.bzz.util;

import java.util.List;

public class StringListCompare {
    public static boolean stringContains(List<String> list, String object) {
        for (String obj : list) {
            if (object.equals(obj)) return true;
        }
        return false;
    }
}
