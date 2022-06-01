package testing;

import employees.HRPerson;
import employees.Person;
import log.LogBook;
import log.UserAction;

import javax.swing.*;

public class LogTest {

    public static void main(String[]args){
        //Ich habe die Testklasse etwas abgeändert, um dem Klasendiagramm zu entsprechen
        HRPerson hrp = new HRPerson("Max", "Muster", new ImageIcon(), 0);
        Person pe = new Person("Maila", "Maurer", new ImageIcon());


        hrp.writeLogEntry(UserAction.CREATE_PERSON,pe);
        hrp.writeLogEntry(UserAction.SET_ASSIGNMENT,pe);
        hrp.writeLogEntry(UserAction.CHANGE_VALUE,pe);
        hrp.writeLogEntry(-1,pe);
        hrp.writeLogEntry(UserAction.DELETE_PERSON,pe);

        LogBook.getLogBookInstance().printLog();
    }
}