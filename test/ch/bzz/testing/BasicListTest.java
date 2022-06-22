package ch.bzz.testing;

import ch.bzz.facade.MainFacade;
import ch.bzz.model.company.Company;
import ch.bzz.model.company.Department;
import ch.bzz.view.component.BasicList;

import javax.swing.*;
import java.nio.file.Paths;
import java.util.Arrays;

public class BasicListTest {
    public static void main(String[] args) {
        JFrame frame = new JFrame("test");
        frame.add(new BasicList(null, 0, ""));
        frame.setVisible(true);
        MainFacade mF = MainFacade.getInstance();
        mF.setCompany(new Company("test"));
        for (String s : Arrays.asList("TestFunction1", "TestFunction2", "TestFunction3")) {
            mF.addFunction(s);
        }
        for (String s1 : Arrays.asList("TestDepartment1", "TestDepartment2", "TestDepartment3")) {
            mF.addDepartment(new Department(s1));
        }
        for (String s : Arrays.asList("TestTeam1", "TestTeam2", "TestTeam3")) {
            mF.addTeam(s);
        }
        for (int i = 0; i < 2; i++) {
            mF.createPerson("Niklas", "Vogel", Paths.get("test.jpg"), "TestDepartment1");
            mF.addFunctionAtPerson(mF.getPerson(mF.getAllPeople().size() - 1).getUuid(), "TestFunction1");
            mF.addTeamAtPerson(mF.getPerson(mF.getAllPeople().size() - 1).getUuid(), "TestTeam1");
        }
        frame.pack();
    }
}
