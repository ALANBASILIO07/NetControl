package Files;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Alan Basilio
 */
public class Sucursales
{

    private String nombre;
    private String gerente;
    private String direccion;
    private List<Torres> torres;

    // Contraseña de la base de datos
    String bdpass = "100%Freestyle";

    public Sucursales()
    {
        this.torres = new ArrayList<>();  // Inicializamos la lista
    }

    public Sucursales(String nombre, String gerente, String direccion, List<Torres> torres)
    {
        this.nombre = nombre;
        this.gerente = gerente;
        this.direccion = direccion;
        this.torres = torres;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public String getGerente()
    {
        return gerente;
    }

    public void setGerente(String gerente)
    {
        this.gerente = gerente;
    }

    public String getDireccion()
    {
        return direccion;
    }

    public void setDireccion(String direccion)
    {
        this.direccion = direccion;
    }

    public List<Torres> getTorres()
    {
        return torres;
    }

    public void setTorres(List<Torres> torres)
    {
        this.torres = torres;
    }

    public void addTorre(Torres torre)
    {
        this.torres.add(torre);
    }

    // Método para registrar una nueva sucursal
    public void altaSucursal(JTextField nombreField, JTextField gerenteField, JTextField direccionField)
    {
        nombre = nombreField.getText();
        gerente = gerenteField.getText();
        direccion = direccionField.getText();

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/netcontrol", "root", bdpass))
        {
            String sql = "INSERT INTO sucursales (nombre, gerente, direccion) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql))
            {
                stmt.setString(1, nombre);
                stmt.setString(2, gerente);
                stmt.setString(3, direccion);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0)
                {
                    JOptionPane.showMessageDialog(null, "Sucursal registrada exitosamente");
                }
            }
        } catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + ex.getMessage());
        }
    }

    // Método para buscar una sucursal por nombre
    public int buscarSucursal(JTextField nombreField)
    {
        nombre = nombreField.getText();

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/netcontrol", "root", bdpass))
        {
            String sql = "SELECT * FROM sucursales WHERE nombre = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql))
            {
                stmt.setString(1, nombre);
                ResultSet rs = stmt.executeQuery();
                if (rs.next())
                {
                    return 1;  // Sucursal encontrada
                }
            }
        } catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }
        return 0;  // Sucursal no encontrada
    }

    // Método para listar todas las sucursales en una tabla
    public void consultaGeneralSucursales(JTable tablaSucursales)
    {
        DefaultTableModel modeloTabla = (DefaultTableModel) tablaSucursales.getModel();
        modeloTabla.setRowCount(0);  // Limpiar la tabla

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/netcontrol", "root", bdpass))
        {
            String sql = "SELECT * FROM sucursales";
            try (PreparedStatement stmt = conn.prepareStatement(sql))
            {
                ResultSet rs = stmt.executeQuery();
                while (rs.next())
                {
                    modeloTabla.addRow(new Object[]
                    {
                        rs.getString("nombre"),
                        rs.getString("gerente"),
                        rs.getString("direccion")
                    });
                }
            }
        } catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Error al obtener los datos de la base de datos: " + ex.getMessage());
        }
    }

    public void consultaGeneralSucursalesContador(JTable tablaSucursales)
    {
        DefaultTableModel modeloTabla = (DefaultTableModel) tablaSucursales.getModel();
        modeloTabla.setRowCount(0);  // Limpiar la tabla

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/netcontrol", "root", bdpass))
        {
            String sql = "SELECT s.nombre, s.gerente, s.direccion, "
                    + "IFNULL((SELECT COUNT(*) FROM torres t WHERE t.NombreSucursal = s.nombre), 0) AS torres_dependientes "
                    + "FROM sucursales s";

            try (PreparedStatement stmt = conn.prepareStatement(sql))
            {
                ResultSet rs = stmt.executeQuery();
                while (rs.next())
                {
                    modeloTabla.addRow(new Object[]
                    {
                        rs.getString("nombre"),
                        rs.getString("gerente"),
                        rs.getString("direccion"),
                        rs.getInt("torres_dependientes")  // Número de torres dependientes
                    });
                }
            }
        } catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Error al obtener los datos de la base de datos: " + ex.getMessage());
        }
    }

    public void consultaSucursalEspecifica(JTable tablaSucursales, JComboBox<String> comboElimina)
    {
        DefaultTableModel modeloTabla = (DefaultTableModel) tablaSucursales.getModel();
        modeloTabla.setRowCount(0);  // Limpiar la tabla

        // Obtener la sucursal seleccionada del JComboBox
        String sucursalSeleccionada = (String) comboElimina.getSelectedItem();

        if (sucursalSeleccionada != null && !sucursalSeleccionada.isEmpty())
        {
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/netcontrol", "root", bdpass))
            {
                String sql = "SELECT * FROM sucursales WHERE nombre = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql))
                {
                    stmt.setString(1, sucursalSeleccionada);  // Asignar la sucursal seleccionada al parámetro de la consulta
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next())
                    {
                        modeloTabla.addRow(new Object[]
                        {
                            rs.getString("nombre"),
                            rs.getString("gerente"),
                            rs.getString("direccion")
                        });
                    }
                }
            } catch (SQLException ex)
            {
                JOptionPane.showMessageDialog(null, "Error al obtener los datos de la base de datos: " + ex.getMessage());
            }
        } else
        {
            //JOptionPane.showMessageDialog(null, "Seleccione una sucursal para consultar.");
        }
    }

    // Método para actualizar una sucursal
    public void modificarSucursal(JTextField nombreField, JTextField gerenteField, JTextField direccionField)
    {
        nombre = nombreField.getText();
        gerente = gerenteField.getText();
        direccion = direccionField.getText();

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/netcontrol", "root", bdpass))
        {
            String sql = "UPDATE sucursales SET gerente = ?, direccion = ? WHERE nombre = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql))
            {
                stmt.setString(1, gerente);
                stmt.setString(2, direccion);
                stmt.setString(3, nombre);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0)
                {
                    JOptionPane.showMessageDialog(null, "Sucursal actualizada exitosamente");
                }
            }
        } catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + ex.getMessage());
        }
    }

    // Método agrega combo para modifica
    public void agregaCombo(JComboBox<String> jcb)
    {
        // Limpiar el ComboBox actual
        jcb.removeAllItems();

        // Conexión a la base de datos y consulta de sucursales
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/netcontrol", "root", bdpass))
        {
            String sql = "SELECT nombre FROM sucursales";  // Cambia la consulta para obtener datos de la tabla 'sucursales'
            try (PreparedStatement stmt = conn.prepareStatement(sql))
            {
                ResultSet rs = stmt.executeQuery();
                // Llenar el ComboBox con los datos de la base de datos
                while (rs.next())
                {
                    jcb.addItem(rs.getString("nombre"));  // Agrega el valor del campo 'nombre' al ComboBox
                }
            }
        } catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Error al obtener los datos de la base de datos: " + ex.getMessage());
        }
    }

    // 
    public void selecSucursal(JComboBox<String> jcb, JTextField txtNombre, JTextField txtGerente, JTextField txtDireccion)
    {
        // Obtener el nombre seleccionado del JComboBox
        String sucursalSeleccionada = (String) jcb.getSelectedItem();

        // Verificar si se ha seleccionado un elemento válido
        if (sucursalSeleccionada != null && !sucursalSeleccionada.isEmpty())
        {
            // Conexión a la base de datos y consulta de datos de la sucursal
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/netcontrol", "root", bdpass))
            {
                String sql = "SELECT * FROM sucursales WHERE nombre = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql))
                {
                    stmt.setString(1, sucursalSeleccionada);
                    ResultSet rs = stmt.executeQuery();

                    // Verificar si se ha encontrado el registro correspondiente
                    if (rs.next())
                    {
                        txtNombre.setText(rs.getString("nombre")); // Nombre de la sucursal
                        txtGerente.setText(rs.getString("gerente")); // Gerente de la sucursal
                        txtDireccion.setText(rs.getString("direccion")); // Dirección de la sucursal
                    } else
                    {
                        JOptionPane.showMessageDialog(null, "No se encontró la sucursal con el nombre seleccionado.");
                    }
                }
            } catch (SQLException ex)
            {
                JOptionPane.showMessageDialog(null, "Error al obtener los datos de la base de datos: " + ex.getMessage());
            }
        }
    }

    // Método para eliminar una sucursal por nombre
    public void eliminarSucursal(JComboBox<String> comboSucursal)
    {
        // Obtener el nombre de la sucursal seleccionada del JComboBox
        String nombreSucursal = (String) comboSucursal.getSelectedItem();

        if (nombreSucursal != null && !nombreSucursal.isEmpty())
        {
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/netcontrol", "root", bdpass))
            {
                String sql = "DELETE FROM sucursales WHERE nombre = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql))
                {
                    stmt.setString(1, nombreSucursal); // Asignar la sucursal seleccionada al parámetro de la consulta

                    int rowsAffected = stmt.executeUpdate();
                    if (rowsAffected > 0)
                    {
                        JOptionPane.showMessageDialog(null, "Sucursal eliminada exitosamente");
                    } else
                    {
                        JOptionPane.showMessageDialog(null, "No se encontró la sucursal para eliminar.");
                    }
                }
            } catch (SQLException ex)
            {
                JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + ex.getMessage());
            }
        } else
        {
            JOptionPane.showMessageDialog(null, "Seleccione una sucursal para eliminar.");
        }
    }

}
