import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HospitalManager {
    // File for patients (text/CSV format - human readable)
    private static final String PATIENTS_FILE = "data/patients.txt";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    // In-memory collections
    private List<Patient> patients = new ArrayList<>();
    private List<Doctor> doctors = new ArrayList<>();
    private List<Administrator> administrators = new ArrayList<>();
    private List<Appointment> appointments = new ArrayList<>();
    private List<MedicalRecord> records = new ArrayList<>();
    private List<Bill> bills = new ArrayList<>();

    // Singleton pattern
    private static HospitalManager instance;

    private HospitalManager() {
        // Load existing patients when manager is first created
        loadPatients();
    }

    public static HospitalManager getInstance() {
        if (instance == null) {
            instance = new HospitalManager();
        }
        return instance;
    }

    // ──────────────────────────────────────────────────────────────
    // Patient Management
    // ──────────────────────────────────────────────────────────────
    public void addPatient(Patient p) {
        patients.add(p);
        savePatients();  // Auto-save after adding (optional - can be removed if only want on exit)
    }

    public List<Patient> getPatients() {
        return new ArrayList<>(patients); // return copy to protect encapsulation
    }

    // Add similar add/get methods for other entities when needed
    public void addDoctor(Doctor d) { doctors.add(d); }
    public List<Doctor> getDoctors() { return new ArrayList<>(doctors); }
    // ... etc.

    // ──────────────────────────────────────────────────────────────
    // Load Patients from text file (CSV format)
    // ──────────────────────────────────────────────────────────────
    private void loadPatients() {
        File file = new File(PATIENTS_FILE);
        if (!file.exists()) {
            System.out.println("No patients data file found. Starting with empty list.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNumber = 0;

            while ((line = br.readLine()) != null) {
                lineNumber++;
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue; // skip empty lines & comments

                String[] parts = line.split(",", -1); // -1 = keep trailing empty fields
                if (parts.length < 14) {
                    System.out.println("Invalid data at line " + lineNumber + ": too few fields");
                    continue;
                }

                try {
                    String personID         = parts[0].trim();
                    String firstName        = parts[1].trim();
                    String lastName         = parts[2].trim();
                    Date dob                = DATE_FORMAT.parse(parts[3].trim());
                    String gender           = parts[4].trim();
                    String contactNumber    = parts[5].trim();
                    String email            = parts[6].trim();
                    String address          = parts[7].trim();
                    String patientID        = parts[8].trim();
                    Date registrationDate   = DATE_FORMAT.parse(parts[9].trim());
                    String bloodGroup       = parts[10].trim();
                    String allergies        = parts[11].trim();
                    String emergencyContact = parts[12].trim();
                    String insuranceInfo    = parts[13].trim();

                    Patient patient = new Patient(
                        personID, firstName, lastName, dob, gender,
                        contactNumber, email, address,
                        patientID, registrationDate, bloodGroup,
                        allergies, emergencyContact, insuranceInfo
                    );

                    patients.add(patient);
                } catch (ParseException e) {
                    System.err.println("Date parsing error at line " + lineNumber + ": " + e.getMessage());
                } catch (Exception e) {
                    System.err.println("Error creating patient at line " + lineNumber + ": " + e.getMessage());
                }
            }
            System.out.println("Successfully loaded " + patients.size() + " patients from file.");
        } catch (IOException e) {
            System.err.println("Error reading patients file: " + e.getMessage());
        }
    }

    // ──────────────────────────────────────────────────────────────
    // Save Patients to text file (CSV format)
    // ──────────────────────────────────────────────────────────────
    public void savePatients() {
        try {
            // Ensure directory exists
            new File("data").mkdirs();

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(PATIENTS_FILE))) {
                // Optional header (makes file easier to understand)
                bw.write("# personID,firstName,lastName,dateOfBirth,gender,contactNumber,email,address," +
                        "patientID,registrationDate,bloodGroup,allergies,emergencyContact,insuranceInfo");
                bw.newLine();

                for (Patient p : patients) {
                    String line = String.join(",",
                        escapeCsv(p.getPersonID()),
                        escapeCsv(p.getFirstName()),
                        escapeCsv(p.getLastName()),
                        DATE_FORMAT.format(p.getDateOfBirth()),
                        escapeCsv(p.getGender()),
                        escapeCsv(p.getContactNumber()),
                        escapeCsv(p.getEmail()),
                        escapeCsv(p.getAddress()),
                        escapeCsv(p.getPatientID()),
                        DATE_FORMAT.format(p.getRegistrationDate()),
                        escapeCsv(p.getBloodGroup()),
                        escapeCsv(p.getAllergies()),
                        escapeCsv(p.getEmergencyContact()),
                        escapeCsv(p.getInsuranceInfo())
                    );
                    bw.write(line);
                    bw.newLine();
                }
                System.out.println("Saved " + patients.size() + " patients to " + PATIENTS_FILE);
            }
        } catch (IOException e) {
            System.err.println("Error saving patients to file: " + e.getMessage());
        }
    }

    // Simple CSV escape (handle commas & quotes in data)
    private String escapeCsv(String value) {
        if (value == null) return "";
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }

    // ──────────────────────────────────────────────────────────────
    // Auto-save on application exit
    // ──────────────────────────────────────────────────────────────
    public static void setupAutoSave() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Application is closing... saving data...");
            HospitalManager.getInstance().savePatients();
            // Add saveDoctors(), saveAppointments() etc. later when implemented
        }));
    }
}