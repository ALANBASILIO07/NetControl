package Files;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JLabel;

/**
 *
 * @author Alan Basilio
 */
public class Fecha
{
    // Método de instancia que recibe un JTextField y le asigna la fecha actual formateada
    public void mostrarFechaEnCampo(JLabel campoFecha)
    {
        // Obtener la fecha actual del sistema
        LocalDate fechaActual = LocalDate.now();

        // Formatear la fecha en el formato deseado (por ejemplo, dd/MM/yyyy)
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaFormateada = fechaActual.format(formato);

        // Mostrar la fecha en el JTextField recibido como parámetro
        campoFecha.setText(fechaFormateada);
    }
}
