package ch.bzz.model.company;

import ch.bzz.model.employees.Person;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Vector;

public class Department {
    private String name;
    private Vector<Person> members = new Vector<>();

    /**
     * constructor setting the name of the department
     *
     * @param name of the department
     */
    @JsonIgnore
    public Department(String name) {
        this.name = name;
    }

    /**
     * gets all people of the department
     * Needed for ObjectMapper
     *
     * @return list of people
     */
    public Vector<Person> getMembers() {
        return members;
    }

    /**
     * sets the people of the department
     * Needed for ObjectMapper
     *
     * @param members to be set
     */
    public void setMembers(Vector<Person> members) {
        this.members = members;
    }

    /**
     * gets the name of the department
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * adds a person to the department
     *
     * @param person to be added
     */
    @JsonIgnore
    public void addMember(Person person) {
        members.add(person);
    }

    /**
     * gets a person of the department using the index
     *
     * @param index of the requested person
     * @return person
     */
    @JsonIgnore
    public Person getMember(int index) {
        return members.get(index);
    }

    /**
     * removes a person of the department
     *
     * @param index of the department
     */
    @JsonIgnore
    public void removeMember(int index) {
        members.remove(index);
    }

    /**
     * gets the number of people in the department
     *
     * @return number of people
     */
    @JsonIgnore
    public int getNumberOfMembers() {
        return members.size();
    }

    /**
     * sets a person at a specific position
     *
     * @param index  of the person
     * @param person to set
     */
    @JsonIgnore
    public void setMember(int index, Person person) {
        members.set(index, person);
    }
}
