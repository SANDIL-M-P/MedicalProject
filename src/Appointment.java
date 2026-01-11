import java.io.Serializable;
import java.util.Date;

public class Appointment implements Serializable {
    private String appointmentID;
    private Patient patient;
    private Doctor doctor;
    private Date appointmentDate;
    private String appointmentTime;
    private String status; // e.g., "Scheduled", "Completed", "Cancelled"
    private String type;   // e.g., "Consultation", "Follow-up"
    private String notes;

    public Appointment(String appointmentID, Patient patient, Doctor doctor, Date appointmentDate,
                       String appointmentTime, String status, String type, String notes) {
        this.appointmentID = appointmentID;
        this.patient = patient;
        this.doctor = doctor;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.status = status;
        this.type = type;
        this.notes = notes;
    }

    public void scheduleAppointment() {
        this.status = "Scheduled";
    }

    public void cancelAppointment() {
        this.status = "Cancelled";
    }

    public void rescheduleAppointment(Date newDate, String newTime) {
        this.appointmentDate = newDate;
        this.appointmentTime = newTime;
        this.status = "Rescheduled";
    }

    // Getters/setters
    public Patient getPatient() { return patient; }
    public Doctor getDoctor() { return doctor; }
    public String getStatus() { return status; }
}