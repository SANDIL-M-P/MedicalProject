import java.io.Serializable;
import java.util.Date;

// Abstract class for abstraction - common to all people, can't instantiate directly
public abstract class Person implements Serializable {
    private String personID;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String gender;
    private String contactNumber;
    private String email;
    private String address;

    // Constructor
    public Person(String personID, String firstName, String lastName, Date dateOfBirth, String gender,
                  String contactNumber, String email, String address) {
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.contactNumber = contactNumber;
        this.email = email;
        this.address = address;
    }

    // Getters/Setters (encapsulation) - fully implemented
    public String getPersonID() { return personID; }
    public void setPersonID(String personID) { this.personID = personID; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public Date getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(Date dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    // Abstract method for polymorphism - subclasses override
    public abstract String getPersonDetails();

    public void updateContactInfo(String contactNumber, String email, String address) {
        this.contactNumber = contactNumber;
        this.email = email;
        this.address = address;
    }
}