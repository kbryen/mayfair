/*
 * Mayfair Stock Control.
 *
 */
package main.java;

import java.awt.*;
import javax.swing.*;
import java.awt.print.*;

public class PrintUtilities implements Printable
{

    private final Component component;

    public static void printComponent(Component c)
    {
        new PrintUtilities(c).print();
    }

    public PrintUtilities(Component componentToBePrinted)
    {
        this.component = componentToBePrinted;
    }

    public void print()
    {
        PrinterJob printJob = PrinterJob.getPrinterJob();
        PageFormat pf = printJob.defaultPage();
        pf.setOrientation(PageFormat.PORTRAIT);
        printJob.setPrintable(this, pf);
        if (printJob.printDialog())
        {
            try
            {
                printJob.print();
            }
            catch (PrinterException ex)
            {
                MayfairStatic.outputMessage(null, ex);
            }
        }
    }

    @Override
    public int print(Graphics g, PageFormat pageFormat, int pageIndex)
    {
        if (pageIndex > 0)
        {
            return (NO_SUCH_PAGE);
        }
        else
        {

            try
            {
                Graphics2D g2d = (Graphics2D) g;
                g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
                disableDoubleBuffering(component);
                component.paint(g2d);
                enableDoubleBuffering(component);
                return (PAGE_EXISTS);
            }
            catch (Exception ex)
            {
                MayfairStatic.outputMessage(null, ex);
                return (NO_SUCH_PAGE);
            }
        }
    }

    public static void disableDoubleBuffering(Component c)
    {
        RepaintManager currentManager = RepaintManager.currentManager(c);
        currentManager.setDoubleBufferingEnabled(false);
    }

    public static void enableDoubleBuffering(Component c)
    {
        RepaintManager currentManager = RepaintManager.currentManager(c);
        currentManager.setDoubleBufferingEnabled(true);
    }
}
