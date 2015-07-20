/**
 * Created by augoff on 7/16/15.
 */
public class Constants
{

    public enum SimType
    {
        Auto,
        Manual
    }

    // simulation
    public static final SimType SIM_TYPE = SimType.Manual;
    public static final int STEP_DURATION_MS = 1000;

    // world
    public static final int WORLD_HEIGHT = 25;
    public static final int WORLD_WIDTH = 25;
    public static final int PRINTING_SCALE = 15;
    public static final int ROBOTS_COUNT = 25;

    // nest
    public static final int NEST_X = 10;
    public static final int NEST_Y = 10;
    public static final int SPREADING_RADIUS = 5;

    // robots
    public static final double SENSOR_RANGE = 5;
    public static final int ROBOT_SPEED = 1;
    public static final int BEACON_TO_WALKER_THRESHOLD = 3;
    public static final int WALKER_TO_BEACON_THRESHOLD = 1;
    public static final int NEIGHBORHOOD_MAX_SIZE = 6;
    public static final int STOP_BEING_USELESS_TIME = 500;
}
