import java.util.*;

/**
 * @author Nelson Gomez
 */
public class Game
{
    private Player m_player = null;
    private int m_currentRoomIdx = -1;
    private static int howManyRooms = 1;
    public static ArrayList<Room> allRooms = null;
    public static void printLogo()
    {
        System.out.println("                    _      _         \n"
+ "  /\\/\\   __ _  __ _(_) ___| | ____ _ \n"
+ " /    \\ / _` |/ _` | |/ __| |/ / _` |\n"
+ "/ /\\/\\ \\ (_| | (_| | | (__|   < (_| |\n"
+ "\\/    \\/\\__,_|\\__, |_|\\___|_|\\_\\__,_|\n"
+ "              |___/                  \n\n");
    }
    
    public static void inputNotRecognized(String input)
    {
        System.out.print("\r");
        for(int i = 0; i < input.length(); i++)
        {
            System.out.print(" ");
        }
        System.out.println("What?");
    }
    
    public static boolean yes(String input)
    {
        if(input.equals("yes") || input.equals("y"))
            return true;
        return false;
    }
    public static boolean no(String input)
    {
        if(input.equals("no") || input.equals("n"))
            return true;
        return false;
    }
    
    public static void buildDungeon()
    {
        allRooms = new ArrayList<Room>();
        for(int i = 1; i <= howManyRooms; i++)
        {
            allRooms.add(Driver.roomCreator(i - 1));
        }
            Location r1e1Loc = new Location(3, 0);
            Location r1e2Loc = new Location(6, 2);
            Location r1e3Loc = new Location(2, 3);
                           //hp, stam, str, def, atk, dex, max hp
        Enemy r1e1 = new Enemy(100, 15, 17, 10, 13, 13, 100, "Billy Bob", r1e1Loc);
            allRooms.get(0).putEntityInRoom(r1e1, r1e1Loc);
        Enemy r1e2 = new Enemy(100, 15, 17, 10, 13, 13, 100, "Tupac", r1e2Loc);
            allRooms.get(0).putEntityInRoom(r1e2, r1e2Loc);
        Enemy r1e3 = new Enemy(100, 15, 17, 10, 13, 13, 100, "The Hippie Movement", r1e3Loc);
            allRooms.get(0).putEntityInRoom(r1e3, r1e3Loc);
        
            Location r1w1Loc = new Location(11, 1);
            Location r1w2Loc = new Location(9, 2);
            Location r1w3Loc = new Location(15, 2);
        Weapon r1w1 = new Sword(40, "The Sword of Github", "Angers the Dream Team by imagining changes.");
            allRooms.get(0).putEntityInRoom(r1w1, r1w1Loc);
        Weapon r1w2 = new Bow(50, "The Bow of CodeDay", "Pierces Nelson's heart with the strength of a thousand nerds.");
            allRooms.get(0).putEntityInRoom(r1w2, r1w2Loc);
        Weapon r1w3 = new Staff(20, "The Staff of Farseer Garcia", "School still refuses to Staff the IT team properly.");
            allRooms.get(0).putEntityInRoom(r1w3, r1w3Loc);
        
            Location r1p1Loc = new Location(12, 3);
        Potion r1p1 = new Potion(25, "Brian's Toast Sandwich");
            allRooms.get(0).putEntityInRoom(r1p1, r1p1Loc);
            
            Location r1a1Loc = new Location(19, 2);
            Location r1a2Loc = new Location(19, 4);
            Location r1a3Loc = new Location(6, 4);
        Armor r1a1 = new Armor("fire", 10, 0, 0, 0, 0, 0, "medium", "Chestplate of Not Getting Burnt", "chestplate");
            allRooms.get(0).putEntityInRoom(r1a1, r1a1Loc);
        Armor r1a2 = new Armor("water", 0, 0, 0, 40, 0, 0, "medium", "Chestplate of Staying Dry", "chestplate");
            allRooms.get(0).putEntityInRoom(r1a2, r1a2Loc);
        Armor r1a3 = new Armor("earth", 15, 0, 0, 0, 0, 0, "light", "Helmet for Surviving in San Francisco", "helmet");
            allRooms.get(0).putEntityInRoom(r1a3, r1a3Loc);
        
        /**[End of Room One!]*/
        
        /*    Location r2e1Loc = new Location(4, 5);
            Location r2e2Loc = new Location(5, 4);
        Enemy r2e1 = new Enemy("The Gay Agenda", r2e1Loc);
            allRooms.get(1).putEntityInRoom(r2e1, r2e1Loc);
        Enemy r2e2 = new Enemy("...slime man", r2e2Loc);
            allRooms.get(1).putEntityInRoom(r2e2, r2e2Loc);
        
            Location r2w1Loc = new Location(0, 1);
            Location r2w2Loc = new Location(0, 3);
        Dagger r2w1 = new Dagger(60, "Dagger of Swagger", "Lethal weapon concealed under the sticker on your snapback.");
            allRooms.get(1).putEntityInRoom(r2w1, r2w1Loc);
        Mace r2w2 = new Mace(75, "Super Smashy Mace", "This mace is confirmed for new Super Smash Bros.");
            allRooms.get(1).putEntityInRoom(r2w2, r2w2Loc);
            
            Location r2p1Loc = new Location(2, 4);
        Potion r2p1 = new Potion(45, "Elite Four's Full Restore");
            allRooms.get(1).putEntityInRoom(r2p1, r2p1Loc);
            
            Location r2a1Loc = new Location(3, 5);
            Location r2a2Loc = new Location(1, 4);
        Armor r2a1 = new Armor("electric", 0, 10, 20, 0, 0, 0, "medium", "Gloves worn by Ben Franklin", "gloves");
            allRooms.get(1).putEntityInRoom(r2a1, r2a1Loc);
        Armor r2a2 = new Armor("cold", 20, 40, 10, 0, 30, 30, "heavy", "A Snuggy", "legs");
            allRooms.get(1).putEntityInRoom(r2a2, r2a2Loc);
        
        /**[End of Room Two!]*/
        
        /*    Location r3e1Loc = new Location(1, 2);
            Location r3e2Loc = new Location(2, 1);
        Enemy r3e1 = new Enemy(200, 50, 70, 100, 30, 30, 200, "Lord Farquaad", r3e1Loc);
            allRooms.get(2).putEntityInRoom(r3e1, r3e1Loc);
        Enemy r3e2 = new Enemy(100, 100, 100, 30, 80, 35, 100, "An Ivy League Admissions Officer", r3e2Loc);
            allRooms.get(2).putEntityInRoom(r3e2, r3e2Loc);
            
            Location r3h1Loc = new Location(4, 2);
        HelpfulPerson r3h1 = new HelpfulPerson("Julian Assange", r3h1Loc);
        
        /**[End of Room Three!]*/
    }
}
