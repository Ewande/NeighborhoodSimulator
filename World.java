import java.util.LinkedList;
import java.util.Random;

/**
 * Created by augoff on 7/16/15.
 */
public class World
{
    LinkedList<Robot> robots;
    Robot[][] map;

    public World()
    {
        robots = new LinkedList<>();
        map = new Robot[Constants.WORLD_WIDTH][Constants.WORLD_HEIGHT];
    }

    public void addNest()
    {
        Robot nest = new Robot(-1);
        nest.x = Constants.NEST_X;
        nest.y = Constants.NEST_Y;
        nest.role = Robot.RobotRole.Nest;
        addToWorld(nest);
    }

    public void addToWorld(Robot robot)
    {
        robots.add(robot);
    }

    public void shuffleRobots()
    {
        Random random = new Random();
        for(Robot robot : robots)
        {
            robot.x = random.nextInt(Constants.WORLD_WIDTH);
            robot.y = random.nextInt(Constants.WORLD_HEIGHT);
        }
    }

    public void shuffleRobotsAroundNest()
    {
        Random random = new Random();
        for(Robot robot : robots)
        {
            robot.x = Constants.NEST_X + random.nextInt(2 * Constants.SPREADING_RADIUS) - Constants.SPREADING_RADIUS;
            robot.y = Constants.NEST_Y + random.nextInt(2 * Constants.SPREADING_RADIUS) - Constants.SPREADING_RADIUS;
        }
    }

    public void moveRobots()
    {
        for(Robot robot : robots)
        {
            robot.updateNeighborhood(robots);
            robot.prepareMove();
        }
        for(Robot robot : robots)
            robot.makeMove();
    }
}
