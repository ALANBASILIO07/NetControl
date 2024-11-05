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
public class Clientes 
{
    public String nombre;
    public String IP;
    public String vigencia;
    public String domicilio;
    public String status;
    public String succursal;
    public String torre;
    public int ID;
    
    // Contraseña de la base de datos
    String bdpass = "100%Freestyle";

    public Clientes()
    {
        
    }

    public Clientes(String nombre, String IP, String vigencia, String domicilio, String status, String succursal, String torre, int ID)
    {
        this.nombre = nombre;
        this.IP = IP;
        this.vigencia = vigencia;
        this.domicilio = domicilio;
        this.status = status;
        this.succursal = succursal;
        this.torre = torre;
        this.ID = ID;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public String getIP()
    {
        return IP;
    }

    public void setIP(String IP)
    {
        this.IP = IP;
    }

    public String getVigencia()
    {
        return vigencia;
    }

    public void setVigencia(String vigencia)
    {
        this.vigencia = vigencia;
    }

    public String getDomicilio()
    {
        return domicilio;
    }

    public void setDomicilio(String domicilio)
    {
        this.domicilio = domicilio;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getSuccursal()
    {
        return succursal;
    }

    public void setSuccursal(String succursal)
    {
        this.succursal = succursal;
    }

    public String getTorre()
    {
        return torre;
    }

    public void setTorre(String torre)
    {
        this.torre = torre;
    }

    public int getID()
    {
        return ID;
    }

    public void setID(int ID)
    {
        this.ID = ID;
    }

    // Método para registrar un nuevo cliente
    public void altaCliente(JTextField nombreField, JTextField IPField, JTextField vigenciaField, JTextField domicilioField, JTextField statusField, JTextField succursalField, JTextField torreField, JTextField IDField) {
        nombre = nombreField.getText();
        IP = IPField.getText();
        vigencia = vigenciaField.getText();
        domicilio = domicilioField.getText();
        status = statusField.getText();
        succursal = succursalField.getText();
        torre = torreField.getText();
        ID = Integer.parseInt(IDField.getText());

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/concesionario", "root", bdpass)) {
            String sql = "INSERT INTO clientes (nombre, IP, vigencia, domicilio, status, succursal, torre, ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, nombre);
                stmt.setString(2, IP);
                stmt.setString(3, vigencia);
                stmt.setString(4, domicilio);
                stmt.setString(5, status);
                stmt.setString(6, succursal);
                stmt.setString(7, torre);
                stmt.setInt(8, ID);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Cliente registrado exitosamente");
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + ex.getMessage());
        }
    }

    // Método para buscar un cliente por ID
    public int buscarCliente(JTextField IDField) {
        ID = Integer.parseInt(IDField.getText());

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/concesionario", "root", bdpass)) {
            String sql = "SELECT * FROM clientes WHERE ID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, ID);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return 1;  // Cliente encontrado
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }
        return 0;  // Cliente no encontrado
    }

    // Método para listar todos los clientes en una tabla
    public void consultaGeneralClientes(JTable tablaClientes) {
        DefaultTableModel modeloTabla = (DefaultTableModel) tablaClientes.getModel();
        modeloTabla.setRowCount(0);  // Limpiar la tabla

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/concesionario", "root", bdpass)) {
            String sql = "SELECT * FROM clientes";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    modeloTabla.addRow(new Object[]{
                        rs.getString("nombre"),
                        rs.getString("IP"),
                        rs.getString("vigencia"),
                        rs.getString("domicilio"),
                        rs.getString("status"),
                        rs.getString("succursal"),
                        rs.getString("torre"),
                        rs.getInt("ID")
                    });
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener los datos de la base de datos: " + ex.getMessage());
        }
    }

    // Método para actualizar un cliente
    public void modificarCliente(JTextField nombreField, JTextField IPField, JTextField vigenciaField, JTextField domicilioField, JTextField statusField, JTextField succursalField, JTextField torreField, JTextField IDField) {
        nombre = nombreField.getText();
        IP = IPField.getText();
        vigencia = vigenciaField.getText();
        domicilio = domicilioField.getText();
        status = statusField.getText();
        succursal = succursalField.getText();
        torre = torreField.getText();
        ID = Integer.parseInt(IDField.getText());

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/concesionario", "root", bdpass)) {
            String sql = "UPDATE clientes SET nombre = ?, IP = ?, vigencia = ?, domicilio = ?, status = ?, succursal = ?, torre = ? WHERE ID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, nombre);
                stmt.setString(2, IP);
                stmt.setString(3, vigencia);
                stmt.setString(4, domicilio);
                stmt.setString(5, status);
                stmt.setString(6, succursal);
                stmt.setString(7, torre);
                stmt.setInt(8, ID);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Cliente actualizado exitosamente");
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + ex.getMessage());
        }
    }

    // Método para eliminar un cliente por ID
    public void eliminarCliente(JTextField IDField) {
        ID = Integer.parseInt(IDField.getText());

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/concesionario", "root", bdpass)) {
            String sql = "DELETE FROM clientes WHERE ID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, ID);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Cliente eliminado exitosamente");
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + ex.getMessage());
        }
    }
}
