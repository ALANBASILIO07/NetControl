package Files;

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

    public Clientes()
    {
        
    }
    
    public Clientes(String nombre, String IP, String vigencia, String domicilio, String status, String succursal, String torre)
    {
        this.nombre = nombre;
        this.IP = IP;
        this.vigencia = vigencia;
        this.domicilio = domicilio;
        this.status = status;
        this.succursal = succursal;
        this.torre = torre;
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
}
