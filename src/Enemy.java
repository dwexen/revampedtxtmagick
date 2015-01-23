import java.util.ArrayList;

/**
 * Write a description of class Enemy here.
 * 
 * DAN
 * @version (a version number or a date)
 */
public class Enemy extends Character
{
    private ArrayList<Potion> myPotions = null;
    private boolean leftLastTime = false;
    private int room = 0;

    public Enemy(int myHealth, int myStam, int myStr, int myDef, int myAtk, 
    int myDex, int myMaxHealth, String n, Location loc)
    {
        super(myHealth, myStam, myStr, myDef, myAtk, myDex, myMaxHealth, n, loc);
    }
    
    public Enemy(String n, Location loc)
    {   super(n, loc);   }
    
    public String getEnemyName()
    {   return super.getName();   }
        
    //Enemies should only ever be in one room so this shouldn't change much
    public Room getRoom()
    {   return Game.allRooms.get(room); }
    
    //Enemies won't be picking up weapons and equipping them so this also shouldn't do much
    public void setWeapon(Weapon w)
    {
        myWeapon = w;
    }
    
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
    
    public void move()
    {
        String dir;
        int r = (int)(Math.random() * 4);
        if(r == 0)
            dir = "up";
        else if(r == 1)
            dir = "down";
        else if(r == 2)
            dir = "left";
        else
            dir = "right";
        
        if(dir.equalsIgnoreCase("up"))
        {
            if(getCurrentRoom().moveUp())
            {    
                leftLastTime = false;
                return;
            }
            else
            {   
                
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
                
            }
        }
    }
    
    public Element randomElement()
    {
        int r = (int)(Math.random() * 8);
        if(r == 0){ return Element.elementWheel("fire"); }
        else if(r == 1){ return Element.elementWheel("earth"); }
        else if(r == 2){ return Element.elementWheel("water");}
        else if(r == 3){ return Element.elementWheel("arcane");}
        else if(r == 4){ return Element.elementWheel("electric");}
        else if(r == 5){ return Element.elementWheel("cold");}
        else if(r == 6){ return Element.elementWheel("shield");}
        else if(r == 7){ return Element.elementWheel("life");}
        return null;
    }
    public Spell makeSpell(int spellSize)
    {
        Spell s = new Spell();
        for(int i = 0; i < spellSize; i ++)
        {
            s.addElement(randomElement());
            if(!(s.addElement(randomElement())))
            {
                while(!(s.addElement(randomElement())))
                {
                    s.addElement(randomElement());
                }
                   
            }
        }
        return s;
    }
       
    
    //How "smart" do we want to make enemies?
    public void fight()
    {
        int r = (int)(Math.random() * 3);
        if(r == 0)
        {
            
        }    
    }
    
    //Because how the hell is an enemy going to VIEW their inventory
    //They just DROP it at the end of the damn fight
    public void dropInventory()
    {
        
    }
    
}

