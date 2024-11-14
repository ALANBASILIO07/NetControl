package Files;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public void altaCliente(JTextField nombreField, JTextField IPField, JLabel vigenciaLabel, JTextField domicilioField, JComboBox<String> sucursalComboBox, JComboBox<String> torreComboBox)
    {
        String nombre = nombreField.getText();
        String IP = IPField.getText();
        String vigenciaTexto = vigenciaLabel.getText();
        String domicilio = domicilioField.getText();
        String sucursal = (String) sucursalComboBox.getSelectedItem();
        String torreEtiqueta = (String) torreComboBox.getSelectedItem();
        Integer noControlTorre = null;

        // Convertir la fecha a formato 'yyyy-MM-dd'
        String vigencia = null;
        try
        {
            SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat formatoSalida = new SimpleDateFormat("yyyy-MM-dd");
            Date fecha = formatoEntrada.parse(vigenciaTexto);
            vigencia = formatoSalida.format(fecha);
        } catch (ParseException e)
        {
            JOptionPane.showMessageDialog(null, "Formato de fecha incorrecto: " + e.getMessage());
            return;
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/netcontrol", "root", bdpass))
        {
            // Consultar el NoControl de la torre seleccionada
            String sqlTorre = "SELECT NoControl FROM torres WHERE Etiqueta = ?";
            try (PreparedStatement stmtTorre = conn.prepareStatement(sqlTorre))
            {
                stmtTorre.setString(1, torreEtiqueta);
                try (ResultSet rs = stmtTorre.executeQuery())
                {
                    if (rs.next())
                    {
                        noControlTorre = rs.getInt("NoControl");
                    } else
                    {
                        JOptionPane.showMessageDialog(null, "No se encontró la torre seleccionada.");
                        return;
                    }
                }
            }

            // Insertar en la tabla clientes usando el NoControl obtenido
            String sqlCliente = "INSERT INTO clientes (Nombre, IP, Vigencia, Domicilio, NombreSucursal, NoControlTorre) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmtCliente = conn.prepareStatement(sqlCliente))
            {
                stmtCliente.setString(1, nombre);
                stmtCliente.setString(2, IP);
                stmtCliente.setString(3, vigencia); // Usar la fecha en formato yyyy-MM-dd
                stmtCliente.setString(4, domicilio);
                stmtCliente.setString(5, sucursal);
                stmtCliente.setInt(6, noControlTorre);

                int rowsAffected = stmtCliente.executeUpdate();
                if (rowsAffected > 0)
                {
                    JOptionPane.showMessageDialog(null, "Cliente registrado exitosamente");
                }
            }
        } catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + ex.getMessage());
        }
    }

    // Método para buscar un cliente por ID
    public int buscarCliente(JTextField IDField)
    {
        ID = Integer.parseInt(IDField.getText());

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/netcontrol", "root", bdpass))
        {
            String sql = "SELECT * FROM clientes WHERE ID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql))
            {
                stmt.setInt(1, ID);
                ResultSet rs = stmt.executeQuery();
                if (rs.next())
                {
                    return 1;  // Cliente encontrado
                }
            }
        } catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }
        return 0;  // Cliente no encontrado
    }

    // Método para listar todos los clientes en una tabla
    public void consultaGeneralClientes(JTable tablaClientes)
    {
        DefaultTableModel modeloTabla = (DefaultTableModel) tablaClientes.getModel();
        modeloTabla.setRowCount(0);  // Limpiar la tabla

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/netcontrol", "root", bdpass))
        {
            String sql = "SELECT * FROM clientes";
            try (PreparedStatement stmt = conn.prepareStatement(sql))
            {
                ResultSet rs = stmt.executeQuery();
                while (rs.next())
                {
                    modeloTabla.addRow(new Object[]
                    {
                        rs.getInt("ID"),
                        rs.getString("nombre"),
                        rs.getString("IP"),
                        rs.getString("vigencia"),
                        rs.getString("domicilio"),
                        rs.getString("status"),
                        rs.getString("NombreSucursal"),
                        rs.getString("NoControlTorre")
                    });
                }
            }
        } catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Error al obtener los datos de la base de datos: " + ex.getMessage());
        }
    }

    // Método para actualizar un cliente
    public void modificarCliente(JTextField nombreField, JTextField IPField, JTextField domicilioField, JComboBox<String> sucursalComboBox, JComboBox<String> torreComboBox)
    {
        String nombre = nombreField.getText();
        String IP = IPField.getText();
        String domicilio = domicilioField.getText();
        String sucursal = (String) sucursalComboBox.getSelectedItem();
        String torreEtiqueta = (String) torreComboBox.getSelectedItem();
        Integer noControlTorre = null;

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/netcontrol", "root", bdpass))
        {
            // Consultar el NoControl de la torre usando la etiqueta seleccionada
            String sqlTorre = "SELECT NoControl FROM torres WHERE Etiqueta = ?";
            try (PreparedStatement stmtTorre = conn.prepareStatement(sqlTorre))
            {
                stmtTorre.setString(1, torreEtiqueta);
                try (ResultSet rs = stmtTorre.executeQuery())
                {
                    if (rs.next())
                    {
                        noControlTorre = rs.getInt("NoControl");
                    } else
                    {
                        JOptionPane.showMessageDialog(null, "No se encontró la torre seleccionada.");
                        return;
                    }
                }
            }

            // Actualizar la tabla clientes sin modificar ID ni status
            String sqlCliente = "UPDATE clientes SET Nombre = ?, IP = ?, Domicilio = ?, NombreSucursal = ?, NoControlTorre = ? WHERE Nombre = ?";
            try (PreparedStatement stmtCliente = conn.prepareStatement(sqlCliente))
            {
                stmtCliente.setString(1, nombre);
                stmtCliente.setString(2, IP);
                stmtCliente.setString(3, domicilio);
                stmtCliente.setString(4, sucursal);
                stmtCliente.setInt(5, noControlTorre);
                stmtCliente.setString(6, nombre); // Aquí usamos el nombre del cliente como referencia para identificar el registro

                int rowsAffected = stmtCliente.executeUpdate();
                if (rowsAffected > 0)
                {
                    JOptionPane.showMessageDialog(null, "Cliente actualizado exitosamente");
                } else
                {
                    JOptionPane.showMessageDialog(null, "No se encontró el cliente para actualizar.");
                }
            }
        } catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + ex.getMessage());
        }
    }

    public void agregaCombo(JComboBox<String> jcb)
    {
        // Limpiar el ComboBox actual
        jcb.removeAllItems();

        // Conexión a la base de datos y consulta de sucursales
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/netcontrol", "root", bdpass))
        {
            String sql = "SELECT Nombre FROM clientes";  // Cambia la consulta para obtener datos de la tabla 'pagos'
            try (PreparedStatement stmt = conn.prepareStatement(sql))
            {
                ResultSet rs = stmt.executeQuery();
                // Llenar el ComboBox con los datos de la base de datos
                while (rs.next())
                {
                    jcb.addItem(rs.getString("Nombre"));  // Agrega el valor del campo 'Nocontrol' al ComboBox
                }
            }
        } catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Error al obtener los datos de la base de datos: " + ex.getMessage());
        }
    }

    public void agregaComboSucursal(JComboBox<String> jcb)
    {
        // Limpiar el ComboBox actual
        jcb.removeAllItems();

        // Conexión a la base de datos y consulta de sucursales
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/netcontrol", "root", bdpass))
        {
            String sql = "SELECT Nombre FROM sucursales";  // Cambia la consulta para obtener datos de la tabla 'pagos'
            try (PreparedStatement stmt = conn.prepareStatement(sql))
            {
                ResultSet rs = stmt.executeQuery();
                // Llenar el ComboBox con los datos de la base de datos
                while (rs.next())
                {
                    jcb.addItem(rs.getString("Nombre"));  // Agrega el valor del campo 'Nocontrol' al ComboBox
                }
            }
        } catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Error al obtener los datos de la base de datos: " + ex.getMessage());
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

    public void selectCliente(JComboBox<String> clienteComboBox, JTextField nombreField, JTextField IPField, JTextField domicilioField, JComboBox<String> sucursalComboBox, JComboBox<String> torreComboBox)
    {
        // Obtener el nombre o ID del cliente seleccionado del JComboBox
        String clienteSeleccionado = (String) clienteComboBox.getSelectedItem();

        // Verificar si se ha seleccionado un cliente válido
        if (clienteSeleccionado != null && !clienteSeleccionado.isEmpty())
        {
            // Conexión a la base de datos y consulta de datos del cliente
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/netcontrol", "root", bdpass))
            {
                String sqlCliente = "SELECT Nombre, IP, Domicilio, NombreSucursal, NoControlTorre FROM clientes WHERE Nombre = ?";
                try (PreparedStatement stmtCliente = conn.prepareStatement(sqlCliente))
                {
                    stmtCliente.setString(1, clienteSeleccionado);
                    try (ResultSet rsCliente = stmtCliente.executeQuery())
                    {
                        // Verificar si se ha encontrado el registro correspondiente
                        if (rsCliente.next())
                        {
                            nombreField.setText(rsCliente.getString("Nombre"));            // Nombre del cliente
                            IPField.setText(rsCliente.getString("IP"));                    // IP del cliente
                            domicilioField.setText(rsCliente.getString("Domicilio"));      // Domicilio del cliente

                            // Seleccionar la sucursal correspondiente en el JComboBox
                            String sucursal = rsCliente.getString("NombreSucursal");
                            sucursalComboBox.setSelectedItem(sucursal);

                            // Obtener el NoControlTorre
                            int noControlTorre = rsCliente.getInt("NoControlTorre");

                            // Consultar la etiqueta correspondiente en la tabla torres
                            String sqlTorre = "SELECT Etiqueta FROM torres WHERE NoControl = ?";
                            try (PreparedStatement stmtTorre = conn.prepareStatement(sqlTorre))
                            {
                                stmtTorre.setInt(1, noControlTorre);
                                try (ResultSet rsTorre = stmtTorre.executeQuery())
                                {
                                    if (rsTorre.next())
                                    {
                                        // Seleccionar la etiqueta en el JComboBox torreComboBox
                                        String etiquetaTorre = rsTorre.getString("Etiqueta");
                                        torreComboBox.setSelectedItem(etiquetaTorre);
                                    } else
                                    {
                                        JOptionPane.showMessageDialog(null, "No se encontró la torre con el número de control especificado.");
                                    }
                                }
                            }
                        } else
                        {
                            JOptionPane.showMessageDialog(null, "No se encontró el cliente seleccionado.");
                        }
                    }
                }
            } catch (SQLException ex)
            {
                JOptionPane.showMessageDialog(null, "Error al obtener los datos de la base de datos: " + ex.getMessage());
            }
        }
    }

    public void consultaClienteEspecifico(JTable tablaClientes, JComboBox<String> comboClientes)
    {
        DefaultTableModel modeloTabla = (DefaultTableModel) tablaClientes.getModel();
        modeloTabla.setRowCount(0);  // Limpiar la tabla

        // Obtener el cliente seleccionado del JComboBox
        String clienteSeleccionado = (String) comboClientes.getSelectedItem();

        if (clienteSeleccionado != null && !clienteSeleccionado.isEmpty())
        {
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/netcontrol", "root", bdpass))
            {
                String sql = "SELECT ID, Nombre, IP, Vigencia, Domicilio, Status, NombreSucursal, NoControlTorre FROM clientes WHERE Nombre = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql))
                {
                    stmt.setString(1, clienteSeleccionado);  // Asignar el cliente seleccionado al parámetro de la consulta
                    ResultSet rs = stmt.executeQuery();

                    while (rs.next())
                    {
                        // Convertir el NoControlTorre a la etiqueta de la torre
                        String torreEtiqueta = obtenerEtiquetaTorre(rs.getInt("NoControlTorre"), conn);

                        modeloTabla.addRow(new Object[]
                        {
                            rs.getInt("ID"), // ID
                            rs.getString("Nombre"), // Nombre
                            rs.getString("IP"), // IP
                            rs.getDate("Vigencia"), // Fecha de Instalación o Vigencia
                            rs.getString("Domicilio"), // Domicilio
                            rs.getString("Status"), // Estatus
                            rs.getString("NombreSucursal"), // Sucursal
                            torreEtiqueta                     // Torre (Etiqueta convertida)
                        });
                    }
                }
            } catch (SQLException ex)
            {
                JOptionPane.showMessageDialog(null, "Error al obtener los datos de la base de datos: " + ex.getMessage());
            }
        } else
        {
            //JOptionPane.showMessageDialog(null, "Seleccione un cliente para consultar.");
        }
    }

    // Método auxiliar para obtener la etiqueta de la torre basada en el NoControl
    private String obtenerEtiquetaTorre(int noControlTorre, Connection conn) throws SQLException
    {
        String etiquetaTorre = null;
        String sql = "SELECT Etiqueta FROM torres WHERE NoControl = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setInt(1, noControlTorre);
            try (ResultSet rs = stmt.executeQuery())
            {
                if (rs.next())
                {
                    etiquetaTorre = rs.getString("Etiqueta");
                }
            }
        }
        return etiquetaTorre;
    }

    // Método para eliminar un cliente por ID
    public void eliminarCliente(JComboBox<String> comboElimina)
    {
        // Obtener el nombre del cliente seleccionado en el JComboBox
        String nombreCliente = (String) comboElimina.getSelectedItem();

        if (nombreCliente != null && !nombreCliente.isEmpty())
        {
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/netcontrol", "root", bdpass))
            {
                String sql = "DELETE FROM clientes WHERE Nombre = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql))
                {
                    stmt.setString(1, nombreCliente);

                    int rowsAffected = stmt.executeUpdate();
                    if (rowsAffected > 0)
                    {
                        JOptionPane.showMessageDialog(null, "Cliente eliminado exitosamente");
                    } else
                    {
                        JOptionPane.showMessageDialog(null, "No se encontró el cliente especificado.");
                    }
                }
            } catch (SQLException ex)
            {
                JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + ex.getMessage());
            }
        } else
        {
            JOptionPane.showMessageDialog(null, "Seleccione un cliente para eliminar.");
        }
    }

}
