package ch.bzz.testing;

import ch.bzz.facade.MainFacade;
import ch.bzz.model.company.Company;
import ch.bzz.model.company.Department;
import ch.bzz.model.employees.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

class MainFacadeTest {

    private MainFacade mF = MainFacade.getInstance();

    @BeforeEach
    public void setUp() {
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
    }

    @Test
    void getInstance() {
        assertNotEquals(null, MainFacade.getInstance());
    }

    @Test
    void getDepartmentByName() {
    }

    @Test
    void getPhotoByUuid() {
        assertNotEquals(null, mF.getPhotoByUuid(mF.getPerson(0).getUuid()));
    }

    @Test
    void getLastNameByUuid() {
        assertEquals("Vogel", mF.getLastNameByUuid(mF.getPerson(0).getUuid()));
    }

    @Test
    void getFirstNameByUuid() {
        assertEquals("Niklas", mF.getFirstNameByUuid(mF.getPerson(0).getUuid()));
    }

    @Test
    void getDepartmentByUuid() {
        assertEquals("TestDepartment1", mF.getDepartmentByUuid(mF.getPerson(0).getUuid()).getName());
    }

    @Test
    void getTeamsByUuid() {
        String[] array = {"TestTeam1"};
        assertArrayEquals(array, mF.getTeamsByUuid(mF.getPerson(0).getUuid()).toArray());
    }

    @Test
    void getFunctionsByUuid() {
        String[] array = {"TestFunction1"};
        assertArrayEquals(array, mF.getFunctionsByUuid(mF.getPerson(0).getUuid()).toArray());
    }

    @Test
    void changeDepartmentByUuid() {
        mF.changeDepartmentByUuid(mF.getPerson(0).getUuid(), "TestDepartment2");
        assertEquals(1, mF.getDepartmentByName("TestDepartment2").getNumberOfMembers());
    }

    @Test
    void createPerson() {
    }

    @Test
    void removePerson() {
    }

    @Test
    void getAllPeople() {
        assertEquals(2,mF.getAllPeople().size());
    }

    @Test
    void fire() {
    }

    @Test
    void getPerson() {
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

    @Test
    void testGetPerson() {
    }

    @Test
    void addFunctionAtPerson() {
    }

    @Test
    void removeFunctionAtPerson() {
    }

    @Test
    void setFunctionAtPerson() {
    }

    @Test
    void testSetFunctionAtPerson() {
    }

    @Test
    void addTeamAtPerson() {
        mF.addTeamAtPerson(mF.getPerson(0).getUuid(), "TestTeam2");
        String[] array = {"TestTeam1", "TestTeam2"};
        assertArrayEquals(array, mF.getTeamsByUuid(mF.getPerson(0).getUuid()).toArray());
    }

    @Test
    void removeTeamAtPerson() {
        mF.removeTeamAtPerson(mF.getPerson(0).getUuid(), "TestTeam1");
        assertEquals(1,mF.getTeamsByUuid(mF.getPerson(0).getUuid()));
    }

    @Test
    void setTeamAtPerson() {
    }

    @Test
    void testSetTeamAtPerson() {
    }

    @Test
    void changeDepartmentName() {
    }

    @Test
    void getAllDepartments() {
    }

    @Test
    void getAllFunctions() {
    }

    @Test
    void getAllTeams() {
    }

    @Test
    void getDepartmentByIndex() {
    }

    @Test
    void getFunctionByIndex() {
    }

    @Test
    void getTeamByIndex() {
    }

    @Test
    void addDepartment() {
    }

    @Test
    void testAddDepartment() {
    }

    @Test
    void addFunction() {
    }

    @Test
    void addTeam() {
    }

    @Test
    void removeDepartment() {
    }

    @Test
    void testRemoveDepartment() {
    }

    @Test
    void removeFunction() {
    }

    @Test
    void testRemoveFunction() {
    }

    @Test
    void removeTeam() {
    }

    @Test
    void testRemoveTeam() {
    }

    @Test
    void changeToHR() {
    }

    @Test
    void testChangeToHR() {
    }

    @Test
    void changeToPerson() {
    }

    @Test
    void testChangeToPerson() {
    }
}