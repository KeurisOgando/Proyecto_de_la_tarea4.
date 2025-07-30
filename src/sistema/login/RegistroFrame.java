package sistema.login;

import javax.swing.*;
import java.awt.*;


public class RegistroFrame extends JFrame {
    private JTextField txtNombre, txtApellido, txtUsuario, txtTelefono, txtEmail;
    private JPasswordField txtContrasena, txtConfirmarContrasena;

    public RegistroFrame() {
        setTitle("Registro de Usuario");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Configuración de componentes
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(new JLabel("Complete todos los campos"), gbc);

        String[] etiquetas = {"Nombre:", "Apellido:", "Usuario:", "Teléfono:", "Email:", "Contraseña:", "Confirmar:"};
        JTextField[] campos = {
                txtNombre = new JTextField(15),
                txtApellido = new JTextField(15),
                txtUsuario = new JTextField(15),
                txtTelefono = new JTextField(15),
                txtEmail = new JTextField(15),
                null, null // Marcadores de posición para los campos de contraseña
        };

        txtContrasena = new JPasswordField(15);
        txtConfirmarContrasena = new JPasswordField(15);

        for (int i = 0; i < etiquetas.length; i++) {
            gbc.gridwidth = 1; gbc.gridx = 0; gbc.gridy = i+1;
            panel.add(new JLabel(etiquetas[i]), gbc);

            gbc.gridx = 1;
            if (i < 5) {
                panel.add(campos[i], gbc);
            } else if (i == 5) {
                panel.add(txtContrasena, gbc);
            } else {
                panel.add(txtConfirmarContrasena, gbc);
            }
        }

        JButton btnRegistrar = new JButton("Registrar");
        gbc.gridx = 0; gbc.gridy = etiquetas.length+1;
        gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        panel.add(btnRegistrar, gbc);

        add(panel);

        // Evento del botón
        btnRegistrar.addActionListener(e -> registrarUsuario());
    }

    private void registrarUsuario() {
        // Validación de campos
        if (txtNombre.getText().isEmpty() || txtApellido.getText().isEmpty() ||
                txtUsuario.getText().isEmpty() || txtTelefono.getText().isEmpty() ||
                txtEmail.getText().isEmpty() || txtContrasena.getPassword().length == 0 ||
                txtConfirmarContrasena.getPassword().length == 0) {

            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!new String(txtContrasena.getPassword()).equals(new String(txtConfirmarContrasena.getPassword()))) {
            JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Usuario nuevo = new Usuario(
                txtUsuario.getText(),
                txtNombre.getText(),
                txtApellido.getText(),
                txtTelefono.getText(),
                txtEmail.getText(),
                new String(txtContrasena.getPassword())
        );

        if (UsuarioDAO.getInstancia().agregarUsuario(nuevo)) {
            JOptionPane.showMessageDialog(this, "Registro exitoso", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "El usuario ya existe", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}