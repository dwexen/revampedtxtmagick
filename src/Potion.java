
/**
 * Abstract class Potion - write a description of the class here
 * 
 * @author The The Dream Team 2: The Electric Boogaloo [Brian Walker]
 * @version (version number or date here)
 */
public class Potion implements Item
{
    private int healthRestored = 0;
    private String name = "";
    public Potion(int restore, String n)
    {
        healthRestored = restore;
        name = n;
    }
    public String description()
    {
        return "Restores " + healthRestored + " hit points";
    }
    public void use()
    {
        
    }
    
    public int getHealthRestored()
    {   return healthRestored;    }
    public String getName()
    {   return name;    }
}
