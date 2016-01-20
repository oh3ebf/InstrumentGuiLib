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

public class Axis extends DrawingObject {

    public static final int AXIS_X = 0;
    public static final int AXIS_Y = 1;
    public static final int GRID_SOLID = 0;
    public static final int GRID_SCOPE = 1;
    protected int scaleDiv = 10;
    protected int midX = 0,  midY = 0;
    protected boolean drawGrid = false;
    protected boolean drawScale = false;
    protected boolean drawBorders = false;
    protected int gridType = GRID_SCOPE;
    protected int x1 = 0,  x2 = 0,  y1 = 0,  y2 = 0;
    protected int position = AXIS_X;
    protected int gridOffset = 4;
    protected int scaleYMin = 0;
    protected int scaleYMax = 0;
    protected int scaleXMin = 0;
    protected int scaleXMax = 0;
    protected String unit = "";

    /** Creates a new instance of Axis */
    public Axis() {
    }

    public Axis(int position) {
        this.position = position;
    }

    public Axis(int position, Color c) {
        this.position = position;
        penColor = c;
    }

    public Axis(int position, Color c, int grid) {
        this.position = position;
        penColor = c;
        gridType = grid;
    }

    /** Function sets current grid drawing state
     *
     * @param state new boolean state
     *
     */
    public void setGridState(boolean state) {
        drawGrid = state;
    }

    /** Function sets current scale drawing state
     *
     * @param state new boolean state
     *
     */
    public void setScaleState(boolean state) {
        drawScale = state;
    }

    /** Function sets current borders drawing state
     *
     * @param state new boolean state
     *
     */
    public void setDrawBorders(boolean state) {
        drawBorders = state;
    }

    /** Function sets drawing direction for axis
     *
     * @param position drawing direction
     *
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /** Function set scale to Y axis
     * 
     * @param min of scale
     * @param max of scale
     * 
     */
    public void setXScale(int min, int max) {
        scaleYMin = min;
        scaleYMax = max;
    }

    /** Function set scale to Y axis
     * 
     * @param min of scale
     * @param max of scale
     * 
     */
    public void setYScale(int min, int max) {
        scaleYMin = min;
        scaleYMax = max;
    }

    /** Function implement drawing algorithm for coodinate system
     *
     * @param g graphics object to paint
     */
    @Override
    public void draw(Graphics2D g) {
        int i = 0;
        int count = 0;
        int height = componentSize.height;
        int width = componentSize.width;

        System.out.println(" x " + (componentSize.width - insets.left - insets.right));
        System.out.println(" y " + (componentSize.height - insets.top - insets.bottom));
        midX = (componentSize.width - insets.left - insets.right) / 2 + insets.left;
        midY = (componentSize.height - insets.top - insets.bottom) / 2 + insets.top;

        // set current drawing color
        g.setColor(penColor);

        switch (position) {
            case AXIS_X:
                // parameters for y axis
                x1 = 0;
                x2 = componentSize.width;
                y1 = midY;
                y2 = midY;

                // grid line count
                count = (componentSize.width - insets.left - insets.right) / scaleDiv;

                // draw solid axis line
                g.drawLine(x1 + insets.left, y1, x2 - insets.right, y2);

                break;
            case AXIS_Y:
                //parameters for x axis
                x1 = midX;
                x2 = midX;
                y1 = 0;
                y2 = componentSize.height;

                // grid line count
                count = (componentSize.height - insets.top - insets.bottom) / scaleDiv;

                // draw solid axis line
                g.drawLine(x1, y1 + insets.top, x2, y2 - insets.bottom);

                break;
        }

        // draw grid if active
        if (drawGrid) {
            switch (position) {
                case AXIS_Y:
                    // draw borders
                    if (drawBorders) {
                        // draw grid line
                        g.drawLine(insets.left, insets.top, insets.left, height - insets.bottom);
                        g.drawLine(width - insets.right, insets.top,
                                width - insets.right, height - insets.bottom);
                    }

                    if (drawGrid) {
                        // draw x grid
                        drawGridScopeHorizontal(g, height, count);
                    }
                    break;
                case AXIS_X:
                    // draw borders
                    if (drawBorders) {
                        // draw grid line
                        g.drawLine(insets.left, insets.top, width - insets.right, insets.top);
                        g.drawLine(insets.left, height - insets.bottom,
                                width - insets.right, height - insets.bottom);
                    }

                    if (drawGrid) {
                        // draw y grid
                        drawGridScopeVertical(g, count);
                    }
                    break;
            }

            // draw scales if active
            if (drawScale) {
                switch (position) {
                    case AXIS_Y:
                        drawScaleY(g, count);
                        break;
                    case AXIS_X:

                        break;
                }
            }
        }
    }

    /** Function draws horizontal grid
     * 
     * @param g graphics to draw
     * @param p1 beginnin point
     * @param p2 beginnin point
     * @param h heihgt of grid
     * @param count number of grid lines
     * 
     */
    private void drawGridScopeHorizontal(Graphics2D g, int h, int count) {
        int i = 0, n = 1;
        int grid1 = midY;
        int grid2 = midY;

        for (i = 0; i < count / 2; i++) {
            // draw horizontal lines
            grid1 -= scaleDiv;
            grid2 += scaleDiv;
            //System.out.println("count " + count);
            if (grid1 > insets.top || grid2 < insets.bottom) {

                if (n == 0) {
                    if ((grid1 > (insets.top + scaleDiv * gridOffset) &&
                            (grid2 < (componentSize.height - insets.bottom - scaleDiv * gridOffset)))) {
                        // draw solid line
                        g.drawLine(insets.left, grid1, componentSize.width - insets.right, grid1);
                        g.drawLine(insets.left, grid2, componentSize.width - insets.right, grid2);
                    }
                } else {
                    // draw grid line left
                    g.drawLine(insets.left, grid1, insets.left + 3, grid1);
                    g.drawLine(insets.left, grid2, insets.left + 3, grid2);
                    // draw grid line rigth
                    g.drawLine(componentSize.width - insets.right, grid1, componentSize.width - insets.right - 3, grid1);
                    g.drawLine(componentSize.width - insets.right, grid2, componentSize.width - insets.right - 3, grid2);
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

    /** Function draws vertical grid
     * 
     * @param g graphics to draw
     * @param p1 beginnin point
     * @param p2 beginnin point
     * @param w width of grid
     * @param count number of grid lines
     * 
     */
    private void drawGridScopeVertical(Graphics2D g, int count) {
        int i = 0, n = 1;
        int grid1 = midX;
        int grid2 = midX;

        for (i = 0; i < count / 2; i++) {
            // draw vertical lines
            grid1 -= scaleDiv;
            grid2 += scaleDiv;

            // draw grid line
            if (grid1 > insets.left || grid2 < insets.right) {

                if (n == 0) {
                    if ((grid1 > (insets.left + scaleDiv * gridOffset)) &&
                            (grid2 < (componentSize.width - insets.right - scaleDiv * gridOffset))) {
                        // draw solid line
                        g.drawLine(grid1, insets.top, grid1, componentSize.height - insets.bottom);
                        g.drawLine(grid2, insets.top, grid2, componentSize.height - insets.bottom);
                    }
                } else {
                    // draw grid line top
                    g.drawLine(grid1, insets.top, grid1, insets.top + 3);
                    g.drawLine(grid2, insets.top, grid2, insets.top + 3);
                    // draw grid line bottom
                    g.drawLine(grid1, componentSize.height - insets.bottom, grid1, componentSize.height - insets.bottom - 3);
                    g.drawLine(grid2, componentSize.height - insets.bottom, grid2, componentSize.height - insets.bottom - 3);
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
