package Files;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Alan Basilio
 */
public class DBConnection
{
    private static String DRIVER = "com.mysql.cj.jdbc.Driver";  // Clase del nuevo driver
    private static String USUARIO = "root";
    private static String PASSWORD = "100%Freestyle";
    private static String URL = "jdbc:mysql://localhost:3306/netcontrol?useSSL=false&serverTimezone=UTC";

    static
    {
        try
        {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e)
        {
            JOptionPane.showMessageDialog(null, "Error en el Driver: " + e);
        }
    }
    
    public Connection getConnection()
    {
        Connection con = null;
        try
        {
            con = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            JOptionPane.showMessageDialog(null, "Conexión Exitosa");
        } catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, "Error de Conexión: " + e);
        }
        return con;
    }
}
