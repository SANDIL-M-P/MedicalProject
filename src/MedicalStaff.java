import java.io.Serializable;
import java.util.Date;

public abstract class MedicalStaff extends Person implements Serializable {
    private String staffID;
    private String department;
    private Date hireDate;
    private double salary;
    private String workSchedule;
    private String qualifications;
    private String staffType;

    public MedicalStaff(String personID, String firstName, String lastName, Date dateOfBirth, String gender,
                        String contactNumber, String email, String address,
                        String staffID, String department, Date hireDate, double salary,
                        String workSchedule, String qualifications, String staffType) {
        super(personID, firstName, lastName, dateOfBirth, gender, contactNumber, email, address);
        this.staffID = staffID;
        this.department = department;
        this.hireDate = hireDate;
        this.salary = salary;
        this.workSchedule = workSchedule;
        this.qualifications = qualifications;
        this.staffType = staffType;
    }

    // Abstract method (must be overridden by subclasses)
    public abstract void performDuties();

    // Public getters (needed for saveNurses(), saveDoctors(), etc.)
    public String getStaffID()        { return staffID; }
    public String getDepartment()     { return department; }
    public Date   getHireDate()       { return hireDate; }
    public double getSalary()         { return salary; }
    public String getWorkSchedule()   { return workSchedule; }
    public String getQualifications() { return qualifications; }
    public String getStaffType()      { return staffType; }

    // Optional setters if needed (but not required for save)
    public void updateSchedule(String schedule) { this.workSchedule = schedule; }

    // Polymorphic display (can be overridden)
    @Override
    public String getPersonDetails() {
        return "Staff ID: " + staffID + ", Name: " + getFirstName() + " " + getLastName();
    }
}