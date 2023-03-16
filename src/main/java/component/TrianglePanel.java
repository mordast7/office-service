package component;

import javax.swing.*;
import java.awt.*;

public class TrianglePanel extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();

        int x1 = 10;
        int y1 = height - 10;

        int x2 = width / 2 - 10;
        int y2 = 10;

        int x3 = width - 10;
        int y3 = height - 10;

        g2.setColor(Color.BLUE);
        g2.setStroke(new BasicStroke(5));

        g2.drawLine(x1, y1, x2, y2);
        g2.drawLine(x2, y2, x3, y3);
        g2.drawLine(x3, y3, x1, y1);
    }
}
