/*
package ch.bzz.Interface;

import ch.bzz.company.Company;
import ch.bzz.company.Department;
import ch.bzz.employees.Participation;
import ch.bzz.employees.Person;

import javax.swing.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;

public class PersonFacade {
    private Company company;

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
        Vector<String> cache = new Vector<>();
        for (int i = 0; i < participation.getNumberOfTeams(); i++) {
            cache.add(participation.getTeam(i));
        }
        return cache;
    }

    private Vector<String> getFunctionByParticipation(Participation participation) {
        Vector<String> cache = new Vector<>();
        for (int i = 0; i < participation.getNumberOfFunctions(); i++) {
            cache.add(participation.getFunction(i));
        }
        return cache;
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
            return true;
        }
        return false;
    }

    public boolean setFullNameByUuid(String uuid, String fullName) {
        Person person = getPersonByUuid(uuid);
        if (person!=null) {
            person.setFirstName(fullName.split(" ")[0]);
            person.setLastName(fullName.split(" ")[1]);
            return true;
        }
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
            return true;
        }
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
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean createPerson(String fName, String lName, ImageIcon photo, String department) {
        Department department1 = getDepartmentByName(department);
        if (department1 != null) {
            department1.addMember(new Person(fName,lName, photo));
            return true;
        }
        return false;
    }

    public boolean removePerson(String uuid) {
        Department department = getDepartmentByUuid(uuid);
        Person person = getPersonByUuid(uuid);
        for (int i = 0; i< department.getNumberOfMembers(); i++) {
            if (department.getMember(i).equals(person)) {
                department.removeMember(i);
                return true;
            }
        }
        return false;
    }

    public HashSet<HashMap<String, String>> getAllPeople() {
        HashSet<HashMap<String, String>> people = new HashSet<>();
        for (int i=0; i<company.getNumberOfDepartments(); i++) {
            for (int j=0; j<company.getDepartment(i).getNumberOfMembers(); j++) {
                Person person = company.getDepartment(i).getMember(i);
                HashMap<String, String> stringPerson = new HashMap<>();
                stringPerson.put("fname", person.getFirstName());
                stringPerson.put("lname", person.getLastName());
                stringPerson.put("uuid", person.getUuid());
                people.add(stringPerson);
            }
        }
        return people;
    }
}

 */
