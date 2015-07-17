import java.util.LinkedList;
import java.util.Random;

/**
 * Created by augoff on 7/16/15.
 */
public class Robot
{
    public enum RobotRole
    {
        Nest,
        Walker,
        Beacon
    }

    int id;
    int x;
    RobotRole role;
    int y;
    LinkedList<Neighbor> neighbors;

    Move preparedMove;

    public Robot(int _id)
    {
        id = _id;
        role = RobotRole.Walker;
        neighbors = new LinkedList<>();
    }

    private int bound(int min, int max, int value)
    {
        return Math.max(Math.min(value, max), min);
    }

    private double getDistance(Robot other)
    {
        int dx = x - other.x;
        int dy = y - other.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public void updateNeighborhood(LinkedList<Robot> robots)
    {
        LinkedList<Neighbor> lastTimeNeighbors = neighbors;
        neighbors = new LinkedList<>();
        robots.sort((Robot r1, Robot r2) -> (int) (getDistance(r1) - getDistance(r2)));
        Neighbor temporary = new Neighbor(null);
        for(Robot robot : robots)
            if(neighbors.size() == 6 || getDistance(robot) > Constants.SENSOR_RANGE)
                return;
            else if(!equals(robot) && robot.role != RobotRole.Walker)
            {
                temporary.robot = robot;
                int index = lastTimeNeighbors.indexOf(temporary);
                if(index >= 0)
                {
                    Neighbor foundNeighbor = lastTimeNeighbors.get(index);
                    foundNeighbor.counter++;
                    neighbors.add(foundNeighbor);
                }
                else
                    neighbors.add(new Neighbor(robot));
            }
    }


    public void prepareMove()
    {
        if(role == RobotRole.Walker)
        {
            if(neighbors.size() <= Constants.WALKER_TO_BEACON_THRESHOLD)
                preparedMove = new Move(x, y, RobotRole.Beacon);
            else
                preparedMove = createRandomWalkMove(role);
        }
        else if(role == RobotRole.Beacon)
        {
            if(neighbors.size() >= Constants.BEACON_TO_WALKER_THRESHOLD)
                preparedMove = createRandomWalkMove(RobotRole.Walker);
        }
    }

    public boolean makeMove()
    {
        if(preparedMove == null)
            return false;

        x = preparedMove.newX;
        y = preparedMove.newY;
        role = preparedMove.newRole;

        preparedMove = null;
        return true;
    }

    public Move createRandomWalkMove(RobotRole newRole)
    {
        Move move = new Move();
        Random random = new Random();
        move.newX = bound(0, Constants.WORLD_WIDTH - 1, x + random.nextInt(2 * Constants.ROBOT_SPEED + 1) - Constants.ROBOT_SPEED);
        move.newY = bound(0, Constants.WORLD_HEIGHT - 1, y + random.nextInt(2 * Constants.ROBOT_SPEED + 1) - Constants.ROBOT_SPEED);
        move.newRole = newRole;
        return move;
    }

    @Override
    public boolean equals(Object obj)
    {
        return obj instanceof Robot && id == ((Robot) obj).id;
    }
}