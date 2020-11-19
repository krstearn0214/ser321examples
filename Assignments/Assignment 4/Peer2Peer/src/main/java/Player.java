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
}