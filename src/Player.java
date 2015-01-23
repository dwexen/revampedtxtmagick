import java.util.ArrayList;
import java.util.Scanner;

/**
 * Write a description of class Player here.
 * 
 * DAN
 * @version (a version number or a date)
 */
public class Player extends Character
{
    private ArrayList<Potion> myPotions = null;
    private boolean leftLastTime = false;
    int failedFights = 0;
    
    public Player(int myHealth, int myStam, int myStr,
        int myDef, int myAtk, int myDex, int myMaxHealth, String n, Location loc)
    {
        super(myHealth, myStam, myStr, myDef, myAtk, myDex, myMaxHealth, n, loc);
    }
    public Player(String n, Location loc) { super(n, loc); }
    
    public void usePotion(Potion p)
    {
        health += p.getHealthRestored();
        for(int i = 0; i < myPotions.size(); i++)
        {
            Potion usedPotion = myPotions.get(i);
            if(usedPotion.getName().equals(p.getName()))
            {   myPotions.remove(usedPotion);   }
        }
    }
    
    public Spell makeSpell(int spellSize)
    {
        Spell s = new Spell();
        for(int i = 0; i < spellSize; i ++)
        {
            if(i == 0)
            {
                System.out.println("First element?");
                String a = Driver.getScanner().nextLine();
                s.addElement(Element.elementWheel(a));
                if(!(s.addElement(Element.elementWheel(a))))
                {
                    while(!(s.addElement(Element.elementWheel(a))))
                    {
                         System.out.println("Cannont add opposite elements, try again");
                         a = Driver.getScanner().nextLine();
                         s.addElement(Element.elementWheel(a));
                    }
                   
                }
            }
            else 
            {
                System.out.println("Next element?");
                String a = Driver.getScanner().nextLine();
                s.addElement(Element.elementWheel(a));
                if(!(s.addElement(Element.elementWheel(a))))
                {
                    while(!(s.addElement(Element.elementWheel(a))))
                    {
                         System.out.println("Cannont add opposite elements, try again");
                         a = Driver.getScanner().nextLine();
                         s.addElement(Element.elementWheel(a));
                    }
                   
                }
            }
        }
        return s;
    }
    
   
    
    public void move()
    {
        String snarkyComment = "Can't do that! *mumbles under breath* dummy";
        System.out.println("Where to?");
        String dir = Driver.getScanner().nextLine();
        if(dir.equalsIgnoreCase("up"))
        {
            if(getCurrentRoom().moveUp())
            {    
                leftLastTime = false;
                return;
            }
            else
            {   
                System.out.println(snarkyComment);
            }
        }
        if(dir.equalsIgnoreCase("down"))
        {
            if(getCurrentRoom().moveDown())
            {  
                leftLastTime = false;
                return;
            }
            else
            {   
                System.out.println(snarkyComment);
            }
        }
        if(dir.equalsIgnoreCase("right"))
        {
            if(getCurrentRoom().moveRight())
            { 
                leftLastTime = false;
                return;
            }
            else
            {   
                System.out.println(snarkyComment);
            }
        }
        if(dir.equalsIgnoreCase("left"))
        {
            if(getCurrentRoom().moveLeft())
            {   
                if(leftLastTime)
                {
                    System.out.println("http://en.wikipedia.org/wiki/Irreplaceable");
                    leftLastTime = false;
                }
                else
                {   leftLastTime = true;    }
                return; 
            }
            else
            {   
                System.out.println(snarkyComment);
            }
        }
        getCurrentRoom().visualize();
    }
    public void search()
    {   
        ArrayList<Enemy> enemies = new ArrayList<Enemy>();
        enemies = getCurrentRoom().getEnemiesInRoom();
        if(enemies.size() != 0)
        {
            System.out.print("Enemies in room: ");
            for(Enemy e : enemies)
            {
                System.out.print(e.getName() + ", ");
            }
        }
        else
            System.out.print("No enemies here...");
        System.out.println("");
        ArrayList<Item> items = new ArrayList<Item>();
        items = getCurrentRoom().getItemsInRoom();
        if(items.size() != 0)
        {
            System.out.print("Items in room: ");
            for(Item i : items)
            {
                System.out.print(i.getName() + ", ");
            }
        }
        else
            System.out.println("Nothing useful here...");
    }
    public Character fight()
    {
        ArrayList<Enemy> enemies = getCurrentRoom().getEnemiesInRoom();
        if(enemies.size() == 0)
        {
            failedFights++;
            if(failedFights < 50)
                System.out.println("Woah there eager beaver, there's no enemies to fight!");
            else
            {
                System.out.println("Out of desperation, you begin to fight yourself.");
                System.out.println("You die from self-inflicted trauma.");
                System.exit(0);
            }
            
            return null;
        }
        else if(enemies.size() == 1)
        { failedFights = 0; return enemies.get(0); }
        failedFights = 0;
        Enemy target = null;
        System.out.println("Which enemy would you like to attack?\n");
        for(Enemy e : getCurrentRoom().getEnemiesInRoom())
        {
            System.out.print(e.getName() + " ");
        }
        String userInput = Driver.getScanner().nextLine();
        while(target == null)
        {
            for(Enemy e : enemies)
            {
                if(userInput.equalsIgnoreCase(e.getName()))
                {
                    target = e;
                }
            }
            if(target == null)
            {   System.out.println("Who?"); }
        }
        return target;
    }
    public void viewEquipment()
    {
        boolean noArmor = false, noWeapons = false;
        if(getArmor() != null && getArmor().size() != 0)
        {
            System.out.println("You are wearing:");
            for(Armor a : getArmor())
            {
                System.out.print(a.getName() + "\t");
            }
        }
        else
            noArmor = true;
        if(getWeapons() != null && getWeapons().size() != 0)
        {
            System.out.println("You are wielding:");
            for(Weapon w : getWeapons())
            {
                System.out.print(w.getName() + "\t");
            }
        }
        else
            noWeapons = true;
        if(!(noArmor && noWeapons))
        {
            System.out.println("Want any descriptions? (y/n)");
            String input = Driver.getScanner().nextLine();
            boolean recognized = false;
            if(Game.yes(input))
            {
                while(!recognized)
                {
                    System.out.println("Which thing?");
                    input = Driver.getScanner().nextLine();
                    for(Item i : getItems())
                    {
                        if(i.getName().equals(input))
                        {   
                            System.out.println(i.description());
                            recognized = true;
                        }
                        else
                            Game.inputNotRecognized(input);
                    }
                }
            }
            else if(Game.no(input))
                return;
            else
                Game.inputNotRecognized(input);
        }
    }
    
    public ArrayList<Weapon> getWeapons()
    {
        return super.getWeapons();
    }
    public ArrayList<Armor> getArmor()
    {
        return super.getArmor();
    }
    public ArrayList<Item> getItems()
    {
        return super.getItems();
    }
    public void putCharacterInRoom()
    {
        getCurrentRoom().putCharacterInRoom(this, getCurrentRoom().getPlayerLocation());
    }
}
