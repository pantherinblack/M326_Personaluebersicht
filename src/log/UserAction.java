package log;

import employees.HRPerson;
import employees.Person;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A model class for storing an entry temporarily
 * @author Kevin
 * @since 18.05.2022
 * @version 1.0
 */
public class UserAction {
    public static final int CREATE_PERSON = 0;
    public static final int CHANGE_VALUE = 1;
    public static final int SET_ASSIGNMENT = 2;
    public static final int DELETE_PERSON = 3;
    private String[] actionDescription = {"Create Person", "Change Value", "Set Assignment", "Delete Person"};
    private String entry;

    /**
     * Constructor, generating the entry with the given information.
     * @param hrPerson doing the action.
     * @param person affected by the action.
     * @param action that is being done.
     */
    public UserAction(HRPerson hrPerson, Person person, int action) {
        entry = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)+": ";
        entry += hrPerson.getFirstName()+" ";
        entry += hrPerson.getLastName()+" -> ";
        if (action >= 0 && action < actionDescription.length) {
            entry += actionDescription[action]+" (";
            entry += person.getFirstName()+" ";
            entry += person.getLastName()+")";
        } else {
            entry += "unknown action";
        }
    }

    /**
     * Gives back the entry in String format.
     * @return entry as String.
     */
    public String getEntry() {
        return entry;
    }
}
