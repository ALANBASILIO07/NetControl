package Files;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JLabel;
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
    public void altaTorre(JComboBox<String> sucursalComboBox, JTextField zonaField, JTextField direccionField, JTextField etiquetaField)
    {
        String sucursal = sucursalComboBox.getSelectedItem().toString(); // Obtener la sucursal seleccionada
        String zona = zonaField.getText();
        String direccion = direccionField.getText();
        String etiqueta = etiquetaField.getText();

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/netcontrol", "root", bdpass))
        {
            String sql = "INSERT INTO torres (Zona, Direccion, Etiqueta, NombreSucursal) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql))
            {
                stmt.setString(1, zona);
                stmt.setString(2, direccion);
                stmt.setString(3, etiqueta);
                stmt.setString(4, sucursal);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0)
                {
                    JOptionPane.showMessageDialog(null, "Torre registrada exitosamente");
                }
            }
        } catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + ex.getMessage());
        }
    }

    // Método para buscar una torre por zona
    public int buscarTorre(JTextField zonaField)
    {
        zona = zonaField.getText();

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/concesionario", "root", bdpass))
        {
            String sql = "SELECT * FROM torres WHERE zona = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql))
            {
                stmt.setString(1, zona);
                ResultSet rs = stmt.executeQuery();
                if (rs.next())
                {
                    return 1;  // Torre encontrada
                }
            }
        } catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }
        return 0;  // Torre no encontrada
    }

    // Método para listar todas las torres en una tabla
    public void consultaGeneralTorres(JTable tablaTorres)
    {
        DefaultTableModel modeloTabla = (DefaultTableModel) tablaTorres.getModel();
        modeloTabla.setRowCount(0);  // Limpiar la tabla

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/netcontrol", "root", bdpass))
        {
            String sql = "SELECT * FROM torres";
            try (PreparedStatement stmt = conn.prepareStatement(sql))
            {
                ResultSet rs = stmt.executeQuery();
                while (rs.next())
                {
                    modeloTabla.addRow(new Object[]
                    {
                        rs.getInt("nocontrol"),
                        rs.getString("zona"),
                        rs.getString("direccion"),
                        rs.getString("etiqueta"),
                        rs.getString("NombreSucursal")
                    });
                }
            }
        } catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Error al obtener los datos de la base de datos: " + ex.getMessage());
        }
    }

    // Método para actualizar una torre
    public void modificarTorre(JTextField zonaField, JTextField direccionField, JTextField etiquetaField, JComboBox<String> jcbSucursal)
    {
        // Obtener los valores de los campos
        String zona = zonaField.getText();
        String direccion = direccionField.getText();
        String etiqueta = etiquetaField.getText();
        String sucursalSeleccionada = (String) jcbSucursal.getSelectedItem();

        // Verificar si la sucursal ha sido seleccionada
        if (sucursalSeleccionada == null || sucursalSeleccionada.isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Por favor, seleccione una sucursal.");
            return;
        }

        // Validar que los campos no estén vacíos
        if (etiqueta.isEmpty() || direccion.isEmpty() || zona.isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Todos los campos deben estar completos.");
            return;
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/netcontrol", "root", bdpass))
        {
            // Preparar la consulta SQL para actualizar los datos
            String sql = "UPDATE torres SET direccion = ?, etiqueta = ?, NombreSucursal = ? WHERE zona = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql))
            {
                stmt.setString(1, direccion); // Actualizar dirección
                stmt.setString(2, etiqueta);  // Actualizar etiqueta
                stmt.setString(3, sucursalSeleccionada); // Actualizar sucursal
                stmt.setString(4, zona); // Condición de búsqueda por zona

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0)
                {
                    JOptionPane.showMessageDialog(null, "Torre actualizada exitosamente");
                } else
                {
                    JOptionPane.showMessageDialog(null, "No se encontró la torre con la zona proporcionada.");
                }
            }
        } catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + ex.getMessage());
        }
    }

    public void agregaComboTorre(JComboBox<String> jcb)
    {
        // Limpiar el ComboBox actual
        jcb.removeAllItems();

        // Conexión a la base de datos y consulta de sucursales
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/netcontrol", "root", bdpass))
        {
            String sql = "SELECT Etiqueta FROM torres";  // Cambia la consulta para obtener datos de la tabla 'pagos'
            try (PreparedStatement stmt = conn.prepareStatement(sql))
            {
                ResultSet rs = stmt.executeQuery();
                // Llenar el ComboBox con los datos de la base de datos
                while (rs.next())
                {
                    jcb.addItem(rs.getString("Etiqueta"));  // Agrega el valor del campo 'Nocontrol' al ComboBox
                }
            }
        } catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Error al obtener los datos de la base de datos: " + ex.getMessage());
        }
    }

    public void selectTorre(JComboBox<String> jcbTorre, JComboBox<String> jcbSucursal, JLabel lblNoControl, JTextField txtEtiqueta, JTextField txtDireccion, JTextField txtZona)
    {
        // Obtener la etiqueta de la torre seleccionada del JComboBox (este sería el combo de torres)
        String etiquetaTorre = (String) jcbTorre.getSelectedItem();

        // Verificar si se ha seleccionado una torre válida
        if (etiquetaTorre != null && !etiquetaTorre.isEmpty())
        {
            // Conexión a la base de datos y consulta de datos de la torre
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/netcontrol", "root", bdpass))
            {
                String sql = "SELECT * FROM torres WHERE Etiqueta = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql))
                {
                    stmt.setString(1, etiquetaTorre); // Establecer la etiqueta para la búsqueda
                    ResultSet rs = stmt.executeQuery();

                    // Verificar si se ha encontrado el registro correspondiente
                    if (rs.next())
                    {
                        lblNoControl.setText(String.valueOf(rs.getInt("NoControl"))); // NoControl en JLabel
                        txtEtiqueta.setText(rs.getString("Etiqueta")); // Etiqueta en JTextField
                        txtDireccion.setText(rs.getString("Direccion")); // Dirección en JTextField
                        txtZona.setText(rs.getString("Zona")); // Zona en JTextField

                        // Establecer el NombreSucursal en el JComboBox de sucursales
                        String nombreSucursal = rs.getString("NombreSucursal");
                        // Agregar la sucursal al JComboBox (si es necesario, también puedes llenarlo con más sucursales)
                        jcbSucursal.setSelectedItem(nombreSucursal); // Seleccionar la sucursal correspondiente en el JComboBox
                    } else
                    {
                        JOptionPane.showMessageDialog(null, "No se encontró la torre con la etiqueta ingresada.");
                    }
                }
            } catch (SQLException ex)
            {
                JOptionPane.showMessageDialog(null, "Error al obtener los datos de la base de datos: " + ex.getMessage());
            }
        } else
        {
            JOptionPane.showMessageDialog(null, "Por favor seleccione una torre.");
        }
    }

    public void consultaTorreEspecifica(JTable tablaTorres, JComboBox<String> comboElimina)
    {
        DefaultTableModel modeloTabla = (DefaultTableModel) tablaTorres.getModel();
        modeloTabla.setRowCount(0);  // Limpiar la tabla

        // Obtener la etiqueta de la torre seleccionada del JComboBox
        String etiquetaSeleccionada = (String) comboElimina.getSelectedItem();

        if (etiquetaSeleccionada != null && !etiquetaSeleccionada.isEmpty())
        {
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/netcontrol", "root", bdpass))
            {
                String sql = "SELECT * FROM torres WHERE Etiqueta = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql))
                {
                    stmt.setString(1, etiquetaSeleccionada);  // Asignar la etiqueta seleccionada al parámetro de la consulta
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next())
                    {
                        modeloTabla.addRow(new Object[]
                        {
                            rs.getInt("NoControl"), // Número de control de la torre
                            rs.getString("Zona"), // Zona de la torre
                            rs.getString("Direccion"), // Dirección de la torre
                            rs.getString("Etiqueta"), // Etiqueta de la torre
                            rs.getString("NombreSucursal") // Nombre de la sucursal asociada
                        });
                    }
                }
            } catch (SQLException ex)
            {
                JOptionPane.showMessageDialog(null, "Error al obtener los datos de la base de datos: " + ex.getMessage());
            }
        } else
        {
            JOptionPane.showMessageDialog(null, "Seleccione una torre para consultar.");
        }
    }

    // Método para eliminar una torre por zona
    public void eliminarTorre(JTextField zonaField)
    {
        zona = zonaField.getText();

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/concesionario", "root", bdpass))
        {
            String sql = "DELETE FROM torres WHERE zona = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql))
            {
                stmt.setString(1, zona);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0)
                {
                    JOptionPane.showMessageDialog(null, "Torre eliminada exitosamente");
                }
            }
        } catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + ex.getMessage());
        }
    }
}
