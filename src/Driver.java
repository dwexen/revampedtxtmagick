/**
 * The driver will make method calls that send parameters to each class and 
 * continually prompt user inputs
 * @Dream Team 2: Electric Boogaloo 
 * @author Matt Colozzo
 */
import java.util.ArrayList;
import java.util.Scanner;

public class Driver
{
    private static Scanner user = new Scanner(System.in);
    private static Player p = null;
    private static Room r = null;
    private static boolean created = false;
    private static Location initialLoc = new Location(0, 0);
    private static String name;
    private static Character playerTarget = null;
    public static Scanner getScanner() { return user; }
    private static boolean noItemMessageSeen = false;
    
    public static void main(String[] args)
    {
        Game.printLogo();
        Game.buildDungeon();
        System.out.println("Welcome to Magicka-ish: the text-based, turn-based\n" + 
            "magic RPG that's like Magicka, but it's not!");
        
        if(created == false)
        {
            newName();
            createPlayer();
            created = true;
        }
        Game.buildDungeon();
        //playerTarget = new Enemy(100,10,10,10,10,10,100,"orc",new Location(1,1));
        //playerTarget.putCharacterInRoom();
        
        p.putCharacterInRoom();
        //dungeon();
        playerActions();
        //EnemyActions();
    }
    
    //Creates a player object
    private static void newName()
    {
        System.out.println("What is your name?");
        name = user.nextLine();
        /**
         * FIX STATS - 5/27
         */
        System.out.print("Type in the class you wish to select\nMageknight: High Strength, " + 
        "Medium Stamina, Low Dexterity, High Defense\nSorcerer: Medium Strength, High Stamina, " +
        "High Dexterity, Low Defense\nWizard: Low Strength, Medium Stamina, Medium Dexterity, Medium Defense, Does not lose Item or Move action when creating spells\n"
        );
    }
    private static void createPlayer()
    {        
        String answer = user.nextLine();
        if(answer.equalsIgnoreCase("mageknight"))
        {
            p = new Player(100, 13, 16, 16, 10, 10, 100, name, initialLoc);
        }
        else if(answer.equalsIgnoreCase("Sorcerer"))
        {
            p = new Player(100, 16, 13, 10, 16, 10, 100, name, initialLoc);
        }
        else if(answer.equalsIgnoreCase("Wizard"))
        {
            p = new Player(100, 16, 13, 10, 16, 10, 100, name, initialLoc);
        }
        else
        {
            Game.inputNotRecognized(answer);
            createPlayer();
        }
    }
    /* Creates a dungeon generated based on parameters
     * Finish once a basic prototype has been completed
    private static void dungeon("Something")
    {
        ArrayList<Enemy> enemies = new ArrayList<Enemy>();
        enemies.add(new Enemy(100, 10, 10, 10, 10, 10, "shrek"));
        ArrayList<Room> dungeon = new ArrayList<Room>();
        
        dungeon.add(r);
    }
    */
    
    //Creates a room, reads in the room number and decides which room to create
    public static Room roomCreator(int roomNum)
    {
       return new Room("rooms/level" + roomNum + ".txt", p);
    }
    
    private static void playerActions()
    {
        System.out.println();
        System.out.println("Choose an action (move/search/fight/equipment)");
        String a = user.nextLine();
        if(a.equalsIgnoreCase("move"))
        {
            p.move();
        }
        else if(a.equalsIgnoreCase("search"))
        {
            p.search();
        }
        else if(a.equalsIgnoreCase("fight"))
        {
            playerTarget = p.fight();
            if(playerTarget != null)
            {   battle();    }
        }
        else if(a.equalsIgnoreCase("equipment"))
        {
            //How the player will equip and unequip items
            p.viewEquipment();
            System.out.println("What would you like to do? (equip/unequip/view)");
            a = user.nextLine();
            if(a.equalsIgnoreCase("unequip"))
            {
                System.out.println("Enter the item to unequip");
                a = user.nextLine();
                for(Armor ar: p.getArmor())
                {
                    if(ar.getName().equalsIgnoreCase(a))
                    {
                        System.out.println("You have unequipped " + ar.getName());
                        p.getArmor().remove(ar);
                    }
                }
            }
            else if(a.equalsIgnoreCase("equip"))
            {
                System.out.println("Enter the item to equip");
                a = user.nextLine();
                boolean recognized = false;
                for(Item i: p.getItems())
                {
                    if(i.getName().equalsIgnoreCase(a))
                    {
                        if(i instanceof Weapon)
                        {
                          p.equipWeapon((Weapon)i);
                          recognized = true;
                        }
                        else if(i instanceof Armor)
                        {
                            p.equipArmor((Armor)i);
                            recognized = true;
                        }
                        else
                        {
                            p.equipItem(i);
                            recognized = true;
                        }
                    }

                    if(!recognized)
                        System.out.println("You do not have that item in your inventory.");
                    else
                        System.out.println("You have equipped " + i.getName());
                }
            }
            else if(a.equalsIgnoreCase("view"))
            {
                boolean foundAnything = false;
                for(Item i : p.getItems())
                {
                    if(!foundAnything)
                    {
                        System.out.println("Your entire inventory:");
                        System.out.print(i.getName());
                        
                        foundAnything = true;
                    }
                    else
                        System.out.print(", " + i.getName());
                }
                
                if(!foundAnything)
                {
                    if(!noItemMessageSeen)
                    {
                        System.out.println("You look into your backpack. A single fly escapes its confines.");
                        System.out.println("     *You have no items in your inventory.*");
                        noItemMessageSeen = true;
                    }
                    else
                        System.out.println("You have no items in your inventory.");
                }
            }
        }
        //The following masterpiece was heavily researched and implemented by DAN
        else if(a.equalsIgnoreCase("die"))
        {
            System.out.print("\f"); 
            System.exit(0);
        }
        else if(a.equalsIgnoreCase("no"))
        {
            System.exit(0);
        }
        else
        {
            Game.inputNotRecognized(a);
            playerActions();
        }
        playerActions();
    }
    
    private static void enemyActions()
    {
        //Calls the arraylist of enemies in the room to select their actions
    }
    public static void battle()
    {
        Battle b = new Battle(p,playerTarget);
        System.out.println("*intense music* Now fighting " + playerTarget.getName());
        b.battleMaker();
    }
}
