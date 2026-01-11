import java.util.Date;

public class Administrator extends Person {   // ← Removed redundant Serializable

    private final String adminID;           // ← added final
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
        return "Sample Report: Total Patients - 50, Appointments Today - 12"; // placeholder
    }

    public void processPayment(Bill bill) {
        bill.setPaymentStatus("Paid");
    }

    // Getters (no setters needed since fields are final)
    public String getAdminID() { return adminID; }
    public String getAccessLevel() { return accessLevel; }
    public String getDepartment() { return department; }
    public Date getHireDate() { return hireDate; }
    public String getAdminType() { return adminType; }
}