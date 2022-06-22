package ch.bzz.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Class look up tings in the properties file
 *
 * @author Kevin
 * @version 1.0
 * @since 18.06.2022
 */
public class ConfigReader {
    /**
     * reads an attribute of the config file
     *
     * @param attribute in the config
     * @return attribute-value
     */
    public static String readConfig(String attribute) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("settings.properties"));
            while (reader.ready()) {
                String line = reader.readLine();
                if (line.split("=")[0].equals(attribute)) return line.split("=")[1];
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
