
package ch.bzz.facades;

import ch.bzz.company.Company;
import ch.bzz.company.Department;
import ch.bzz.employees.Participation;
import ch.bzz.employees.Person;
import ch.bzz.log.DataHandler;

import javax.swing.*;
import java.util.*;

public class MainFacade {
    private Company company;
    private static MainFacade instance;
    private List<ChangesModel> changesModels = new ArrayList<>();

    private MainFacade() {
        company = DataHandler.getInstance().loadApp();
    }

    private Person getPersonByUuid(String uuid) {
        for (int d = 0; d < company.getNumberOfDepartments(); d++) {
            for (int p = 0; p < company.getDepartment(d).getNumberOfMembers(); p++) {
                if (company.getDepartment(d).getMember(p).getUuid().equals(uuid))
                    return company.getDepartment(d).getMember(p);
            }
        }
        return null;
    }

    private Department getDepartmentByPerson(Person person) {
        for (int d = 0; d < company.getNumberOfDepartments(); d++) {
            for (int p = 0; p < company.getDepartment(d).getNumberOfMembers(); p++) {
                if (company.getDepartment(d).getMember(p).equals(person))
                    return company.getDepartment(d);
            }
        }
        return null;
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

    private Department getDepartmentByName(String name) {
        for (int d = 0; d< company.getNumberOfDepartments(); d++) {
            if (company.getDepartment(d).getName().equals(name)) return company.getDepartment(d);
        }
        return null;
    }

    public ImageIcon getPhotoByUuid(String uuid) {
        Person person = getPersonByUuid(uuid);
        if (person != null) return person.getPhoto();
        return null;
    }

    public String getLastNameByUuid(String uuid) {
        Person person = getPersonByUuid(uuid);
        if (person!=null) return person.getLastName();
        return null;
    }

    public String getFirstNameByUuid(String uuid) {
        Person person = getPersonByUuid(uuid);
        if (person!=null) return person.getFirstName();
        return null;
    }

    public Department getDepartmentByUuid(String uuid) {
        return getDepartmentByPerson(getPersonByUuid(uuid));
    }

    public Vector<String> getTeamsByUuid(String uuid) {
        Person person = getPersonByUuid(uuid);
        if (person!= null) {
            Participation participation = getParticipationByPerson(person);
            if (participation!= null) {
                return getTeamsByParticipation(participation);
            }
        }
        return null;
    }

    public Vector<String> getFunctionsByUuid(String uuid) {
        Person person = getPersonByUuid(uuid);
        if (person!= null) {
            Participation participation = getParticipationByPerson(person);
            if (participation!= null) {
                return getFunctionByParticipation(participation);
            }
        }
        return null;
    }

    public boolean setPhotoByUuid(String uuid, ImageIcon photo) {
        Person person = getPersonByUuid(uuid);
        if (person!=null) {
            person.setPhoto(photo);
            fire();
            return true;
        }
        fire();
        return false;
    }

    public boolean setFullNameByUuid(String uuid, String fullName) {
        Person person = getPersonByUuid(uuid);
        if (person!=null) {
            person.setFirstName(fullName.split(" ")[0]);
            person.setLastName(fullName.split(" ")[1]);
            fire();
            return true;
        }
        fire();
        return false;
    }

    public boolean changeDepartmentByUuid(String uuid, String department) {
        Person person = getPersonByUuid(uuid);
        Department department1 = getDepartmentByPerson(person);
        Department department2 = getDepartmentByName(department);

        if (person != null && department1!= null && department2!= null) {
            for (int i = 0; i<department1.getNumberOfMembers(); i++) {
                if (department1.getMember(i).equals(person)) department1.removeMember(i);
            }
            department2.addMember(person);
            fire();
            return true;
        }
        fire();
        return false;
    }

    public boolean addTeamByUuid(String uuid, String function) {
        for (int i = 0; i< company.getNumberOfFunction(); i++) {
            if (company.getFunction(i).equals(function)) {
                Person person = getPersonByUuid(uuid);
                if (person != null) {
                    Participation participation = getParticipationByPerson(person);
                    if (participation != null) {
                        participation.addFunction(function);
                        fire();
                        return true;
                    }
                }
            }
        }
        fire();
        return false;
    }

    public boolean createPerson(String fName, String lName, ImageIcon photo, String department) {
        Department department1 = getDepartmentByName(department);
        if (department1 != null) {
            department1.addMember(new Person(fName,lName, photo));
            fire();
            return true;
        }
        fire();
        return false;
    }

    public boolean removePerson(String uuid) {
        Department department = getDepartmentByUuid(uuid);
        Person person = getPersonByUuid(uuid);
        for (int i = 0; i< department.getNumberOfMembers(); i++) {
            if (department.getMember(i).equals(person)) {
                department.removeMember(i);
                fire();
                return true;
            }
        }
        fire();
        return false;
    }

    public List<Person> getAllPeople() {
        List<Person> people = new ArrayList<>();
        for (int i=0; i<company.getNumberOfDepartments(); i++) {
            for (int j=0; j<company.getDepartment(i).getNumberOfMembers(); j++) {
                Person person = company.getDepartment(i).getMember(i);
                people.add(person);
            }
        }
        return new ArrayList<>(people);
    }

    public void fire() {
        for (ChangesModel changesModel : changesModels) {
            changesModel.fireContentsChanged(this, 0,-1);
        }
    }

    public Person getPerson(int index) {
        return getPerson(index,null, null, null,null, null);
    }

    public List<Person> sortPeople(String name, String function, String department, String team, String sort) {
        List<Person> people = getAllPeople();
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
                case "dsc":
                    people.sort(Comparator.comparing(Person::getFullName));
                    Collections.reverse(people);
            }
        }
        return people;
    }

    public Person getPerson(int index, String name, String function, String department, String team, String sort) {
        return sortPeople(name, function, department, team, sort).get(index);

    }

    public static MainFacade getInstance() {
        return instance;
    }

    public void addModel(ChangesModel changesModel) {
        changesModels.add(changesModel);
    }

    public void removeModel(ChangesModel changesModel) {
        changesModels.remove(changesModel);
    }

    public void addFunction(String uuid,String function, int index) {
        getFunctionsByUuid(uuid).insertElementAt(function, index);
        fire();
    }

    public void addFunction(String uuid, String function) {
        addFunction(uuid, function, getFunctionsByUuid(uuid).size());
        fire();
    }

    public void addTeam(String uuid, String department, int index) {
        getTeamsByUuid(uuid).insertElementAt(department, index);
    }

    public void addTeam(String uuid, String department) {
        addFunction(uuid,department, getTeamsByUuid(uuid).size());
    }
}
