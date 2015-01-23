/**
 * @author Rico Suave
 */
public class Location
{
    private int m_x, m_y;
    public Location() { setLocation(0, 0); }
    public Location(int x, int y) { setLocation(x, y); }
    public int getX() { return m_x; }
    public int getY() { return m_y; }
    
    public void setX(int x) { m_x = x; }
    public void setY(int y) { m_y = y; }
    public void setLocation(int x, int y) { setX(x); setY(y); }
}
