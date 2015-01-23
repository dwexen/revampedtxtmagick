/**
 * Defines a spell consisting of an undetermined number of elements.
 * 
 * @Dream Team 2: Electric Boogaloo 
 * @author Nelson Gomez
 */
import java.util.ArrayList;
public class Spell
{
    private ArrayList<Element> m_elements;
    private boolean m_actsOverTime;
    private boolean m_isActingOverTime;
    private int m_turnsLeftToCast;
    private Character m_target;
    
    // Wizards only: do not lose item or move actions while casting spells,
    // but they will lose their weapon action when they cast five elements.
    
    // Holy constructors, Batman
    private void basicInit(boolean actsOverTime, int turnsToCastOverTime)
    {
        m_target = null;
        m_isActingOverTime = false;
        m_actsOverTime = actsOverTime;
        m_turnsLeftToCast = (actsOverTime ? turnsToCastOverTime : -1);
    }
    
    public Spell()
    {
        basicInit(false, -1);
        m_elements = new ArrayList<Element>();
    }
    
    public Spell(ArrayList<Element> elements)
    {
        basicInit(false, -1);
        m_elements = elements;
    }
    
    public Spell(boolean actsOverTime, int turnsToCastOverTime)
    {
        basicInit(actsOverTime, turnsToCastOverTime);
        m_elements = new ArrayList<Element>();
    }
    
    public Spell(ArrayList<Element> elements, boolean actsOverTime, int turnsToCastOverTime)
    {
        basicInit(actsOverTime, turnsToCastOverTime);
        m_elements = elements;
    }
    
    public boolean hasElement(String elementDesc)
    {
        elementDesc = elementDesc.toLowerCase();
        for(Element e : m_elements)
        {
            if(e.getDesc().toLowerCase().equals(elementDesc))
                return true;
        }
        
        return false;
    }
    
    public boolean checkOpposites(String elementDesc)
    {
        String oppositeElement, oppositeElement2 = "";
        elementDesc = elementDesc.toLowerCase();
        if(elementDesc.equalsIgnoreCase("earth")) oppositeElement = "lightning";
        else if(elementDesc.equalsIgnoreCase("arcane")) oppositeElement = "life";
        //else if(elementDesc.equals("cold")) oppositeElement = "steam";
        else if(elementDesc.equalsIgnoreCase("fire")) oppositeElement = "cold";
        
        else if(elementDesc.equalsIgnoreCase("lightning")) 
        {
            oppositeElement = "rock";
            oppositeElement2 = "water";
        }
        //else if(elementDesc.equals("life")) oppositeElement = "rock";
        //else if(elementDesc.equals("steam")) oppositeElement = "cold";
        else if(elementDesc.equalsIgnoreCase("cold")) oppositeElement = "fire";
        
        else if(elementDesc.equalsIgnoreCase("water")) oppositeElement = "lightning";
        
        else return true;
        
        for(Element e : m_elements)
        {
            if(e.getDesc().equalsIgnoreCase(oppositeElement) || 
            e.getDesc().equalsIgnoreCase(oppositeElement2))
                return false;
        }
        
        return true;
    }
    
    public boolean addElement(Element element)
    {
        if(!checkOpposites(element.getDesc()))
            return false;
        
        m_elements.add(element);
        return true;
    }
    
    public ArrayList<Element> getElements() { return m_elements; }
    public double getTotalDamage()
    {
        double sum = 0;
        for(Element e : m_elements)
            sum += e.getDamage();
            
        return sum;
    }
    
    public double getDamageOverTime()
    {
        double sum = 0;
        for(Element e: m_elements)
            if(e.getActingOT())
            {
                sum += e.getDamageOverTime();
            }
        return sum;
    }
    
    public void act()
    {
        if(m_target != null)
        {
            // <TODO>
            // For now we just in/decrease the amount of health the target has.
            // Later we will alter other statistics as needed.
            double health = m_target.getHealth();
            m_target.changeHealth(health - getTotalDamage());
        }
    }
    
    public void cast(Character target)
    {
        // We can't do much with this yet.
        //String resistance = target.getArmor().getResistance();
        
        // Set targets
        m_target = target;
        
        if(m_actsOverTime) {
            m_isActingOverTime = true;
            m_target = target;
        }
    }
    
    
    // This specifies if a spell *has* an effect over time.
    // This is independent of whether or not it actually *is* having an effect at this moment
    //     and whether or not the spell has been cast.
    public boolean getActsOverTime() { return m_actsOverTime; }
    
    // This specifies if a spell IS CURRENTLY affecting its target every turn
    public boolean getActingOverTime() { return m_isActingOverTime; }
}