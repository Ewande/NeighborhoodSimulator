import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.TimeUnit;

/**
 * Created by augoff on 7/16/15.
 */
public class Simulation implements KeyListener
{

    World world;
    SimGUI gui;

    public World createWorld()
    {
        World world = new World();
        world.addNest();

        for(int i = 0; i < 15; i++)
            world.addToWorld(new Robot(i));

        world.shuffleRobotsAroundNest();
        return world;
    }

    public void run()
    {
        world = createWorld();
        gui = new SimGUI(world);

        if(Constants.SIM_TYPE == Constants.SimType.Manual)
            gui.addKeyListener(this);

        else
            while(true)
            {
                world.moveRobots();
                gui.repaint();

                try {
                    TimeUnit.MILLISECONDS.sleep(Constants.STEP_DURATION_MS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
    }

    public static void main(String[] args)
    {
        new Simulation().run();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        world.moveRobots();
        gui.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
