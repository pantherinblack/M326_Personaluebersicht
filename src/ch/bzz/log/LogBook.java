package ch.bzz.log;

import java.io.*;
import java.util.Vector;

/**
 * Class to manage the Logbook. Is able to Read and Write.
 * @author Kevin
 * @since 18.05.2022
 * @version 1.0
 *
 */
public class LogBook {
    private Vector<String> entries = new Vector<>();
    private static LogBook instance;
    private BufferedWriter writer;
    private BufferedReader reader;
    private boolean fileWritingEnabled;

    /**
     * Creates a Logbook. Is private because of usage of the singleton pattern.
     */
    private LogBook() {
        File f = new File("logboook.log");
        try {
            if (!f.createNewFile()) {
                reader = new BufferedReader(new FileReader(f));
                readFile();
                reader.close();
            }
            writer = new BufferedWriter(new FileWriter(f, true));
            fileWritingEnabled = true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates an instance if not existing.
     * - Singleton pattern.
     * @return instance.
     */
    public static LogBook getLogBookInstance() {
        if (instance == null) instance = new LogBook();
        return instance;
    }


    /**
     * Adds an entry and saves it to the File.
     * @param entry to be added.
     */
    public void addEntry(String entry) {
        if (fileWritingEnabled) {
            entries.add(entry);
            writeFile(entry);
        }
    }

    /**
     * Gives the requested entry in form of a String.
     * @param index of the entry stored.
     * @return String of the entry.
     */
    public String getEntry(int index) {
        return entries.get(index);
    }

    /**
     * Gives back the number of entries stored.
     * @return int size.
     */
    public int getSize() {
        return entries.size();
    }

    /**
     * Closes the writer and deletes the current instance.
     */
    public void logBookClose() {
        try {
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        instance = null;
    }

    /**
     * Prints all logs saved to the console.
     */
    public void printLog() {
        for (String entry : entries) {
            System.out.println(entry);
        }
    }

    /**
     * writes the entry to a file (appending).
     * @param entry to be appended.
     */
    private void writeFile(String entry) {
        try {
            writer.append(entry);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Reads all entrys in form of stings and stores it in the attribute.
     * @throws IOException
     */
    private void readFile() throws IOException{
        while (reader.ready()) {
            entries.add(reader.readLine());
        }
    }
}