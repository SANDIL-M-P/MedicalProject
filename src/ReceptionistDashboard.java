import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ReceptionistDashboard extends JFrame {

    public ReceptionistDashboard() {
        setTitle("Receptionist Dashboard - MediCare Hospital");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main content panel with vertical buttons
        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 80, 40, 80));

        // Welcome label
        JLabel welcome = new JLabel("Welcome, Receptionist!", SwingConstants.CENTER);
        welcome.setFont(new Font("Arial", Font.BOLD, 28));
        panel.add(welcome);

        // Register New Patient button
        JButton registerBtn = new JButton("Register New Patient");
        registerBtn.setFont(new Font("Arial", Font.PLAIN, 18));
        registerBtn.addActionListener(e -> new PatientRegistrationForm().setVisible(true));
        panel.add(registerBtn);

        // Schedule Appointment button → now opens the real form
        JButton scheduleBtn = new JButton("Schedule Appointment");
        scheduleBtn.setFont(new Font("Arial", Font.PLAIN, 18));
        scheduleBtn.addActionListener(e -> {
            new AppointmentForm().setVisible(true);
        });
        panel.add(scheduleBtn);

        // View All Patients button (table view)
        JButton viewPatientsBtn = new JButton("View All Patients");
        viewPatientsBtn.setFont(new Font("Arial", Font.PLAIN, 18));
        viewPatientsBtn.addActionListener(e -> showPatientsTable());
        panel.add(viewPatientsBtn);


        JButton viewAppointmentsBtn = new JButton("View All Appointments");
        viewAppointmentsBtn.setFont(new Font("Arial", Font.PLAIN, 18));
        viewAppointmentsBtn.addActionListener(e -> showAllAppointmentsTable());
        panel.add(viewAppointmentsBtn);
        // Optional: Add more buttons later (e.g., View Appointments, Generate Bill, etc.)
        // For now we keep it simple with 3 main actions

        add(panel);

                // Logout button (bottom right)
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        logoutBtn.setPreferredSize(new Dimension(120, 40));
        logoutBtn.addActionListener(e -> {
            dispose(); // close current dashboard
            new LoginFrame().setVisible(true); // open login screen
        });

        // Add to a bottom panel
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(logoutBtn);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 15, 20));
        add(bottomPanel, BorderLayout.SOUTH);
            }

    private void showAllAppointmentsTable() {
    List<Appointment> appointments = HospitalManager.getInstance().getAppointments();

    if (appointments.isEmpty()) {
        JOptionPane.showMessageDialog(this, 
            "No appointments scheduled yet.", 
            "Information", 
            JOptionPane.INFORMATION_MESSAGE);
        return;
    }

    String[] columnNames = {
        "Appointment ID", "Patient", "Doctor", "Date", "Time", 
        "Status", "Type", "Notes"
    };

    Object[][] data = new Object[appointments.size()][columnNames.length];
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    for (int i = 0; i < appointments.size(); i++) {
        Appointment a = appointments.get(i);
        data[i][0] = a.getAppointmentID();
        data[i][1] = a.getPatient().getFirstName() + " " + a.getPatient().getLastName();
        data[i][2] = a.getDoctor().getFirstName() + " " + a.getDoctor().getLastName();
        data[i][3] = dateFormat.format(a.getAppointmentDate());
        data[i][4] = a.getAppointmentTime();
        data[i][5] = a.getStatus();
        data[i][6] = a.getType();
        data[i][7] = a.getNotes().isEmpty() ? "-" : a.getNotes();
    }

    DefaultTableModel model = new DefaultTableModel(data, columnNames) {
        @Override
        public boolean isCellEditable(int row, int column) { return false; }
    };

    JTable table = new JTable(model);
    table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    table.setFillsViewportHeight(true);
    table.setRowHeight(28);
    table.setGridColor(new Color(220, 220, 220));
    table.setShowGrid(true);
    table.setAutoCreateRowSorter(true);

    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.setPreferredSize(new Dimension(950, 500));

    JDialog dialog = new JDialog(this, "All Scheduled Appointments", true);
    dialog.setLayout(new BorderLayout(10, 10));
    dialog.setSize(1000, 650);
    dialog.setLocationRelativeTo(this);

    JLabel title = new JLabel("All Appointments (" + appointments.size() + ")", SwingConstants.CENTER);
    title.setFont(new Font("Arial", Font.BOLD, 20));
    title.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
    dialog.add(title, BorderLayout.NORTH);

    dialog.add(scrollPane, BorderLayout.CENTER);

    JButton closeBtn = new JButton("Close");
    closeBtn.addActionListener(e -> dialog.dispose());

    JPanel bottom = new JPanel();
    bottom.add(closeBtn);
    dialog.add(bottom, BorderLayout.SOUTH);

    dialog.setVisible(true);
}

    private void showPatientsTable() {
        List<Patient> patients = HospitalManager.getInstance().getPatients();

        if (patients.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "No patients registered yet.",
                "Information",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String[] columnNames = {
            "Patient ID", "Full Name", "Date of Birth", "Gender",
            "Blood Group", "Allergies", "Emergency Contact",
            "Insurance Info", "Registered On"
        };

        Object[][] data = new Object[patients.size()][columnNames.length];
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (int i = 0; i < patients.size(); i++) {
            Patient p = patients.get(i);
            data[i][0] = p.getPatientID();
            data[i][1] = p.getFirstName() + " " + p.getLastName();
            data[i][2] = (p.getDateOfBirth() != null) ? dateFormat.format(p.getDateOfBirth()) : "N/A";
            data[i][3] = p.getGender();
            data[i][4] = p.getBloodGroup();
            data[i][5] = p.getAllergies().isEmpty() ? "-" : p.getAllergies();
            data[i][6] = p.getEmergencyContact();
            data[i][7] = p.getInsuranceInfo().isEmpty() ? "-" : p.getInsuranceInfo();
            data[i][8] = dateFormat.format(p.getRegistrationDate());
        }

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // table is read-only
            }
        };

        JTable table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFillsViewportHeight(true);
        table.setRowHeight(28);
        table.setGridColor(new Color(220, 220, 220));
        table.setShowGrid(true);

        // Enable column sorting
        table.setAutoCreateRowSorter(true);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(950, 500));

        // Dialog window
        JDialog dialog = new JDialog(this, "All Registered Patients", true);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.setSize(1000, 650);
        dialog.setLocationRelativeTo(this);

        JLabel titleLabel = new JLabel("Registered Patients (" + patients.size() + ")", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        dialog.add(titleLabel, BorderLayout.NORTH);

        dialog.add(scrollPane, BorderLayout.CENTER);

        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Arial", Font.PLAIN, 16));
        closeButton.setPreferredSize(new Dimension(120, 40));
        closeButton.addActionListener(e -> dialog.dispose());

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 15, 0));
        bottomPanel.add(closeButton);
        dialog.add(bottomPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    // Optional: for standalone testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ReceptionistDashboard().setVisible(true));
    }
}
