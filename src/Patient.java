import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// Inheritance: extends Person
public class Patient extends Person implements Serializable {
    private String patientID;
    private Date registrationDate;
    private String bloodGroup;
    private String allergies;
    private String emergencyContact;
    private String insuranceInfo;
    private List<MedicalRecord> medicalHistory = new ArrayList<>(); // Composition: has records

    public Patient(String personID, String firstName, String lastName, Date dateOfBirth, String gender,
                   String contactNumber, String email, String address, String patientID, Date registrationDate,
                   String bloodGroup, String allergies, String emergencyContact, String insuranceInfo) {
        super(personID, firstName, lastName, dateOfBirth, gender, contactNumber, email, address);
        this.patientID = patientID;
        this.registrationDate = registrationDate;
        this.bloodGroup = bloodGroup;
        this.allergies = allergies;
        this.emergencyContact = emergencyContact;
        this.insuranceInfo = insuranceInfo;
    }

    // Polymorphism: override
    @Override
    public String getPersonDetails() {
        return "Patient ID: " + patientID + ", Name: " + getFirstName() + " " + getLastName();
    }

    public void registerPatient() {
        // Logic: add to hospital's patient list (we'll add later)
    }

    public void updateMedicalInfo(String allergies, String insuranceInfo) {
        this.allergies = allergies;
        this.insuranceInfo = insuranceInfo;
    }

    public List<MedicalRecord> getMedicalHistory() {
        return medicalHistory;
    }

    public void addMedicalRecord(MedicalRecord record) {
        medicalHistory.add(record);
    }

    // Add getters/setters
    public String getPatientID() { return patientID; }
    public Date getRegistrationDate() { return registrationDate; }
    public String getBloodGroup() { return bloodGroup; }
    public String getAllergies() { return allergies; }
    public String getEmergencyContact() { return emergencyContact; }
    public String getInsuranceInfo() { return insuranceInfo; }
}