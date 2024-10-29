package Files;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Alan Basilio
 */
public class Torres
{
    private String zona;
    private String direccion;
    private List<Antenas> enlaces; // Lista de Antenas asociadas a esta torre
    private String etiqueta;
    private int nocontrol;
    
    // Contraseña de la base de datos
    String bdpass = "100%Freestyle";

    public Torres()
    {
        this.enlaces = new ArrayList<>(); // Inicializamos la lista de enlaces (antenas)
    }

    public Torres(String zona, String direccion, List<Antenas> enlaces, String etiqueta, int nocontrol)
    {
        this.zona = zona;
        this.direccion = direccion;
        this.enlaces = enlaces;
        this.etiqueta = etiqueta;
        this.nocontrol = nocontrol;
    }

    public String getZona()
    {
        return zona;
    }

    public void setZona(String zona)
    {
        this.zona = zona;
    }

    public String getDireccion()
    {
        return direccion;
    }

    public void setDireccion(String direccion)
    {
        this.direccion = direccion;
    }

    public List<Antenas> getEnlaces()
    {
        return enlaces;
    }

    public void setEnlaces(List<Antenas> enlaces)
    {
        this.enlaces = enlaces;
    }

    public void addEnlace(Antenas antena)
    {
        this.enlaces.add(antena);
    }

    public String getEtiqueta()
    {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta)
    {
        this.etiqueta = etiqueta;
    }

    public int getNocontrol()
    {
        return nocontrol;
    }

    public void setNocontrol(int nocontrol)
    {
        this.nocontrol = nocontrol;
    }
    
    // Método para registrar una nueva torre
    public void altaTorre(JTextField zonaField, JTextField direccionField, JTextField etiquetaField, JTextField nocontrolField) {
        zona = zonaField.getText();
        direccion = direccionField.getText();
        etiqueta = etiquetaField.getText();
        nocontrol = Integer.parseInt(nocontrolField.getText());

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/concesionario", "root", bdpass)) {
            String sql = "INSERT INTO torres (zona, direccion, etiqueta, nocontrol) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, zona);
                stmt.setString(2, direccion);
                stmt.setString(3, etiqueta);
                stmt.setInt(4, nocontrol);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Torre registrada exitosamente");
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + ex.getMessage());
        }
    }

    // Método para buscar una torre por zona
    public int buscarTorre(JTextField zonaField) {
        zona = zonaField.getText();

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/concesionario", "root", bdpass)) {
            String sql = "SELECT * FROM torres WHERE zona = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, zona);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return 1;  // Torre encontrada
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }
        return 0;  // Torre no encontrada
    }

    // Método para listar todas las torres en una tabla
    public void consultaGeneralTorres(JTable tablaTorres) {
        DefaultTableModel modeloTabla = (DefaultTableModel) tablaTorres.getModel();
        modeloTabla.setRowCount(0);  // Limpiar la tabla

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/concesionario", "root", bdpass)) {
            String sql = "SELECT * FROM torres";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    modeloTabla.addRow(new Object[]{
                        rs.getString("zona"),
                        rs.getString("direccion"),
                        rs.getString("etiqueta"),
                        rs.getInt("nocontrol")
                    });
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener los datos de la base de datos: " + ex.getMessage());
        }
    }

    // Método para actualizar una torre
    public void modificarTorre(JTextField zonaField, JTextField direccionField, JTextField etiquetaField, JTextField nocontrolField) {
        zona = zonaField.getText();
        direccion = direccionField.getText();
        etiqueta = etiquetaField.getText();
        nocontrol = Integer.parseInt(nocontrolField.getText());

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/concesionario", "root", bdpass)) {
            String sql = "UPDATE torres SET direccion = ?, etiqueta = ?, nocontrol = ? WHERE zona = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, direccion);
                stmt.setString(2, etiqueta);
                stmt.setInt(3, nocontrol);
                stmt.setString(4, zona);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Torre actualizada exitosamente");
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + ex.getMessage());
        }
    }

    // Método para eliminar una torre por zona
    public void eliminarTorre(JTextField zonaField) {
        zona = zonaField.getText();

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/concesionario", "root", bdpass)) {
            String sql = "DELETE FROM torres WHERE zona = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, zona);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Torre eliminada exitosamente");
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + ex.getMessage());
        }
    }
}