package log;

import employees.HRPerson;
import employees.Person;

public class UserAction {
    public static final int CREACTE_PERSON = 0;
    public static final int CHANGE_VALUE = 1;
    public static final int SET_ASSIGNMENT = 2;
    public static final int DELETE_PERSON = 3;
    private String[] actionDescription;
    private String entry;

    public UserAction(HRPerson hrPerson, Person person, int action) {
        entry = hrPerson.getFirstName();
        entry += hrPerson.getLastName();
        if (action >= 0 && action < actionDescription.length) {
            entry += person.getFirstName();
            entry += person.getLastName();
            entry += actionDescription[action];
        } else {
            entry += "unknown action";
        }
    }

    public String getEntry() {
        return entry;
    }
}
