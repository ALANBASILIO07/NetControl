package Files;

/**
 *
 * @author Alan Basilio
 */
public class Torres
{
    public String zona;
    public String direccion;
    public String enlaces;
    public String etiqueta;
    public int nocontrol;

    public Torres()
    {
        
    }

    public Torres(String zona, String direccion, String enlaces, String etiqueta, int nocontrol)
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

    public String getEnlaces()
    {
        return enlaces;
    }

    public void setEnlaces(String enlaces)
    {
        this.enlaces = enlaces;
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
    
    
}
