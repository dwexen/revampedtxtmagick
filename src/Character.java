
/**
 * Write a description of class Character here.
 * 
 * DAN w/ minor additons by others (but still mostly Dan)
 */
import java.util.ArrayList;
import java.util.Iterator;

public class Character
{
    protected Weapon myWeapon = null;
    protected Location myLocation = null;
    protected String name;
    protected double maxHealth, health;
    protected int stam, str, def, atk, dex;
    protected ArrayList<Weapon> weapons;
    protected ArrayList<Armor> armor;
    protected ArrayList<Item> items;
    protected ArrayList<String> statusEffects;
    protected int currentRoom = 0; 
    
    public Character(double myHealth, int myStam, int myStr, int myDef, 
    int myAtk, int myDex, double myMaxHealth, String n, Location loc)
    {
        health = myHealth;
        stam = myStam;
        str = myStr;
        def = myDef;
        atk = myAtk;
        dex = myDex;
        maxHealth = myMaxHealth;
        name = n;
        myLocation = loc;
        
        items = new ArrayList<Item>();
        armor = new ArrayList<Armor>();
        weapons = new ArrayList<Weapon>();
        statusEffects = new ArrayList<String>();
    }
    
    // Default constructor which just uses defaults for everything.
    // Useful for instantiating Players or weak enemies.
    public Character(String n, Location loc)
    {
        health = DefaultStats.health;
        maxHealth = DefaultStats.maxHealth;
        stam = DefaultStats.stamina;
        def = DefaultStats.defense;
        dex = DefaultStats.dexterity;
        
        name = n;
        myLocation = loc;
    }
    

    public void changeHealth(double myHealth)
    {
        health = myHealth;
        if(health > maxHealth)
            health = maxHealth;
    }
    
    public void changeStam(Armor a)
    {
        stam += a.getStamBoost();
    }

    public void changeArmorStats(Armor a){
        changeAtk(a);
        changeStr(a);
        changeDef(a);
        changeDef(a);

    }
    public void changeMaxHealth(Armor a)
    {
        maxHealth += a.getHealthBoost();
    }

    public void changeStr(Armor a)
    {
        str += a.getStrBoost();
    }
    
    public void changeDef(Armor a)
    {
        def += a.getDefBoost();
    }
    
    public void changeAtk(Armor a)
    {
        atk += a.getAtkBoost();
    }
    
    public void changeDex(Armor a)
    {
        dex += a.getDexBoost();
    }
    
    public double getMaxHealth()
    {
        return maxHealth;
    }
    
    public double getHealth()
    {
        return health;
    }
    
    public int getStam()
    {
        return stam;
    }
    
    public int getStr()
    {
        return str;
    }
    
    public int getDef()
    {
        return def;
    }
    
    public int getDex()
    {
        return dex;
    }
    
    public void setName(String myName)
    {
        name = myName;
    }
    
    public int getAtk()
    { return atk;}
    
    public String getName()
    {
        return name;
    }
    
    public Location getLocation()
    {   return myLocation;  }
    
    public ArrayList<Weapon> getWeapons()
    {
        return weapons;
    }
    
    public ArrayList<Armor> getArmor()
    {
        return armor;
    }
    
    public ArrayList<Item> getItems()
    { return items; }
    
    public Weapon getWeapon()
    { return myWeapon; }
    
    public ArrayList<String> getStatusEffects()
    { return statusEffects; }
    
    public void spellStatusEffects(Spell s)
    {
        for(Element e: s.getElements())
        {
            String d = e.getDesc();
            if(d.equalsIgnoreCase("cold") && e.getActingOT())
            {
               replaceEffect("wet","frozen");
                statusEffect("Slowed");
            }
            else if(d.equalsIgnoreCase("fire") && e.getActingOT())
            {
                removeEffect("fire");
                statusEffect("Burning");
            }
            else if(d.equalsIgnoreCase("earth") && e.getActingOT())
            {
                statusEffect("Knocked down");
            }
            else if(d.equalsIgnoreCase("water") && e.getActingOT())
            {
                removeEffect("burning");
                statusEffect("wet");

            }
            else if(d.equalsIgnoreCase("shield") && e.getActingOT())
            {
                statusEffects.add("shielded");
            }
        }
    }
    
    public void statusEffect(String s)
    { statusEffects.add(s); }
    
    public void damageTaken(int n)
    {
        health = health - n;
    }
    public void healingTaken(int n)
    { health = health + n; }
    public Room getCurrentRoom()
    {   return Game.allRooms.get(currentRoom);  }
    public void putCharacterInRoom()
    {
        getCurrentRoom().putCharacterInRoom(this, myLocation);
    }
    
    public void equipArmor(Armor a)
    {
        armor.add(a);
    }
    public void equipWeapon(Weapon w)
    {
        myWeapon = w;
    }
    public void equipItem(Item i)
    { items.add(i); }
    
    public void setLocation(int x, int y)
    {
        if(getCurrentRoom() == null)
            return;
        
        if(myLocation == null)
            myLocation = new Location();
        else
            getCurrentRoom().clearObjectAtLocation(myLocation.getX(), myLocation.getY());
           
        myLocation.setLocation(x, y);
        getCurrentRoom().setObjectAtLocation(x, y, this);
    }

    private boolean removeEffect(String effect){
        return replaceEffect(effect,null);
    }

    private boolean replaceEffect(String effect, String replacement){
            boolean removed = false;
        if(statusEffects != null && statusEffects.size() > 0){
            Iterator<String> it = statusEffects.iterator();
            String statusEffect;

            while(it.hasNext()){
                statusEffect = it.next();
                if(statusEffect.equalsIgnoreCase(effect)){
                    it.remove();
                    if(!removed) {
                        removed = !removed;
                        if(replacement != null)
                            statusEffects.add(replacement);
                    }

                }
            }
        }
        return removed;
    }
}
