/** COEN 275 OOAD (Winter 2016)
 *  Group Project
 *  Team 8
 */
package users;

public class User
{
    private int ID;
    private static int userCount = 0;
    private String name;
    private String SSN;
    private String address;
    private String phoneNumber;
    private Portfolio p ;//new Portfolio();
    private Authentication a ;//new Authentication();
    
    User()
    {
        ID = userCount++;
    }
    
    public User(String realName,String socialSecurity,String addr,String phnum,Portfolio port,Authentication auth)
    {
        ID = userCount++;
        name = realName;
        SSN = socialSecurity;
        address = addr;
        phoneNumber = phnum;
        p = port;
        a = auth;
    }
        
        
    
    public int getID()
    {
        return ID;
    }
    public String getName()
    {
        return name;
    }
    public String getSSN()
    {
        return SSN;
    }
    public String getAddress()
    {
        return address;
    }
    public String getPhoneNumber()
    {
        return phoneNumber;
    }
    public Portfolio getPortfolio()
    {
        return p;
    }
    public Authentication getAuth()
    {
        return a;
    }
    public boolean setID(int i)
    {
        ID = i;
        return true;
    }
    public boolean setName(String n)
    {
        name = n;
        return true;
    }
    public boolean setSSN(String s)
    {
        SSN = s;
        return true;
    }
    public boolean setAddress(String a)
    {
        address =a;
        return true;
    }
    public boolean setPhoneNumber(String ph)
    {
        phoneNumber = ph;
        return true;
    }
    public boolean setAuth(String usn,String pwd)
    {
        a.setUsername(usn);
        a.setPassword(usn);
        return true;
    }
    
    
   
}