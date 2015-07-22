import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

/**
 * Created by augoff on 7/16/15.
 */
public class World
{
    LinkedList<Robot> robots;
    int iteration;
    Robot[][] map;

    public World()
    {
        robots = new LinkedList<>();
        map = new Robot[Constants.WORLD_WIDTH][Constants.WORLD_HEIGHT];
        iteration = 0;
    }

    public void addNest()
    {
        Robot nest = new Robot(-1);
        nest.x = Constants.NEST_X;
        nest.y = Constants.NEST_Y;
        nest.role = Robot.RobotRole.Nest;
        addToWorld(nest);
        updateRobotPosition(nest, -1, -1);
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

    private boolean isFieldInBounds(int x, int y)
    {
        return x >= 0 && x < Constants.WORLD_WIDTH && y >= 0 && y < Constants.WORLD_HEIGHT;
    }

    public boolean isFieldFree(int x, int y)
    {
        return isFieldInBounds(x, y) && map[x][y] == null;
    }

    public void updateRobotPosition(Robot robot, int oldX, int oldY)
    {
        map[robot.x][robot.y] = robot;
        if(isFieldInBounds(oldX, oldY))
            map[oldX][oldY] = null;
    }

    public void shuffleRobotsAroundNest()
    {
        Random random = new Random();
        for(Robot robot : robots)
        {
            if(robot.role != Robot.RobotRole.Nest)
            {
                int x = -1, y = -1, counter = 0;
                while (counter < 100 && !robot.setPosition(this, x, y))
                {
                    counter++;
                    x = Constants.NEST_X + random.nextInt(2 * Constants.SPREADING_RADIUS) - Constants.SPREADING_RADIUS;
                    y = Constants.NEST_Y + random.nextInt(2 * Constants.SPREADING_RADIUS) - Constants.SPREADING_RADIUS;
                }
                if (counter == 100)
                    System.out.println("No free space for robot with ID = " + robot.id);
            }
        }
    }

    public void prepareMoves()
    {
        LinkedList<Robot> copyForSorting = new LinkedList<>(robots);
        robots.stream()
                .filter(robot -> robot.preparedMove == null)
                .forEach(robot ->
        {
            robot.updateNeighborhood(copyForSorting);
            robot.prepareMove();
        });
    }

    public void moveRobots()
    {
        LinkedList<Robot> copy = new LinkedList<>(robots);
        Collections.shuffle(copy);
        boolean movesMade = true;
        for(Robot robot : copy)
            movesMade &= robot.makeMove(this);

        if(movesMade)
            iteration++;
    }
}
