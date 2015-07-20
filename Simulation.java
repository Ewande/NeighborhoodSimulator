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
        System.out.println("Starting simulation GUI...");
        world = createWorld();
        gui = new SimGUI(world);

        if(Constants.SIM_TYPE == Constants.SimType.Manual)
        {
            world.prepareMoves();
            gui.addKeyListener(this);
        }
        else
            while(true)
            {
                world.prepareMoves();
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
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e)
    {
        int keyCode = e.getKeyCode();
        switch (keyCode)
        {
            case KeyEvent.VK_I:
                gui.setLabelType(SimGUI.LabelType.Id);
                gui.updateDisplay();
                break;

            case KeyEvent.VK_N:
                gui.setLabelType(SimGUI.LabelType.NeighborsCount);
                gui.updateDisplay();
                break;

            default:
                world.moveRobots();
                world.prepareMoves();
                gui.updateDisplay();
                break;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
