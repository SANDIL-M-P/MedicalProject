import java.io.Serializable;
import java.util.Date;

public class MedicalRecord implements Serializable {
    private String recordID;
    private Patient patient;
    private Doctor doctor;
    private Date visitDate;
    private String symptoms;
    private String diagnosis;
    private String treatment;
    private String prescription;
    private String testResults;

    public MedicalRecord(String recordID, Patient patient, Doctor doctor, Date visitDate,
                         String symptoms, String diagnosis, String treatment,
                         String prescription, String testResults) {
        this.recordID = recordID;
        this.patient = patient;
        this.doctor = doctor;
        this.visitDate = visitDate;
        this.symptoms = symptoms;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        this.prescription = prescription;
        this.testResults = testResults;
    }

    public void createRecord() {
        // Logic to add to patient's history (handled in Patient class)
    }

    public void updateRecord(String newDiagnosis, String newTreatment) {
        this.diagnosis = newDiagnosis;
        this.treatment = newTreatment;
    }

    public String generateSummary() {
        return "Visit: " + visitDate + " | Diagnosis: " + diagnosis + " | Treatment: " + treatment;
    }

    // Getters/setters

    public Date getVisitDate() { return visitDate; }
    public String getSymptoms() { return symptoms; }
    public String getDiagnosis() { return diagnosis; }
    public String getTreatment() { return treatment; }
    public String getPrescription() { return prescription; }
}