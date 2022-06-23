package ch.bzz.facade;

import ch.bzz.exception.DuplicateEntryException;
import ch.bzz.exception.InUseException;
import ch.bzz.exception.NotExistentException;
import ch.bzz.interfaces.ModelListener;
import ch.bzz.log.LogBook;
import ch.bzz.log.UserAction;
import ch.bzz.model.company.Company;
import ch.bzz.model.company.Department;
import ch.bzz.model.employees.HRPerson;
import ch.bzz.model.employees.Participation;
import ch.bzz.model.employees.Person;
import ch.bzz.util.DataHandler;
import ch.bzz.util.StringListCompare;

import java.nio.file.Path;
import java.util.Collections;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Vector;

/**
 * Manages all Dat access form the view ot hte Model and triggers back, if the data changes
 *
 * @author Kevin
 * @version 2.5
 * @since 12.06.2022
 */
public class MainFacade {
    private static MainFacade instance = null;
    private final Vector<ModelListener> modelListeners = new Vector<>();
    private Company company;
    private HRPerson hrPerson;

    /**
     * loads the app
     */
    private MainFacade() {
        company = DataHandler.getInstance().loadApp();
    }

    /**
     * gets the instance of the class
     *
     * @return instance
     */
    public static MainFacade getInstance() {
        if (instance == null) instance = new MainFacade();
        return instance;
    }

    /**
     * returns the person object, using the uuid to search for it
     *
     * @param uuid to search for
     * @return person
     * @throws NotExistentException if the uuid does not exist
     */
    public Person getPersonByUuid(String uuid) throws NotExistentException {
        for (int d = 0; d < company.getNumberOfDepartments(); d++) {
            for (int p = 0; p < company.getDepartment(d).getNumberOfMembers(); p++) {
                if (company.getDepartment(d).getMember(p).getUuid().equals(uuid))
                    return company.getDepartment(d).getMember(p);
            }
        }
        throw new NotExistentException();
    }

    /**
     * gets the department of a person
     *
     * @param person to get the department from
     * @return department
     * @throws NotExistentException if the person is not saved
     */
    private Department getDepartmentByPerson(Person person) throws NotExistentException {
        for (int d = 0; d < company.getNumberOfDepartments(); d++) {
            for (int p = 0; p < company.getDepartment(d).getNumberOfMembers(); p++) {
                if (company.getDepartment(d).getMember(p) == person)
                    return company.getDepartment(d);
            }
        }
        throw new NotExistentException();
    }

    /**
     * gets the participation of the person
     *
     * @param person to get the participation from
     * @return participation
     */
    private Participation getParticipationByPerson(Person person) {
        return person.getParticipation();
    }

    /**
     * gets the teams of the participation
     *
     * @param participation to get the teams from
     * @return teams
     */
    private Vector<String> getTeamsByParticipation(Participation participation) {
        return participation.getTeams();
    }

    /**
     * gets the functions of the participation
     *
     * @param participation to get the functions from
     * @return functions
     */
    private Vector<String> getFunctionByParticipation(Participation participation) {
        return participation.getFunctions();
    }

    /**
     * searches for the department using the name
     *
     * @param name to search
     * @return department
     * @throws NotExistentException if the department does not exist
     */
    public Department getDepartmentByName(String name) throws NotExistentException {
        for (int d = 0; d < company.getNumberOfDepartments(); d++) {
            if (company.getDepartment(d).getName().equals(name)) return company.getDepartment(d);
        }
        throw new NotExistentException();
    }

    /**
     * gets the photo of the uuid of the person
     *
     * @param uuid of the person
     * @return photo
     */
    public Path getPhotoByUuid(String uuid) {
        return getPersonByUuid(uuid).getPhoto();
    }


    /**
     * gets the forst name of a person searched for using the uuid
     *
     * @param uuid to search for
     * @return firstname
     */
    public String getFirstNameByUuid(String uuid) {
        return getPersonByUuid(uuid).getFirstName();
    }

    /**
     * gets the last name of a person searched for using the uuid
     *
     * @param uuid to search for
     * @return lastname
     */
    public String getLastNameByUuid(String uuid) {
        return getPersonByUuid(uuid).getLastName();
    }

    /**
     * gets the fullname of a person searched for using the uuid
     *
     * @param uuid to search for
     * @return fullname
     */
    public String getFullNameByUuid(String uuid) {
        return getPersonByUuid(uuid).getFirstName() + " " + getPersonByUuid(uuid).getLastName();
    }

    /**
     * gets the index of the department searched for using the department-name
     *
     * @param department name to search for
     * @return department
     */
    public int getIndexOfDepartment(String department) {
        for (int i = 0; i < getAllDepartments().size(); i++) {
            if (getAllDepartments().get(i).getName().equals(department)) return i;
        }
        return 0;
    }

    /**
     * gets the department using the uuid of the person
     *
     * @param uuid of the person to search for
     * @return department
     */
    public Department getDepartmentByUuid(String uuid) {
        return getDepartmentByPerson(getPersonByUuid(uuid));
    }

    /**
     * gets the teams using the uuid of the person
     *
     * @param uuid of the person to search for
     * @return teams
     */
    public Vector<String> getTeamsByUuid(String uuid) {
        return getTeamsByParticipation(getParticipationByPerson(getPersonByUuid(uuid)));
    }

    /**
     * gets te functions using the uuid of the person
     *
     * @param uuid of the person to search for
     * @return functions
     */
    public Vector<String> getFunctionsByUuid(String uuid) {
        return getFunctionByParticipation(getParticipationByPerson(getPersonByUuid(uuid)));
    }

    /**
     * sets the photo of a person using the uuid
     *
     * @param uuid  of the person
     * @param photo to set
     */
    public void setPhotoByUuid(String uuid, Path photo) {
        getPersonByUuid(uuid).setPhoto(photo);
        fire();
    }

    /**
     * sets the full name of a person using the uuid of the person
     *
     * @param uuid     of the person
     * @param fullName to set
     */
    public void setFullNameByUuid(String uuid, String fullName) {
        Person person = getPersonByUuid(uuid);
        person.setFirstName(fullName.split(" ")[0]);
        person.setLastName(fullName.split(" ")[1]);
        fire();
    }

    /**
     * changes the department of a person
     *
     * @param uuid       of the person
     * @param department the name of the new department
     * @throws NotExistentException
     */
    public void changeDepartmentByUuid(String uuid, String department) throws NotExistentException {
        Person person = getPersonByUuid(uuid);
        Department department1 = getDepartmentByPerson(person);

        for (int i = 0; i < department1.getNumberOfMembers(); i++) {
            if (department1.getMember(i) == person) {
                department1.removeMember(i);
                getDepartmentByName(department).addMember(person);
                fire();
                LogBook.getLogBookInstance().addEntry(new UserAction(hrPerson, person, UserAction.SET_ASSIGNMENT).getEntry());
                return;
            }
        }
        throw new NotExistentException();
    }

    /**
     * creates a new person and sets it in the department
     *
     * @param fName      of the person
     * @param lName      of the person
     * @param photo      of the person
     * @param department where the person shall be
     */
    public void createPerson(String fName, String lName, Path photo, String department) {
        Person person = new Person(fName, lName, photo);
        getDepartmentByName(department).addMember(person);
        LogBook.getLogBookInstance().addEntry(new UserAction(hrPerson, person, UserAction.CREATE_PERSON).getEntry());
        fire();
    }

    /**
     * removes a perrson using the uuid
     *
     * @param uuid to search for
     * @throws NotExistentException if the uuid does not exist
     */
    public void removePerson(String uuid) throws NotExistentException {
        Department department = getDepartmentByUuid(uuid);
        for (int i = 0; i < department.getNumberOfMembers(); i++) {
            if (department.getMember(i) == getPersonByUuid(uuid)) {
                LogBook.getLogBookInstance().addEntry(new UserAction(hrPerson, getPersonByUuid(uuid), UserAction.DELETE_PERSON).getEntry());
                department.removeMember(i);
                fire();
                return;
            }
        }
        throw new NotExistentException();
    }

    /**
     * gets all people
     *
     * @return people
     */
    public Vector<Person> getAllPeople() {
        Vector<Person> people = new Vector<>();
        for (int i = 0; i < company.getNumberOfDepartments(); i++) {
            for (int j = 0; j < company.getDepartment(i).getNumberOfMembers(); j++) {
                people.add(company.getDepartment(i).getMember(j));
            }
        }
        return new Vector<>(people);
    }

    /**
     * updates all views
     */
    public void fire() {
        for (ModelListener modelListener : modelListeners) {
            modelListener.fireContentsChanged(this, 0, -1);
        }
        DataHandler.getInstance().saveApp();
    }

    /**
     * gets a person using the index.
     *
     * @param index of the person
     * @return person
     */
    public Person getPerson(int index) {
        return getAllPeople().get(index);
    }

    /**
     * gives back a list of all people narrowed by the parameters and sorted
     *
     * @param name       the people need to have
     * @param function   people need to have
     * @param department people need to have
     * @param team       people need to have
     * @param sort       "A-Z", "Z-A", else it wont be sorted
     * @return peoples matching
     */
    public Vector<Person> sortPeople(String name, String function, String department, String team, String sort) {
        Vector<Person> people = getAllPeople();
        if (function != null && !function.isEmpty()) {
            people.removeIf(person -> !StringListCompare.stringContains(getFunctionsByUuid(person.getUuid()), function));
        }

        if (department != null && !department.isEmpty()) {
            people.removeIf(person -> !getDepartmentByPerson(person).getName().equals(department));
        }

        if (team != null && !team.isEmpty()) {
            people.removeIf(person -> !StringListCompare.stringContains(getTeamsByUuid(person.getUuid()), team));
        }

        if (name != null && !name.isEmpty()) {
            people.removeIf(person -> !person.getFullName().contains(name));
        }

        if (sort != null && !sort.isEmpty()) {

            switch (sort) {
                case "A-Z":
                    people.sort(Comparator.comparing(Person::getFullName));
                    break;
                case "Z-A":
                    people.sort(Comparator.comparing(Person::getFullName));
                    Collections.reverse(people);
                    break;
            }
        }
        return people;
    }

    /**
     * gets a specific person of the sorted list
     *
     * @param index      of the person
     * @param name       people need to have
     * @param function   people need to have
     * @param department people need to have
     * @param team       people need to have
     * @param sort       "A-Z", "Z-A", else it wont be sorted
     * @return person
     */
    public Person getPerson(int index, String name, String function, String department, String team, String sort) {
        return sortPeople(name, function, department, team, sort).get(index);

    }

    /**
     * adds a ModelListener, wich listens to change
     *
     * @param modelListener to be added
     */
    public void addModelListener(ModelListener modelListener) {
        modelListeners.add(modelListener);
    }

    /**
     * removes the ModelListener
     *
     * @param modelListener to remove
     */
    public void removeModelListener(ModelListener modelListener) {
        modelListeners.remove(modelListener);
    }

    /**
     * adds a function to a person using the uuid
     *
     * @param uuid     of the person
     * @param function to be added
     * @throws DuplicateEntryException if the person already has the function
     * @throws NotExistentException    if the function does not exist
     */
    public void addFunctionAtPerson(String uuid, String function) throws DuplicateEntryException, NotExistentException {
        if (!StringListCompare.stringContains(getFunctionsByUuid(uuid), function)) {
            if (isExistentFunction(function)) {
                getFunctionsByUuid(uuid).add(function);
                LogBook.getLogBookInstance().addEntry(new UserAction(hrPerson, getPersonByUuid(uuid), UserAction.SET_ASSIGNMENT).getEntry());
                fire();
                return;
            }
            throw new NotExistentException();
        }
        throw new DuplicateEntryException();
    }

    /**
     * removes the function of a person using the uuid
     *
     * @param uuid     of the person
     * @param function to be removed
     * @throws NotExistentException if the function does not exist
     */
    public void removeFunctionAtPerson(String uuid, String function) throws NotExistentException {
        if (StringListCompare.stringContains(getFunctionsByUuid(uuid), function)) {
            if (isExistentFunction(function)) {
                getFunctionsByUuid(uuid).remove(function);
                LogBook.getLogBookInstance().addEntry(new UserAction(hrPerson, getPersonByUuid(uuid), UserAction.SET_ASSIGNMENT).getEntry());
                fire();
                return;
            }
        }
        throw new NotExistentException();
    }

    /**
     * set a function in the list to a person using the uuid
     *
     * @param uuid     of the person
     * @param function to be added
     * @param index    to where the function should be set
     * @throws DuplicateEntryException if the person already has the function
     * @throws NotExistentException    if the function does not exist
     */
    public void setFunctionAtPerson(String uuid, String function, int index) throws DuplicateEntryException, NotExistentException {
        if (!StringListCompare.stringContains(getFunctionsByUuid(uuid), function)) {
            if (isExistentFunction(function)) {
                getFunctionsByUuid(uuid).set(index, function);
                LogBook.getLogBookInstance().addEntry(new UserAction(hrPerson, getPersonByUuid(uuid), UserAction.SET_ASSIGNMENT).getEntry());
                fire();
                return;
            }
            throw new NotExistentException();
        }
        throw new DuplicateEntryException();
    }

    /**
     * adds a team to the person using the uuid
     *
     * @param uuid of the person
     * @param team to be added
     * @throws DuplicateEntryException if the person already has a team
     * @throws NotExistentException    if the team does not exist
     */
    public void addTeamAtPerson(String uuid, String team) throws DuplicateEntryException, NotExistentException {
        if (!StringListCompare.stringContains(getTeamsByUuid(uuid), team)) {
            if (isExistentTeam(team)) {
                getTeamsByUuid(uuid).add(team);
                LogBook.getLogBookInstance().addEntry(new UserAction(hrPerson, getPersonByUuid(uuid), UserAction.SET_ASSIGNMENT).getEntry());
                fire();
                return;
            }
            throw new NotExistentException();
        }
        throw new DuplicateEntryException();
    }

    /**
     * removes a team form a person using the uuid
     *
     * @param uuid of the person
     * @param team to be removed
     * @throws NotExistentException if the team does not exist
     */
    public void removeTeamAtPerson(String uuid, String team) throws NotExistentException {
        if (StringListCompare.stringContains(getTeamsByUuid(uuid), team)) {
            if (isExistentTeam(team)) {
                getTeamsByUuid(uuid).remove(team);
                LogBook.getLogBookInstance().addEntry(new UserAction(hrPerson, getPersonByUuid(uuid), UserAction.SET_ASSIGNMENT).getEntry());
                fire();
                return;
            }
        }
        throw new NotExistentException();
    }

    /**
     * sets a team in a list to the person using the uuid
     *
     * @param uuid  of the person
     * @param team  to be added
     * @param index to where it should be set
     * @throws DuplicateEntryException if the person already has a team
     * @throws NotExistentException    if the team does not exist
     */
    public void setTeamAtPerson(String uuid, String team, int index) throws DuplicateEntryException, NotExistentException {
        if (!StringListCompare.stringContains(getTeamsByUuid(uuid), team)) {
            if (isExistentTeam(team)) {
                getTeamsByUuid(uuid).set(index, team);
                LogBook.getLogBookInstance().addEntry(new UserAction(hrPerson, getPersonByUuid(uuid), UserAction.SET_ASSIGNMENT).getEntry());
                fire();
                return;
            }
            throw new NotExistentException();
        }
        throw new DuplicateEntryException();
    }

    /**
     * test if the department exists
     *
     * @param department (String) to look for
     * @return true -> exists, false -> does not exist
     */
    private boolean isExistentDepartment(String department) {
        Vector<Department> departments = company.getDepartments();
        for (Department department1 : departments) {
            if (department1.getName().equals(department)) return true;
        }
        return false;
    }

    /**
     * test if the department exists
     *
     * @param department (Object) to look for
     * @return true -> exists, false -> does not exist
     */
    private boolean isExistentDepartment(Department department) {
        for (int i = 0; i < company.getNumberOfDepartments(); i++) {
            if (company.getDepartment(i).getName().equals(department.getName())) return true;
        }
        return false;
    }

    /**
     * tests if the function exists
     *
     * @param function to look for
     * @return true -> exists, false -> does not exist
     */
    private boolean isExistentFunction(String function) {
        for (int i = 0; i < company.getNumberOfFunction(); i++) {
            if (company.getFunction(i).equals(function)) return true;
        }
        return false;
    }

    /**
     * tests if the team exists
     *
     * @param team to look for
     * @return true -> exists, false -> does not exist
     */
    private boolean isExistentTeam(String team) {
        for (int i = 0; i < company.getNumberOfTeams(); i++) {
            if (company.getTeam(i).equals(team)) return true;
        }
        return false;
    }

    /**
     * changes the name of the department
     *
     * @param oldName of the department
     * @param newName of the department
     * @throws DuplicateEntryException if the new name already exists
     */
    public void changeDepartmentName(String oldName, String newName) throws DuplicateEntryException {
        if (isExistentDepartment(newName)) throw new DuplicateEntryException();
        getDepartmentByName(oldName).setName(newName);
        fire();
    }

    /**
     * changes the name of the team
     *
     * @param oldName of the team
     * @param newName of the team
     * @throws DuplicateEntryException if the new name already exists
     */
    public void changeTeamName(String oldName, String newName) throws NotExistentException, DuplicateEntryException {
        if (isExistentTeam(newName)) throw new DuplicateEntryException();
        if (!isExistentTeam(oldName)) throw new NotExistentException();
        for (int i = 0; i < getAllTeams().size(); i++) {
            if (getAllTeams().get(i).equals(oldName)) getAllTeams().set(i, newName);
        }
        for (Person person : getAllPeople()) {
            Participation part = person.getParticipation();
            for (int i = 0; i < part.getNumberOfTeams(); i++) {
                if (part.getTeams().get(i).equals(oldName))
                    part.getTeams().set(i, newName);
            }
        }
        fire();
    }

    /**
     * changes the name of the function
     *
     * @param oldName of the function
     * @param newName of the function
     * @throws DuplicateEntryException if the new name already exists
     */
    public void changeFunctionName(String oldName, String newName) throws NotExistentException, DuplicateEntryException {
        if (isExistentFunction(newName)) throw new DuplicateEntryException();
        if (!isExistentFunction(oldName)) throw new NotExistentException();
        for (int i = 0; i < getAllTeams().size(); i++) {
            if (getAllFunctions().get(i).equals(oldName)) getAllFunctions().set(i, newName);
        }
        for (Person person : getAllPeople()) {
            Participation part = person.getParticipation();
            for (int i = 0; i < part.getNumberOfFunctions(); i++) {
                if (part.getFunctions().get(i).equals(oldName))
                    part.getFunctions().set(i, newName);
            }
        }
        fire();
    }

    /**
     * gives back all departments
     *
     * @return departments
     */
    public Vector<Department> getAllDepartments() {
        return company.getDepartments();
    }

    /**
     * gives back all function
     *
     * @return functions
     */
    public Vector<String> getAllFunctions() {
        return company.getFunctions().getDesignations();
    }

    /**
     * gives back all team
     *
     * @return teams
     */
    public Vector<String> getAllTeams() {
        return company.getTeams().getDesignations();
    }

    /**
     * gets a department using the index
     *
     * @param index of the department
     * @return department
     */
    public Department getDepartmentByIndex(int index) {
        return getAllDepartments().get(index);
    }

    /**
     * gets a function using the index
     *
     * @param index of the function
     * @return function
     */
    public String getFunctionByIndex(int index) {
        return getAllFunctions().get(index);
    }

    /**
     * gets a team using the index
     *
     * @param index of the team
     * @return team
     */
    public String getTeamByIndex(int index) {
        return getAllTeams().get(index);
    }

    /**
     * adds a department
     *
     * @param department (Object) to add
     * @throws DuplicateEntryException if it already exists
     */
    public void addDepartment(Department department) throws DuplicateEntryException {
        if (isExistentDepartment(department)) throw new DuplicateEntryException();
        company.addDepartment(department);
        fire();
    }

    /**
     * adds a department
     *
     * @param department (String) to add
     */
    public void addDepartment(String department) {
        addDepartment(new Department(department));
    }

    /**
     * adds a function
     *
     * @param function to add
     * @throws DuplicateEntryException if it already exists
     */
    public void addFunction(String function) throws DuplicateEntryException {
        if (isExistentFunction(function)) throw new DuplicateEntryException();
        company.addFunction(function);
        fire();
    }

    /**
     * adds a team
     *
     * @param team to add
     * @throws DuplicateEntryException if it already exists
     */
    public void addTeam(String team) throws DuplicateEntryException {
        if (isExistentTeam(team)) throw new DuplicateEntryException();
        company.addTeam(team);
        fire();
    }

    /**
     * removes a department
     *
     * @param index of the department
     * @throws InUseException if it is still in use by a person
     */
    public void removeDepartment(int index) throws InUseException {
        if (isDepartmentInUse(company.getDepartment(index))) throw new InUseException();
        company.removeDepartment(index);
        fire();
    }

    /**
     * removes a function
     *
     * @param index of the function
     * @throws InUseException if it is still in use by a person
     */
    public void removeFunction(int index) throws InUseException {
        if (isFunctionInUse(getFunctionByIndex(index))) throw new InUseException();
        company.removeFunction(index);
        fire();
    }

    /**
     * removes a team
     *
     * @param index of the team
     * @throws InUseException if it is still in use by a person
     */
    public void removeTeam(int index) throws InUseException {
        if (isTeamInUse(getTeamByIndex(index))) throw new InUseException();
        company.removeTeam(index);
        fire();
    }

    /**
     * changes a regular person to an HR-Person
     *
     * @param uuid  of the person
     * @param modus mode of the person (HR_Person constants)
     * @param pwd   password of the person
     */
    public void changeToHR(String uuid, int modus, String pwd) {
        changeToHR(getPersonByUuid(uuid), modus, pwd);
    }

    /**
     * changes a regulat person to an HR_person
     *
     * @param person to be changes
     * @param modus  mode of the person (HR_Person constants)
     * @param pwd    password of the person
     */
    private void changeToHR(Person person, int modus, String pwd) {
        Department department = getDepartmentByPerson(person);
        for (int i = 0; i < department.getNumberOfMembers(); i++) {
            if (department.getMember(i) == person) department.setMember(i, person.toHRPerson(modus, pwd));
        }
        LogBook.getLogBookInstance().addEntry(new UserAction(hrPerson, person, UserAction.CHANGE_VALUE).getEntry());
        fire();
    }

    /**
     * changes an HR-Person to a regular person
     *
     * @param uuid of the person
     * @throws InputMismatchException if uuid belongs to a regular person
     */
    public void changeToPerson(String uuid) throws InputMismatchException {
        Person person = getPersonByUuid(uuid);
        if (person instanceof HRPerson)
            changeToPerson((HRPerson) person);
        else throw new InputMismatchException();
    }

    /**
     * changes an HR_Person to a regulat person
     *
     * @param person to be changed
     */
    private void changeToPerson(HRPerson person) {
        Department department = getDepartmentByPerson(person);
        for (int i = 0; i < department.getNumberOfMembers(); i++) {
            if (department.getMember(i) == person) department.setMember(i, person);
        }
        LogBook.getLogBookInstance().addEntry(new UserAction(hrPerson, person, UserAction.CHANGE_VALUE).getEntry());
        fire();
    }

    /**
     * test if the function is in use by a person
     *
     * @param function to test for
     * @return true -> in use, false -> not in use
     */
    private boolean isFunctionInUse(String function) {
        Vector<Person> people = getAllPeople();
        for (Person person : people) {
            if (StringListCompare.stringContains(getFunctionsByUuid(person.getUuid()), function)) return true;
        }
        return false;
    }

    /**
     * test f the team is in use by a person
     *
     * @param team to test for
     * @return true -> in use, false -> not in use
     */
    private boolean isTeamInUse(String team) {
        Vector<Person> people = getAllPeople();
        for (Person person : people) {
            if (StringListCompare.stringContains(getTeamsByUuid(person.getUuid()), team)) return true;
        }
        return false;
    }

    /**
     * test if the department is in use of a person
     *
     * @param department to tst for
     * @return true -> in use, false -> not in use
     */
    private boolean isDepartmentInUse(Department department) {
        return (department.getNumberOfMembers() > 0);
    }

    /**
     * sets teh company (Override)
     *
     * @param company to set
     */
    public void setCompany(Company company) {
        this.company = company;
    }

    /**
     * sets teh HR_Person, to generate logs form
     *
     * @param hrPerson to set
     */
    public void setHrPerson(HRPerson hrPerson) {
        this.hrPerson = hrPerson;
    }

    /**
     * gest all HR-People
     *
     * @return HR-People
     */
    public Vector<HRPerson> getHRPeople() {
        Vector<HRPerson> hrPeople = new Vector<>();
        for (Person person : getAllPeople()) {
            if (person instanceof HRPerson) hrPeople.add((HRPerson) person);
        }
        return hrPeople;
    }

    /**
     * test if logged in
     *
     * @return true -> logged in, false -> logged out
     */
    public int isLoggedIn() {
        if (hrPerson == null) return -1;
        return hrPerson.getModus();
    }

    /**
     * gets the name of the company
     *
     * @return company-name
     */
    public String getCompanyName() {
        return company.getName();
    }

    /**
     * setst the name of the company
     *
     * @param name to be set
     */
    public void setCompanyName(String name) {
        company.setName(name);
        store();

    }

    /**
     * stores the application to a json file
     */
    public void store() {
        DataHandler.getInstance().saveApp();
    }
}