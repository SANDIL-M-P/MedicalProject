import java.awt.*;
import java.util.Date;
import javax.swing.*;

public class PatientRegistrationForm extends JFrame {
    public PatientRegistrationForm() {
        setTitle("Register New Patient");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Fields (simplified - add more as needed)
        JTextField firstName = new JTextField(20);
        JTextField lastName = new JTextField(20);
        JTextField patientID = new JTextField(15);
        JTextField bloodGroup = new JTextField(10);
        JTextField allergies = new JTextField(20);
        JTextField emergencyContact = new JTextField(20);
        JTextField insurance = new JTextField(20);

        int row = 0;
        addField(panel, gbc, row++, "First Name:", firstName);
        addField(panel, gbc, row++, "Last Name:", lastName);
        addField(panel, gbc, row++, "Patient ID:", patientID);
        addField(panel, gbc, row++, "Blood Group:", bloodGroup);
        addField(panel, gbc, row++, "Allergies:", allergies);
        addField(panel, gbc, row++, "Emergency Contact:", emergencyContact);
        addField(panel, gbc, row++, "Insurance Info:", insurance);

        JButton registerButton = new JButton("Register Patient");
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        panel.add(registerButton, gbc);

        registerButton.addActionListener(e -> {
            try {
                // Simple validation + create patient
                if (firstName.getText().trim().isEmpty() || lastName.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "First and Last name required!");
                    return;
                }

                Patient patient = new Patient(
                        "P" + System.currentTimeMillis(), // temp personID
                        firstName.getText().trim(),
                        lastName.getText().trim(),
                        new Date(), // dob - you can add date picker later
                        "Not Specified",
                        emergencyContact.getText().trim(),
                        "no@email.com",
                        "N/A",
                        patientID.getText().trim(),
                        new Date(),
                        bloodGroup.getText().trim(),
                        allergies.getText().trim(),
                        emergencyContact.getText().trim(),
                        insurance.getText().trim()
                );

                HospitalManager.getInstance().addPatient(patient);
                JOptionPane.showMessageDialog(this, "Patient registered successfully!\nID: " + patient.getPatientID());
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        add(panel);
    }

    private void addField(JPanel panel, GridBagConstraints gbc, int row,
                          String labelText, JComponent field) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        panel.add(new JLabel(labelText), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 1;
        panel.add(field, gbc);
    }
}