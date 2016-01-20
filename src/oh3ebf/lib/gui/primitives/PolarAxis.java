/***********************************************************
 * Software: instrument gui library
 * Module:   Axis base class
 * Version:  0.1
 * Licence:  GPL2
 *
 * Owner: Kim Kristo
 * Date creation : 4.9.2012
 *
 ***********************************************************/
package oh3ebf.lib.gui.primitives;

import java.awt.Color;
import java.awt.Graphics2D;

public class PolarAxis extends Axis {

    protected double unityXscaler = 0.0D;
    protected double unityYscaler = 0.0D;
    protected double scaleFactor = 0.2D;

    /** Creates a new instance of Axis */
    public PolarAxis() {
    }

    public PolarAxis(int position) {
        this.position = position;
    }

    public PolarAxis(int position, Color c) {
        this.position = position;
        penColor = c;
    }

    public PolarAxis(int position, Color c, int grid) {
        this.position = position;
        penColor = c;
        gridType = grid;
    }

    /** Function implement drawing algorithm for coodinate system
     *
     * @param g graphics object to paint
     */
    @Override
    public void draw(Graphics2D g) {
        int i = 0;
        int count = 0;

        System.out.println(" x " + (componentSize.width - insets.left - insets.right));
        System.out.println(" y " + (componentSize.height - insets.top - insets.bottom));
        midX = (componentSize.width - insets.left - insets.right) / 2 + insets.left;
        midY = (componentSize.height - insets.top - insets.bottom) / 2 + insets.top;

        // set current drawing color
        g.setColor(penColor);

        // parameters for axis
        x1 = 0;
        x2 = componentSize.width;
        y1 = 0;
        y2 = componentSize.height;
        unityXscaler = 1.0D / ((componentSize.width - insets.left - insets.right) / 2);
        unityYscaler = 1.0D / ((componentSize.height - insets.top - insets.bottom) / 2);

        // grid line count
        //count = (componentSize.width - insets.left - insets.right) / scaleDiv;

        // draw solid x axis line
        g.drawLine(x1 + insets.left, midY, x2 - insets.right, midY);

        // draw solid y axis line
        g.drawLine(midX, y1 + insets.top, midX, y2 - insets.bottom);

        // draw grid if active
        if (drawGrid) {
            // draw drid circles
            for (double d = scaleFactor; d <= 1.0D; d += scaleFactor) {
                drawCircle(g, d, d);
            }
        }

        // draw scales if active
        if (drawScale) {
        /*
        switch (position) {
        case AXIS_Y:
        drawScaleY(g, count);
        break;
        case AXIS_X:
        break;
        }
        }*/
        }
    }

    /** Function draws circle from origo
     * 
     * @param g grachics to draw
     * @param w circle width at unity
     * @param h circle width at unity
     */
    
    private void drawCircle(Graphics2D g, double w, double h) {
        // calculate circle size
        int wNormal = (int) (w / unityXscaler);
        int hNormal = (int) (h / unityYscaler);

        // draw circle, origo at midX,midY
        g.drawArc(midX - wNormal, midY - hNormal, wNormal * 2, hNormal * 2, 0, 360);
    }

    /** Function draws Y scales
     * 
     * @param g graphics to draw
     * @param p1 beginnin point
     * @param count number of grid lines
     */
    private void drawScaleY(Graphics2D g, int count) {
        int i = 0, n = 1;
        int gridB = midY;
        int gridT = midY;

        // calculate increment value
        int step = (scaleYMax - scaleYMin) / ((count / scaleDiv) * 2);

        // set initial values for upper and lover part scale counters
        int valueUp = (scaleYMin - scaleYMax) / 2;
        int valueDown = (scaleYMin - scaleYMax) / 2;

        // draw middle scale value
        g.drawString((scaleYMin - scaleYMax) / 2 + unit, 0, gridB);

        for (i = 1; i < count; i++) {
            // calculate next scale grid values
            gridB -= scaleDiv;
            gridT += scaleDiv;

            // draw only inside of insets
            if (gridT > insets.top || gridB < insets.bottom) {
                // draw scale value only when solid line is drawn
                if (n == 0) {
                    // calculate next scale values
                    valueUp -= step;
                    valueDown += step;

                    // draw scale values
                    g.drawString(valueDown + unit, 5, gridB);
                    g.drawString(valueUp + unit, 5, gridT);
                }
            }
            // offset counter
            if (n == gridOffset) {
                n = 0;
            } else {
                n++;
            }
        }
    }
}
