import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;

    public LoginFrame() {
        setTitle("MediCare Hospital - Login");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("MediCare Hospital System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        mainPanel.add(new JLabel("Login as:"), gbc);

        String[] roles = {"Receptionist", "Doctor", "Administrator"};
        roleComboBox = new JComboBox<>(roles);
        gbc.gridx = 1;
        mainPanel.add(roleComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(new JLabel("Username:"), gbc);

        usernameField = new JTextField(20);
        gbc.gridx = 1;
        mainPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(new JLabel("Password:"), gbc);

        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        mainPanel.add(passwordField, gbc);

        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setPreferredSize(new Dimension(150, 45));
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(loginButton, gbc);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText().trim();
                String password = new String(passwordField.getPassword()).trim();
                String role = (String) roleComboBox.getSelectedItem();

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(LoginFrame.this,
                            "Please enter username and password!",
                            "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                dispose(); // close login window

                HospitalManager mgr = HospitalManager.getInstance();

                if ("Doctor".equals(role)) {
                    // Username = staffID, password = "doctor123" (demo)
                    Doctor doctor = mgr.findDoctorByID(username);
                    if (doctor != null && "doctor123".equals(password)) {
                        new DoctorDashboard(doctor).setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(LoginFrame.this,
                                "Invalid doctor credentials! Use staffID as username and 'doctor123' as password.",
                                "Login Failed", JOptionPane.ERROR_MESSAGE);
                        // Reopen login instead of exiting
                        new LoginFrame().setVisible(true);
                    }
                } else if ("Administrator".equals(role)) {
                    // You can add admin validation later
                    new AdminDashboard().setVisible(true);
                } else {
                    // Receptionist - no validation for now
                    new ReceptionistDashboard().setVisible(true);
                }
            }
        });

        add(mainPanel, BorderLayout.CENTER);
    }

    // Optional: for standalone testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}