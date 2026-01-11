import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        // Auto-save on exit
        HospitalManager.setupAutoSave();

        // Run GUI on Event Dispatch Thread (required for Swing)
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}