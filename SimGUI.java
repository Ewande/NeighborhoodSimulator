import javax.swing.*;
import java.awt.*;

/**
 * Created by augoff on 7/16/15.
 */
public class SimGUI extends JFrame
{
    public class WorldPanel extends JPanel
    {
        World world;

        public WorldPanel(World _world)
        {
            world = _world;
            setPreferredSize(new Dimension(Constants.WORLD_WIDTH * Constants.PRINTING_SCALE,
                    Constants.WORLD_HEIGHT * Constants.PRINTING_SCALE));
        }

        @Override
        protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setFont(g2d.getFont().deriveFont(10.0f));
            for(Robot robot : world.robots)
            {
                int x = robot.x * Constants.PRINTING_SCALE;
                int y = (Constants.WORLD_HEIGHT - robot.y - 1) * Constants.PRINTING_SCALE;
                if(robot.role == Robot.RobotRole.Walker)
                    g2d.setColor(Color.RED);
                else if (robot.role == Robot.RobotRole.Beacon)
                    g2d.setColor(Color.BLUE);
                else if(robot.role == Robot.RobotRole.Nest)
                    g2d.setColor(Color.GREEN);
                g2d.fillOval(x, y, Constants.PRINTING_SCALE, Constants.PRINTING_SCALE);
                g2d.setColor(Color.BLACK);
                g2d.drawOval(x, y, Constants.PRINTING_SCALE, Constants.PRINTING_SCALE);
                g2d.drawString(robot.neighbors.size() + "", x, y);

            }
        }
    }

    public SimGUI(World world)
    {
        super("Simulator");
        setContentPane(new WorldPanel(world));
        setVisible(true);
        pack();
    }





}
