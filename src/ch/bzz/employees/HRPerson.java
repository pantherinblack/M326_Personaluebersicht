package ch.bzz.employees;

import ch.bzz.log.LogBook;
import ch.bzz.log.UserAction;

import javax.swing.*;

public class HRPerson extends Person {
    private int modus;
    private String pwd;

    public HRPerson(String fName, String lName, ImageIcon photo, int modus) {
        super(fName, lName, photo);
    }

    public void writeLogEntry(int action, Person person) {
        UserAction ua = new UserAction(this, person, action);
        LogBook.getLogBookInstance().addEntry(ua.getEntry());
    }

    public int getModus() {
        return modus;
    }

    public void setModus(int modus) {
        this.modus = modus;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
