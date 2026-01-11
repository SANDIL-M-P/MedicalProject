import java.util.Date;


// Inheritance and Abstraction
public abstract class MedicalStaff extends Person {
    private String staffID;
    private String department;
    private Date hireDate;
    private double salary;
    private String workSchedule;
    private String qualifications;
    private String staffType;

    public MedicalStaff(String personID, String firstName, String lastName, Date dateOfBirth, String gender,
                        String contactNumber, String email, String address, String staffID, String department,
                        Date hireDate, double salary, String workSchedule, String qualifications, String staffType) {
        super(personID, firstName, lastName, dateOfBirth, gender, contactNumber, email, address);
        this.staffID = staffID;
        this.department = department;
        this.hireDate = hireDate;
        this.salary = salary;
        this.workSchedule = workSchedule;
        this.qualifications = qualifications;
        this.staffType = staffType;
    }

    // Abstract for polymorphism
    public abstract void performDuties();

    public void login() {
        // Simulate login
    }

    public void updateSchedule(String workSchedule) {
        this.workSchedule = workSchedule;
    }

    public String getWorkingHours() {
        return workSchedule;
    }

    // Inside MedicalStaff class (abstract class)
    public String getStaffID() {
        return staffID;
    }

    // Getters/setters
}