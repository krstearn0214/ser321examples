public class Player
{
    private int points;
    private boolean isHost;
    private String username;

    public Player(String name)
    {
        points = 0;
        isHost = false;
        username = name;
    }

    public void nowHost(boolean h)
    {
        isHost = h;
    }

    public boolean getHost()
    {
        return isHost;
    }

    public String getName()
    {
        return username;
    }

    public void score()
    {
        points++;
    }

    public int getPoints()
    {
        return points;
    }
}