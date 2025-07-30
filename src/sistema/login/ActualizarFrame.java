package sistema.login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActualizarFrame extends JFrame {
    // Campos
    private final Usuario usuario;
    private final MainFrame mainFrame;

    private JTextField txtNombre, txtApellido, txtTelefono, txtEmail;
    private JPasswordField txtContrasena, txtConfirmarContrasena;
    private JLabel lblUsuarioActual; //El nombre de usuario
    private JButton btnGuardar, btnCancelar;

    // Constructor new ActualizarFrame(usuario, this)
    public ActualizarFrame(Usuario usuario, MainFrame mainFrame) {
        this.usuario = usuario;
        this.mainFrame = mainFrame;

        // Configuración básica de la ventana
        setTitle("Actualizar Usuario");
        setSize(450, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(mainFrame);

        // Crea el panel principal con GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Márgenes
        gbc.anchor = GridBagConstraints.WEST; // Alinea componentes a la izquierda

        // Título/Instrucción
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(new JLabel("Actualice los datos del usuario:"), gbc);

        // Nombre de Usuario
        gbc.gridy++;
        gbc.gridwidth = 1;
        panel.add(new JLabel("Usuario:"), gbc);
        gbc.gridx = 1;
        lblUsuarioActual = new JLabel(usuario.getNombreUsuario());
        panel.add(lblUsuarioActual, gbc);

        // Campo Nombre
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        txtNombre = new JTextField(20);
        txtNombre.setText(usuario.getNombre());
        panel.add(txtNombre, gbc);

        // Campo Apellido
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Apellido:"), gbc);
        gbc.gridx = 1;
        txtApellido = new JTextField(20);
        txtApellido.setText(usuario.getApellido());
        panel.add(txtApellido, gbc);

        // Campo Teléfono
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Teléfono:"), gbc);
        gbc.gridx = 1;
        txtTelefono = new JTextField(20);
        txtTelefono.setText(usuario.getTelefono());
        panel.add(txtTelefono, gbc);

        // Campo Email
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        txtEmail = new JTextField(20);
        txtEmail.setText(usuario.getEmail());
        panel.add(txtEmail, gbc);

        // Campo Contraseña
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Contraseña:"), gbc);
        gbc.gridx = 1;
        txtContrasena = new JPasswordField(20);
        txtContrasena.setText(usuario.getContrasena());
        panel.add(txtContrasena, gbc);

        // Campo Confirmar Contraseña
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Confirmar Contraseña:"), gbc);
        gbc.gridx = 1;
        txtConfirmarContrasena = new JPasswordField(20);
        txtConfirmarContrasena.setText(usuario.getContrasena());
        panel.add(txtConfirmarContrasena, gbc);

        // Botones
        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER; // Centrar botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0)); // Espacio entre botones
        buttonPanel.add(btnGuardar);
        buttonPanel.add(btnCancelar);
        panel.add(buttonPanel, gbc);

        add(panel);

        // Configurar eventos
        configurarEventos();
    }

    private void configurarEventos() {
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarCambios();
            }
        });

        btnCancelar.addActionListener(e -> dispose()); // Simplemente cierra la ventana
    }

    private void guardarCambios() {
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String email = txtEmail.getText().trim();
        String contrasena = new String(txtContrasena.getPassword());
        String confirmarContrasena = new String(txtConfirmarContrasena.getPassword());

        // Validación de campos vacíos
        if (nombre.isEmpty() || apellido.isEmpty() || telefono.isEmpty() || email.isEmpty() ||
                contrasena.isEmpty() || confirmarContrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validación de coincidencia de contraseñas
        if (!contrasena.equals(confirmarContrasena)) {
            JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden", "Error de Validación", JOptionPane.ERROR_MESSAGE);
            return;
        }


        Usuario usuarioActualizado = new Usuario(
                usuario.getNombreUsuario(),
                nombre,
                apellido,
                telefono,
                email,
                contrasena
        );

        // Llama al DAO para actualizar el usuario
        if (UsuarioDAO.getInstancia().actualizarUsuario(usuario.getNombreUsuario(), usuarioActualizado)) {
            JOptionPane.showMessageDialog(this, "Usuario actualizado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            mainFrame.actualizarTablaUsuarios(); // Refrescar la tabla principal
            dispose(); // Cerrar la ventana de actualización
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar el usuario", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}