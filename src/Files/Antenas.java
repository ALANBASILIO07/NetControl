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
    public void altaAntena(JComboBox<String> categoriaField, JTextField frecuenciaField, JTextField modeloField, JComboBox<String> titularCombo, JComboBox<String> torreCombo, JTextField nocontrolField)
    {
        String categoria = (String) categoriaField.getSelectedItem(); // Obtener la categoría seleccionada
        int frecuencia = Integer.parseInt(frecuenciaField.getText());
        String modelo = modeloField.getText();
        String titular = (String) titularCombo.getSelectedItem(); // Obtener el titular seleccionado
        String torreEtiqueta = (String) torreCombo.getSelectedItem(); // Obtener la etiqueta de la torre seleccionada
        String nocontrol = nocontrolField.getText();  // Asumimos que el campo NoControl se proporciona directamente

        int noControlTorre = 0;

        // Consultar el NoControl de la torre seleccionada
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/netcontrol", "root", bdpass))
        {
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
        } catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Error al consultar la torre: " + ex.getMessage());
            return;
        }

        // Insertar la nueva antena
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/netcontrol", "root", bdpass))
        {
            // Asegurándonos de que el orden en el INSERT coincide con la tabla
            String sql = "INSERT INTO antenas (NoControl, Categoria, Frecuencia, Modelo, Titular, NoControlTorre) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql))
            {
                // Ahora el orden es correcto: NoControl primero y NoControlTorre al final
                stmt.setString(1, nocontrol); // NoControl (debe ser único, asignado directamente desde nocontrolField)
                stmt.setString(2, categoria);  // Categoria
                stmt.setInt(3, frecuencia);    // Frecuencia
                stmt.setString(4, modelo);     // Modelo
                stmt.setString(5, titular);    // Titular
                stmt.setInt(6, noControlTorre); // NoControlTorre (obtenido de la consulta)

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0)
                {
                    JOptionPane.showMessageDialog(null, "Antena registrada exitosamente");
                }
            }
        } catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + ex.getMessage());
        }
    }

    // Método para buscar una antena
    public int buscarAntena(JTextField nocontrolField)
    {
        nocontrol = nocontrolField.getText();

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/concesionario", "root", bdpass))
        {
            String sql = "SELECT * FROM antenas WHERE nocontrol = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql))
            {
                stmt.setString(1, nocontrol);
                ResultSet rs = stmt.executeQuery();
                if (rs.next())
                {
                    return 1;  // Antena encontrada
                }
            }
        } catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }
        return 0;  // Antena no encontrada
    }

    // Método para listar todas las antenas en una tabla
    public void consultaGeneralAntenas(JTable tablaAntenas)
    {
        DefaultTableModel modeloTabla = (DefaultTableModel) tablaAntenas.getModel();
        modeloTabla.setRowCount(0);  // Limpiar la tabla

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/netcontrol", "root", bdpass))
        {
            String sql = "SELECT * FROM antenas";
            try (PreparedStatement stmt = conn.prepareStatement(sql))
            {
                ResultSet rs = stmt.executeQuery();
                while (rs.next())
                {
                    modeloTabla.addRow(new Object[]
                    {
                        rs.getString("nocontrol"),
                        rs.getString("categoria"),
                        rs.getInt("frecuencia"),
                        rs.getString("modelo"),
                        rs.getString("NoControlTorre")
                    });
                }
            }
        } catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Error al obtener los datos de la base de datos: " + ex.getMessage());
        }
    }

    public void consultaAntenaEspecifica(JTable tablaAntenas, JComboBox<String> comboElimina)
    {
        DefaultTableModel modeloTabla = (DefaultTableModel) tablaAntenas.getModel();
        modeloTabla.setRowCount(0);  // Limpiar la tabla

        // Obtener el NoControlTorre seleccionado del JComboBox
        String noControlTorreSeleccionado = (String) comboElimina.getSelectedItem();

        if (noControlTorreSeleccionado != null && !noControlTorreSeleccionado.isEmpty())
        {
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/netcontrol", "root", bdpass))
            {
                // Consulta para obtener antenas asociadas al NoControlTorre seleccionado
                String sql = "SELECT * FROM antenas WHERE NoControl = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql))
                {
                    stmt.setString(1, noControlTorreSeleccionado);  // Asignar el NoControlTorre al parámetro de la consulta
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next())
                    {
                        modeloTabla.addRow(new Object[]
                        {
                            rs.getString("NoControl"), // NoControl de la antena
                            rs.getString("Categoria"), // Categoría de la antena
                            rs.getInt("Frecuencia"), // Frecuencia de la antena
                            rs.getString("Modelo"), // Modelo de la antena
                            rs.getString("NoControlTorre") // Titular de la antena
                        });
                    }
                }
            } catch (SQLException ex)
            {
                JOptionPane.showMessageDialog(null, "Error al obtener los datos de la base de datos: " + ex.getMessage());
            }
        } else
        {
            //JOptionPane.showMessageDialog(null, "Seleccione una torre para consultar.");
        }
    }

    // Método para actualizar una antena
    public void modificarAntena(JComboBox<String> categoriaField, JTextField frecuenciaField, JTextField modeloField, JComboBox<String> titularCombo, JComboBox<String> torreCombo, JTextField nocontrolField)
    {
        String categoria = (String) categoriaField.getSelectedItem();
        int frecuencia = Integer.parseInt(frecuenciaField.getText());
        String modelo = modeloField.getText();
        String titular = (String) titularCombo.getSelectedItem();
        String torreEtiqueta = (String) torreCombo.getSelectedItem();
        String nocontrol = nocontrolField.getText();  // El NoControl de la antena que se va a modificar

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

            // Actualizar la tabla antenas sin modificar NoControl (esto se asume que es el identificador único)
            String sqlAntena = "UPDATE antenas SET Categoria = ?, Frecuencia = ?, Modelo = ?, Titular = ?, NoControlTorre = ? WHERE NoControl = ?";
            try (PreparedStatement stmtAntena = conn.prepareStatement(sqlAntena))
            {
                stmtAntena.setString(1, categoria);
                stmtAntena.setInt(2, frecuencia);
                stmtAntena.setString(3, modelo);
                stmtAntena.setString(4, titular);
                stmtAntena.setInt(5, noControlTorre);
                stmtAntena.setString(6, nocontrol);  // El NoControl de la antena como referencia para la actualización

                int rowsAffected = stmtAntena.executeUpdate();
                if (rowsAffected > 0)
                {
                    JOptionPane.showMessageDialog(null, "Antena actualizada exitosamente");
                } else
                {
                    JOptionPane.showMessageDialog(null, "No se encontró la antena para actualizar.");
                }
            }
        } catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + ex.getMessage());
        }
    }

    public void agregaComboAntena(JComboBox<String> jcb)
    {
        // Limpiar el ComboBox actual
        jcb.removeAllItems();

        // Conexión a la base de datos y consulta de sucursales
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/netcontrol", "root", bdpass))
        {
            String sql = "SELECT NoControl FROM antenas";  // Cambia la consulta para obtener datos de la tabla 'pagos'
            try (PreparedStatement stmt = conn.prepareStatement(sql))
            {
                ResultSet rs = stmt.executeQuery();
                // Llenar el ComboBox con los datos de la base de datos
                while (rs.next())
                {
                    jcb.addItem(rs.getString("NoControl"));  // Agrega el valor del campo 'Nocontrol' al ComboBox
                }
            }
        } catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Error al obtener los datos de la base de datos: " + ex.getMessage());
        }
    }

    public void agregaComboCombinado(JComboBox<String> jcb)
    {
        // Limpiar el ComboBox actual
        jcb.removeAllItems();

        // Conexión a la base de datos y consulta de sucursales y clientes
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/netcontrol", "root", bdpass))
        {
            // Consultar los nombres de las sucursales
            String sqlSucursales = "SELECT nombre FROM sucursales";
            try (PreparedStatement stmt = conn.prepareStatement(sqlSucursales))
            {
                ResultSet rs = stmt.executeQuery();
                // Llenar el ComboBox con los datos de las sucursales
                while (rs.next())
                {
                    jcb.addItem(rs.getString("nombre"));  // Agrega el valor del campo 'nombre' de la sucursal
                }
            }

            // Consultar los nombres de los clientes
            String sqlClientes = "SELECT Nombre FROM clientes";
            try (PreparedStatement stmt = conn.prepareStatement(sqlClientes))
            {
                ResultSet rs = stmt.executeQuery();
                // Llenar el ComboBox con los datos de los clientes
                while (rs.next())
                {
                    jcb.addItem(rs.getString("Nombre"));  // Agrega el valor del campo 'Nombre' del cliente
                }
            }
        } catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Error al obtener los datos de la base de datos: " + ex.getMessage());
        }
    }

    public void selectAntena(JComboBox<String> antenaComboBox, JComboBox<String> categoriaField, JTextField frecuenciaField, JTextField modeloField, JComboBox<String> titularCombo, JComboBox<String> torreComboBox, JTextField nocontrolField)
    {
        // Obtener el NoControl de la antena seleccionada
        String antenaSeleccionada = (String) antenaComboBox.getSelectedItem();

        // Verificar si se ha seleccionado una antena válida
        if (antenaSeleccionada != null && !antenaSeleccionada.isEmpty())
        {
            // Conexión a la base de datos y consulta de datos de la antena
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/netcontrol", "root", bdpass))
            {
                String sqlAntena = "SELECT Categoria, Frecuencia, Modelo, Titular, NoControlTorre FROM antenas WHERE NoControl = ?";
                try (PreparedStatement stmtAntena = conn.prepareStatement(sqlAntena))
                {
                    stmtAntena.setString(1, antenaSeleccionada);
                    try (ResultSet rsAntena = stmtAntena.executeQuery())
                    {
                        // Verificar si se ha encontrado el registro correspondiente
                        if (rsAntena.next())
                        {
                            categoriaField.setSelectedItem(rsAntena.getString("Categoria"));  // Categoria
                            frecuenciaField.setText(String.valueOf(rsAntena.getInt("Frecuencia")));  // Frecuencia
                            modeloField.setText(rsAntena.getString("Modelo"));    // Modelo
                            titularCombo.setSelectedItem(rsAntena.getString("Titular"));  // Titular

                            // Obtener el NoControlTorre
                            int noControlTorre = rsAntena.getInt("NoControlTorre");

                            // Consultar la etiqueta correspondiente a partir del NoControlTorre
                            String sqlTorre = "SELECT Etiqueta FROM torres WHERE NoControl = ?";
                            try (PreparedStatement stmtTorre = conn.prepareStatement(sqlTorre))
                            {
                                stmtTorre.setInt(1, noControlTorre);
                                try (ResultSet rsTorre = stmtTorre.executeQuery())
                                {
                                    if (rsTorre.next())
                                    {
                                        // Seleccionar la etiqueta de la torre correspondiente en el JComboBox
                                        String etiquetaTorre = rsTorre.getString("Etiqueta");
                                        torreComboBox.setSelectedItem(etiquetaTorre); // Establecer la torre en el ComboBox
                                    } else
                                    {
                                        JOptionPane.showMessageDialog(null, "No se encontró la torre con el número de control especificado.");
                                    }
                                }
                            }

                            // Rellenar el campo NoControl con el valor correspondiente
                            nocontrolField.setText(antenaSeleccionada);
                        } else
                        {
                            JOptionPane.showMessageDialog(null, "No se encontró la antena seleccionada.");
                        }
                    }
                }
            } catch (SQLException ex)
            {
                JOptionPane.showMessageDialog(null, "Error al obtener los datos de la base de datos: " + ex.getMessage());
            }
        }
    }

    // Método para eliminar una antena
    public void eliminarAntena(JComboBox<String> nocontrolComboBox)
    {
        // Obtener el valor seleccionado en el JComboBox
        String nocontrol = (String) nocontrolComboBox.getSelectedItem();

        if (nocontrol == null || nocontrol.isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una antena para eliminar.");
            return;
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/netcontrol", "root", bdpass))
        {
            String sql = "DELETE FROM antenas WHERE NoControl = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql))
            {
                stmt.setString(1, nocontrol);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0)
                {
                    JOptionPane.showMessageDialog(null, "Antena eliminada exitosamente");
                } else
                {
                    JOptionPane.showMessageDialog(null, "No se encontró la antena para eliminar.");
                }
            }
        } catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + ex.getMessage());
        }
    }
}
