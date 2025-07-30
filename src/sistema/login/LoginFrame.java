package sistema.login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JButton btnLogin, btnRegistro;

    public LoginFrame() {
        // Configuración básica de la ventana
        setTitle("Login de Usuarios");
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centra en pantalla

        // Crea el panel principal 
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Márgenes

        // Título
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(new JLabel("Ingrese sus credenciales"), gbc);

        // Campo Usuario
        gbc.gridwidth = 1;
        gbc.gridy++;
        panel.add(new JLabel("Usuario:"), gbc);

        gbc.gridx = 1;
        txtUsuario = new JTextField(15);
        panel.add(txtUsuario, gbc);

        // Campo Contraseña
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Contraseña:"), gbc);

        gbc.gridx = 1;
        txtContrasena = new JPasswordField(15);
        panel.add(txtContrasena, gbc);

        // Botones
        gbc.gridx = 0;
        gbc.gridy++;
        btnLogin = new JButton("Iniciar Sesión");
        panel.add(btnLogin, gbc);

        gbc.gridx = 1;
        btnRegistro = new JButton("Registrarse");
        panel.add(btnRegistro, gbc);

        // agrega panel a la ventana
        add(panel);

        // Configurar eventos
        configurarEventos();
    }

    private void configurarEventos() {
        // Evento para el botón de Login
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = txtUsuario.getText();
                String contrasena = new String(txtContrasena.getPassword());

                // Valida campos vacíos
                if (usuario.isEmpty() || contrasena.isEmpty()) {
                    JOptionPane.showMessageDialog(LoginFrame.this,
                            "Debe ingresar usuario y contraseña",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Valida credenciales
                if (UsuarioDAO.getInstancia().validarCredenciales(usuario, contrasena)) {
                    LoginFrame.this.dispose(); // Cierra ventana de login
                    new MainFrame().setVisible(true); // Abre ventana principal
                } else {
                    JOptionPane.showMessageDialog(LoginFrame.this,
                            "Usuario o contraseña incorrectos",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Evento para el botón de Registro
        btnRegistro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegistroFrame().setVisible(true); // Abre ventana de registro
            }
        });
    }
}
