package ch.bzz.util;

import java.util.List;

/**
 * Class to compare lists and string
 * @author Kevin
 * @since 18.06.2022
 * @version 1.1
 */
public class StringListCompare {
    /**
     * test, if the string is in the String-list
     * @param list of strings
     * @param object to look for
     * @return true -> contains it, false -> does not contain it.
     */
    public static boolean stringContains(List<String> list, String object) {
        for (String obj : list) {
            if (object.equals(obj)) return true;
        }
        return false;
    }
}
