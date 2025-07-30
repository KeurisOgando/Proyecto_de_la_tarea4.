package sistema.login;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private static UsuarioDAO instancia;
    private List<Usuario> usuarios;

    // Patrón Singleton

    private UsuarioDAO() {
        usuarios = new ArrayList<>();
        // Credenciales del administrador
        usuarios.add(new Usuario("Keuris", "Keuris", "Ogando",
                "829-399-6717", "keurisogandop22@gmail.com", "20242503"));
    }

    public static UsuarioDAO getInstancia() {
        if (instancia == null) {
            instancia = new UsuarioDAO();
        }
        return instancia;
    }

    // Operaciones CRUD
    public boolean agregarUsuario(Usuario usuario) {
        if (existeUsuario(usuario.getNombreUsuario())) {
            return false; // El usuario ya existe
        }
        usuarios.add(usuario);
        return true;
    }

    public List<Usuario> obtenerTodosUsuarios() {
        return new ArrayList<>(usuarios); // Devuelve una copia para evitar modificaciones externas
    }

    public boolean actualizarUsuario(String nombreUsuario, Usuario usuarioActualizado) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getNombreUsuario().equals(nombreUsuario)) {
                usuarios.set(i, usuarioActualizado); // Reemplaza el usuario existente
                return true;
            }
        }
        return false; 
    }

    public boolean eliminarUsuario(String nombreUsuario) {
        return usuarios.removeIf(u -> u.getNombreUsuario().equals(nombreUsuario));
    }

    public Usuario obtenerUsuario(String nombreUsuario) {
        for (Usuario u : usuarios) {
            if (u.getNombreUsuario().equals(nombreUsuario)) {
                return u;
            }
        }
        return null; 
    }

    public boolean existeUsuario(String nombreUsuario) {
        return usuarios.stream().anyMatch(u -> u.getNombreUsuario().equals(nombreUsuario));
    }

    public boolean validarCredenciales(String nombreUsuario, String contrasena) {
        Usuario usuario = obtenerUsuario(nombreUsuario);
        return usuario != null && usuario.getContrasena().equals(contrasena);
    }
}
