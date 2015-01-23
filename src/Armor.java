import java.util.Scanner;

/**
 * Write a description of class Armor here.
 * 
 * @Dream Team 2: Electric Boogaloo Brian Walker 
 * @2014 Q4
 */
public class Armor implements Item
{
    //Stereotypical Instance Variable Declarations    
    private String resistance = "", description = "", statToBoost = "", weight = "", name = "", bodyPart = "";;
    private int strBoost, dexBoost, defBoost, atkBoost, stamBoost, healthBoost;
    //Stereotypical Default Constructor-
    public Armor(String resist, int str, int dex, int def, int atk, int stam, int health, String wt, String n, String body)
    {
        resistance = resist;
        description = "Resists " + getResistance();
        strBoost = str;
        dexBoost = dex;
        defBoost = def;
        atkBoost = atk;
        stamBoost = stam;
        healthBoost = health;
        weight = wt;
        name = n;
        bodyPart = body;
    }
    
    public boolean equip()
    {
        System.out.println(description + "/nWould you like to equip this armor?" +
                           "Armor of this kind currently being worn will be discarded. (y/n)");
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
    
    public void use()
    {}
    
    //Stereotypical Accessor Methods-
    public String getResistance()
    {   return resistance;  }
    public String description()
    {   return description; }
    public int getStrBoost()
    {   return strBoost;  }
    public int getDexBoost()
    {   return dexBoost;  }
    public int getStamBoost()
    {   return stamBoost;   }
    public double getHealthBoost()
    {   return healthBoost;   }
    public int getDefBoost()
    {   return dexBoost;  }
    public int getAtkBoost()
    {   return atkBoost;    }
    public String getName()
    {   return name;    }
    public String getBodyPart()
    {   return bodyPart;    }
}
