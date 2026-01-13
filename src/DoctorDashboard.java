import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class DoctorDashboard extends JFrame {

    private final Doctor currentDoctor;

    public DoctorDashboard(Doctor doctor) {
        this.currentDoctor = doctor;

        setTitle("Doctor Dashboard - Dr. " + doctor.getFirstName() + " " + doctor.getLastName());
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridLayout(5, 1, 15, 25));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 80, 40, 80));

        // Welcome
        JLabel welcome = new JLabel("Welcome, Dr. " + doctor.getFirstName() + " " + doctor.getLastName() + "!", SwingConstants.CENTER);
        welcome.setFont(new Font("Arial", Font.BOLD, 28));
        mainPanel.add(welcome);

        // Button 1: View My Appointments
        JButton viewApptsBtn = new JButton("View My Appointments");
        viewApptsBtn.setFont(new Font("Arial", Font.PLAIN, 18));
        viewApptsBtn.addActionListener(e -> showMyAppointmentsTable());
        mainPanel.add(viewApptsBtn);

        // Button 2: Check Patient Medical Records
        JButton viewRecordsBtn = new JButton("Check Patient Medical Records");
        viewRecordsBtn.setFont(new Font("Arial", Font.PLAIN, 18));
        viewRecordsBtn.addActionListener(e -> showPatientRecordsSelection());
        mainPanel.add(viewRecordsBtn);

        // Button 3: Add Medical Record
        JButton addRecordBtn = new JButton("Add New Medical Record");
        addRecordBtn.setFont(new Font("Arial", Font.PLAIN, 18));
        addRecordBtn.addActionListener(e -> openAddRecordForm());
        mainPanel.add(addRecordBtn);

        add(mainPanel);

            // Logout button (bottom right)
    JButton logoutBtn = new JButton("Logout");
    logoutBtn.setFont(new Font("Arial", Font.PLAIN, 16));
    logoutBtn.setPreferredSize(new Dimension(120, 40));
    logoutBtn.addActionListener(e -> {
        dispose();
        new LoginFrame().setVisible(true);
    });

    JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    bottomPanel.add(logoutBtn);
    bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 15, 20));
    add(bottomPanel, BorderLayout.SOUTH);

    }

    // Show only this doctor's appointments in a table
    private void showMyAppointmentsTable() {
        List<Appointment> allAppts = HospitalManager.getInstance().getAppointments();
        List<Appointment> myAppts = allAppts.stream()
                .filter(a -> a.getDoctor().getStaffID().equals(currentDoctor.getStaffID()))
                .collect(Collectors.toList());

        if (myAppts.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No appointments scheduled for you.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String[] columns = {"App ID", "Patient Name", "Date", "Time", "Status", "Type", "Notes"};
        Object[][] data = new Object[myAppts.size()][columns.length];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for (int i = 0; i < myAppts.size(); i++) {
            Appointment a = myAppts.get(i);
            data[i][0] = a.getAppointmentID();
            data[i][1] = a.getPatient().getFirstName() + " " + a.getPatient().getLastName();
            data[i][2] = sdf.format(a.getAppointmentDate());
            data[i][3] = a.getAppointmentTime();
            data[i][4] = a.getStatus();
            data[i][5] = a.getType();
            data[i][6] = a.getNotes().isEmpty() ? "—" : a.getNotes();
        }

        showTableDialog(data, columns, "My Scheduled Appointments (" + myAppts.size() + ")");
    }

    // Select patient → show their medical history table
    private void showPatientRecordsSelection() {
        List<Appointment> myAppts = HospitalManager.getInstance().getAppointments().stream()
                .filter(a -> a.getDoctor().getStaffID().equals(currentDoctor.getStaffID()))
                .collect(Collectors.toList());

        if (myAppts.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No patients available to view records.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String[] options = myAppts.stream()
                .map(a -> a.getPatient().getFirstName() + " " + a.getPatient().getLastName() + " (ID: " + a.getPatient().getPatientID() + ")")
                .toArray(String[]::new);

        String choice = (String) JOptionPane.showInputDialog(this,
                "Select patient to view medical records:", "Patient Selection",
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (choice == null) return;

        String patientID = choice.substring(choice.lastIndexOf("ID: ") + 4, choice.lastIndexOf(")"));
        Patient patient = HospitalManager.getInstance().findPatientByID(patientID);

        if (patient == null || patient.getMedicalHistory().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No medical records found for this patient.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String[] columns = {"Date", "Symptoms", "Diagnosis", "Treatment", "Prescription"};
        Object[][] data = new Object[patient.getMedicalHistory().size()][columns.length];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        int row = 0;
        for (MedicalRecord rec : patient.getMedicalHistory()) {
            data[row][0] = sdf.format(rec.getVisitDate());
            data[row][1] = rec.getSymptoms().isEmpty() ? "—" : rec.getSymptoms();
            data[row][2] = rec.getDiagnosis().isEmpty() ? "—" : rec.getDiagnosis();
            data[row][3] = rec.getTreatment().isEmpty() ? "—" : rec.getTreatment();
            data[row][4] = rec.getPrescription().isEmpty() ? "—" : rec.getPrescription();
            row++;
        }

        showTableDialog(data, columns, "Medical History - " + patient.getFirstName() + " " + patient.getLastName());
    }

    // Simple form to add new medical record
    private void openAddRecordForm() {
        List<Appointment> myAppts = HospitalManager.getInstance().getAppointments().stream()
                .filter(a -> a.getDoctor().getStaffID().equals(currentDoctor.getStaffID()))
                .collect(Collectors.toList());

        if (myAppts.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No patients available to add record.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String[] options = myAppts.stream()
                .map(a -> a.getPatient().getFirstName() + " " + a.getPatient().getLastName() + " (ID: " + a.getPatient().getPatientID() + ")")
                .toArray(String[]::new);

        String choice = (String) JOptionPane.showInputDialog(this,
                "Select patient to add record for:", "Select Patient",
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (choice == null) return;

        String patientID = choice.substring(choice.lastIndexOf("ID: ") + 4, choice.lastIndexOf(")"));
        Patient patient = HospitalManager.getInstance().findPatientByID(patientID);

        // Input fields
        JTextField symptomsField = new JTextField(30);
        JTextField diagnosisField = new JTextField(30);
        JTextField treatmentField = new JTextField(30);
        JTextField prescriptionField = new JTextField(30);

        JPanel inputPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        inputPanel.add(new JLabel("Symptoms:"));
        inputPanel.add(symptomsField);
        inputPanel.add(new JLabel("Diagnosis:"));
        inputPanel.add(diagnosisField);
        inputPanel.add(new JLabel("Treatment:"));
        inputPanel.add(treatmentField);
        inputPanel.add(new JLabel("Prescription:"));
        inputPanel.add(prescriptionField);

        int result = JOptionPane.showConfirmDialog(this, inputPanel,
                "Add Medical Record for " + patient.getFirstName() + " " + patient.getLastName(),
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            currentDoctor.addMedicalRecord(patient,
                    symptomsField.getText().trim(),
                    diagnosisField.getText().trim(),
                    treatmentField.getText().trim(),
                    prescriptionField.getText().trim());

            JOptionPane.showMessageDialog(this, "Medical record added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Reusable table dialog
    private void showTableDialog(Object[][] data, String[] columns, String title) {
        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFillsViewportHeight(true);
        table.setRowHeight(28);
        table.setGridColor(new Color(220, 220, 220));
        table.setShowGrid(true);
        table.setAutoCreateRowSorter(true);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(900, 500));

        JDialog dialog = new JDialog(this, title, true);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.setSize(950, 600);
        dialog.setLocationRelativeTo(this);

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        dialog.add(titleLabel, BorderLayout.NORTH);

        dialog.add(scrollPane, BorderLayout.CENTER);

        JButton closeBtn = new JButton("Close");
        closeBtn.addActionListener(e -> dialog.dispose());

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(closeBtn);
        dialog.add(bottomPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }
}