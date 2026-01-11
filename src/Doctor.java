import java.io.Serializable;
import java.util.Date;

// Inheritance
public class Doctor extends MedicalStaff implements Serializable {
    private String specialization;
    private double consultationFee;
    private String licenseNumber;
    private int yearsOfExperience;

    public Doctor(String personID, String firstName, String lastName, Date dateOfBirth, String gender,
                  String contactNumber, String email, String address, String staffID, String department,
                  Date hireDate, double salary, String workSchedule, String qualifications, String staffType,
                  String specialization, double consultationFee, String licenseNumber, int yearsOfExperience) {
        super(personID, firstName, lastName, dateOfBirth, gender, contactNumber, email, address, staffID, department,
              hireDate, salary, workSchedule, qualifications, staffType);
        this.specialization = specialization;
        this.consultationFee = consultationFee;
        this.licenseNumber = licenseNumber;
        this.yearsOfExperience = yearsOfExperience;
    }

    // Polymorphism: override from MedicalStaff
    @Override
    public void performDuties() {
        // Doctor-specific
        System.out.println("Diagnosing patient...");
    }

    // Polymorphism: override abstract from Person
    @Override
    public String getPersonDetails() {
        return "Doctor ID: " + getStaffID() + ", Name: " + getFirstName() + " " + getLastName() +
               ", Specialization: " + specialization;
    }

    public void diagnosePatient(Patient patient, String diagnosis) {
        // Add to record (implement later)
    }

    public void prescribeMedication(Patient patient, String medication) {
        // Logic (implement later)
    }

    // Add getters/setters for new fields
    public String getSpecialization() { return specialization; }
    public double getConsultationFee() { return consultationFee; }
    public String getLicenseNumber() { return licenseNumber; }
    public int getYearsOfExperience() { return yearsOfExperience; }
}