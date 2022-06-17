package ch.bzz.testing;

import ch.bzz.company.Department;
import ch.bzz.facades.MainFacade;
import org.junit.Test;

import javax.swing.*;

public class MainFacadeTest {
    @Test
    public void init() {
        MainFacade.getInstance().getCompany().addFunction("TestFunction");
        MainFacade.getInstance().getCompany().addDepartment(new Department("TestDepartment"));
        MainFacade.getInstance().getCompany().addTeam("TestTeam");
        MainFacade.getInstance().createPerson("Niklas", "Vogel", new ImageIcon(), "TestDepartment");
        MainFacade.getInstance().addFunction(MainFacade.getInstance().getPerson(0).getUuid(), "TestFunction");
    }

}
