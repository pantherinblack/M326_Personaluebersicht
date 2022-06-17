package ch.bzz.company;

import ch.bzz.employees.Person;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Vector;

public class Department {

    public Department() {

    }
    public void setName(String name) {
        this.name = name;
    }

    public Vector<Person> getMembers() {
        return members;
    }

    public void setMembers(Vector<Person> members) {
        this.members = members;
    }

    private String name;
    private Vector<Person> members = new Vector<>();
    @JsonIgnore
    public Department(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    @JsonIgnore
    public void addMember(Person person) {
        members.add(person);
    }
    @JsonIgnore
    public Person getMember(int index) {
        return members.get(index);
    }
    @JsonIgnore
    public void removeMember(int index) {
        members.remove(index);
    }
    @JsonIgnore
    public int getNumberOfMembers() {
        return members.size();
    }
}
