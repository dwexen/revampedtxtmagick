
/**
 * Write a description of class HelpfulPerson here.
 * 
 * @The Dream Team 2: The Electric Boogaloo [Brian Walker] & DAN
 * @version (a version number or a date)
 */
public class HelpfulPerson extends Character
{
    private String helpfulHint = "";
    public HelpfulPerson(double myHealth, int myStam, int myStr, int myDef, int myAtk,
    int myDex, double myMaxHealth, String n, Location loc, String hint)
    {
        super(myHealth, myStam, myStr, myDef, myAtk, myDex, myMaxHealth, n, loc);
        helpfulHint = hint;
    }
    
    public HelpfulPerson(String n, Location loc)
    {   super(n, loc);  }
    
    public String getHelpfulHint(){return helpfulHint;}
    public void speak(){System.out.println(helpfulHint);}
    
    ////because helpfulPeople can be used as shields
    //public Armor helpfulPerson()
    //{
    //    helpfulPerson.hideBehind;
    //}
    //thanks dan
}
