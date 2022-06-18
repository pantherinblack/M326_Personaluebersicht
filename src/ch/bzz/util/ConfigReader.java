package ch.bzz.util;

import java.io.*;

public class ConfigReader {
    public static String readConfig(String attribute) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("settings.properties"));
            if (reader.ready()) {
                String line = reader.readLine();
                if (line.split("=")[0].equals(attribute)) return line.split("=")[1];
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
