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

    private MainFacade mF = MainFacade.getInstance();

    @BeforeEach
    public void init() {
        mF.setCompany(new Company("test"));
        mF.addFunction("TestFunction1");
        mF.addFunction("TestFunction2");
        mF.addFunction("TestFunction3");
        mF.addDepartment(new Department("TestDepartment1"));
        mF.addDepartment(new Department("TestDepartment2"));
        mF.addDepartment(new Department("TestDepartment3"));
        mF.addTeam("TestTeam1");
        mF.addTeam("TestTeam2");
        mF.addTeam("TestTeam3");
        mF.createPerson("Niklas", "Vogel", Paths.get("test.jpg"), "TestDepartment1");
        mF.addFunction(mF.getPerson(mF.getAllPeople().size() - 1).getUuid(), "TestFunction1");
        mF.addTeam(mF.getPerson(mF.getAllPeople().size() - 1).getUuid(), "TestTeam1");
        mF.createPerson("Niklas", "Vogel", Paths.get("test.jpg"), "TestDepartment1");
        mF.addFunction(mF.getPerson(mF.getAllPeople().size() - 1).getUuid(), "TestFunction1");
        mF.addTeam(mF.getPerson(mF.getAllPeople().size() - 1).getUuid(), "TestTeam1");
    }

    @org.junit.jupiter.api.Test
    void getInstance() {
        assertNotEquals(null, MainFacade.getInstance());
    }

    @org.junit.jupiter.api.Test
    void getPhotoByUuid() {
        assertNotEquals(null, mF.getPhotoByUuid(mF.getPerson(0).getUuid()));
    }

    @org.junit.jupiter.api.Test
    void getLastNameByUuid() {
        assertEquals("Vogel", mF.getLastNameByUuid(mF.getPerson(0).getUuid()));
    }

    @org.junit.jupiter.api.Test
    void getFirstNameByUuid() {
        assertEquals("Niklas", mF.getFirstNameByUuid(mF.getPerson(0).getUuid()));
    }

    @org.junit.jupiter.api.Test
    void getDepartmentByUuid() {
        assertEquals("TestDepartment1", mF.getDepartmentByUuid(mF.getPerson(0).getUuid()).getName());
    }

    @org.junit.jupiter.api.Test
    void getTeamsByUuid() {
        String[] array = {"TestTeam1"};
        assertArrayEquals(array, mF.getTeamsByUuid(mF.getPerson(0).getUuid()).toArray());
    }

    @org.junit.jupiter.api.Test
    void getFunctionsByUuid() {
        String[] array = {"TestFunction1"};
        assertArrayEquals(array, mF.getFunctionsByUuid(mF.getPerson(0).getUuid()).toArray());
    }

    @org.junit.jupiter.api.Test
    void changeDepartmentByUuid() {
        mF.changeDepartmentByUuid(mF.getPerson(0).getUuid(), "TestDepartment2");
        assertEquals(1, mF.getDepartmentByName("TestDepartment2").getNumberOfMembers());
    }

    @org.junit.jupiter.api.Test
    void addTeamByUuid() {
        mF.addTeam(mF.getPerson(0).getUuid(), "TestTeam2");
        String[] array = {"TestTeam1", "TestTeam2"};
        assertArrayEquals(array, mF.getTeamsByUuid(mF.getPerson(0).getUuid()).toArray());
    }

    @org.junit.jupiter.api.Test
    void getAllPeople() {
        assertEquals(2,mF.getAllPeople().size());
    }

    @org.junit.jupiter.api.Test
    void removePerson() {
        mF.removePerson(mF.getPerson(0).getUuid());
        assertEquals(1,mF.getAllPeople().size());
    }

    @org.junit.jupiter.api.Test
    void sortPeopleEmpty() {
        Vector<Person> all = mF.getAllPeople();
        assertEquals(all, mF.sortPeople("Niklas", null, "TestDepartment1", null, ""));
    }

    @org.junit.jupiter.api.Test
    void sortPeopleWell() {
        Vector<Person> all = mF.getAllPeople();
        assertEquals(all, mF.sortPeople("Niklas", "TestFunction1", "TestDepartment1", "TestTeam1", ""));
    }

    @org.junit.jupiter.api.Test
    void sortPeopleAsc() {
        Vector<Person> all = mF.getAllPeople();
        assertEquals(all, mF.sortPeople("Niklas", "TestFunction1", "TestDepartment1", "TestTeam1", "asc"));
    }

    @org.junit.jupiter.api.Test
    void sortPeopleWrongName() {
        Vector<Person> empty = new Vector<>();
        assertEquals(empty, mF.sortPeople("Franz", "TestFunction1", "TestDepartment1", "TestTeam1", "asc"));
    }

    @org.junit.jupiter.api.Test
    void sortPeopleWrongFunction() {
        Vector<Person> empty = new Vector<>();
        assertEquals(empty, mF.sortPeople("Niklas", "TestFunction", "TestDepartment1", "TestTeam1", "asc"));
    }

    @org.junit.jupiter.api.Test
    void sortPeopleWrongDepartment() {
        Vector<Person> empty = new Vector<>();
        assertEquals(empty, mF.sortPeople("Niklas", "TestFunction1", "TestDepartment", "TestTeam1", "asc"));
    }

    @org.junit.jupiter.api.Test
    void sortPeopleWrongTeam() {
        Vector<Person> empty = new Vector<>();
        assertEquals(empty, mF.sortPeople("Niklas", "TestFunction1", "TestDepartment1", "TestTeam", "asc"));
    }

    @org.junit.jupiter.api.Test
    void sortPeopleDsc() {
        Vector<Person> reverse = mF.getAllPeople();
        Collections.reverse(reverse);
        assertEquals(reverse, mF.sortPeople("Niklas", "TestFunction1", "TestDepartment1", "TestTeam1", "dsc"));
    }

    @org.junit.jupiter.api.Test
    void addFunction() {
    }

    @org.junit.jupiter.api.Test
    void removeFunction() {
    }

    @org.junit.jupiter.api.Test
    void setFunction() {
    }

    @org.junit.jupiter.api.Test
    void testSetFunction() {
    }

    @org.junit.jupiter.api.Test
    void addTeam() {
    }

    @org.junit.jupiter.api.Test
    void removeTeam() {
    }

    @org.junit.jupiter.api.Test
    void setTeam() {
    }

    @org.junit.jupiter.api.Test
    void testSetTeam() {
    }

    @org.junit.jupiter.api.Test
    void changeDepartmentName() {
    }

    @org.junit.jupiter.api.Test
    void stringContains() {
    }

    @org.junit.jupiter.api.Test
    void getAllDepartments() {
    }

    @org.junit.jupiter.api.Test
    void getAllFunctions() {
    }

    @org.junit.jupiter.api.Test
    void getAllTeams() {
    }

    @org.junit.jupiter.api.Test
    void getDepartment() {
    }

    @org.junit.jupiter.api.Test
    void getFunction() {
    }

    @org.junit.jupiter.api.Test
    void getTeam() {
    }

    @org.junit.jupiter.api.Test
    void addDepartment() {
    }

    @org.junit.jupiter.api.Test
    void testAddDepartment() {
    }

    @org.junit.jupiter.api.Test
    void testAddFunction() {
    }

    @org.junit.jupiter.api.Test
    void testAddTeam() {
    }

    @org.junit.jupiter.api.Test
    void removeDepartment() {
    }

    @org.junit.jupiter.api.Test
    void testRemoveDepartment() {
    }

    @org.junit.jupiter.api.Test
    void testRemoveFunction() {
    }

    @org.junit.jupiter.api.Test
    void testRemoveFunction1() {
    }

    @org.junit.jupiter.api.Test
    void testRemoveTeam() {
    }

    @org.junit.jupiter.api.Test
    void testRemoveTeam1() {
    }

    @org.junit.jupiter.api.Test
    void changeToHR() {
    }

    @org.junit.jupiter.api.Test
    void testChangeToHR() {
    }

    @org.junit.jupiter.api.Test
    void changeToPerson() {
    }

    @org.junit.jupiter.api.Test
    void testChangeToPerson() {
    }
}