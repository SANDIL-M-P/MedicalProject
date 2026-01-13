import java.io.Serializable;
import java.util.Date;

public class Nurse extends MedicalStaff implements Serializable {

    public Nurse(String personID, String firstName, String lastName, Date dateOfBirth, String gender,
                 String contactNumber, String email, String address,
                 String staffID, String department, Date hireDate, double salary,
                 String workSchedule, String qualifications, String staffType) {
        super(personID, firstName, lastName, dateOfBirth, gender, contactNumber, email, address,
              staffID, department, hireDate, salary, workSchedule, qualifications, staffType);
    }

    @Override
    public void performDuties() {
        System.out.println("Nurse " + getFirstName() + " is providing patient care...");
    }

    // Optional: override getPersonDetails() for better display
    @Override
    public String getPersonDetails() {
        return "Nurse ID: " + getStaffID() + ", Name: " + getFirstName() + " " + getLastName();
    }
}