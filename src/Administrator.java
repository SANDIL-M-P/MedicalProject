import java.io.Serializable;
import java.util.Date;

public class Administrator extends Person {

    private final String adminID;
    private final String accessLevel;
    private final String department;
    private final Date hireDate;
    private final String adminType;

    public Administrator(String personID, String firstName, String lastName, Date dateOfBirth, String gender,
                         String contactNumber, String email, String address,
                         String adminID, String accessLevel, String department, Date hireDate, String adminType) {
        super(personID, firstName, lastName, dateOfBirth, gender, contactNumber, email, address);
        this.adminID = adminID;
        this.accessLevel = accessLevel;
        this.department = department;
        this.hireDate = hireDate;
        this.adminType = adminType;
    }

    @Override
    public String getPersonDetails() {
        return "Administrator ID: " + adminID + ", Name: " + getFirstName() + " " + getLastName() +
               ", Access Level: " + accessLevel;
    }

    public void login() {
        System.out.println("Administrator logged in: " + adminID);
    }

    public String generateReport() {
        return "Sample Report: Total Patients - " + HospitalManager.getInstance().getPatients().size() +
               ", Total Doctors - " + HospitalManager.getInstance().getDoctors().size();
    }

    public void processPayment(Bill bill) {
        bill.setPaymentStatus("Paid");
    }

    // New: Register a new Doctor
    public void registerDoctor(Doctor doctor) {
        HospitalManager.getInstance().addDoctor(doctor);
        System.out.println("Doctor registered: " + doctor.getPersonDetails());
    }

    // New: Register a new Nurse (as MedicalStaff subclass)
    public void registerNurse(Nurse nurse) {
        HospitalManager.getInstance().addNurse(nurse);
        System.out.println("Nurse registered: " + nurse.getPersonDetails());
    }

    // Getters
    public String getAdminID() { return adminID; }
    public String getAccessLevel() { return accessLevel; }
    public String getDepartment() { return department; }
    public Date getHireDate() { return hireDate; }
    public String getAdminType() { return adminType; }
}