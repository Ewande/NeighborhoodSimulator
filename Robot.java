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
    int y;
    RobotRole role;
    LinkedList<Neighbor> neighbors;
    int noWalkerCounter;

    Move preparedMove;

    public Robot(int _id)
    {
        id = _id;
        x = -1;
        y = -1;
        noWalkerCounter = 0;
        role = RobotRole.Walker;
        neighbors = new LinkedList<>();
    }

    public boolean setPosition(World world, int _x, int _y)
    {
        if(!world.isFieldFree(_x, _y))
            return false;
        int oldX = x, oldY = y;
        x = _x;
        y = _y;
        world.updateRobotPosition(this, oldX, oldY);
        return true;
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

    private boolean isAnyWalkerAround(LinkedList<Robot> robots)
    {
        return robots.stream()
                .anyMatch(robot -> getDistance(robot) <= Constants.SENSOR_RANGE && robot.role == RobotRole.Walker);
    }

    public void updateNeighborhood(LinkedList<Robot> robots)
    {
        LinkedList<Neighbor> lastTimeNeighbors = neighbors;
        neighbors = new LinkedList<>();
        robots.sort((Robot r1, Robot r2) -> getDistance(r1) - getDistance(r2) > 0 ? 1 : -1);
        Neighbor temporary = new Neighbor(null);

        noWalkerCounter = isAnyWalkerAround(robots) ? 0 : noWalkerCounter + 1;

        for(Robot robot : robots)
            if(neighbors.size() == Constants.NEIGHBORHOOD_MAX_SIZE || getDistance(robot) > Constants.SENSOR_RANGE)
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
            if(neighbors.size() >= Constants.BEACON_TO_WALKER_THRESHOLD && Math.random() < 1.0/neighbors.size())
                preparedMove = createRandomWalkMove(RobotRole.Walker);
            else if(noWalkerCounter > Constants.STOP_BEING_USELESS_TIME && Math.random() < 0.5)
                preparedMove = createRandomWalkMove(RobotRole.Walker);
            else
                preparedMove = Move.getIdle(this);
        }
        else if(role == RobotRole.Nest)
            preparedMove = Move.getIdle(this);

    }

    public boolean makeMove(World world)
    {
        if(preparedMove == null)
            return false;
        setPosition(world, preparedMove.newX, preparedMove.newY);
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