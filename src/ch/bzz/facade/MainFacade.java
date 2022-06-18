
package ch.bzz.facade;

import ch.bzz.exception.DuplicateEntryException;
import ch.bzz.exception.NotExistentException;
import ch.bzz.model.company.Company;
import ch.bzz.model.company.Department;
import ch.bzz.model.employees.Participation;
import ch.bzz.model.employees.Person;
import ch.bzz.interfaces.ChangesModel;
import ch.bzz.util.DataHandler;

import javax.swing.*;
import java.util.*;

public class MainFacade {
    private final Company company;
    private static MainFacade instance = null;
    private final Vector<ChangesModel> changesModels = new Vector<>();

    private MainFacade() {
        company = DataHandler.getInstance().loadApp();
    }

    private Person getPersonByUuid(String uuid) throws NotExistentException {
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
                if (company.getDepartment(d).getMember(p).equals(person))
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

    private Department getDepartmentByName(String name) throws NotExistentException {
        for (int d = 0; d < company.getNumberOfDepartments(); d++) {
            if (company.getDepartment(d).getName().equals(name)) return company.getDepartment(d);
        }
        throw new NotExistentException();
    }

    public ImageIcon getPhotoByUuid(String uuid) {
        return getPersonByUuid(uuid).getPhoto();
    }

    public String getLastNameByUuid(String uuid) {
        return getPersonByUuid(uuid).getLastName();
    }

    public String getFirstNameByUuid(String uuid) {
        return getPersonByUuid(uuid).getFirstName();
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

    public void setPhotoByUuid(String uuid, ImageIcon photo) {
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

        for (int i = 0; i<department1.getNumberOfMembers(); i++) {
            if (department1.getMember(i).equals(person)) {
                department1.removeMember(i);
                getDepartmentByName(department).addMember(person);
                fire();
                return;
            }
        }
        throw new NotExistentException();
    }

    public void addTeamByUuid(String uuid, String function) throws NotExistentException {
        for (int i = 0; i< company.getNumberOfFunction(); i++) {
            if (company.getFunction(i).equals(function)) {
                getParticipationByPerson(getPersonByUuid(uuid)).addFunction(function);
                fire();
                return;
            }
        }
        throw new NotExistentException();
    }

    public void createPerson(String fName, String lName, ImageIcon photo, String department) {
        getDepartmentByName(department).addMember(new Person(fName,lName, photo));
        fire();
    }

    public void removePerson(String uuid) throws NotExistentException {
        Department department = getDepartmentByUuid(uuid);
        for (int i = 0; i< department.getNumberOfMembers(); i++) {
            if (department.getMember(i).equals(getPersonByUuid(uuid))) {
                department.removeMember(i);
                fire();
                return;
            }
        }
        throw new NotExistentException();
    }

    public Vector<Person> getAllPeople() {
        Vector<Person> people = new Vector<>();
        for (int i=0; i<company.getNumberOfDepartments(); i++) {
            for (int j=0; j<company.getDepartment(i).getNumberOfMembers(); j++) {
                people.add(company.getDepartment(i).getMember(j));
            }
        }
        return new Vector<>(people);
    }

    public void fire() {
        for (ChangesModel changesModel : changesModels) {
            changesModel.fireContentsChanged(this, 0,-1);
        }
        DataHandler.getInstance().saveApp();
    }

    public Person getPerson(int index) {
        return getAllPeople().get(index);
    }

    public Vector<Person> sortPeople(String name, String function, String department, String team, String sort) {
        Vector<Person> people = getAllPeople();
        if (function!= null && !function.isEmpty()) {
            people.removeIf(person -> !getFunctionsByUuid(person.getUuid()).contains(function));
        }

        if (department!= null && !department.isEmpty()) {
            people.removeIf(person -> !getDepartmentByPerson(person).getName().equals(department));
        }

        if (team!= null && !team.isEmpty()) {
            people.removeIf(person -> !getTeamsByUuid(person.getUuid()).contains(team));
        }

        if (name!=null && !name.isEmpty()) {
            people.removeIf(person -> !person.getFullName().contains(name));
        }

        if (sort!=null && !sort.isEmpty()) {

            switch (sort) {
                case "asc":
                    people.sort(Comparator.comparing(Person::getFullName));
                    break;
                case "dsc":
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

    public static MainFacade getInstance() {
        if (instance==null) instance = new MainFacade();
        return instance;
    }

    public void addModel(ChangesModel changesModel) {
        changesModels.add(changesModel);
    }

    public void removeModel(ChangesModel changesModel) {
        changesModels.remove(changesModel);
    }

    public void addFunction(String uuid,String function, int index) throws DuplicateEntryException, NotExistentException {
        if (!stringContains(getFunctionsByUuid(uuid), function)) {
            if (isExistentFunction(function)) {
                getFunctionsByUuid(uuid).insertElementAt(function, index);
                fire();
                return;
            }
            throw new NotExistentException();
        }
        throw new DuplicateEntryException();
    }

    public void addFunction(String uuid, String function) {
        addFunction(uuid, function, getFunctionsByUuid(uuid).size());
    }

    public void addTeam(String uuid, String team, int index) throws DuplicateEntryException, NotExistentException {
        if (!stringContains(getTeamsByUuid(uuid), team)) {
            if (isExistentTeam(team)) {
                getTeamsByUuid(uuid).insertElementAt(team, index);
                fire();
                return;
            }
            throw new NotExistentException();
        }
        throw new DuplicateEntryException();
    }

    public void addTeam(String uuid, String department) {
        addTeam(uuid,department, getTeamsByUuid(uuid).size());
    }

    public Company getCompany() {
        return company;
    }

    public boolean isExistentDepartment(String department) {
        List<Department> departments = company.getDepartments();
        for (Department department1 : departments) {
            if (department1.getName().equals(department)) return true;
        }
        return false;
    }

    public boolean isExistentDepartment(Department department) {
        return company.getDepartments().contains(department);
    }

    public boolean isExistentFunction(String function) {
        for (int i=0; i < company.getNumberOfFunction(); i++) {
            if (company.getFunction(i).equals(function)) return true;
        }
        return false;
    }

    public boolean isExistentTeam(String team) {
        for (int i=0; i < company.getNumberOfTeams(); i++) {
            if (company.getTeam(i).equals(team)) return true;
        }
        return false;
    }

    public void changeDepartmentName(String oldName, String newName) {

    }

    public boolean stringContains(List<String> list, String object) {
        for (String obj : list) {
            if (object.equals(obj)) return true;
        }
        return false;
    }
}
