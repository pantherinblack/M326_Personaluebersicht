package ch.bzz.model.employees;

import ch.bzz.log.LogBook;
import ch.bzz.log.UserAction;

import java.nio.file.Path;

/**
 * Model for an HR-Person
 *
 * @author Kevin
 * @version 1.0
 * @since 18.05.2022
 */
public class HRPerson extends Person {
    private int modus;
    private String pwd;

    /**
     * generates a HR-Person
     *
     * @param fName firstname
     * @param lName lastname
     * @param photo photo (ImageIcon)
     * @param modus mode
     */
    public HRPerson(String fName, String lName, Path photo, int modus) {
        super(fName, lName, photo);
        this.modus = modus;
    }

    /**
     * writes a logEntry using the logbook
     *
     * @param action performed
     * @param person modified
     */
    public void writeLogEntry(int action, Person person) {
        UserAction ua = new UserAction(this, person, action);
        LogBook.getLogBookInstance().addEntry(ua.getEntry());
    }

    /**
     * gets the mode
     *
     * @return mode
     */
    public int getModus() {
        return modus;
    }

    /**
     * sets the mode
     *
     * @param modus to be set
     */
    public void setModus(int modus) {
        this.modus = modus;
    }

    /**
     * gets the password
     *
     * @return password
     */
    public String getPwd() {
        return pwd;
    }

    /**
     * sets the password
     *
     * @param pwd (password)
     */
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    /**
     * changes an HRPerson to a Person
     *
     * @return Person
     */
    public Person changeToPerson() {
        return new Person(getFirstName(), getLastName(), getPhoto());
    }
}
