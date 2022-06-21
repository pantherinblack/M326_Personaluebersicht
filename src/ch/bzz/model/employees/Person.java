package ch.bzz.model.employees;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.nio.file.Path;
import java.util.UUID;

/**
 * Model for a person
 *
 * @author Kevin
 * @version 1.2
 * @since 18.05.2022
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = HRPerson.class, name = "HRPerson")})
public class Person {
    private String uuid;
    private Path photo;
    private String firstName;
    private String lastName;

    private Participation participation;

    /**
     * constructor sets default values for the person
     *
     * @param fName firstname
     * @param lName lastname
     * @param photo photo (ImageIcon)
     */
    public Person(String fName, String lName, Path photo) {
        this.uuid = UUID.randomUUID().toString();
        setFirstName(fName);
        setLastName(lName);
        setPhoto(photo);
        participation = new Participation();
    }

    /**
     * empty constructor needed for ObjectMapper
     */
    public Person() {
    }

    /**
     * gets the photo of the person
     *
     * @return photo (ImageIcon)
     */
    public Path getPhoto() {
        return photo;
    }

    /**
     * sets the photo of the person
     *
     * @param photo (ImageIcon)
     */
    public void setPhoto(Path photo) {
        this.photo = photo;
    }

    /**
     * gets the firstname of the person
     *
     * @return firstname
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * sets the firstname of the person
     *
     * @param firstName of the person
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * gets the lastname of the person
     *
     * @return lastname
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * sets the lastname of the person
     *
     * @param lastName of the person
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * gets the uuid of the person
     *
     * @return uuid
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * sets the uuid of the person
     * Needed for ObjectMapper
     *
     * @param uuid
     */
    protected void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * gets the participation of the person
     *
     * @return participation
     */
    public Participation getParticipation() {
        return participation;
    }

    /**
     * sets the participation for this person
     * Needed for ObjectMapper
     *
     * @param participation if the person
     */
    private void setParticipation(Participation participation) {
        this.participation = participation;
    }

    /**
     * gets the full name
     *
     * @return firstname + lastname combined
     */
    @JsonIgnore
    public String getFullName() {
        return firstName + " " + lastName;
    }

    /**
     * converts a Person to an HRPerson
     *
     * @param modus of the HRPerson
     * @param pwd   of the HRPerson
     * @return HRPerson
     */
    public HRPerson toHRPerson(int modus, String pwd) {
        HRPerson hrPerson = new HRPerson(firstName, lastName, photo, modus);
        hrPerson.setPwd(pwd);
        hrPerson.setUuid(uuid);
        return hrPerson;
    }
}
