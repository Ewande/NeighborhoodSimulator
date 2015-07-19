/**
 * Created by augoff on 7/16/15.
 */
public class Move
{
    int newX;
    int newY;
    Robot.RobotRole newRole;


    public Move()
    {

    }

    public Move(int _newX, int _newY, Robot.RobotRole _newRole)
    {
        newX = _newX;
        newY = _newY;
        newRole = _newRole;
    }

    public static Move getIdle(Robot robot)
    {
        return new Move(robot.x, robot.y, robot.role);
    }
}
