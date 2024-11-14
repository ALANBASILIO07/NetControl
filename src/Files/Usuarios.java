package Files;

import Views.Password;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Alan Basilio
 */
public class Usuarios
{

    public String username;
    public String password;
    public String bdpass;

    // Contraseña de la base de datos
    String adminpass = "100%Freestyle";

    public Usuarios()
    {

    }

    public Usuarios(String username, String password, String bdpass)
    {
        this.username = username;
        this.password = password;
        this.bdpass = bdpass;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getBdpass()
    {
        return bdpass;
    }

    public void setBdpass(String bdpass)
    {
        this.bdpass = bdpass;
    }

    public void altaUsuario(JTextField usuarioField, JTextField passwordField, JTextField confirmPasswordField)
    {
        String username = usuarioField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // Verificar si las contraseñas coinciden
        if (!password.equals(confirmPassword))
        {
            JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden.");
            return;
        }

        // Abrir el cuadro de diálogo para ingresar la contraseña de administrador
        JTextField adminPasswordField = new JPasswordField(); // JTextField para ingresar la contraseña de administración
        int option = JOptionPane.showConfirmDialog(null, adminPasswordField, "Ingrese la contraseña de administración", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION)
        {
            // Crear instancia de Password y verificar la contraseña ingresada
            Password passwordChecker = new Password();
            if (!passwordChecker.isAdminPasswordCorrect(adminPasswordField))
            {
                JOptionPane.showMessageDialog(null, "Contraseña de administración incorrecta.");
                return;
            }
        } else
        {
            JOptionPane.showMessageDialog(null, "Operación cancelada.");
            return;
        }

        // Realizar el alta de usuario en la base de datos si la contraseña de administrador es correcta
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/netcontrol", "root", adminpass))
        {
            String sql = "INSERT INTO usuarios (username, password) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql))
            {
                stmt.setString(1, username);
                stmt.setString(2, password);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0)
                {
                    JOptionPane.showMessageDialog(null, "Usuario registrado exitosamente");
                }
            }
        } catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + ex.getMessage());
        }
    }

    public boolean verificarCredenciales(JTextField usuarioField, JTextField passwordField)
    {
        // Obtener el texto de los JTextField
        String username = usuarioField.getText();
        String password = passwordField.getText();
        boolean credencialesCorrectas = false;

        // Conexión a la base de datos y consulta de las credenciales
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/netcontrol", "root", adminpass))
        {
            String sql = "SELECT * FROM usuarios WHERE username = ? AND password = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql))
            {
                stmt.setString(1, username);
                stmt.setString(2, password);

                ResultSet rs = stmt.executeQuery();

                // Si el ResultSet tiene un registro, las credenciales son correctas
                if (rs.next())
                {
                    credencialesCorrectas = true;
                } else
                {
                    JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos.");
                }
            }
        } catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + ex.getMessage());
        }

        return credencialesCorrectas;
    }
}
