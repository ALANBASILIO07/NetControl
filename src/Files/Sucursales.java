package Files;

/**
 *
 * @author Alan Basilio
 */
public class Sucursales
{
    public String nombre;
    public String gerente;
    public String direccion;
    public int torres;

    public Sucursales()
    {
        
    }

    public Sucursales(String nombre, String gerente, String direccion, int torres)
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

    public int getTorres()
    {
        return torres;
    }

    public void setTorres(int torres)
    {
        this.torres = torres;
    }
    
    
}
