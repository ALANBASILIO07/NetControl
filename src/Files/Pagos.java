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
public class Pagos
{
    public String fecha;
    public int nocontrol;
    public double importe;
    public String mesaplicado;
    public String tipo;
    public String cliente;
    
    // Contraseña de la base de datos
    String bdpass = "100%Freestyle";

    public Pagos()
    {
        
    }

    public Pagos(String fecha, int nocontrol, double importe, String mesaplicado, String tipo, String cliente)
    {
        this.fecha = fecha;
        this.nocontrol = nocontrol;
        this.importe = importe;
        this.mesaplicado = mesaplicado;
        this.tipo = tipo;
        this.cliente = cliente;
    }

    public String getFecha()
    {
        return fecha;
    }

    public void setFecha(String fecha)
    {
        this.fecha = fecha;
    }

    public int getNocontrol()
    {
        return nocontrol;
    }

    public void setNocontrol(int nocontrol)
    {
        this.nocontrol = nocontrol;
    }

    public double getImporte()
    {
        return importe;
    }

    public void setImporte(double importe)
    {
        this.importe = importe;
    }

    public String getMesaplicado()
    {
        return mesaplicado;
    }

    public void setMesaplicado(String mesaplicado)
    {
        this.mesaplicado = mesaplicado;
    }

    public String getTipo()
    {
        return tipo;
    }

    public void setTipo(String tipo)
    {
        this.tipo = tipo;
    }

    public String getCliente()
    {
        return cliente;
    }

    public void setCliente(String cliente)
    {
        this.cliente = cliente;
    }
    
    // Método para registrar un nuevo pago
    public void altaPago(JTextField fechaField, JTextField nocontrolField, JTextField importeField, JTextField mesaplicadoField, JTextField tipoField, JTextField clienteField) {
        fecha = fechaField.getText();
        nocontrol = Integer.parseInt(nocontrolField.getText());
        importe = Double.parseDouble(importeField.getText());
        mesaplicado = mesaplicadoField.getText();
        tipo = tipoField.getText();
        cliente = clienteField.getText();

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/concesionario", "root", bdpass)) {
            String sql = "INSERT INTO pagos (fecha, nocontrol, importe, mesaplicado, tipo, cliente) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, fecha);
                stmt.setInt(2, nocontrol);
                stmt.setDouble(3, importe);
                stmt.setString(4, mesaplicado);
                stmt.setString(5, tipo);
                stmt.setString(6, cliente);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Pago registrado exitosamente");
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + ex.getMessage());
        }
    }

    // Método para buscar un pago por nocontrol
    public int buscarPago(JTextField nocontrolField) {
        nocontrol = Integer.parseInt(nocontrolField.getText());

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/concesionario", "root", bdpass)) {
            String sql = "SELECT * FROM pagos WHERE nocontrol = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, nocontrol);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return 1;  // Pago encontrado
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }
        return 0;  // Pago no encontrado
    }

    // Método para listar todos los pagos en una tabla
    public void consultaGeneralPagos(JTable tablaPagos) {
        DefaultTableModel modeloTabla = (DefaultTableModel) tablaPagos.getModel();
        modeloTabla.setRowCount(0);  // Limpiar la tabla

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/concesionario", "root", bdpass)) {
            String sql = "SELECT * FROM pagos";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    modeloTabla.addRow(new Object[]{
                        rs.getString("fecha"),
                        rs.getInt("nocontrol"),
                        rs.getDouble("importe"),
                        rs.getString("mesaplicado"),
                        rs.getString("tipo"),
                        rs.getString("cliente")
                    });
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener los datos de la base de datos: " + ex.getMessage());
        }
    }

    // Método para actualizar un pago
    public void modificarPago(JTextField fechaField, JTextField nocontrolField, JTextField importeField, JTextField mesaplicadoField, JTextField tipoField, JTextField clienteField) {
        fecha = fechaField.getText();
        nocontrol = Integer.parseInt(nocontrolField.getText());
        importe = Double.parseDouble(importeField.getText());
        mesaplicado = mesaplicadoField.getText();
        tipo = tipoField.getText();
        cliente = clienteField.getText();

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/concesionario", "root", bdpass)) {
            String sql = "UPDATE pagos SET fecha = ?, importe = ?, mesaplicado = ?, tipo = ?, cliente = ? WHERE nocontrol = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, fecha);
                stmt.setDouble(2, importe);
                stmt.setString(3, mesaplicado);
                stmt.setString(4, tipo);
                stmt.setString(5, cliente);
                stmt.setInt(6, nocontrol);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Pago actualizado exitosamente");
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + ex.getMessage());
        }
    }

    // Método para eliminar un pago por nocontrol
    public void eliminarPago(JTextField nocontrolField) {
        nocontrol = Integer.parseInt(nocontrolField.getText());

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/concesionario", "root", bdpass)) {
            String sql = "DELETE FROM pagos WHERE nocontrol = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, nocontrol);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Pago eliminado exitosamente");
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + ex.getMessage());
        }
    }
}
