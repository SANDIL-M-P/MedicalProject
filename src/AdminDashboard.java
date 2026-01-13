import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AdminDashboard extends JFrame {

    public AdminDashboard() {
        setTitle("Administrator Dashboard - MediCare Hospital");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridLayout(7, 1, 15, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 80, 40, 80));

        JLabel welcome = new JLabel("Welcome, Administrator!", SwingConstants.CENTER);
        welcome.setFont(new Font("Arial", Font.BOLD, 28));
        mainPanel.add(welcome);

        // Button 1: Register Doctor
        JButton regDoctorBtn = new JButton("Register New Doctor");
        regDoctorBtn.setFont(new Font("Arial", Font.PLAIN, 18));
        regDoctorBtn.addActionListener(e -> openDoctorRegistrationForm());
        mainPanel.add(regDoctorBtn);

        // Button 2: Display Doctor Details
        JButton viewDoctorsBtn = new JButton("View All Doctors");
        viewDoctorsBtn.setFont(new Font("Arial", Font.PLAIN, 18));
        viewDoctorsBtn.addActionListener(e -> showDoctorsTable());
        mainPanel.add(viewDoctorsBtn);

        // Button 3: Display All Appointments
        JButton viewApptsBtn = new JButton("View All Appointments");
        viewApptsBtn.setFont(new Font("Arial", Font.PLAIN, 18));
        viewApptsBtn.addActionListener(e -> showAllAppointmentsTable());
        mainPanel.add(viewApptsBtn);

        // Button 4: Display All Patients
        JButton viewPatientsBtn = new JButton("View All Patients");
        viewPatientsBtn.setFont(new Font("Arial", Font.PLAIN, 18));
        viewPatientsBtn.addActionListener(e -> showAllPatientsTable());
        mainPanel.add(viewPatientsBtn);

        // Button 5: Register Nurse
        JButton regNurseBtn = new JButton("Register New Nurse");
        regNurseBtn.setFont(new Font("Arial", Font.PLAIN, 18));
        regNurseBtn.addActionListener(e -> openNurseRegistrationForm());
        mainPanel.add(regNurseBtn);

        // Button 6: View Nurse Details
        JButton viewNursesBtn = new JButton("View All Nurses");
        viewNursesBtn.setFont(new Font("Arial", Font.PLAIN, 18));
        viewNursesBtn.addActionListener(e -> showNursesTable());
        mainPanel.add(viewNursesBtn);

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

    // 1. Doctor Registration Form
    private void openDoctorRegistrationForm() {
        JTextField firstName = new JTextField(20);
        JTextField lastName = new JTextField(20);
        JTextField staffID = new JTextField(15);
        JTextField specialization = new JTextField(20);
        JTextField license = new JTextField(15);
        JTextField yearsExp = new JTextField(5);

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.add(new JLabel("First Name:")); panel.add(firstName);
        panel.add(new JLabel("Last Name:")); panel.add(lastName);
        panel.add(new JLabel("Staff ID:")); panel.add(staffID);
        panel.add(new JLabel("Specialization:")); panel.add(specialization);
        panel.add(new JLabel("License Number:")); panel.add(license);
        panel.add(new JLabel("Years of Experience:")); panel.add(yearsExp);

        int result = JOptionPane.showConfirmDialog(this, panel, "Register New Doctor", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                int exp = Integer.parseInt(yearsExp.getText().trim());
                Doctor doctor = new Doctor(
                    "D" + System.currentTimeMillis(), firstName.getText().trim(), lastName.getText().trim(),
                    new Date(), "Not Specified", "N/A", "N/A", "N/A",
                    staffID.getText().trim(), "N/A", new Date(), 0, "N/A", "N/A", "Doctor",
                    specialization.getText().trim(), 2000.0, license.getText().trim(), exp
                );
                HospitalManager.getInstance().addDoctor(doctor);
                JOptionPane.showMessageDialog(this, "Doctor registered successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Years of experience must be a number!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // 2. Display All Doctors Table
    private void showDoctorsTable() {
        List<Doctor> doctors = HospitalManager.getInstance().getDoctors();
        if (doctors.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No doctors registered yet.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String[] columns = {"Staff ID", "Name", "Specialization", "License", "Years Exp"};
        Object[][] data = new Object[doctors.size()][columns.length];

        for (int i = 0; i < doctors.size(); i++) {
            Doctor d = doctors.get(i);
            data[i][0] = d.getStaffID();
            data[i][1] = d.getFirstName() + " " + d.getLastName();
            data[i][2] = d.getSpecialization();
            data[i][3] = d.getLicenseNumber();
            data[i][4] = d.getYearsOfExperience();
        }

        showTableDialog(data, columns, "All Doctors");
    }

    // 3. Display All Appointments Table
    private void showAllAppointmentsTable() {
        List<Appointment> appts = HospitalManager.getInstance().getAppointments();
        if (appts.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No appointments scheduled.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String[] columns = {"ID", "Patient", "Doctor", "Date", "Time", "Status", "Type"};
        Object[][] data = new Object[appts.size()][columns.length];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for (int i = 0; i < appts.size(); i++) {
            Appointment a = appts.get(i);
            data[i][0] = a.getAppointmentID();
            data[i][1] = a.getPatient().getFirstName() + " " + a.getPatient().getLastName();
            data[i][2] = a.getDoctor().getFirstName() + " " + a.getDoctor().getLastName();
            data[i][3] = sdf.format(a.getAppointmentDate());
            data[i][4] = a.getAppointmentTime();
            data[i][5] = a.getStatus();
            data[i][6] = a.getType();
        }

        showTableDialog(data, columns, "All Appointments");
    }

    // 4. Display All Patients Table
    private void showAllPatientsTable() {
        List<Patient> patients = HospitalManager.getInstance().getPatients();
        if (patients.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No patients registered.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String[] columns = {"Patient ID", "Name", "Blood Group", "Allergies", "Registered"};
        Object[][] data = new Object[patients.size()][columns.length];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for (int i = 0; i < patients.size(); i++) {
            Patient p = patients.get(i);
            data[i][0] = p.getPatientID();
            data[i][1] = p.getFirstName() + " " + p.getLastName();
            data[i][2] = p.getBloodGroup();
            data[i][3] = p.getAllergies().isEmpty() ? "—" : p.getAllergies();
            data[i][4] = sdf.format(p.getRegistrationDate());
        }

        showTableDialog(data, columns, "All Patients");
    }

    // 5. Nurse Registration Form
    private void openNurseRegistrationForm() {
        JTextField firstName = new JTextField(20);
        JTextField lastName = new JTextField(20);
        JTextField staffID = new JTextField(15);
        JTextField department = new JTextField(20);

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.add(new JLabel("First Name:")); panel.add(firstName);
        panel.add(new JLabel("Last Name:")); panel.add(lastName);
        panel.add(new JLabel("Staff ID:")); panel.add(staffID);
        panel.add(new JLabel("Department:")); panel.add(department);

        int result = JOptionPane.showConfirmDialog(this, panel, "Register New Nurse", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            Nurse nurse = new Nurse(
                "N" + System.currentTimeMillis(), firstName.getText().trim(), lastName.getText().trim(),
                new Date(), "Not Specified", "N/A", "N/A", "N/A",
                staffID.getText().trim(), department.getText().trim(), new Date(), 0, "N/A", "N/A", "Nurse"
            );
            HospitalManager.getInstance().addNurse(nurse);
            JOptionPane.showMessageDialog(this, "Nurse registered successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // 6. Display All Nurses Table
    private void showNursesTable() {
        List<Nurse> nurses = HospitalManager.getInstance().getNurses();
        if (nurses.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No nurses registered yet.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String[] columns = {"Staff ID", "Name", "Department"};
        Object[][] data = new Object[nurses.size()][columns.length];

        for (int i = 0; i < nurses.size(); i++) {
            Nurse n = nurses.get(i);
            data[i][0] = n.getStaffID();
            data[i][1] = n.getFirstName() + " " + n.getLastName();
            data[i][2] = n.getDepartment();
        }

        showTableDialog(data, columns, "All Nurses");
    }

    // Reusable table dialog
    private void showTableDialog(Object[][] data, String[] columns, String title) {
        DefaultTableModel model = new DefaultTableModel(data, columns) {
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

        JPanel bottom = new JPanel();
        bottom.add(closeBtn);
        dialog.add(bottom, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }
}