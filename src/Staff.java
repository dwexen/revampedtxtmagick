
/**
 * Write a description of class Staff here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Staff extends Weapon
{
   public Staff(int dmg, String n, String desc)
   {
        super(dmg, n, desc);
   }
   
   public boolean defenderInRange(Character attacker, Character defender)
    {
        Character a = attacker;
        Character d = defender;
        boolean inRange = false;
        if(distance(a, d) <= 3.5)
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
