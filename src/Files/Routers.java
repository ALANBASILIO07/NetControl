package Files;

/**
 *
 * @author Alan Basilio
 */
public class Routers
{
    public String IPCliente;
    public String Titular;
    public String modelo;

    public Routers()
    {
        
    }

    public Routers(String IPCliente, String Titular, String modelo)
    {
        this.IPCliente = IPCliente;
        this.Titular = Titular;
        this.modelo = modelo;
    }

    public String getIPCliente()
    {
        return IPCliente;
    }

    public void setIPCliente(String IPCliente)
    {
        this.IPCliente = IPCliente;
    }

    public String getTitular()
    {
        return Titular;
    }

    public void setTitular(String Titular)
    {
        this.Titular = Titular;
    }

    public String getModelo()
    {
        return modelo;
    }

    public void setModelo(String modelo)
    {
        this.modelo = modelo;
    }
}
