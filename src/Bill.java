import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Bill implements Serializable {
    private String billID;
    private Patient patient;
    private List<String> services = new ArrayList<>();
    private List<String> medications = new ArrayList<>();
    private double consultationFees;
    private double testCharges;
    private double totalAmount;
    private String paymentStatus = "Pending";
    private double insuranceCoverage;
    private Date dueDate;

    public Bill(String billID, Patient patient, double consultationFees, double testCharges,
                double insuranceCoverage, Date dueDate) {
        this.billID = billID;
        this.patient = patient;
        this.consultationFees = consultationFees;
        this.testCharges = testCharges;
        this.insuranceCoverage = insuranceCoverage;
        this.dueDate = dueDate;
        calculateTotal();
    }

    public void calculateTotal() {
        totalAmount = consultationFees + testCharges - insuranceCoverage;
    }

    public void processPayment() {
        paymentStatus = "Paid";
    }

    public void applyInsuranceDiscount(double discount) {
        insuranceCoverage += discount;
        calculateTotal();
    }

    public void addService(String service) {
        services.add(service);
    }

    // ──────────────────────────────────────────────────────────────
    // Getters and Setters
    // ──────────────────────────────────────────────────────────────

    public String getBillID() {
        return billID;
    }

    public Patient getPatient() {
        return patient;
    }

    public List<String> getServices() {
        return new ArrayList<>(services); // defensive copy
    }

    public List<String> getMedications() {
        return new ArrayList<>(medications); // defensive copy
    }

    public double getConsultationFees() {
        return consultationFees;
    }

    public double getTestCharges() {
        return testCharges;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public double getInsuranceCoverage() {
        return insuranceCoverage;
    }

    public Date getDueDate() {
        return dueDate;
    }
}