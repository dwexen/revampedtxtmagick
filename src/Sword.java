
/**
 * Write a description of class Sword here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Sword extends Weapon
{   
    public Sword(int dmg, String n, String desc)
    {
        super(dmg, n, desc);
    }
    
    public boolean defenderInRange(Character attacker, Character defender)
    {
        Character a = attacker;
        Character d = defender;
        if(distance(a, d) <= 2.5)
        {   return true; }
        return false;
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
