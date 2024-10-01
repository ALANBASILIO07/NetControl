package Files;

/**
 *
 * @author Alan Basilio
 */
public class Antenas
{
    public String categoria;
    public int frecuencia;
    public String modelo;
    public String titular;

    public Antenas()
    {
        
    }

    public Antenas(String categoria, int frecuencia, String modelo, String titular)
    {
        this.categoria = categoria;
        this.frecuencia = frecuencia;
        this.modelo = modelo;
        this.titular = titular;
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
    
    
}
