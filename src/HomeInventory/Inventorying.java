package HomeInventory;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;

class Inventorying implements Printable
{
    public int print(Graphics g, PageFormat pf, int pageIndex)
    {
        Graphics2D g2D = (Graphics2D) g;
        if ((pageIndex + 1) > HomeInventory_1.lastPage)
        {
            return NO_SUCH_PAGE;
        }
        int i, iEnd;
// here you decide what goes on each page and draw it
// header
        g2D.setFont(new Font("Arial", Font.BOLD, 14));
        g2D.drawString("Home Inventory Items - Page " + (pageIndex + 1),
                (int) pf.getImageableX(), (int) (pf.getImageableY() + 25));
// get starting y
        int dy = (int) g2D.getFont().getStringBounds("S",
                g2D.getFontRenderContext()).getHeight();
        int y = (int) (pf.getImageableY() + 4 * dy);
        iEnd = HomeInventory_1.entriesPerPage * (pageIndex + 1);
        if (iEnd > HomeInventory_1.numberEntries)
            iEnd = HomeInventory_1.numberEntries;
        for (i = HomeInventory_1.entriesPerPage * pageIndex; i < iEnd; i++)
        {
// dividing line
            Line2D.Double dividingLine = new
                    Line2D.Double(pf.getImageableX(), y, pf.getImageableX() + pf.getImageableWidth(), y);
            g2D.draw(dividingLine);
            y += dy;
            g2D.setFont(new Font("Arial", Font.BOLD, 12));
            g2D.drawString(InventoryItem.description, (int) pf.getImageableX(), y);
            y += dy;
            g2D.setFont(new Font("Arial", Font.PLAIN, 12));
            g2D.drawString("Location: " + InventoryItem.location, (int)
                    (pf.getImageableX() + 25), y);
            y += dy;
            if(InventoryItem.marked)
                g2D.drawString("Item is marked with identifying information.", (int)
                        (pf.getImageableX() + 25), y);
            else
                g2D.drawString("Item is NOT marked with identifying information.", (int)
                        (pf.getImageableX() + 25), y);
            y += dy;
            g2D.drawString("Serial Number: " +
                    InventoryItem.serialNumber, (int) (pf.getImageableX() + 25), y);
            y += dy;
            g2D.drawString("Price: $" + InventoryItem.purchasePrice + ","
                    + "Purchased on: " + InventoryItem.purchaseDate, (int) (pf.getImageableX() +
                    25), y);
            y += dy;
            g2D.drawString("Purchased at: " +
                    InventoryItem.purchaseLocation, (int) (pf.getImageableX() + 25), y);
            y += dy;

            try
            {
// maintain original width/height ratio
                Image inventoryImage = new
                        ImageIcon(InventoryItem.photoFile).getImage();
                double ratio = (double) (inventoryImage.getWidth(null)) / (double)
                        inventoryImage.getHeight(null);
                g2D.drawImage(inventoryImage, (int) (pf.getImageableX() + 25), y, (int) (100 *
                        ratio), 100, null);
            }
            catch (Exception ex)
            {
// have place to go in case image file doesn't open
            }
            y += 2 * dy + 100;
        }
        return PAGE_EXISTS;
    }
}