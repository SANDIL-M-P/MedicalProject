import java.io.Serializable;
import java.util.Date;

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

    @Override
    public void performDuties() {
        System.out.println("Doctor " + getFirstName() + " is consulting patients...");
    }

    @Override
    public String getPersonDetails() {
        return "Doctor ID: " + getStaffID() + ", Name: " + getFirstName() + " " + getLastName() +
               ", Specialization: " + specialization;
    }

    // Method to add diagnosis & treatment to patient's record
    public void addMedicalRecord(Patient patient, String symptoms, String diagnosis, String treatment, String prescription) {
        MedicalRecord record = new MedicalRecord(
            "REC" + System.currentTimeMillis(),
            patient,
            this,
            new Date(),
            symptoms,
            diagnosis,
            treatment,
            prescription,
            "" // test results can be added later
        );
        patient.addMedicalRecord(record);
        System.out.println("Medical record added for patient: " + patient.getPatientID());
    }

    // Getters
    public String getSpecialization() { return specialization; }
    public double getConsultationFee() { return consultationFee; }
    public String getLicenseNumber() { return licenseNumber; }
    public int getYearsOfExperience() { return yearsOfExperience; }

    public String getDepartment() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDepartment'");
    }

    public Date getHireDate() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getHireDate'");
    }

    public double getSalary() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSalary'");
    }

    public String getWorkSchedule() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getWorkSchedule'");
    }

    public String getQualifications() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getQualifications'");
    }

    public String getStaffType() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getStaffType'");
    }
}