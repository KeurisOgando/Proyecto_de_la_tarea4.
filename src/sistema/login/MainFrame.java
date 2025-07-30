package sistema.login;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;


public class MainFrame extends JFrame {
    private final JTable tablaUsuarios;
    private final JButton btnActualizar;
    private final JButton btnEliminar; //botón para eliminar
    private final JButton btnCerrarSesion; // botón para cerrar sesión
    private final DefaultTableModel modeloTabla;

    public MainFrame() {
        // Configuración básica
        setTitle("Gestión de Usuarios");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Inicialización de campos final
        String[] columnas = {"Nombre", "Apellido", "Teléfono", "Email", "Usuario"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaUsuarios = new JTable(modeloTabla);
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar"); // Inicializa botón de eliminar
        btnCerrarSesion = new JButton("Cerrar Sesión"); // Inicializa botón de cerrar sesión


        // Configuración de eventos
        btnActualizar.addActionListener(this::manejarActualizacion);
        btnEliminar.addActionListener(this::manejarEliminacion); 
        btnCerrarSesion.addActionListener(e -> cerrarSesion());

        // Diseño
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar); // botón de eliminar al panel
        panelBotones.add(btnCerrarSesion); // botón de cerrar sesión al panel

        add(new JScrollPane(tablaUsuarios), BorderLayout.CENTER); // Tabla con scroll
        add(panelBotones, BorderLayout.SOUTH); // El Panel de botones en la parte inferior

    
        actualizarTablaUsuarios();
    }

    private void manejarActualizacion(ActionEvent e) {
        int fila = tablaUsuarios.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione un usuario para actualizar", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String usuarioNombre = (String) modeloTabla.getValueAt(fila, 4);
        Usuario user = UsuarioDAO.getInstancia().obtenerUsuario(usuarioNombre);

        if (user != null) {
            new ActualizarFrame(user, this).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Error al obtener los datos del usuario para actualizar", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void manejarEliminacion(ActionEvent e) {
        int fila = tablaUsuarios.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione un usuario para eliminar", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String usuarioAEliminar = (String) modeloTabla.getValueAt(fila, 4);

        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de que desea eliminar al usuario " + usuarioAEliminar + "?",
                "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (UsuarioDAO.getInstancia().eliminarUsuario(usuarioAEliminar)) {
                JOptionPane.showMessageDialog(this, "Usuario eliminado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                actualizarTablaUsuarios(); // Refresca la tabla
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar el usuario", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void cerrarSesion() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de que desea cerrar sesión?",
                "Cerrar Sesión", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            this.dispose(); 
            new LoginFrame().setVisible(true);
        }
    }

    public void actualizarTablaUsuarios() {
        modeloTabla.setRowCount(0); 
        for (Usuario usuario : UsuarioDAO.getInstancia().obtenerTodosUsuarios()) {
            modeloTabla.addRow(new Object[]{
                    usuario.getNombre(),
                    usuario.getApellido(),
                    usuario.getTelefono(),
                    usuario.getEmail(),
                    usuario.getNombreUsuario()
            });
        }
    }
}
