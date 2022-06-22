package ch.bzz.util;

import ch.bzz.model.company.Company;
import ch.bzz.model.company.Department;
import ch.bzz.model.employees.HRPerson;
import ch.bzz.model.employees.Person;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Class to Load the application state or save it.
 *
 * @author Kevin
 * @version 1.0
 * @since 18.06.2022
 */
public class DataHandler {

    public static final Path path = Paths.get(ConfigReader.readConfig("dataPath") + "\\data.json");
    private static Company company;
    private static DataHandler instance;

    /**
     * Constructor generates paths and files, if not existent
     */
    private DataHandler() {
        if (!Files.exists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * gets the instance of the singleton
     *
     * @return instance
     */
    public static DataHandler getInstance() {
        if (instance == null) {
            instance = new DataHandler();
        }
        return instance;
    }

    /**
     * Saves the application to a json file
     */
    public void saveApp() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        FileOutputStream fileOutputStream = null;
        Writer writer = null;

        try {
            fileOutputStream = new FileOutputStream(path.toString());
            writer = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));

            objectWriter.writeValue(writer, company);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * loads the application.
     *
     * @return company (containing all Models)
     */
    public Company loadApp() {
        try {
            if (!Files.exists(path)) Files.createFile(path);
            byte[] jsonData = Files.readAllBytes(path);
            ObjectMapper objectMapper = new ObjectMapper();
            company = objectMapper.readValue(jsonData, Company.class);
        } catch (IOException e) {
            company = new Company("Company-name");
            company.addTeam("Team1");
            company.addFunction("Function1");
            company.addDepartment(new Department("Department1"));
            HRPerson hr = new HRPerson("Max", "Mustermann", null,1);
            hr.setPwd("");
            company.getDepartment(0).addMember(hr);
        }

        //TODO
        return company;
    }
}
