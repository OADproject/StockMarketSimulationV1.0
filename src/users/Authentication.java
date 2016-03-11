/** COEN 275 OOAD (Winter 2016)
 *  Group Project
 *  Team 8
 */
package users;
public class Authentication
{
    private String username;
    private String password;
    
    public Authentication()
    {
    
    }
    public Authentication(String usn,String pwd)
    {
        username = usn;
        password = pwd;
    }
    
    public boolean setUsername(String usn)
    {
        username = usn;
        return true;
    }
    public boolean setPassword(String pwd)
    {
        password = pwd;
        return true;
    }
    public String getUsername()
    {
        return username;
    }
    public String getPassword()
    {
        return password;
    }
}