import javax.swing.*;
import java.awt.*;
import java.util.function.Function;

/**
 * Created by augoff on 7/16/15.
 */
public class SimGUI extends JFrame
{
    private World world;
    private Function<Robot, String> labelFunction;


    public SimGUI(World _world)
    {
        super("Simulator - prepared");
        world = _world;
        setLabelType(LabelType.NeighborsCount);
        setContentPane(new WorldPanel());
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void updateDisplay()
    {
        setTitle("Simulator - step " + world.iteration);
        repaint();
    }

    public void setLabelType(LabelType labelType)
    {
        switch (labelType)
        {
            case NeighborsCount:
                labelFunction = robot -> Integer.toString(robot.neighbors.size());
                break;

            case Id:
                labelFunction = robot -> Integer.toString(robot.id);
                break;

            default:
                break;
        }
    }

    public enum LabelType
    {
        NeighborsCount,
        Id
    }

    public class WorldPanel extends JPanel
    {

        public WorldPanel()
        {
            setPreferredSize(new Dimension(Constants.WORLD_WIDTH * Constants.PRINTING_SCALE,
                    Constants.WORLD_HEIGHT * Constants.PRINTING_SCALE));
        }


        public Color getRobotColor(Robot robot)
        {
            switch (robot.role)
            {
                case Walker:
                    return Color.RED;
                case Beacon:
                    return Color.BLUE;
                case Nest:
                    return Color.GREEN;
                default:
                    return Color.BLACK;
            }
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
                g2d.setColor(getRobotColor(robot));
                g2d.fillOval(x, y, Constants.PRINTING_SCALE, Constants.PRINTING_SCALE);
                g2d.setColor(Color.BLACK);
                g2d.drawOval(x, y, Constants.PRINTING_SCALE, Constants.PRINTING_SCALE);
                g2d.drawString(labelFunction.apply(robot), x, y);
            }
        }
    }
}
