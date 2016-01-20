/***********************************************************
 * Software: instrument gui library
 * Module:   Smith chart circle labels drawing class
 * Version:  0.1
 * Licence:  GPL2
 *
 * Owner: Kim Kristo
 * Date creation : 4.10.2013
 *
 ***********************************************************/
package oh3ebf.lib.gui.primitives;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class SmithLabel {

    protected String name;
    protected double reNorm;
    protected double imNorm;
    protected DecimalFormat df = new DecimalFormat("#.######");
    DecimalFormatSymbols symbols = new DecimalFormatSymbols();

    public SmithLabel(String name, double reNorm, double imNorm) {
        this.reNorm = reNorm;
        this.imNorm = imNorm;
        this.name = name;
    }

    /**
     * 
     * @param g
     * @param scale
     * @param Z0
     */
    public void drawLabel(Graphics2D g, double scale, double Z0) {
        FontMetrics fm = g.getFontMetrics();
        Point2D.Double p = new Point2D.Double();

        symbols.setDecimalSeparator('.');
        symbols.setGroupingSeparator(' ');
        df.setDecimalFormatSymbols(symbols);

        p = getPosition(scale, Z0);

        if (reNorm == 0.0D) {
            if ((imNorm >= 0.0D) && (imNorm < 1.0D)) {
                g.drawString(name, (float) (p.x * scale - 3.0D) - fm.stringWidth(name), -(float) (p.y * scale));
            } else if ((imNorm < 0.0D) && (imNorm > -1.0D)) {
                g.drawString(name, (float) (p.x * scale - 3.0D) - fm.stringWidth(name), -(float) (p.y * scale - 12.0D));
            } else if ((imNorm < 0.0D) && (imNorm <= -1.0D)) {
                g.drawString(name, (float) (p.x * scale + 3.0D), -(float) (p.y * scale - 12.0D));
            } else {
                g.drawString(name, (float) (p.x * scale + 3.0D), -(float) (p.y * scale + 2.0D));
            }
        } else {
            g.drawString(name, (float) (p.x * scale + 3.0D), -(float) (p.y * scale + 2.0D));
        }
    }

    /** Function returns ???
     * 
     * @param scale
     * @param Z0
     * @return point
     */
    public Point2D.Double getPosition(double scale, double Z0) {
        Point2D.Double p = new Point2D.Double();

        //mitä  tässä lasketaan??? reaalinormitettuna == NaN
        if (reNorm == (1.0D / 0.0D)) {
            p.x = 1.0D;
            p.y = 0.0D;
        } else {

            if (imNorm == 0.0D) {
                p.x = (reNorm - 1.0D) / (1.0D + reNorm);
                p.y = 0.0D;
            } else {
                Circle re_c = new RealCircle(reNorm);
                Circle im_c = new ImaginaryCircle(imNorm);
                //System.out.println("r: " + reNorm + " i: " + imNorm);

                double[] tmpArray = re_c.intersectionCircle(im_c);
                double x3a = tmpArray[0];
                double y3a = tmpArray[1];
                double x3b = tmpArray[2];
                double y3b = tmpArray[3];

                if (imNorm > 0.0D) {
                    p.x = x3b;
                    p.y = y3b;
                } else {
                    p.x = x3a;
                    p.y = y3a;
                }
            }
        }

        return (p);
    }
}
