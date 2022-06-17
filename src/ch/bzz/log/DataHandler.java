package ch.bzz.log;

import ch.bzz.company.Company;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import javax.swing.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DataHandler {

    private static Company company;
    private static DataHandler instance;

    private DataHandler() {
        loadApp();
    }

    public void saveApp(Company company) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        FileOutputStream fileOutputStream = null;
        Writer writer = null;

        try {
            fileOutputStream = new FileOutputStream("data.json");
            writer = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));

            objectWriter.writeValue(writer, company);
        } catch (IOException ignored) {}
    }

    public Company loadApp() {
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get("data.json"));
            ObjectMapper objectMapper = new ObjectMapper();
            company = objectMapper.readValue(jsonData, Company.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (company==null) {
            company = new Company("Empty");
        }
        return company;
    }

    public static DataHandler getInstance() {
        if (instance != null) {
            instance = new DataHandler();
        }
        return instance;
    }
}