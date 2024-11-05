package Files;

/**
 *
 * @author Alan Basilio
 */
public class Usuarios
{
    public String username;
    public String password;
    public String bdpass;

    public Usuarios()
    {
        
    }

    public Usuarios(String username, String password, String bdpass)
    {
        this.username = username;
        this.password = password;
        this.bdpass = bdpass;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getBdpass()
    {
        return bdpass;
    }

    public void setBdpass(String bdpass)
    {
        this.bdpass = bdpass;
    }
    
    
}
