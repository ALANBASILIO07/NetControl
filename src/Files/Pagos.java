package Files;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
    public void altaPago(JComboBox<String> comboCliente, JComboBox<String> comboMesAplicado, JComboBox<String> comboTipo, JLabel fechaLabel, JTextField importeField)
    {
        // Obtener el cliente seleccionado del ComboBox
        String nombreCliente = (String) comboCliente.getSelectedItem();
        int clienteID = obtenerIDCliente(nombreCliente); // Obtener el ID del cliente usando la función

        // Obtener los otros campos de los ComboBoxes
        String mesaplicado = (String) comboMesAplicado.getSelectedItem();
        String tipo = (String) comboTipo.getSelectedItem();

        // Obtener la fecha desde el JLabel y convertirla al formato MySQL (yyyy-MM-dd)
        String fechaString = fechaLabel.getText(); // La fecha en formato dd/MM/yyyy
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
        java.text.SimpleDateFormat mysqlDateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = null;
        try
        {
            date = sdf.parse(fechaString);
        } catch (java.text.ParseException e)
        {
            JOptionPane.showMessageDialog(null, "Error al convertir la fecha: " + e.getMessage());
            return;
        }
        String fecha = mysqlDateFormat.format(date); // Convertir la fecha al formato correcto

        // Obtener el importe desde el JTextField
        String importeString = importeField.getText(); // Obtener el importe del JTextField
        double importe = 0.0;
        try
        {
            importe = Double.parseDouble(importeString);
        } catch (NumberFormatException e)
        {
            JOptionPane.showMessageDialog(null, "Error al ingresar el importe: " + e.getMessage());
            return;
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/netcontrol", "root", bdpass))
        {
            String sql = "INSERT INTO pagos (fecha, importe, mesaplicado, tipo, clienteID) VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement stmt = conn.prepareStatement(sql))
            {
                stmt.setString(1, fecha); // Fecha en formato yyyy-MM-dd
                stmt.setDouble(2, importe);
                stmt.setString(3, mesaplicado);
                stmt.setString(4, tipo);
                stmt.setInt(5, clienteID); // ID del cliente obtenido

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0)
                {
                    JOptionPane.showMessageDialog(null, "Pago registrado exitosamente");
                }
            }
        } catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + ex.getMessage());
        }
    }

    // Método para buscar un pago por nocontrol
    public int buscarPago(JTextField nocontrolField)
    {
        nocontrol = Integer.parseInt(nocontrolField.getText());

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/concesionario", "root", bdpass))
        {
            String sql = "SELECT * FROM pagos WHERE nocontrol = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql))
            {
                stmt.setInt(1, nocontrol);
                ResultSet rs = stmt.executeQuery();
                if (rs.next())
                {
                    return 1;  // Pago encontrado
                }
            }
        } catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }
        return 0;  // Pago no encontrado
    }

    // Método para listar todos los pagos en una tabla
    public void consultaPagosCliente(JTable tablaPagos, JComboBox<String> comboClientes)
    {
        DefaultTableModel modeloTabla = (DefaultTableModel) tablaPagos.getModel();
        modeloTabla.setRowCount(0);  // Limpiar la tabla

        // Obtener el nombre del cliente seleccionado del ComboBox
        String nombreCliente = (String) comboClientes.getSelectedItem();

        if (nombreCliente == null || nombreCliente.isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Por favor seleccione un cliente.");
            return;
        }

        // Obtener el ID del cliente a partir de su nombre
        int clienteID = obtenerIDCliente(nombreCliente);

        if (clienteID == -1)
        {
            JOptionPane.showMessageDialog(null, "Cliente no encontrado.");
            return;
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/netcontrol", "root", bdpass))
        {
            String sql = "SELECT * FROM pagos WHERE ClienteID = ?";

            try (PreparedStatement stmt = conn.prepareStatement(sql))
            {
                stmt.setInt(1, clienteID);  // Establecer el ID del cliente en la consulta
                ResultSet rs = stmt.executeQuery();
                while (rs.next())
                {
                    modeloTabla.addRow(new Object[]
                    {
                        rs.getDate("Fecha"),
                        rs.getInt("NoControl"),
                        rs.getDouble("Importe"),
                        rs.getString("MesAplicado"),
                        rs.getString("Tipo"),
                        clienteID  // El ID del cliente se muestra, aunque se puede reemplazar por nombre si es necesario
                    });
                }
            }
        } catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Error al obtener los datos de la base de datos: " + ex.getMessage());
        }
    }

// Método auxiliar para obtener el ID del cliente a partir de su nombre
    private int obtenerIDCliente(String nombreCliente)
    {
        int idCliente = -1; // Si no se encuentra, retorna -1
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/netcontrol", "root", bdpass))
        {
            String sql = "SELECT ID FROM clientes WHERE Nombre = ?";

            try (PreparedStatement stmt = conn.prepareStatement(sql))
            {
                stmt.setString(1, nombreCliente);  // Establecer el nombre del cliente en la consulta
                ResultSet rs = stmt.executeQuery();

                if (rs.next())
                {
                    idCliente = rs.getInt("ID");
                }
            }
        } catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Error al obtener el ID del cliente: " + ex.getMessage());
        }
        return idCliente;
    }

    // Método para actualizar un pago
    public void modificarPago(JTextField fechaField, JTextField nocontrolField, JTextField importeField, JTextField mesaplicadoField, JTextField tipoField, JTextField clienteField)
    {
        fecha = fechaField.getText();
        nocontrol = Integer.parseInt(nocontrolField.getText());
        importe = Double.parseDouble(importeField.getText());
        mesaplicado = mesaplicadoField.getText();
        tipo = tipoField.getText();
        cliente = clienteField.getText();

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/concesionario", "root", bdpass))
        {
            String sql = "UPDATE pagos SET fecha = ?, importe = ?, mesaplicado = ?, tipo = ?, cliente = ? WHERE nocontrol = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql))
            {
                stmt.setString(1, fecha);
                stmt.setDouble(2, importe);
                stmt.setString(3, mesaplicado);
                stmt.setString(4, tipo);
                stmt.setString(5, cliente);
                stmt.setInt(6, nocontrol);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0)
                {
                    JOptionPane.showMessageDialog(null, "Pago actualizado exitosamente");
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
            String sql = "SELECT Nocontrol FROM pagos";  // Cambia la consulta para obtener datos de la tabla 'pagos'
            try (PreparedStatement stmt = conn.prepareStatement(sql))
            {
                ResultSet rs = stmt.executeQuery();
                // Llenar el ComboBox con los datos de la base de datos
                while (rs.next())
                {
                    jcb.addItem(rs.getString("Nocontrol"));  // Agrega el valor del campo 'Nocontrol' al ComboBox
                }
            }
        } catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Error al obtener los datos de la base de datos: " + ex.getMessage());
        }
    }

    public void selecPago(JComboBox<String> jcb, JLabel lblCliente, JLabel lblTipoPago, JLabel lblMesAplicado, JLabel lblImporte, JLabel lblFecha)
    {
        // Obtener el NoControl seleccionado del JComboBox
        String noControlSeleccionado = (String) jcb.getSelectedItem();

        // Verificar si se ha seleccionado un elemento válido
        if (noControlSeleccionado != null && !noControlSeleccionado.isEmpty())
        {
            // Conexión a la base de datos y consulta de datos de pagos
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/netcontrol", "root", bdpass))
            {
                String sql = "SELECT p.Fecha, p.Importe, p.MesAplicado, p.Tipo, c.Nombre "
                        + "FROM pagos p "
                        + "JOIN clientes c ON p.ClienteID = c.ID "
                        + "WHERE p.NoControl = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql))
                {
                    stmt.setInt(1, Integer.parseInt(noControlSeleccionado));  // Convertir NoControl a entero
                    ResultSet rs = stmt.executeQuery();

                    // Verificar si se ha encontrado el registro correspondiente
                    if (rs.next())
                    {
                        // Actualizar los JLabels con los datos obtenidos de la base de datos
                        lblCliente.setText(rs.getString("Nombre")); // Cliente
                        lblTipoPago.setText(rs.getString("Tipo")); // Tipo de Pago
                        lblMesAplicado.setText(rs.getString("MesAplicado")); // Mes Aplicado
                        lblImporte.setText(rs.getBigDecimal("Importe").toString()); // Importe
                        lblFecha.setText(rs.getDate("Fecha").toString()); // Fecha
                    } else
                    {
                        JOptionPane.showMessageDialog(null, "No se encontró el pago con el número de control seleccionado.");
                    }
                }
            } catch (SQLException ex)
            {
                JOptionPane.showMessageDialog(null, "Error al obtener los datos de la base de datos: " + ex.getMessage());
            }
        }
    }

    // Método para eliminar un pago por nocontrol
    public void eliminarPago(JTextField nocontrolField)
    {
        nocontrol = Integer.parseInt(nocontrolField.getText());

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/concesionario", "root", bdpass))
        {
            String sql = "DELETE FROM pagos WHERE nocontrol = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql))
            {
                stmt.setInt(1, nocontrol);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0)
                {
                    JOptionPane.showMessageDialog(null, "Pago eliminado exitosamente");
                }
            }
        } catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + ex.getMessage());
        }
    }

    public void consultaPagosPorMes(JTable tablaPagos, JComboBox<String> comboClientes)
    {
        DefaultTableModel modeloTabla = (DefaultTableModel) tablaPagos.getModel();
        modeloTabla.setRowCount(0);  // Limpiar la tabla

        // Obtener el nombre del cliente seleccionado del ComboBox
        String nombreCliente = (String) comboClientes.getSelectedItem();

        if (nombreCliente == null || nombreCliente.isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Por favor seleccione un cliente.");
            return;
        }

        // Obtener el ID del cliente a partir de su nombre
        int clienteID = obtenerIDCliente(nombreCliente);

        if (clienteID == -1)
        {
            JOptionPane.showMessageDialog(null, "Cliente no encontrado.");
            return;
        }

        // Mapa para almacenar las filas correspondientes a los pagos de cada mes
        Map<String, List<Double>> pagosPorMes = new LinkedHashMap<>();

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/netcontrol", "root", bdpass))
        {
            String sql = "SELECT * FROM pagos WHERE ClienteID = ?";

            try (PreparedStatement stmt = conn.prepareStatement(sql))
            {
                stmt.setInt(1, clienteID);  // Establecer el ID del cliente en la consulta
                ResultSet rs = stmt.executeQuery();

                // Iterar a través de los resultados y organizar los pagos por mes
                while (rs.next())
                {
                    String mesAplicado = rs.getString("MesAplicado");
                    double importe = rs.getDouble("Importe");

                    // Agregar el pago al mapa de pagos por mes
                    pagosPorMes.computeIfAbsent(mesAplicado, k -> new ArrayList<>()).add(importe);
                }
            }
        } catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Error al obtener los datos de la base de datos: " + ex.getMessage());
            return;
        }

        // Inicializamos una lista para almacenar las filas
        List<Object[]> filas = new ArrayList<>();

        // Procesar los pagos por mes y generar las filas para la tabla
        for (String mes : pagosPorMes.keySet())
        {
            List<Double> pagos = pagosPorMes.get(mes);

            // Agregar pagos por mes en las filas correspondientes
            for (int i = 0; i < pagos.size(); i++)
            {
                // Crear una nueva fila si es necesario
                if (i >= filas.size())
                {
                    filas.add(new Object[12]);
                }

                // Asignar el importe al mes correspondiente
                int mesIndex = obtenerIndiceMes(mes);
                filas.get(i)[mesIndex] = pagos.get(i);
            }
        }

        // Ahora agregar las filas procesadas a la tabla
        for (Object[] fila : filas)
        {
            modeloTabla.addRow(fila);
        }
    }

// Método para obtener el índice del mes en el arreglo de 12 columnas
    private int obtenerIndiceMes(String mes)
    {
        switch (mes.toLowerCase())
        {
            case "enero":
                return 0;
            case "febrero":
                return 1;
            case "marzo":
                return 2;
            case "abril":
                return 3;
            case "mayo":
                return 4;
            case "junio":
                return 5;
            case "julio":
                return 6;
            case "agosto":
                return 7;
            case "septiembre":
                return 8;
            case "octubre":
                return 9;
            case "noviembre":
                return 10;
            case "diciembre":
                return 11;
            default:
                return -1;  // Mes no válido
        }
    }
}
