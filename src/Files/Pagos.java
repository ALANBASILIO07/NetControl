package Files;

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
    
    
}
