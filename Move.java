/**
 * Created by augoff on 7/16/15.
 */
public class Move
{
    int oldX;
    int oldY;
    Robot.RobotRole oldRole;

    int newX;
    int newY;
    Robot.RobotRole newRole;

    public Move(int _oldX, int _oldY, Robot.RobotRole _oldRole, int _newX, int _newY, Robot.RobotRole _newRole)
    {
        oldX = _oldX;
        oldY = _oldY;
        oldRole = _oldRole;

        newX = _newX;
        newY = _newY;
        newRole = _newRole;
    }

    public Move(Robot robot, int _newX, int _newY, Robot.RobotRole _newRole)
    {
        this(robot.x, robot.y, robot.role, _newX, _newY, _newRole);
    }

    public static Move getIdle(Robot robot)
    {
        return new Move(robot, robot.x, robot.y, robot.role);
    }

    public Move getReversed()
    {
        return new Move(newX, newY, newRole, oldX, oldY, oldRole);
    }
}
