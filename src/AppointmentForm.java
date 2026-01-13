import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public class AppointmentForm extends JFrame {
    private JTextField patientIDField, timeField, typeField, notesField;
    private JComboBox<String> doctorComboBox;
    private JFormattedTextField dateField;

    public AppointmentForm() {
        setTitle("Schedule Appointment");
        setSize(550, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        // Patient ID
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Patient ID:"), gbc);
        patientIDField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(patientIDField, gbc);
        row++;

        // Doctor selection (you need some doctors in system)
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Doctor:"), gbc);
        doctorComboBox = new JComboBox<>();
        // Populate with doctors (you can add sample doctors first)
        for (Doctor d : HospitalManager.getInstance().getDoctors()) {
            doctorComboBox.addItem(d.getFirstName() + " " + d.getLastName() + " (" + d.getSpecialization() + ")");
        }
        gbc.gridx = 1;
        panel.add(doctorComboBox, gbc);
        row++;

        // Date
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Date (yyyy-MM-dd):"), gbc);
        dateField = new JFormattedTextField(new SimpleDateFormat("yyyy-MM-dd"));
        gbc.gridx = 1;
        panel.add(dateField, gbc);
        row++;

        // Time
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Time:"), gbc);
        timeField = new JTextField(10);
        gbc.gridx = 1;
        panel.add(timeField, gbc);
        row++;

        // Type
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Type:"), gbc);
        typeField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(typeField, gbc);
        row++;

        // Notes
        gbc.gridx = 0; gbc.gridy = row;
        panel.add(new JLabel("Notes:"), gbc);
        notesField = new JTextField(30);
        gbc.gridx = 1;
        panel.add(notesField, gbc);
        row++;

        // Schedule button
        JButton scheduleBtn = new JButton("Schedule");
        scheduleBtn.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        panel.add(scheduleBtn, gbc);

        scheduleBtn.addActionListener(e -> scheduleAppointment());

        add(panel);
    }

    private void scheduleAppointment() {
        try {
            String patientID = patientIDField.getText().trim();
            Patient patient = HospitalManager.getInstance().findPatientByID(patientID);
            if (patient == null) {
                JOptionPane.showMessageDialog(this, "Patient not found!");
                return;
            }

            if (doctorComboBox.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(this, "Select a doctor!");
                return;
            }

            // Get selected doctor (this is simplified - improve later)
            Doctor doctor = HospitalManager.getInstance().getDoctors().get(doctorComboBox.getSelectedIndex());

            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateField.getText().trim());
            String time = timeField.getText().trim();
            String type = typeField.getText().trim();
            String notes = notesField.getText().trim();

            String appID = "APP" + System.currentTimeMillis();
            Appointment ap = new Appointment(appID, patient, doctor, date, time, "Scheduled", type, notes);
            ap.scheduleAppointment();

            HospitalManager.getInstance().addAppointment(ap);
            JOptionPane.showMessageDialog(this, "Appointment scheduled successfully!\nID: " + appID);
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}