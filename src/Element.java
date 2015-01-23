
/**
 * 
 *Elements:
 *Fire, water, earth, electric, cold, shield, life, arcane
 *@Dream Team 2: Electric Boogaloo 
 * @author Matt Colozzo
 */
import java.util.Scanner;

public class Element
{
    double damage;
    double damageOverTime;
    boolean isActingOverTime;
    String description;
    
    public Element(double d, double dOT, String desc, boolean act)
    {
        damage = d;
        damageOverTime = dOT;
        description = desc;
        isActingOverTime = act;
    }
    
    public static Element elementWheel(String ele)
    {
        if(ele.equalsIgnoreCase("fire"))
        {
            Element e = new Element(5, 10, "Fire", true);
            return e;
        }
        else if(ele.equalsIgnoreCase("water"))
        {
            Element e = new Element(0, 0, "Water", false);
            return e;
        }
        else if(ele.equalsIgnoreCase("earth"))
        {
            Element e = new Element(30, 0, "Earth", false);
            return e;
        }
        else if(ele.equalsIgnoreCase("arcane"))
        {
            Element e = new Element(20, 0, "Arcane", false);
            return e;
        }
        else if(ele.equalsIgnoreCase("electric"))
        {
            Element e = new Element(15, 5, "Electric", false);
            return e;
        }
        else if(ele.equalsIgnoreCase("cold"))
        {
            Element e = new Element(10, 0, "Cold", true);
            return e;
        }
        else if(ele.equalsIgnoreCase("shield"))
        {
            Element e = new Element(0, 0, "Shield", false);
            return e;
        }
        else if(ele.equalsIgnoreCase("life"))
        {
            Element e = new Element(-10, -10, "Life", true);
            return e;
        }
        return null;
    }
    
    public boolean isActingOverTime(boolean b)
    {
        isActingOverTime = b;
        return isActingOverTime;
    }
    public boolean getActingOT()
    { return isActingOverTime; }
    public double getDamage()
    { return damage; }
    public double getDamageOverTime()
    { return damageOverTime; }
    public String getDesc()
    { return description; }
}
