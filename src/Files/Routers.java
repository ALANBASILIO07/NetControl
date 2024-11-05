package Files;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Alan Basilio
 */
public class Routers
{
    public String IPCliente;
    public String Titular;
    public String modelo;
    public int ID;
    
    // Contraseña de la base de datos
    String bdpass = "100%Freestyle";

    public Routers()
    {
        
    }

    public Routers(String IPCliente, String Titular, String modelo)
    {
        this.IPCliente = IPCliente;
        this.Titular = Titular;
        this.modelo = modelo;
    }

    public String getIPCliente()
    {
        return IPCliente;
    }

    public void setIPCliente(String IPCliente)
    {
        this.IPCliente = IPCliente;
    }

    public String getTitular()
    {
        return Titular;
    }

    public void setTitular(String Titular)
    {
        this.Titular = Titular;
    }

    public String getModelo()
    {
        return modelo;
    }

    public void setModelo(String modelo)
    {
        this.modelo = modelo;
    }
    
     // Método para registrar un nuevo router
    public void altaRouter(JTextField ipField, JTextField titularField, JTextField modeloField, JTextField idField) {
        IPCliente = ipField.getText();
        Titular = titularField.getText();
        modelo = modeloField.getText();
        ID = Integer.parseInt(idField.getText());

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/concesionario", "root", bdpass)) {
            String sql = "INSERT INTO routers (IPCliente, Titular, modelo, ID) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, IPCliente);
                stmt.setString(2, Titular);
                stmt.setString(3, modelo);
                stmt.setInt(4, ID);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Router registrado exitosamente");
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + ex.getMessage());
        }
    }

    // Método para buscar un router por ID
    public int buscarRouter(JTextField idField) {
        ID = Integer.parseInt(idField.getText());

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/concesionario", "root", bdpass)) {
            String sql = "SELECT * FROM routers WHERE ID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, ID);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return 1;  // Router encontrado
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }
        return 0;  // Router no encontrado
    }

    // Método para listar todos los routers en una tabla
    public void consultaGeneralRouters(JTable tablaRouters) {
        DefaultTableModel modeloTabla = (DefaultTableModel) tablaRouters.getModel();
        modeloTabla.setRowCount(0);  // Limpiar la tabla

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/concesionario", "root", bdpass)) {
            String sql = "SELECT * FROM routers";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    modeloTabla.addRow(new Object[]{
                        rs.getString("IPCliente"),
                        rs.getString("Titular"),
                        rs.getString("modelo"),
                        rs.getInt("ID")
                    });
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener los datos de la base de datos: " + ex.getMessage());
        }
    }

    // Método para actualizar un router
    public void modificarRouter(JTextField ipField, JTextField titularField, JTextField modeloField, JTextField idField) {
        IPCliente = ipField.getText();
        Titular = titularField.getText();
        modelo = modeloField.getText();
        ID = Integer.parseInt(idField.getText());

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/concesionario", "root", bdpass)) {
            String sql = "UPDATE routers SET IPCliente = ?, Titular = ?, modelo = ? WHERE ID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, IPCliente);
                stmt.setString(2, Titular);
                stmt.setString(3, modelo);
                stmt.setInt(4, ID);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Router actualizado exitosamente");
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + ex.getMessage());
        }
    }

    // Método para eliminar un router por ID
    public void eliminarRouter(JTextField idField) {
        ID = Integer.parseInt(idField.getText());

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/concesionario", "root", bdpass)) {
            String sql = "DELETE FROM routers WHERE ID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, ID);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Router eliminado exitosamente");
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + ex.getMessage());
        }
    }
}
