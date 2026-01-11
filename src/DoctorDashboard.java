import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class DoctorDashboard extends JFrame {
    public DoctorDashboard(String doctorName) {
        setTitle("Doctor Dashboard - " + doctorName);
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel label = new JLabel("Welcome, Dr. " + doctorName + "! (Patient Diagnosis & Prescriptions)", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        add(label);
    }
}