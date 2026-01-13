import java.io.Serializable;
import java.util.Date;

public class Appointment implements Serializable {
    private String appointmentID;
    private Patient patient;
    private Doctor doctor;
    private Date appointmentDate;
    private String appointmentTime;
    private String status;
    private String type;
    private String notes;

    public Appointment(String appointmentID, Patient patient, Doctor doctor,
                       Date appointmentDate, String appointmentTime,
                       String status, String type, String notes) {
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

    // Required getters for saving & displaying
    public String getAppointmentID()     { return appointmentID; }
    public Patient getPatient()          { return patient; }
    public Doctor  getDoctor()           { return doctor; }
    public Date    getAppointmentDate()  { return appointmentDate; }
    public String  getAppointmentTime()  { return appointmentTime; }
    public String  getStatus()           { return status; }
    public String  getType()             { return type; }
    public String  getNotes()            { return notes; }

    // Optional setter if needed later
    public void setStatus(String status) { this.status = status; }
}