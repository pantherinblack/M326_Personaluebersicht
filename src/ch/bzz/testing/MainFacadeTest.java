package ch.bzz.testing;

import ch.bzz.facade.PersonFunctionListModel;
import ch.bzz.model.company.Department;
import ch.bzz.facade.MainFacade;
import org.junit.Test;

import javax.swing.*;


public class MainFacadeTest {
    @Test
    public void init() {

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Test");
        JList<String> list = new JList<>();

        frame.add(list);
        frame.setVisible(true);
        MainFacade mF = MainFacade.getInstance();

        mF.getCompany().addFunction("TestFunction");
        mF.getCompany().addFunction("TestFunction2");
        mF.getCompany().addFunction("TestFunction3");
        mF.getCompany().addDepartment(new Department("TestDepartment"));
        mF.getCompany().addTeam("TestTeam");
        mF.getCompany().addTeam("TestTeam2");
        mF.getCompany().addTeam("TestTeam3");
        mF.createPerson("Niklas", "Vogel", null, "TestDepartment");
        list.setModel(new PersonFunctionListModel(mF.getPerson(mF.getAllPeople().size()-1).getUuid()));
        mF.addFunction(mF.getPerson(mF.getAllPeople().size()-1).getUuid(), "TestFunction");
        mF.addTeam(mF.getPerson(mF.getAllPeople().size()-1).getUuid(), "TestTeam");


        mF.addFunction(mF.getPerson(mF.getAllPeople().size()-1).getUuid(),"TestFunction2");
    }
}