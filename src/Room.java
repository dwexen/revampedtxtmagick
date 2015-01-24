
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Write a description of class Room here.
 * 
 * @Dream Team 2: Electric Boogaloo [Nelson Gomez, Brian Walker] 
 * @version (a version number or a date)
 */
enum RoomItems { FLOOR, WALL, /*HOLE,*/ DOOR, SPAWN }
public class Room
{
    private Object[][] roomObjects;
    private Location playerLocation;
    private Battle battle;
    private Player player;
    
    public Battle getBattle() { return battle; }
    
    public Location getPlayerLocation() { return playerLocation; }
    public Object getObjectAtLocation(int x, int y) { return roomObjects[y][x]; }
    public void setObjectAtLocation(int x, int y, Object o) { roomObjects[y][x] = o; }
    public void clearObjectAtLocation(int x, int y) { roomObjects[y][x] = null; }
    public void removeFromRoom(Object o)
    {
        for(int y = 0; y < roomObjects.length; y++)
        {
            for(int x = 0; x < roomObjects[y].length; x++)
            {
                if(roomObjects[y][x].equals(o)) {
                    roomObjects[y][x] = new Integer(RoomItems.FLOOR.ordinal());
                    return;
                }
            }
        }
    }

    public Room(String file, Player plr)
    {
        player = plr;
        
        try
        {
            parseFileDescriptor(file);
            determineSpawnLoc();
        }
        catch(IOException e)
        {
            System.out.println("WARNING: Could not load level " + file + ", game may be unstable");
            System.out.println(e.toString());
        }
    }
    
    public boolean isValid(int x, int y)
    {
        if(roomObjects == null)
            return false;
            
        if(y < 0 || x < 0)
            return false;
        
        if(y >= roomObjects.length)
            return false;
        
        if(roomObjects[y] == null)
            return false;
        
        if(x >= roomObjects[y].length)
            return false;
        
        if(roomObjects[y][x] == null)
            return false;
        
        if(roomObjects[y][x] instanceof Integer)
        {
            Integer obj = (Integer)(roomObjects[y][x]);
            if(obj.intValue() == RoomItems.WALL.ordinal())
                return false;
        }
        
        return true;
    }
    
    public boolean moveUp()
    {
        int x = playerLocation.getX(), y = playerLocation.getY();
        if(!isValid(playerLocation.getX(), playerLocation.getY() - 1))
            return false;
        
        Object north = roomObjects[y - 1][x];
        move(x, y - 1);
        
        return true;
    }
    
    public boolean moveDown()
    {
        int x = playerLocation.getX(), y = playerLocation.getY();
        if(!isValid(playerLocation.getX(), playerLocation.getY() + 1))
            return false;
        
        Object south = roomObjects[y + 1][x];
        move(x, y + 1);
        
        return true;
    }
    
    public boolean moveLeft()
    {
        int x = playerLocation.getX(), y = playerLocation.getY();
        if(!isValid(playerLocation.getX() - 1, playerLocation.getY()))
            return false;
        
        Object weast = roomObjects[y][x - 1];
        move(x - 1, y);
        
        return true;
    }
    
    public boolean moveRight()
    {
        int x = playerLocation.getX(), y = playerLocation.getY();
        if(!isValid(playerLocation.getX() + 1, playerLocation.getY()))
            return false;
        
        Object east = roomObjects[y][x + 1];
        move(x + 1, y);
        
        return true;
    }
    
    private Enemy withinRangeOfEnemy()
    {
        if(playerLocation == null)
            return null;
        
        int range = 1; // 1 square unit
        int xLeft = playerLocation.getX() - range;
        int xRight = playerLocation.getX() + range;
        int yTop = playerLocation.getY() - range;
        int yBottom = playerLocation.getY() + range;
        
        for(int y = yTop; y < yBottom; y++)
        {
            for(int x = xLeft; x < xRight; x++)
            {
                if(!isValid(x, y))
                    continue;
                
                if(roomObjects[y][x] == null)
                    continue;
                
                if(roomObjects[y][x] instanceof Enemy)
                    return (Enemy)(roomObjects[y][x]);
            }
        }
        
        return null;
    }
    
    private void move(int x, int y)
    {
        int oldX = playerLocation.getX();
        int oldY = playerLocation.getY();
        playerLocation.setLocation(x, y);
        
        if(roomObjects[y][x] instanceof Object)
        {
            if(roomObjects[y][x] instanceof Enemy)
            {
                System.out.println("You have run into an enemy. Would you like to fight it?");
                System.out.println("Note: you cannot move in this direction until the enemy is slain.");
                String input = Driver.getScanner().nextLine();
                do
                {
                    if(Game.yes(input))
                    {
                        Enemy foundEnemy = (Enemy)(roomObjects[y][x]);
                        battle = new Battle(player, foundEnemy);
                        battle.battleMaker();
                    }
                    else if(Game.no(input))
                    {
                        System.out.println("You take a step back, avoiding combat.");
                        playerLocation.setLocation(oldX, oldY);
                        return;
                    }
                    else
                        Game.inputNotRecognized(input);
                }
                while(!Game.yes(input) && !Game.no(input));
            }
            else if(roomObjects[y][x] instanceof HelpfulPerson)
            {
                HelpfulPerson character = (HelpfulPerson)(roomObjects[y][x]);
                character.speak();
                System.out.println("       *" + character.getName() + " disappears.*");
            }
            else if(roomObjects[y][x] instanceof Item)
            {
                Item i = (Item)(roomObjects[y][x]);
                player.equipItem(i);
                System.out.println("You have encountered " + i.getName() + " and have added it to your inventory.");
            }
        }
        
        roomObjects[y][x] = roomObjects[oldY][oldX];
        roomObjects[oldY][oldX] = new Integer(RoomItems.FLOOR.ordinal());
        Enemy foundEnemy = withinRangeOfEnemy();
        
        if(foundEnemy != null && battle == null)
        {
            System.out.println("You have encountered an enemy. Would you like to fight it?");
            String input = Driver.getScanner().nextLine();
            do
            {
                if(Game.yes(input))
                {
                    battle = new Battle(player, foundEnemy);
                    battle.battleMaker();
                }
            }
            while(!Game.yes(input) && !Game.no(input));
        }
        
        visualize();
    }

    public ArrayList<Item> getItemsInRoom()
    {
        ArrayList<Item> temp = new ArrayList<Item>();
        for(Object[] row : roomObjects)
        {
            for(Object item : row)
            {
                if(item instanceof Item)
                    temp.add((Item)item);
            }
        }

        return temp;
    }

    public ArrayList<Character> getCharactersInRoom()
    {
        ArrayList<Character> temp = new ArrayList<Character>();
        for(Object[] row : roomObjects)
        {
            for(Object item : row)
            {
                if(item instanceof Character)
                    temp.add((Character)item);
            }
        }

        return temp;
    }

    public ArrayList<Enemy> getEnemiesInRoom()
    {
           ArrayList<Character> characters = getCharactersInRoom();
           ArrayList<Enemy> temp = new ArrayList<Enemy>();
               for(Character c : characters)
               {
                       if(c instanceof Enemy)
                           temp.add((Enemy)c);
                           }

               return temp;
    }

    public ArrayList<Armor> getArmorInRoom()
    {
        ArrayList<Item> items = getItemsInRoom();
        ArrayList<Armor> temp = new ArrayList<Armor>();
        for(Item i : items)
        {
            if(i instanceof Armor)
                temp.add((Armor)i);
        }

        return temp;
    }

    public ArrayList<Weapon> getWeaponsInRoom()
    {
        ArrayList<Item> items = getItemsInRoom();
        ArrayList<Weapon> temp = new ArrayList<Weapon>();
        for(Item i : items)
        {
            if(i instanceof Weapon)
                temp.add((Weapon)i);
        }

        return temp;
    }

    public ArrayList<Potion> getPotionsInRoom()
    {
        ArrayList<Item> items = getItemsInRoom();
        ArrayList<Potion> temp = new ArrayList<Potion>();
        for(Item i : items)
        {
            if(i instanceof Potion)
                temp.add((Potion)i);
        }

        return temp;
    }

    public Location getLocation() { return playerLocation; }

    public void setLocation(Location loc)
    {
        if(loc == null)
            return;
        
        if(loc.getX() < 0 || loc.getX() > roomObjects[0].length || loc.getY() < 0 || loc.getY() > roomObjects.length)
            return;

        playerLocation = loc;
    }
    
    public void putEntityInRoom(Object o, Location l)
    {
        if(o == null || l == null || !isValid(l.getX(), l.getY())
            || ( !(o instanceof Character) && !(o instanceof Item) && !(o instanceof Integer)))
            return;
        
        setObjectAtLocation(l.getX(), l.getY(), o);
    }

    public void putCharacterInRoom(Character c, Location l) { putEntityInRoom(c, l); }
    public void putItemInRoom(Item i, Location l) { putEntityInRoom(i, l); }

    // This work of art has been made possible by generous contributions by
    //     the Nelson Gomez Foundation for Holy Shit What Is This I Don't Even
    //     and by viewers like you.
    // http://stackoverflow.com/a/285745
    private String[] readLines(String filename) throws IOException
    {
        FileReader fileReader = new FileReader(filename);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        ArrayList<String> lines = new ArrayList<String>();
        String line = null;

        while ((line = bufferedReader.readLine()) != null)
            lines.add(line);

        bufferedReader.close();
        return lines.toArray(new String[lines.size()]);
    }

    private void parseFileDescriptor(String file) throws IOException
    {
        int rows = 0, cols = -1;
        String[] lines = readLines(file);
        ArrayList<ArrayList<Object>> room = new ArrayList<ArrayList<Object>>(); 

        for(String line : lines)
        {
            if(line.length() < 1)
                continue;
            
            if(line.substring(0,1).equals("#"))
                continue;
            
            if(line.substring(0,1).equals("|"))
            {
                ArrayList<Object> tmp = new ArrayList<Object>();
                room.add(tmp);

                String[] cells = line.split("\\|");
                for(String cell : cells)
                    processCell(cell, tmp);
            }
        }

        roomObjects = new Object[room.size()][room.get(0).size()];
        int x = 0, y = 0;
        for(ArrayList<Object> row : room)
        {
            y = 0;
            for(Object item : row)
            {
                if(y > room.get(0).size()) {
                    System.out.println("WARNING: Level " + file + " has non-square grid");
                    break;
                }

                roomObjects[x][y] = item;
                y++;
            }

            x++;
        }
    }

    private void processCell(String cell, ArrayList<Object> list)
    {
        int item = -1;
        if(cell.equals("/"))
            list.add(new Integer(RoomItems.WALL.ordinal()));
        else if(cell.equalsIgnoreCase("S"))
            list.add(new Integer(RoomItems.SPAWN.ordinal()));
        /*else if(cell.equals("%"))
            list.add(new Integer(RoomItems.HOLE.ordinal()));*/
        else if(cell.equalsIgnoreCase("D")) 
            list.add(new Integer(RoomItems.DOOR.ordinal()));
        else
            list.add(new Integer(RoomItems.FLOOR.ordinal()));
    }

    private void determineSpawnLoc()
    {
        boolean foundDoor = false;
        for(int y = 0; y < roomObjects.length; y++)
        {
            if(foundDoor)
                break;

            for(int x = 0; x < roomObjects[y].length; x++)
            {
                if(roomObjects[y][x] instanceof Integer)
                {
                    Object primitive = roomObjects[y][x];
                    Integer value = (Integer)primitive;
                    if(value.intValue() == RoomItems.SPAWN.ordinal())
                    {
                        playerLocation = new Location(x, y);
                        foundDoor = true;
                        break;
                    }
                }
            }
        }

        if(!foundDoor)
            playerLocation = new Location(0, 0);
    }

    public void visualize()
    {
        for(Object[] y : roomObjects)
        {
            int c = 0;
            for(Object x : y)
            {
                if(x instanceof Item)
                {
                    if(x instanceof Weapon)
                        System.out.print("W");
                    else if(x instanceof Potion)
                        System.out.print("%");
                    else
                        System.out.print("I");
                    System.out.print("|");

                    c++;
                }
                else if(x instanceof Character)
                {
                    if(x instanceof Player)
                        System.out.print("P");
                    else if(x instanceof Enemy)
                        System.out.print("E");
                    else
                        System.out.print("C");
                    System.out.print("|");

                    c++;
                }
                else if(x instanceof Integer)
                {
                    int data = ((Integer)x).intValue();
                    if(data == RoomItems.FLOOR.ordinal())
                        System.out.print(" ");
                    else if(data == RoomItems.WALL.ordinal())
                        System.out.print("#");
                    /*else if(data == RoomItems.HOLE.ordinal())
                        System.out.print("X");*/
                    else if(data == RoomItems.DOOR.ordinal())
                        System.out.print("D");
                    else if(data == RoomItems.SPAWN.ordinal())
                        System.out.print("S");
                    else
                        System.out.print("?");
                    System.out.print("|");

                    c++;
                }
            }

            System.out.print("\n");
            for(int i = 0; i < c; i++)
            {
                System.out.print("-+");
            }
            System.out.print("\n");
        }
        
        System.out.println("W = Weapon    % = Potion    I = Other Item");
        System.out.println("P = You!      E = Enemy     C - Other Character");
        System.out.println("# = Wall      D = Door      S = Spawn Location");
    }
}
