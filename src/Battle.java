/**
 * Manages battles and decides how to attack and what damage to deal.
 * 
 * @author Nelson Gomez & Matt Colozzo
 */
//I am a change to be pushed
import java.util.Scanner;
import java.util.InputMismatchException;
public class Battle
{
    private Character m_attacker;
    private Character m_defender;
    private boolean isAt, isDt, battleTracker = true;
   
    public Battle(Character attacker, Character defender)
    {
        m_attacker = attacker;
        m_defender = defender;
    }
    
    public void battleMaker()
    {
        if(m_attacker instanceof Player){ isAt = true; }
        else{isDt = true; }
        attackerTurn();
    }
    
    public void attackerTurn()
    {
        boolean opponentDead = false;
        if(isAt && m_attacker instanceof Player)
        {
            System.out.println("Your turn! What is your action? (attack/spell/move)");
            String a = Driver.getScanner().nextLine();
            if(a.equalsIgnoreCase("attack"))
            {
                if(m_attacker.getWeapon() != null)
                    m_defender.damageTaken(m_attacker.getWeapon().attack(m_attacker,m_defender));
                else
                {
                    m_defender.damageTaken((int)(Math.pow(m_attacker.getAtk(), 2) / m_defender.getDef()));
                    //equivalent to: the ratio of atk to def, times the atk
                    //this way it increases or decreases power as appropriate
                    //check my math though: (atk / def) * atk = atk^2 / def
                }
                
                if(m_defender.getHealth() <= 0)
                {
                    opponentDead = true;
                    System.out.println(m_defender.getName() + " has fallen! You are victorious!");
                }
                else
                {   System.out.println("Your enemy is down to " + m_defender.getHealth() + " health!"); }
            }
            else if(a.equalsIgnoreCase("spell"))
            {
                System.out.println("Spell size? (1 - 5)");
                int b = 0;
                boolean improperInteger = false;
                while(b == 0)
                {
                    try
                    {
                        b = Driver.getScanner().nextInt();
                    }
                    catch(InputMismatchException e)
                    {
                        System.out.println("That's not even a proper integer you noodle.");
                        Driver.getScanner().nextLine();
                        improperInteger = true; //exists to prevent yelling at you twice;
                    }
                    if((b > 5 || b < 1) && !improperInteger)
                    {
                        System.out.println("Your parents raised you better than to input numbers outside the given range.");
                        b = 0;
                    }
                }
                Driver.getScanner().nextLine();
                spellDamageCalculator(((Player)m_attacker).makeSpell(b), "a");
                
                if(m_defender.getHealth() <= 0)
                {
                    opponentDead = true;
                    System.out.println(m_defender.getName() + " has fallen! You are victorious!");
                }
                else
                {   System.out.println("Your enemy is down to " + m_defender.getHealth() + " health!"); }
            }
            else if(a.equalsIgnoreCase("move"))
            {
                ((Player)(m_attacker)).move();
            }
            /*else
            {   Game.inputNotRecognized(a); }*/
        }
        else
        {
            int r = (int)(Math.random() * 2);
            if(r == 0)
            {
                int b = (int)(Math.random() * 5 + 1);
                spellDamageCalculator(((Enemy)m_attacker).makeSpell(b), "d");
            }
            else if(r == 1)
            {
                if(m_defender.getWeapon() != null)
                {
                    m_defender.damageTaken(m_attacker.getWeapon().attack(m_attacker,m_defender));
                }
                else
                {
                    m_defender.damageTaken((int)(Math.pow(m_attacker.getAtk(), 2) / m_defender.getDef()));
                    //equivalent to: the ratio of atk to def, times the atk
                    //this way it increases or decreases power as appropriate
                    //check my math though: (atk / def) * atk = atk^2 / def
                }
            }
            System.out.println("You are down to " + m_defender.getHealth() + " health!");
        }
        
        if(!opponentDead)
            defenderTurn();
        else
            m_defender.getCurrentRoom().removeFromRoom(m_defender);
    }
    public void defenderTurn()
    {
        
        boolean opponentDead = false;
        if(isAt && m_defender instanceof Player)
        {
            System.out.println("Your turn! What is your action? (attack/spell/move)");
            String a = Driver.getScanner().nextLine();
            if(a.equalsIgnoreCase("attack"))
            {
                if(m_defender.getWeapon() != null)
                    m_attacker.damageTaken(m_defender.getWeapon().attack(m_defender,m_attacker));
                else
                {
                    m_attacker.damageTaken((int)(Math.pow(m_defender.getAtk(), 2) / m_attacker.getDef()));
                    //equivalent to: the ratio of atk to def, times the atk
                    //this way it increases or decreases power as appropriate
                    //check my math though: (atk / def) * atk = atk^2 / def
                }
                
                if(m_attacker.getHealth() <= 0)
                {
                    opponentDead = true;
                    System.out.println(m_attacker.getName() + " has fallen! You are victorious!");
                }
                else
                {   System.out.println("Your enemy is down to " + m_attacker.getHealth() + " health!"); }
            }
            else if(a.equalsIgnoreCase("spell"))
            {
                System.out.println("Spell size? (1 - 5)");
                int b = 0;
                boolean improperInteger = false;
                while(b == 0)
                {
                    try
                    {
                        b = Driver.getScanner().nextInt();
                    }
                    catch(InputMismatchException e)
                    {
                        System.out.println("That's not even a number you noodle.");
                        Driver.getScanner().nextLine(); //exists to prevent yelling at you twice;
                        improperInteger = true;
                    }
                    if((b > 5 || b < 1) && !improperInteger)
                    {
                        System.out.println("Your parents raised you better than to input numbers outside the given range.");
                        b = 0;
                    }
                }
                Driver.getScanner().nextLine();
                spellDamageCalculator(((Player)m_defender).makeSpell(b), "a");
                
                if(m_attacker.getHealth() <= 0)
                {
                    opponentDead = true;
                    System.out.println(m_attacker.getName() + " has fallen! You are victorious!");
                }
                else
                {   System.out.println("Your enemy is down to " + m_attacker.getHealth() + " health!"); }
            }
            else if(a.equalsIgnoreCase("move"))
            {
                ((Player)(m_defender)).move();
            }
            /*else
            {   Game.inputNotRecognized(a); }*/
        }
        else
        {
            int r = (int)(Math.random() * 2);
            if(r == 0)
            {
                int b = (int)(Math.random() * 5 + 1);
                spellDamageCalculator(((Enemy)m_defender).makeSpell(b), "d");
            }
            else if(r == 1)
            {
                if(m_defender.getWeapon() != null)
                {
                    m_attacker.damageTaken(m_defender.getWeapon().attack(m_defender,m_attacker));
                }
                else
                {
                    m_attacker.damageTaken((int)(Math.pow(m_defender.getAtk(), 2) / m_attacker.getDef()));
                    //equivalent to: the ratio of atk to def, times the atk
                    //this way it increases or decreases power as appropriate
                    //check my math though: (atk / def) * atk = atk^2 / def
                }
            }
            System.out.println("You are down to " + m_attacker.getHealth() + " health!");
        }
        
        if(!opponentDead)
            attackerTurn();
        else
            m_attacker.getCurrentRoom().removeFromRoom(m_attacker);
    }
    public void spellDamageCalculator(Spell s, String t)
    {
        if(t.equals("a"))
        {
            if(m_defender.getArmor() != null)
            {
                for(Armor a: m_defender.getArmor())
                {
                    for(Element e: s.getElements())
                    {
                        if(a.getResistance().equalsIgnoreCase(e.getDesc()))
                        {
                            e.isActingOverTime(false);
                        }
                    }
                }
            }
            m_defender.damageTaken((int)s.getDamageOverTime());
            m_defender.damageTaken((int)s.getTotalDamage());
            m_defender.spellStatusEffects(s);
        }
        else if(t.equals("d"))
        {
            if(m_attacker.getArmor() != null)
            {
                for(Armor a: m_attacker.getArmor())
                {
                    for(Element e: s.getElements())
                    {
                        if(a.getResistance().equalsIgnoreCase(e.getDesc()))
                        {
                            e.isActingOverTime(false);
                        }
                    }
                }
            }
            m_attacker.damageTaken((int)s.getDamageOverTime());
            m_attacker.damageTaken((int)s.getTotalDamage());
            m_attacker.spellStatusEffects(s);
        }
    }
}
