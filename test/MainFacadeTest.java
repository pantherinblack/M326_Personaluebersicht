import ch.bzz.facade.MainFacade;
import ch.bzz.model.company.Company;
import ch.bzz.model.company.Department;
import ch.bzz.model.employees.Person;
import ch.bzz.util.DataHandler;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeEach;

import java.nio.file.Paths;
import java.util.Collections;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;


public class MainFacadeTest {
    /*
    public static void main(String[] args) {
        JFrame frame = new JFrame("Test");

        frame.setVisible(true);
        MainFacade mF = MainFacade.getInstance();

        mF.addFunction("TestFunction");
        mF.addFunction("TestFunction2");
        mF.addFunction("TestFunction3");
        mF.addDepartment(new Department("TestDepartment"));
        mF.addTeam("TestTeam");
        mF.addTeam("TestTeam2");
        mF.addTeam("TestTeam3");
        mF.createPerson("Niklas", "Vogel", null, "TestDepartment");
        JList<String> list = new JList<>(new ParticipationListModel(mF.getPerson(mF.getAllPeople().size() - 1).getUuid(), ParticipationListModel.MODE_FUNCTION));
        frame.add(list);
        mF.addFunction(mF.getPerson(mF.getAllPeople().size() - 1).getUuid(), "TestFunction");
        mF.addTeam(mF.getPerson(mF.getAllPeople().size() - 1).getUuid(), "TestTeam");


        mF.addFunction(mF.getPerson(mF.getAllPeople().size() - 1).getUuid(), "TestFunction2");
    }
     */

}