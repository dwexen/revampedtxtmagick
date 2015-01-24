import java.util.Scanner;
/**
 * Abstract class Weapon - write a description of the class here
 * 
 * @Brian Walker
 * @Dream Team 2: Electric Boogaloo
 */
public abstract class Weapon implements Item
{
    protected int damage;
    protected String name = "", description = "";
    
    public Weapon(int dmg, String n, String desc)
    {
        damage = dmg;
        name = n;
        description = desc;
    }
    
    public String description()
    {   return "this is a weapon";}
    
    public boolean equip()
    {
        System.out.println(description() + "/nWould you like to equip this " + getName() +
                           "Your currently held weapon will be discarded. (y/n)");
        Scanner user = new Scanner(System.in);     //Stereotypical read-in procedure
        boolean recognized = false;
        do
        {
            String input = user.nextLine();
            if(Game.yes(input))
            {
                recognized = true;
                return true;
            }
            else if(Game.no(input))
            {
                recognized = true;
            }
        }while(!recognized); //Makes sure user is not a dumb butt
        return false;
    }
    
    public double distance(Character attacker, Character defender)
    {
        Character a = attacker;
        Character d = defender;
        int aX = a.getLocation().getX();
        int aY = a.getLocation().getY();
        int dX = d.getLocation().getX();
        int dY = d.getLocation().getY();
        return Math.abs(Math.sqrt(Math.pow((aX - dX),2) + Math.pow((aY - dY),2)));
    }
    
    public abstract int attack(Character attacker, Character defender);

    public void use()
    {
        
    }
    public String getName(){return name;}
    public int getDamage(){return damage;}
    
}
