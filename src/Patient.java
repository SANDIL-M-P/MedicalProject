import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Represents a patient in the MediCare Hospital system.
 * Inherits from Person and adds patient-specific attributes and medical history.
 */
public class Patient extends Person implements Serializable {

    private String patientID;
    private Date registrationDate;
    private String bloodGroup;
    private String allergies;
    private String emergencyContact;
    private String insuranceInfo;

    // Composition: Patient has a list of medical records
    private List<MedicalRecord> medicalHistory = new ArrayList<>();

    public Patient(String personID, String firstName, String lastName, Date dateOfBirth, String gender,
                   String contactNumber, String email, String address,
                   String patientID, Date registrationDate,
                   String bloodGroup, String allergies, String emergencyContact, String insuranceInfo) {
        super(personID, firstName, lastName, dateOfBirth, gender, contactNumber, email, address);
        this.patientID = patientID;
        this.registrationDate = registrationDate;
        this.bloodGroup = bloodGroup;
        this.allergies = allergies;
        this.emergencyContact = emergencyContact;
        this.insuranceInfo = insuranceInfo;
    }

    // Polymorphism: override abstract method from Person
    @Override
    public String getPersonDetails() {
        return "Patient ID: " + patientID + ", Name: " + getFirstName() + " " + getLastName();
    }

    /**
     * Placeholder method for registering patient (can be extended later)
     */
    public void registerPatient() {
        // Logic can be expanded (e.g., validation, hospital list addition)
    }

    /**
     * Updates medical information (allergies and insurance)
     */
    public void updateMedicalInfo(String allergies, String insuranceInfo) {
        this.allergies = allergies;
        this.insuranceInfo = insuranceInfo;
    }

    /**
     * Returns a read-only view of the patient's medical history.
     * Returns a defensive copy to prevent external modification.
     *
     * @return unmodifiable list of medical records
     */
    public List<MedicalRecord> getMedicalHistory() {
        return Collections.unmodifiableList(medicalHistory);
    }

    /**
     * Adds a new medical record to the patient's history.
     *
     * @param record the medical record to add
     */
    public void addMedicalRecord(MedicalRecord record) {
        if (record != null) {
            medicalHistory.add(record);
        }
    }

    // ──────────────────────────────────────────────────────────────
    // Getters for all fields (encapsulation)
    // ──────────────────────────────────────────────────────────────

    public String getPatientID() {
        return patientID;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public String getAllergies() {
        return allergies;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public String getInsuranceInfo() {
        return insuranceInfo;
    }

    // Optional setters (only if needed - many fields are immutable after creation)
    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public void setInsuranceInfo(String insuranceInfo) {
        this.insuranceInfo = insuranceInfo;
    }
}