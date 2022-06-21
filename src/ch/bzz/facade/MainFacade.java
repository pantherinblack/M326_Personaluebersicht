package ch.bzz.facade;

import ch.bzz.exception.DuplicateEntryException;
import ch.bzz.exception.InUseException;
import ch.bzz.exception.NotExistentException;
import ch.bzz.interfaces.ModelListener;
import ch.bzz.interfaces.ViewListener;
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
import java.util.*;

public class MainFacade {
    private static MainFacade instance = null;
    private final Vector<ModelListener> modelListeners = new Vector<>();
    private final Vector<ViewListener> viewListeners = new Vector<>();
    private Company company;
    private HRPerson hrPerson;

    private MainFacade() {
        company = DataHandler.getInstance().loadApp();
    }

    public static MainFacade getInstance() {
        if (instance == null) instance = new MainFacade();
        return instance;
    }

    public Person getPersonByUuid(String uuid) throws NotExistentException {
        for (int d = 0; d < company.getNumberOfDepartments(); d++) {
            for (int p = 0; p < company.getDepartment(d).getNumberOfMembers(); p++) {
                if (company.getDepartment(d).getMember(p).getUuid().equals(uuid))
                    return company.getDepartment(d).getMember(p);
            }
        }
        throw new NotExistentException();
    }

    private Department getDepartmentByPerson(Person person) throws NotExistentException {
        for (int d = 0; d < company.getNumberOfDepartments(); d++) {
            for (int p = 0; p < company.getDepartment(d).getNumberOfMembers(); p++) {
                if (company.getDepartment(d).getMember(p) == person)
                    return company.getDepartment(d);
            }
        }
        throw new NotExistentException();
    }

    private Participation getParticipationByPerson(Person person) {
        return person.getParticipation();
    }

    private Vector<String> getTeamsByParticipation(Participation participation) {
        return participation.getTeams();
    }

    private Vector<String> getFunctionByParticipation(Participation participation) {
        return participation.getFunctions();
    }

    public Department getDepartmentByName(String name) throws NotExistentException {
        for (int d = 0; d < company.getNumberOfDepartments(); d++) {
            if (company.getDepartment(d).getName().equals(name)) return company.getDepartment(d);
        }
        throw new NotExistentException();
    }

    public Path getPhotoByUuid(String uuid) {
        return getPersonByUuid(uuid).getPhoto();
    }


    //TODO
    public String getFirstNameByUuid(String uuid) {
        return getPersonByUuid(uuid).getFirstName();
    }

    //TODO
    public String getLastNameByUuid(String uuid) {
        return getPersonByUuid(uuid).getLastName();
    }

    public String getFullNameByUuid(String uuid) {
        return getPersonByUuid(uuid).getFirstName() + " " + getPersonByUuid(uuid).getLastName();
    }

    public int getIndexOfDepartment(String department) {
        for (int i= 0; i < getAllDepartments().size(); i++) {
            if (getAllDepartments().get(i).getName().equals(department)) return i;
        }
        return 0;
    }

    public Department getDepartmentByUuid(String uuid) {
        return getDepartmentByPerson(getPersonByUuid(uuid));
    }

    public Vector<String> getTeamsByUuid(String uuid) {
        return getTeamsByParticipation(getParticipationByPerson(getPersonByUuid(uuid)));
    }

    public Vector<String> getFunctionsByUuid(String uuid) {
        return getFunctionByParticipation(getParticipationByPerson(getPersonByUuid(uuid)));
    }

    public void setPhotoByUuid(String uuid, Path photo) {
        getPersonByUuid(uuid).setPhoto(photo);
        fire();
    }

    public void setFullNameByUuid(String uuid, String fullName) {
        Person person = getPersonByUuid(uuid);
        person.setFirstName(fullName.split(" ")[0]);
        person.setLastName(fullName.split(" ")[1]);
        fire();
    }

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

    public void createPerson(String fName, String lName, Path photo, String department) {
        Person person = new Person(fName, lName, photo);
        getDepartmentByName(department).addMember(person);
        LogBook.getLogBookInstance().addEntry(new UserAction(hrPerson, person, UserAction.CREATE_PERSON).getEntry());
        fire();
    }

    public void removePerson(String uuid) throws NotExistentException {
        Department department = getDepartmentByUuid(uuid);
        for (int i = 0; i < department.getNumberOfMembers(); i++) {
            if (department.getMember(i) == getPersonByUuid(uuid)) {
                department.removeMember(i);
                LogBook.getLogBookInstance().addEntry(new UserAction(hrPerson, getPersonByUuid(uuid), UserAction.DELETE_PERSON).getEntry());
                fire();
                return;
            }
        }
        throw new NotExistentException();
    }

    public Vector<Person> getAllPeople() {
        Vector<Person> people = new Vector<>();
        for (int i = 0; i < company.getNumberOfDepartments(); i++) {
            for (int j = 0; j < company.getDepartment(i).getNumberOfMembers(); j++) {
                people.add(company.getDepartment(i).getMember(j));
            }
        }
        return new Vector<>(people);
    }

    public void fire() {
        for (ModelListener modelListener : modelListeners) {
            modelListener.fireContentsChanged(this, 0, -1);
        }
        DataHandler.getInstance().saveApp();
    }

    public void fireSelected(String uuid) {
        for (ViewListener viewListener : viewListeners) {
            viewListener.fireChanges(uuid);
        }
    }

    public Person getPerson(int index) {
        return getAllPeople().get(index);
    }

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

    public Person getPerson(int index, String name, String function, String department, String team, String sort) {
        return sortPeople(name, function, department, team, sort).get(index);

    }

    public void addModelListener(ModelListener modelListener) {
        modelListeners.add(modelListener);
    }

    public void removeModelListener(ModelListener modelListener) {
        modelListeners.remove(modelListener);
    }

    public void addViewListener(ViewListener viewListener) {
        viewListeners.add(viewListener);
    }

    public void removeViewListener(ViewListener viewListener) {
        viewListeners.remove(viewListener);
    }

    public void addFunctionAtPerson(String uuid, String function) throws DuplicateEntryException, NotExistentException {
        if (!StringListCompare.stringContains(getFunctionsByUuid(uuid), function)) {
            if (isExistentFunction(function)) {
                getFunctionsByUuid(uuid).add(function);
                LogBook.getLogBookInstance().addEntry(new UserAction(hrPerson, getPersonByUuid(uuid), UserAction.CHANGE_VALUE).getEntry());
                fire();
                return;
            }
            throw new NotExistentException();
        }
        throw new DuplicateEntryException();
    }

    public void removeFunctionAtPerson(String uuid, String function) throws NotExistentException {
        if (StringListCompare.stringContains(getFunctionsByUuid(uuid), function)) {
            if (isExistentFunction(function)) {
                getFunctionsByUuid(uuid).remove(function);
                LogBook.getLogBookInstance().addEntry(new UserAction(hrPerson, getPersonByUuid(uuid), UserAction.CHANGE_VALUE).getEntry());
                fire();
                return;
            }
        }
        throw new NotExistentException();
    }

    public void setFunctionAtPerson(String uuid, String function, int index) throws DuplicateEntryException, NotExistentException {
        if (!StringListCompare.stringContains(getFunctionsByUuid(uuid), function)) {
            if (isExistentFunction(function)) {
                getFunctionsByUuid(uuid).set(index, function);
                LogBook.getLogBookInstance().addEntry(new UserAction(hrPerson, getPersonByUuid(uuid), UserAction.CHANGE_VALUE).getEntry());
                fire();
                return;
            }
            throw new NotExistentException();
        }
        throw new DuplicateEntryException();
    }

    public void addTeamAtPerson(String uuid, String team) throws DuplicateEntryException, NotExistentException {
        if (!StringListCompare.stringContains(getTeamsByUuid(uuid), team)) {
            if (isExistentTeam(team)) {
                getTeamsByUuid(uuid).add(team);
                LogBook.getLogBookInstance().addEntry(new UserAction(hrPerson, getPersonByUuid(uuid), UserAction.CHANGE_VALUE).getEntry());
                fire();
                return;
            }
            throw new NotExistentException();
        }
        throw new DuplicateEntryException();
    }

    public void removeTeamAtPerson(String uuid, String team) throws NotExistentException {
        if (StringListCompare.stringContains(getTeamsByUuid(uuid), team)) {
            if (isExistentTeam(team)) {
                getTeamsByUuid(uuid).remove(team);
                LogBook.getLogBookInstance().addEntry(new UserAction(hrPerson, getPersonByUuid(uuid), UserAction.CHANGE_VALUE).getEntry());
                fire();
                return;
            }
        }
        throw new NotExistentException();
    }

    public void setTeamAtPerson(String uuid, String team, int index) throws DuplicateEntryException, NotExistentException {
        if (!StringListCompare.stringContains(getTeamsByUuid(uuid), team)) {
            if (isExistentTeam(team)) {
                getTeamsByUuid(uuid).set(index, team);
                LogBook.getLogBookInstance().addEntry(new UserAction(hrPerson, getPersonByUuid(uuid), UserAction.CHANGE_VALUE).getEntry());
                fire();
                return;
            }
            throw new NotExistentException();
        }
        throw new DuplicateEntryException();
    }

    private boolean isExistentDepartment(String department) {
        Vector<Department> departments = company.getDepartments();
        for (Department department1 : departments) {
            if (department1.getName().equals(department)) return true;
        }
        return false;
    }

    private boolean isExistentDepartment(Department department) {
        for (int i = 0; i < company.getNumberOfDepartments(); i++) {
            if (company.getDepartment(i).getName().equals(department.getName())) return true;
        }
        return false;
    }

    private boolean isExistentFunction(String function) {
        for (int i = 0; i < company.getNumberOfFunction(); i++) {
            if (company.getFunction(i).equals(function)) return true;
        }
        return false;
    }

    private boolean isExistentTeam(String team) {
        for (int i = 0; i < company.getNumberOfTeams(); i++) {
            if (company.getTeam(i).equals(team)) return true;
        }
        return false;
    }

    public void changeDepartmentName(String oldName, String newName) {
        getDepartmentByName(oldName).setName(newName);
        fire();
    }

    public void changeTeamName(String oldName, String newName) throws NotExistentException {
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

    public void changeFunctionName(String oldName, String newName) throws NotExistentException {
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

    public Vector<Department> getAllDepartments() {
        return company.getDepartments();
    }

    public Vector<String> getAllFunctions() {
        return company.getFunctions().getDesignations();
    }

    public Vector<String> getAllTeams() {
        return company.getTeams().getDesignations();
    }

    public Department getDepartmentByIndex(int index) {
        return getAllDepartments().get(index);
    }

    public String getFunctionByIndex(int index) {
        return getAllFunctions().get(index);
    }

    public String getTeamByIndex(int index) {
        return getAllTeams().get(index);
    }

    public void addDepartment(Department department) throws DuplicateEntryException {
        if (isExistentDepartment(department)) throw new DuplicateEntryException();
        company.addDepartment(department);
        fire();
    }

    public void addDepartment(String department) {
        addDepartment(new Department(department));
    }

    public void addFunction(String function) throws DuplicateEntryException {
        if (isExistentFunction(function)) throw new DuplicateEntryException();
        company.addFunction(function);
        fire();
    }

    public void addTeam(String team) throws DuplicateEntryException {
        if (isExistentTeam(team)) throw new DuplicateEntryException();
        company.addTeam(team);
        fire();
    }

    public void removeDepartment(int index) throws InUseException {
        if (isDepartmentInUse(company.getDepartment(index))) throw new InUseException();
        company.removeDepartment(index);
        fire();
    }

    public void removeFunction(int index) throws InUseException {
        if (isFunctionInUse(getFunctionByIndex(index))) throw new InUseException();
        company.removeFunction(index);
        fire();
    }

    public void removeTeam(int index) throws InUseException {
        if (isTeamInUse(getTeamByIndex(index))) throw new InUseException();
        company.removeTeam(index);
        fire();
    }

    public void changeToHR(String uuid, int modus, String pwd) {
        changeToHR(getPersonByUuid(uuid), modus, pwd);
    }

    private void changeToHR(Person person, int modus, String pwd) {
        Department department = getDepartmentByPerson(person);
        for (int i = 0; i < department.getNumberOfMembers(); i++) {
            if (department.getMember(i) == person) department.setMember(i, person.toHRPerson(modus, pwd));
        }
        LogBook.getLogBookInstance().addEntry(new UserAction(hrPerson, person, UserAction.CHANGE_VALUE).getEntry());
        fire();
    }

    public void changeToPerson(String uuid) throws InputMismatchException {
        Person person = getPersonByUuid(uuid);
        if (person instanceof HRPerson)
            changeToPerson((HRPerson) person);
        else throw new InputMismatchException();
    }

    private void changeToPerson(HRPerson person) {
        Department department = getDepartmentByPerson(person);
        for (int i = 0; i < department.getNumberOfMembers(); i++) {
            if (department.getMember(i) == person) department.setMember(i, person);
        }
        LogBook.getLogBookInstance().addEntry(new UserAction(hrPerson, person, UserAction.CHANGE_VALUE).getEntry());
        fire();
    }

    private boolean isFunctionInUse(String function) {
        Vector<Person> people = getAllPeople();
        for (Person person : people) {
            if (StringListCompare.stringContains(getFunctionsByUuid(person.getUuid()), function)) return true;
        }
        return false;
    }

    private boolean isTeamInUse(String team) {
        Vector<Person> people = getAllPeople();
        for (Person person : people) {
            if (StringListCompare.stringContains(getTeamsByUuid(person.getUuid()), team)) return true;
        }
        return false;
    }

    private boolean isDepartmentInUse(String department) {
        return isDepartmentInUse(getDepartmentByName(department));
    }

    private boolean isDepartmentInUse(Department department) {
        return (department.getNumberOfMembers() > 0);
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public void setHrPerson(HRPerson hrPerson) {
        this.hrPerson = hrPerson;
    }

    public Vector<HRPerson> getHRPeople() {
        Vector<HRPerson> hrPeople = new Vector<>();
        for (Person person : getAllPeople()) {
            if (person instanceof HRPerson) hrPeople.add((HRPerson) person);
        }
        return hrPeople;
    }

    public int isLoggedIn() {
        if (hrPerson==null) return -1;
        return hrPerson.getModus();
    }

    public String getCompanyName() {
        return company.getName();
    }

    public void setCompanyName(String name) {
        company.setName(name);
        storeOnly();

    }

    public void storeOnly() {
        DataHandler.getInstance().saveApp();
    }
}