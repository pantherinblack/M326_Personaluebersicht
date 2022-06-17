package ch.bzz.employees;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.swing.*;

/**
 *
 *
 *
 */
/* start erro@author Lorenzo Giuntini (Medox36) */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = HRPerson.class, name = "HRPerson")})
/* end @author Lorenzo Giuntini (Medox36) */
public class Person {
    private String uuid;
    private ImageIcon photo;
    private String firstName;
    private String lastName;

    private Participation participation;

    public Person(String fName, String lName, ImageIcon photo) {
        setFirstName(fName);
        setLastName(lName);
        setPhoto(photo);
    }

    public ImageIcon getPhoto() {
        return photo;
    }

    public void setPhoto(ImageIcon photo) {
        this.photo = photo;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Participation getParticipation() {
        return participation;
    }

    public void setParticipation(Participation participation) {
        this.participation = participation;
    }

    public String getFullName() {
        return firstName+" "+lastName;
    }
}
