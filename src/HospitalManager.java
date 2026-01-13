import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HospitalManager {
    // File for patients (text/CSV format - human readable)
    private static final String PATIENTS_FILE = "data/patients.txt";
    private static final String DOCTORS_FILE  = "data/doctors.txt";
    private static final String NURSES_FILE   = "data/nurses.txt";
    private static final String APPOINTMENTS_FILE = "data/appointments.txt";

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    // In-memory collections
    private List<Patient> patients = new ArrayList<>();
    private List<Doctor> doctors = new ArrayList<>();
    private List<Nurse> nurses = new ArrayList<>();
    private List<Administrator> administrators = new ArrayList<>();
    private List<Appointment> appointments = new ArrayList<>();
    private List<MedicalRecord> records = new ArrayList<>();
    private List<Bill> bills = new ArrayList<>();

    // Singleton pattern
    private static HospitalManager instance;

    private HospitalManager() {
        // Sample data (only if collections are empty after loading)
        if (doctors.isEmpty()) {
            doctors.add(new Doctor(
                "D001", "Nimal", "Perera", new Date(80, 5, 15), "Male",
                "0777123456", "nimal@medicare.lk", "No. 45, Kotte Road, Pita Kotte",
                "DOC001", "Cardiology", new Date(), 250000, "Mon-Fri 8-4",
                "MBBS, MD, FRCP", "Consultant",
                "Cardiology", 2500.0, "SLMC-98765", 18
            ));
        }

        // Load all data
        loadPatients();
        loadDoctors();
        loadNurses();
        loadAppointments();
    }

    public static HospitalManager getInstance() {
        if (instance == null) {
            instance = new HospitalManager();
        }
        return instance;
    }

    // ──────────────────────────────────────────────────────────────
    // Patient Management (existing)
    // ──────────────────────────────────────────────────────────────
    public void addPatient(Patient p) {
        patients.add(p);
        savePatients();
    }

    public List<Patient> getPatients() {
        return new ArrayList<>(patients);
    }

    // ──────────────────────────────────────────────────────────────
    // Doctor Management (new)
    // ──────────────────────────────────────────────────────────────
    public void addDoctor(Doctor d) {
        doctors.add(d);
        saveDoctors();
    }

    public List<Doctor> getDoctors() {
        return new ArrayList<>(doctors);
    }

    // ──────────────────────────────────────────────────────────────
    // Nurse Management (new)
    // ──────────────────────────────────────────────────────────────
    public void addNurse(Nurse n) {
        nurses.add(n);
        saveNurses();
    }

    public List<Nurse> getNurses() {
        return new ArrayList<>(nurses);
    }

    // ──────────────────────────────────────────────────────────────
    // Appointment Management (existing)
    // ──────────────────────────────────────────────────────────────
    public void addAppointment(Appointment ap) {
        appointments.add(ap);
        saveAppointments();
    }

    public List<Appointment> getAppointments() {
        return new ArrayList<>(appointments);
    }

    // ──────────────────────────────────────────────────────────────
    // Load / Save Patients (existing)
    // ──────────────────────────────────────────────────────────────
    private void loadPatients() {
        File file = new File(PATIENTS_FILE);
        if (!file.exists()) {
            System.out.println("No patients data file found. Starting empty.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNumber = 0;
            while ((line = br.readLine()) != null) {
                lineNumber++;
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;

                String[] parts = line.split(",", -1);
                if (parts.length < 14) continue;

                try {
                    String personID = parts[0].trim();
                    String firstName = parts[1].trim();
                    String lastName = parts[2].trim();
                    Date dob = DATE_FORMAT.parse(parts[3].trim());
                    String gender = parts[4].trim();
                    String contactNumber = parts[5].trim();
                    String email = parts[6].trim();
                    String address = parts[7].trim();
                    String patientID = parts[8].trim();
                    Date regDate = DATE_FORMAT.parse(parts[9].trim());
                    String bloodGroup = parts[10].trim();
                    String allergies = parts[11].trim();
                    String emergencyContact = parts[12].trim();
                    String insuranceInfo = parts[13].trim();

                    Patient patient = new Patient(
                        personID, firstName, lastName, dob, gender,
                        contactNumber, email, address,
                        patientID, regDate, bloodGroup,
                        allergies, emergencyContact, insuranceInfo
                    );
                    patients.add(patient);
                } catch (Exception e) {
                    System.err.println("Error parsing patient line " + lineNumber + ": " + e.getMessage());
                }
            }
            System.out.println("Loaded " + patients.size() + " patients.");
        } catch (IOException e) {
            System.err.println("Error reading patients file: " + e.getMessage());
        }
    }

    public void savePatients() {
        try {
            new File("data").mkdirs();
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(PATIENTS_FILE))) {
                bw.write("# personID,firstName,lastName,dateOfBirth,gender,contactNumber,email,address,patientID,registrationDate,bloodGroup,allergies,emergencyContact,insuranceInfo");
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
                System.out.println("Saved " + patients.size() + " patients.");
            }
        } catch (IOException e) {
            System.err.println("Error saving patients: " + e.getMessage());
        }
    }

    // ──────────────────────────────────────────────────────────────
    // Load / Save Doctors (new)
    // ──────────────────────────────────────────────────────────────
    private void loadDoctors() {
        File file = new File(DOCTORS_FILE);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;

                String[] parts = line.split(",", -1);
                if (parts.length < 19) continue;  // adjust based on Doctor fields

                try {
                    String personID = parts[0].trim();
                    String firstName = parts[1].trim();
                    String lastName = parts[2].trim();
                    Date dob = DATE_FORMAT.parse(parts[3].trim());
                    String gender = parts[4].trim();
                    String contact = parts[5].trim();
                    String email = parts[6].trim();
                    String address = parts[7].trim();
                    String staffID = parts[8].trim();
                    String dept = parts[9].trim();
                    Date hireDate = DATE_FORMAT.parse(parts[10].trim());
                    double salary = Double.parseDouble(parts[11].trim());
                    String schedule = parts[12].trim();
                    String qual = parts[13].trim();
                    String type = parts[14].trim();
                    String spec = parts[15].trim();
                    double fee = Double.parseDouble(parts[16].trim());
                    String license = parts[17].trim();
                    int exp = Integer.parseInt(parts[18].trim());

                    Doctor d = new Doctor(personID, firstName, lastName, dob, gender, contact, email, address,
                                          staffID, dept, hireDate, salary, schedule, qual, type,
                                          spec, fee, license, exp);
                    doctors.add(d);
                } catch (Exception e) {
                    System.err.println("Error parsing doctor line: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading doctors: " + e.getMessage());
        }
    }

    public void saveDoctors() {
        try {
            new File("data").mkdirs();
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(DOCTORS_FILE))) {
                bw.write("# personID,firstName,lastName,dob,gender,contact,email,address,staffID,dept,hireDate,salary,schedule,qual,type,specialization,fee,license,yearsExp");
                bw.newLine();

                for (Doctor d : doctors) {
                    String line = String.join(",",
                        escapeCsv(d.getPersonID()),
                        escapeCsv(d.getFirstName()),
                        escapeCsv(d.getLastName()),
                        DATE_FORMAT.format(d.getDateOfBirth()),
                        escapeCsv(d.getGender()),
                        escapeCsv(d.getContactNumber()),
                        escapeCsv(d.getEmail()),
                        escapeCsv(d.getAddress()),
                        escapeCsv(d.getStaffID()),
                        escapeCsv(d.getDepartment()),
                        DATE_FORMAT.format(d.getHireDate()),
                        String.valueOf(d.getSalary()),
                        escapeCsv(d.getWorkSchedule()),
                        escapeCsv(d.getQualifications()),
                        escapeCsv(d.getStaffType()),
                        escapeCsv(d.getSpecialization()),
                        String.valueOf(d.getConsultationFee()),
                        escapeCsv(d.getLicenseNumber()),
                        String.valueOf(d.getYearsOfExperience())
                    );
                    bw.write(line);
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Error saving doctors: " + e.getMessage());
        }
    }

    // ──────────────────────────────────────────────────────────────
    // Load / Save Nurses (new)
    // ──────────────────────────────────────────────────────────────
    private void loadNurses() {
        File file = new File(NURSES_FILE);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;

                String[] parts = line.split(",", -1);
                if (parts.length < 15) continue;

                try {
                    String personID = parts[0].trim();
                    String firstName = parts[1].trim();
                    String lastName = parts[2].trim();
                    Date dob = DATE_FORMAT.parse(parts[3].trim());
                    String gender = parts[4].trim();
                    String contact = parts[5].trim();
                    String email = parts[6].trim();
                    String address = parts[7].trim();
                    String staffID = parts[8].trim();
                    String dept = parts[9].trim();
                    Date hireDate = DATE_FORMAT.parse(parts[10].trim());
                    double salary = Double.parseDouble(parts[11].trim());
                    String schedule = parts[12].trim();
                    String qual = parts[13].trim();
                    String type = parts[14].trim();

                    Nurse n = new Nurse(personID, firstName, lastName, dob, gender, contact, email, address,
                                        staffID, dept, hireDate, salary, schedule, qual, type);
                    nurses.add(n);
                } catch (Exception e) {
                    System.err.println("Error parsing nurse line: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading nurses: " + e.getMessage());
        }
    }

    public void saveNurses() {
        try {
            new File("data").mkdirs();
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(NURSES_FILE))) {
                bw.write("# personID,firstName,lastName,dob,gender,contact,email,address,staffID,dept,hireDate,salary,schedule,qual,type");
                bw.newLine();

                for (Nurse n : nurses) {
                    String line = String.join(",",
                        escapeCsv(n.getPersonID()),
                        escapeCsv(n.getFirstName()),
                        escapeCsv(n.getLastName()),
                        DATE_FORMAT.format(n.getDateOfBirth()),
                        escapeCsv(n.getGender()),
                        escapeCsv(n.getContactNumber()),
                        escapeCsv(n.getEmail()),
                        escapeCsv(n.getAddress()),
                        escapeCsv(n.getStaffID()),
                        escapeCsv(n.getDepartment()),
                        DATE_FORMAT.format(n.getHireDate()),
                        String.valueOf(n.getSalary()),
                        escapeCsv(n.getWorkSchedule()),
                        escapeCsv(n.getQualifications()),
                        escapeCsv(n.getStaffType())
                        
                    );
                    bw.write(line);
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Error saving nurses: " + e.getMessage());
        }
    }

    // ──────────────────────────────────────────────────────────────
    // Appointments (existing - unchanged)
    // ──────────────────────────────────────────────────────────────
    private void loadAppointments() {
        // your existing code...
    }

    public void saveAppointments() {
        // your existing code...
    }

    public Patient findPatientByID(String id) {
        for (Patient p : patients) {
            if (p.getPatientID().equals(id)) return p;
        }
        return null;
    }

    public Doctor findDoctorByID(String id) {
        for (Doctor d : doctors) {
            if (d.getStaffID().equals(id)) return d;
        }
        return null;
    }

    // ──────────────────────────────────────────────────────────────
    // Auto-save
    // ──────────────────────────────────────────────────────────────
    public static void setupAutoSave() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            HospitalManager mgr = getInstance();
            mgr.savePatients();
            mgr.saveDoctors();
            mgr.saveNurses();
            mgr.saveAppointments();
        }));
    }

    // Escape CSV (existing)
    private String escapeCsv(String value) {
        if (value == null) return "";
        value = value.replace("\"", "\"\"");
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value + "\"";
        }
        return value;
    }
}