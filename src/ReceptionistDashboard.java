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

        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 80, 40, 80));

        JLabel welcome = new JLabel("Welcome, Receptionist!", SwingConstants.CENTER);
        welcome.setFont(new Font("Arial", Font.BOLD, 28));
        panel.add(welcome);

        JButton registerBtn = new JButton("Register New Patient");
        registerBtn.setFont(new Font("Arial", Font.PLAIN, 18));
        registerBtn.addActionListener(e -> new PatientRegistrationForm().setVisible(true));
        panel.add(registerBtn);

        JButton scheduleBtn = new JButton("Schedule Appointment");
        scheduleBtn.setFont(new Font("Arial", Font.PLAIN, 18));
        scheduleBtn.addActionListener(e -> 
            JOptionPane.showMessageDialog(this, "Appointment scheduling - coming soon!", 
                                         "Info", JOptionPane.INFORMATION_MESSAGE));
        panel.add(scheduleBtn);

        JButton viewPatientsBtn = new JButton("View All Patients");
        viewPatientsBtn.setFont(new Font("Arial", Font.PLAIN, 18));
        viewPatientsBtn.addActionListener(e -> showPatientsTable());
        panel.add(viewPatientsBtn);

        add(panel);
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
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFillsViewportHeight(true);
        table.setRowHeight(28);
        table.setGridColor(new Color(220, 220, 220));
        table.setShowGrid(true);

        table.setAutoCreateRowSorter(true);  // sorting

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(950, 500));

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

    // Optional: for quick testing of this class alone
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ReceptionistDashboard().setVisible(true));
    }
}