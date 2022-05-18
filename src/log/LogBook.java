package log;

import java.io.*;
import java.util.Vector;

public class LogBook {
    private Vector<String> entries;
    private static LogBook instance;
    private BufferedWriter writer;
    private BufferedReader reader;
    private boolean fileWritingEnabled = false;

    private LogBook() {
        File f = new File("logbuch.log");
        try {
            if (f.createNewFile()==false) {
                reader = new BufferedReader(new FileReader(f));
                readFile();
                reader.close();
            }
            writer = new BufferedWriter(new FileWriter(f));
            fileWritingEnabled = true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static LogBook getLogBookInstance() {
        if (instance == null) instance = new LogBook();
        return instance;
    }

    public void addEntry(String  entry) {
        if (fileWritingEnabled) {
            entries.add(entry);
            writeFile(entry);

        }
    }

    public String getEntry(int index) {
        return entries.get(index);
    }

    public int getSize() {
        return entries.size();
    }

    public void logBookClose() {
        try {
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void printLog() {

    }

    private void writeFile(String entry) {
        try {
            writer.append(entry);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void readFile() throws IOException{
        while (reader.ready()) {
            entries.add(reader.readLine());
        }
    }
}