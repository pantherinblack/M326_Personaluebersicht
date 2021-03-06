package ch.bzz.testing;

import ch.bzz.exception.DuplicateEntryException;
import ch.bzz.exception.InUseException;
import ch.bzz.exception.NotExistentException;
import ch.bzz.facade.MainFacade;
import ch.bzz.model.company.Company;
import ch.bzz.model.company.Department;
import ch.bzz.model.employees.HRPerson;
import ch.bzz.model.employees.Person;
import ch.bzz.util.StringListCompare;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test-class for MeinFacade, test happy cases and most unhappy cases
 *
 * @author Kevin
 * @version 1.0
 * @since 18.06.2022
 */
class MainFacadeTest {

    private final MainFacade mF = MainFacade.getInstance();

    @BeforeEach
    public void setUp() {
        mF.setCompany(new Company("test"));
        mF.setHrPerson(new HRPerson("Karl", "Heinz", null, 1));
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
        assertNotNull(MainFacade.getInstance());
    }

    /**
     * counts for all methods using the uuid, beacuse they use the similar method to get the merosn
     */
    @Test
    void translateCorrectUuid() {
        assertNotEquals(new Vector<>(), mF.getTeamsByUuid(mF.getPerson(0).getUuid()));
        assertNotNull(mF.getTeamsByUuid(mF.getPerson(0).getUuid()));
    }

    /**
     * counts for all methods using the uuid, beacuse they use the similar method to get the merosn
     */
    @Test
    void translateWrongUuid() {
        assertThrowsExactly(NotExistentException.class, () -> mF.getFirstNameByUuid("no uuid"));
    }

    @Test
    void getDepartmentByName() {
        assertNotNull(mF.getDepartmentByName("TestDepartment1"));
    }

    @Test
    void getDepartmentByWrongName() {
        assertThrowsExactly(NotExistentException.class, () -> mF.getDepartmentByName("TestDepartment"));
    }

    @Test
    void getPhotoByUuid() {
        assertNotNull(mF.getPhotoByUuid(mF.getPerson(0).getUuid()));
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
        mF.createPerson("Adras", "Tarlos", null, "TestDepartment2");
        assertEquals(3, mF.getAllPeople().size());
    }

    @Test
    void removePerson() {
        mF.removePerson(mF.getPerson(0).getUuid());
        assertEquals(1, mF.getAllPeople().size());
    }

    @Test
    void removeToManyPerson() {
        mF.removePerson(mF.getPerson(0).getUuid());
        mF.removePerson(mF.getPerson(0).getUuid());
        assertThrowsExactly(ArrayIndexOutOfBoundsException.class, () -> mF.removePerson(mF.getPerson(0).getUuid()));
    }

    @Test
    void getAllPeople() {
        assertEquals(2, mF.getAllPeople().size());
    }


    @Test
    void getPerson() {
        assertNotNull(mF.getPerson(0));
    }

    @Test
    void getPersonNegative() {
        assertThrowsExactly(ArrayIndexOutOfBoundsException.class, () -> mF.getPerson(-1));
    }

    @Test
    void getPersonToHigh() {
        assertThrowsExactly(ArrayIndexOutOfBoundsException.class, () -> mF.getPerson(2));
    }

    @org.junit.jupiter.api.Test
    void sortPeopleEmpty() {
        Vector<Person> all = mF.getAllPeople();
        assertEquals(all, mF.sortPeople(null, null, null, null, null));
    }

    @org.junit.jupiter.api.Test
    void sortPeopleWell() {
        Vector<Person> all = mF.getAllPeople();
        assertEquals(all, mF.sortPeople("Niklas", "TestFunction1", "TestDepartment1", "TestTeam1", "A-Z"));
    }

    @org.junit.jupiter.api.Test
    void sortPeopleAsc() {
        Vector<Person> all = mF.getAllPeople();
        all.sort(Comparator.comparing(Person::getFullName));
        assertEquals(all, mF.sortPeople("Niklas", "TestFunction1", "TestDepartment1", "TestTeam1", "A-Z"));
    }

    @org.junit.jupiter.api.Test
    void sortPeopleWrongName() {
        Vector<Person> empty = new Vector<>();
        assertEquals(empty, mF.sortPeople("Franz", "TestFunction1", "TestDepartment1", "TestTeam1", "A-Z"));
    }

    @org.junit.jupiter.api.Test
    void sortPeopleWrongFunction() {
        Vector<Person> empty = new Vector<>();
        assertEquals(empty, mF.sortPeople("Niklas", "TestFunction", "TestDepartment1", "TestTeam1", "A-Z"));
    }

    @org.junit.jupiter.api.Test
    void sortPeopleWrongDepartment() {
        Vector<Person> empty = new Vector<>();
        assertEquals(empty, mF.sortPeople("Niklas", "TestFunction1", "TestDepartment", "TestTeam1", "A-Z"));
    }

    @org.junit.jupiter.api.Test
    void sortPeopleWrongTeam() {
        Vector<Person> empty = new Vector<>();
        assertEquals(empty, mF.sortPeople("Niklas", "TestFunction1", "TestDepartment1", "TestTeam", "A-Z"));
    }

    @org.junit.jupiter.api.Test
    void sortPeopleDsc() {
        Vector<Person> reverse = mF.getAllPeople();
        reverse.sort(Comparator.comparing(Person::getFullName));
        Collections.reverse(reverse);
        assertEquals(reverse, mF.sortPeople("Niklas", "TestFunction1", "TestDepartment1", "TestTeam1", "Z-A"));
    }

    @Test
    void testGetPerson() {
        assertEquals(mF.sortPeople("Niklas", "TestFunction1", "TestDepartment1", "TestTeam1", "").get(0),
                mF.getPerson(0, "Niklas", "TestFunction1", "TestDepartment1", "TestTeam1", ""));
    }

    @Test
    void addFunctionAtPerson() {
        mF.addFunctionAtPerson(mF.getPerson(0).getUuid(), "TestFunction3");
        assertEquals(2, mF.getFunctionsByUuid(mF.getPerson(0).getUuid()).size());
    }

    @Test
    void addFunctionAtPersonDuplicate() {
        assertThrowsExactly(DuplicateEntryException.class,
                () -> mF.addFunctionAtPerson(mF.getPerson(0).getUuid(), "TestFunction1"));
    }

    @Test
    void removeFunctionAtPerson() {
        mF.removeFunctionAtPerson(mF.getPerson(0).getUuid(), "TestFunction1");
        assertEquals(0, mF.getFunctionsByUuid(mF.getPerson(0).getUuid()).size());
    }

    @Test
    void removeFunctionAtPersonNotExisting() {
        assertThrowsExactly(NotExistentException.class,
                () -> mF.removeFunctionAtPerson(mF.getPerson(0).getUuid(), "TestFunction"));
    }

    @Test
    void setFunctionAtPerson() {
        mF.setFunctionAtPerson(mF.getPerson(0).getUuid(), "TestFunction2", 0);
        assertEquals("TestFunction2", mF.getFunctionsByUuid(mF.getPerson(0).getUuid()).get(0));
    }

    @Test
    void setFunctionAtPersonNotFunction() {
        assertThrowsExactly(NotExistentException.class,
                () -> mF.setFunctionAtPerson(mF.getPerson(0).getUuid(), "TestFunction", 0));
    }

    @Test
    void setFunctionAtPersonOutOfBounds() {
        assertThrowsExactly(ArrayIndexOutOfBoundsException.class,
                () -> mF.setFunctionAtPerson(mF.getPerson(2).getUuid(), "TestFunction2", 0));
    }

    @Test
    void addTeamAtPerson() {
        mF.addTeamAtPerson(mF.getPerson(0).getUuid(), "TestTeam2");
        String[] array = {"TestTeam1", "TestTeam2"};
        assertArrayEquals(array, mF.getTeamsByUuid(mF.getPerson(0).getUuid()).toArray());
    }

    @Test
    void addTeamAtPersonAlreadyExists() {
        assertThrowsExactly(DuplicateEntryException.class,
                () -> mF.addTeamAtPerson(mF.getPerson(0).getUuid(), "TestTeam1"));
    }

    @Test
    void removeTeamAtPerson() {
        mF.removeTeamAtPerson(mF.getPerson(0).getUuid(), "TestTeam1");
        assertEquals(0, mF.getTeamsByUuid(mF.getPerson(0).getUuid()).size());
    }

    @Test
    void setTeamAtPerson() {
        mF.setTeamAtPerson(mF.getPerson(0).getUuid(), "TestTeam2", 0);
        assertEquals("TestTeam2", mF.getTeamsByUuid(mF.getPerson(0).getUuid()).get(0));
    }

    @Test
    void setTeamAtPersonNoTeam() {
        assertThrowsExactly(NotExistentException.class,
                () -> mF.setTeamAtPerson(mF.getPerson(0).getUuid(), "TestTeam", 0));
    }

    @Test
    void setTeamAtPersonOutOfBounds() {
        assertThrowsExactly(ArrayIndexOutOfBoundsException.class,
                () -> mF.setTeamAtPerson(mF.getPerson(0).getUuid(), "TestTeam2", 1));
    }

    @Test
    void changeDepartmentName() {
        mF.changeDepartmentName("TestDepartment1", "Dep");
        assertNotNull(mF.getDepartmentByName("Dep"));
        assertEquals("Dep", mF.getDepartmentByUuid(mF.getPerson(0).getUuid()).getName());
    }

    @Test
    void changeDepartmentNameNotExisting() {
        assertThrowsExactly(NotExistentException.class,
                () -> mF.changeDepartmentName("TestDepart", "Dep"));
    }

    @Test
    void changeTeamName() {
        mF.changeTeamName("TestTeam1", "Team1");
        assertTrue(StringListCompare.stringContains(mF.getAllTeams(), "Team1") &&
                StringListCompare.stringContains(mF.getTeamsByUuid(mF.getPerson(0).getUuid()), "Team1"));
    }

    @Test
    void changeTeamNameNotExisting() {
        assertThrowsExactly(NotExistentException.class,
                () -> mF.changeTeamName("TestTeam", "Team1"));
    }

    @Test
    void changeFunctionName() {
        mF.changeFunctionName("TestFunction1", "Function1");
        assertTrue(StringListCompare.stringContains(mF.getAllFunctions(), "Function1") &&
                StringListCompare.stringContains(mF.getFunctionsByUuid(mF.getPerson(0).getUuid()), "Function1"));

    }

    @Test
    void changeFunctionNameNotExisting() {
        assertThrowsExactly(NotExistentException.class,
                () -> mF.changeFunctionName("TestTeam", "Team1"));
    }

    @Test
    void getAllDepartments() {
        assertNotEquals(0, mF.getAllDepartments().size());
    }

    @Test
    void getAllFunctions() {
        assertNotEquals(0, mF.getAllFunctions().size());
    }

    @Test
    void getAllTeams() {
        assertNotEquals(0, mF.getAllTeams().size());
    }

    @Test
    void getDepartmentByIndex() {
        assertEquals("TestDepartment1", mF.getDepartmentByIndex(0).getName());
    }

    @Test
    void getFunctionByIndex() {
        assertEquals("TestFunction1", mF.getFunctionByIndex(0));
    }

    @Test
    void getTeamByIndex() {
        assertEquals("TestTeam1", mF.getTeamByIndex(0));
    }

    @Test
    void addDepartment() {
        mF.addDepartment(new Department("test"));
        assertEquals(4, mF.getAllDepartments().size());
    }

    @Test
    void addDepartmentAlreadyExists() {
        assertThrowsExactly(DuplicateEntryException.class,
                () -> mF.addDepartment(new Department("TestDepartment1")));
    }

    @Test
    void testAddDepartment() {
        mF.addDepartment("test");
        assertEquals(4, mF.getAllDepartments().size());
    }

    @Test
    void testAddDepartmentAlreadyExists() {
        assertThrowsExactly(DuplicateEntryException.class,
                () -> mF.addDepartment("TestDepartment1"));
    }

    @Test
    void addFunction() {
        mF.addFunction("Test");
        assertEquals(4, mF.getAllFunctions().size());
    }

    @Test
    void addFunctionAlreadyExists() {
        assertThrowsExactly(DuplicateEntryException.class,
                () -> mF.addFunction("TestFunction1"));
    }

    @Test
    void addTeam() {
        mF.addTeam("Team");
        assertEquals(4, mF.getAllTeams().size());
    }

    @Test
    void addTeamAlreadyExists() {
        assertThrowsExactly(DuplicateEntryException.class,
                () -> mF.addFunction("TestFunction1"));
    }

    @Test
    void removeDepartment() {
        mF.removeDepartment(2);
        assertEquals(2, mF.getAllDepartments().size());
    }

    @Test
    void removeDepartmentNotExisting() {
        assertThrowsExactly(ArrayIndexOutOfBoundsException.class,
                () -> mF.removeDepartment(3));
    }

    @Test
    void removeDepartmentStillInUse() {
        assertThrowsExactly(InUseException.class,
                () -> mF.removeDepartment(0));
    }

    @Test
    void removeFunction() {
        mF.removeFunction(2);
        assertEquals(2, mF.getAllFunctions().size());
    }

    @Test
    void removeFunctionNotExisting() {
        assertThrowsExactly(ArrayIndexOutOfBoundsException.class,
                () -> mF.removeFunction(3));
    }

    @Test
    void removeFunctionStillInUse() {
        assertThrowsExactly(InUseException.class,
                () -> mF.removeFunction(0));
    }

    @Test
    void removeTeam() {
        mF.removeTeam(2);
        assertEquals(2, mF.getAllTeams().size());
    }

    @Test
    void removeTeamNotExisting() {
        assertThrowsExactly(ArrayIndexOutOfBoundsException.class,
                () -> mF.removeTeam(3));
    }

    @Test
    void changeToHR() {
        mF.changeToHR(mF.getPerson(0).getUuid(), HRPerson.MODE_NORMAL, "password");
        assertTrue(mF.getPerson(0) instanceof HRPerson);
    }


    @Test
    void changeToPerson() {
        mF.changeToHR(mF.getPerson(0).getUuid(), HRPerson.MODE_NORMAL, "password");
        mF.changeToPerson(mF.getPerson(0).getUuid());
        assertTrue(mF.getPerson(0) instanceof HRPerson);
    }

}