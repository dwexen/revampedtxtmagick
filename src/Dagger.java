
/**
 * Write a description of class Dagger here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Dagger extends Weapon
{
    public Dagger(int dmg, String n, String desc)
    {
        super(dmg, n, desc);
    }
    
    public boolean defenderInRange(Character attacker, Character defender)
    {
        Character a = attacker;
        Character d = defender;
        boolean inRange = false;
        if(distance(a, d) <= 1.5)
        {   return true; }
        return inRange;
    }
    
    public int attack(Character attacker, Character defender)
    {
        Character a = attacker;
        Character d = defender;
        int str = a.getStr();
        int def = d.getDef();
        int finalDamage = damage * (str / def);
        return finalDamage;
    }
}
