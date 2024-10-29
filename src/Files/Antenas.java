package Files;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Alan Basilio
 */
public class Antenas
{
    private String categoria;
    private int frecuencia;
    private String modelo;
    private String titular;
    private String nocontrol;
    private List<Routers> enlacesRouter; // Lista de routers asociados a esta antena
    
    String bdpass = "100%Freestyle";  // Contraseña base de datos

    public Antenas()
    {
        this.enlacesRouter = new ArrayList<>(); // Inicializamos la lista de routers
    }

    public Antenas(String categoria, int frecuencia, String modelo, String titular, String nocontrol, List<Routers> enlacesRouter)
    {
        this.categoria = categoria;
        this.frecuencia = frecuencia;
        this.modelo = modelo;
        this.titular = titular;
        this.nocontrol = nocontrol;
        this.enlacesRouter = enlacesRouter;
    }

    public String getCategoria()
    {
        return categoria;
    }

    public void setCategoria(String categoria)
    {
        this.categoria = categoria;
    }

    public int getFrecuencia()
    {
        return frecuencia;
    }

    public void setFrecuencia(int frecuencia)
    {
        this.frecuencia = frecuencia;
    }

    public String getModelo()
    {
        return modelo;
    }

    public void setModelo(String modelo)
    {
        this.modelo = modelo;
    }

    public String getTitular()
    {
        return titular;
    }

    public void setTitular(String titular)
    {
        this.titular = titular;
    }

    public String getNocontrol()
    {
        return nocontrol;
    }

    public void setNocontrol(String nocontrol)
    {
        this.nocontrol = nocontrol;
    }

    public List<Routers> getEnlacesRouter()
    {
        return enlacesRouter;
    }

    public void setEnlacesRouter(List<Routers> enlacesRouter)
    {
        this.enlacesRouter = enlacesRouter;
    }

    public void addRouter(Routers router)
    {
        this.enlacesRouter.add(router);
    }
    
    // Método para registrar una nueva antena
    public void altaAntena(JTextField categoriaField, JTextField frecuenciaField, JTextField modeloField, JTextField titularField, JTextField nocontrolField) {
        categoria = categoriaField.getText();
        frecuencia = Integer.parseInt(frecuenciaField.getText());
        modelo = modeloField.getText();
        titular = titularField.getText();
        nocontrol = nocontrolField.getText();

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/concesionario", "root", bdpass)) {
            String sql = "INSERT INTO antenas (categoria, frecuencia, modelo, titular, nocontrol) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, categoria);
                stmt.setInt(2, frecuencia);
                stmt.setString(3, modelo);
                stmt.setString(4, titular);
                stmt.setString(5, nocontrol);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Antena registrada exitosamente");
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + ex.getMessage());
        }
    }

    // Método para buscar una antena
    public int buscarAntena(JTextField nocontrolField) {
        nocontrol = nocontrolField.getText();

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/concesionario", "root", bdpass)) {
            String sql = "SELECT * FROM antenas WHERE nocontrol = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, nocontrol);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return 1;  // Antena encontrada
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }
        return 0;  // Antena no encontrada
    }

    // Método para listar todas las antenas en una tabla
    public void consultaGeneralAntenas(JTable tablaAntenas) {
        DefaultTableModel modeloTabla = (DefaultTableModel) tablaAntenas.getModel();
        modeloTabla.setRowCount(0);  // Limpiar la tabla

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/concesionario", "root", bdpass)) {
            String sql = "SELECT * FROM antenas";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    modeloTabla.addRow(new Object[]{
                        rs.getString("categoria"),
                        rs.getInt("frecuencia"),
                        rs.getString("modelo"),
                        rs.getString("titular"),
                        rs.getString("nocontrol")
                    });
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener los datos de la base de datos: " + ex.getMessage());
        }
    }

    // Método para actualizar una antena
    public void modificarAntena(JTextField categoriaField, JTextField frecuenciaField, JTextField modeloField, JTextField titularField, JTextField nocontrolField) {
        categoria = categoriaField.getText();
        frecuencia = Integer.parseInt(frecuenciaField.getText());
        modelo = modeloField.getText();
        titular = titularField.getText();
        nocontrol = nocontrolField.getText();

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/concesionario", "root", bdpass)) {
            String sql = "UPDATE antenas SET categoria = ?, frecuencia = ?, modelo = ?, titular = ? WHERE nocontrol = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, categoria);
                stmt.setInt(2, frecuencia);
                stmt.setString(3, modelo);
                stmt.setString(4, titular);
                stmt.setString(5, nocontrol);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Antena actualizada exitosamente");
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + ex.getMessage());
        }
    }

    // Método para eliminar una antena
    public void eliminarAntena(JTextField nocontrolField) {
        nocontrol = nocontrolField.getText();

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/concesionario", "root", bdpass)) {
            String sql = "DELETE FROM antenas WHERE nocontrol = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, nocontrol);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Antena eliminada exitosamente");
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + ex.getMessage());
        }
    }
}
