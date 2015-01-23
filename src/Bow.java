
/**
 * Write a description of class Bow here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Bow extends Weapon
{
   public Bow(int dmg, String n, String desc)
   {
        super(dmg, n, desc);
   }
   
   public boolean defenderInRange(Character attacker, Character defender)
   {
        Character a = attacker;
        Character d = defender;
        boolean inRange = false;
        if(distance(a, d) <= 5.5)
        {   return true; }
        return inRange;
   }
   
   public boolean onTarget(Character attacker)
   {
       int dex = attacker.getDex();
       double accuracy = 50.0 * (dex / 10);
       if(Math.random() * 100 < accuracy)
       {    return true;    }
       return false;
   }
   
   public int attack(Character attacker, Character defender)
   {
        Character a = attacker;
        Character d = defender;
        int str = a.getStr();
        int def = d.getDef();
        int finalDamage = damage * (str / def);
        if(onTarget(a))
        {   
            System.out.println("Good shot!");
            return finalDamage;
        }
        else
        {
            System.out.println("You missed! Too bad we never coded in a crosshair.");
            return 0;
        }
   }
}
