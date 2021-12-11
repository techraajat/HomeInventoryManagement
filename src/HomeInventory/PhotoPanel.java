package HomeInventory;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

class PhotoPanel extends JPanel
{
    @Override
    public void paintComponent(Graphics g)
    {
        Graphics2D g2D = (Graphics2D) g;
        super.paintComponent(g2D);
// draw border
        g2D.setPaint(Color.BLACK);
        g2D.draw(new Rectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1));
// show photo
        Image photoImage = new
                ImageIcon(HomeInventory_1.photoTextArea.getText()).getImage();
        int w = getWidth();
        int h = getHeight();
        double rWidth = (double) getWidth() / (double) photoImage.getWidth(null);
        double rHeight = (double) getHeight() / (double) photoImage.getHeight(null);
        if (rWidth > rHeight)
        {
// leave height at display height, change width by amount height is changed
            w = (int) (photoImage.getWidth(null) * rHeight);
        }
        else
        {
// leave width at display width, change height by amount width is changed
            h = (int) (photoImage.getHeight(null) * rWidth);
        }
// center in panel
        g2D.drawImage(photoImage, (int) (0.5 * (getWidth() - w)), (int) (0.5 * (getHeight() -
                h)), w, h, null);
        g2D.dispose();
    }
}
