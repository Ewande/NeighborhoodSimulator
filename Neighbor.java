/**
 * Created by augoff on 7/16/15.
 */
public class Neighbor
{
    Robot robot;
    int counter;

    public Neighbor(Robot _robot)
    {
        robot = _robot;
    }

    @Override
    public boolean equals(Object obj)
    {
        return obj instanceof Neighbor && robot.equals(((Neighbor) obj).robot);
    }
}
