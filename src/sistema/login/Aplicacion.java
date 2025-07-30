package sistema.login;

import javax.swing.*;

public class Aplicacion {
    public static void main(String[] args) {
        // El sistema operativo
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Mostrar el login
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}