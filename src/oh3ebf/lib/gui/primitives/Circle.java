/***********************************************************
 * Software: instrument gui library
 * Module:   Smith chart circle drawing class
 * Version:  0.1
 * Licence:  GPL2
 *
 * Owner: Kim Kristo
 * Date creation : 4.10.2013
 *
 ***********************************************************/
package oh3ebf.lib.gui.primitives;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class Circle {

    protected double x0;
    protected double y0;
    protected double r0;

    public Circle(double x, double y, double r) {
        x0 = x;
        y0 = y;
        r0 = r;
    }

    /** Function gets radius
     * 
     * @return r
     */
    public double getR() {
        return r0;
    }

    /** Function gets x coordinate
     * 
     * @return x
     */
    public double getX() {
        return x0;
    }

    /** Function gets y coordinate
     * 
     * @return y
     */
    public double getY() {
        return y0;
    }

    /** Function sets radius
     * 
     * @param r radius
     */
    public void setR(double r) {
        r0 = r;
    }

    /** Function generates scaled ellipse
     * 
     * @param scale to use
     * @return ellipse
     */
    public Ellipse2D.Double getEllipse(double scale) {
        // ellipse at origo, radius r0 scaled 
        return new Ellipse2D.Double((x0 - r0) * scale, (-y0 - r0) * scale, 2.0D * r0 * scale, 2.0D * r0 * scale);
    }

    /** draw circle from scaled ellipse
     * 
     * @param g
     * @param scale
     * @param col
     */
    public void drawCircle(Graphics2D g, double scale, Color col) {
        g.setColor(col);

        if (r0 != (1.0D / 0.0D)) {
            g.draw(getEllipse(scale));
        } else {
            g.drawLine((int) (-scale), 0, (int) scale, 0);
        }
    }

    /** draw circle from ellipse with fixed color
     * 
     * @param g
     * @param scale
     */
    public void drawCircle(Graphics2D g, double scale) {
        drawCircle(g, scale, Color.GREEN);
    }

    /** Function calculates two circles intersection points
     * 
     * @param sec circle to calculate
     * @return intersection points in table
     */
    public double[] intersectionCircle(Circle sec) {
        double x1 = sec.getX();
        double y1 = sec.getY();
        double r1 = sec.getR();
        double[] returnArray = new double[4];        
        
        // calculate delta x
        double diffX = x1 - x0;
        // calculate delta y
        double diffY = y1 - y0;
        
        // distance between centers
        double d = Math.sqrt(Math.pow(diffX, 2) + Math.pow(diffY, 2));
        
        // distance from center 1 to line joining points of intersection
        double k = (Math.pow(d, 2) + Math.pow(r0, 2) - Math.pow(r1, 2)) / (2 * d);
                
        // x a
        returnArray[0] = x0 + (diffX * k / d) + ((diffY / d) * Math.sqrt(Math.pow(r0, 2) - Math.pow(k, 2)));
        // y a
        returnArray[1] = y0 + (diffY * k / d) - ((diffX / d) * Math.sqrt(Math.pow(r0, 2) - Math.pow(k, 2)));
        // x b
        returnArray[2] = x0 + (diffX * k / d) - ((diffY / d) * Math.sqrt(Math.pow(r0, 2) - Math.pow(k, 2)));
        // y b
        returnArray[3] = y0 + (diffY * k / d) + ((diffX / d) * Math.sqrt(Math.pow(r0, 2) - Math.pow(k, 2)));
        

        //double localX = x0;
        //double localY = y0;
        //double localR = this.r0;
        // calculate distance between circle centers
        // pow(a,0.5d)??? onko sama kuin sqrt
        //double d = Math.pow(Math.pow(y1 - x0, 2.0D) + Math.pow(x1 - x0, 2.0D), 0.5D);
        /*
        double d = Math.sqrt(Math.pow(y1 - x0, 2.0D) + Math.pow(x1 - x0, 2.0D));
        
        // check if circles intersect
        if (!checkIntersectingCircle(sec)) {
        //JOptionPane.showMessageDialog(null, "Circles are not intersecting check your settings", "Not intersecting", 1);
        } else {
            double a = (Math.pow(r0, 2.0D) - Math.pow(r1, 2.0D) + Math.pow(d, 2.0D)) / (2.0D * d);
            //double h = Math.pow(Math.pow(r0, 2.0D) - Math.pow(a, 2.0D), 0.5D);
            double h = Math.sqrt(Math.pow(r0, 2.0D) - Math.pow(a, 2.0D));
            
            double x2 = x0 + a / d * (x1 - x0);
            double y2 = y0 + a / d * (y1 - y0);
            
            double x3a = x2 + h / d * (y1 - y0);
            double y3a = y2 - h / d * (x1 - x0);
            
            double x3b = x2 - h / d * (y1 - y0);
            double y3b = y2 + h / d * (x1 - x0);
            returnArray[0] = x3a;
            returnArray[1] = y3a;
            returnArray[2] = x3b;
            returnArray[3] = y3b;
        }
*/
        return returnArray;
    }

    /** Function check if two circles are intercepting each others
     * 
     * @param sec circle to ceck
     * @return true if circles intersect
     * 
     */
    
    public boolean checkIntersectingCircle(Circle sec) {
        double x1 = sec.getX();
        double y1 = sec.getY();
        double r1 = sec.getR();
        boolean intersecting = false;

        // calculate distance between center points
        //double d = Math.pow(Math.pow(y1 - y0, 2.0D) + Math.pow(x1 - x0, 2.0D), 0.5D);
        double d = Math.sqrt(Math.pow(y1 - y0, 2.0D) + Math.pow(x1 - x0, 2.0D));

        if (d < Math.abs(r0 - r1)) {
            intersecting = false;
        } else {
            if (d > r0 + r1) {
                intersecting = false;
            } else {
                if (((d == 0.0D ? 1 : 0) & (r0 == r1 ? 1 : 0)) != 0) {
                    intersecting = false;
                } else {
                    intersecting = true;
                }
            }
        }
        return intersecting;
    }
}
