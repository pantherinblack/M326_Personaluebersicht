package ch.bzz.testing;

import ch.bzz.log.LogBook;
import ch.bzz.log.UserAction;
import ch.bzz.model.employees.HRPerson;
import ch.bzz.model.employees.Person;

public class LogTest {

    public static void main(String[] args) {
        HRPerson hrp = new HRPerson("Max", "Muster", null, 0);
        Person pe = new Person("Maila", "Maurer", null);


        hrp.writeLogEntry(UserAction.CREATE_PERSON, pe);
        hrp.writeLogEntry(UserAction.SET_ASSIGNMENT, pe);
        hrp.writeLogEntry(UserAction.CHANGE_VALUE, pe);
        hrp.writeLogEntry(-1, pe);
        hrp.writeLogEntry(UserAction.DELETE_PERSON, pe);

        LogBook.getLogBookInstance().printLog();
    }
}
