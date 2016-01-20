/***********************************************************
 * Software: instrument gui library
 * Module:   Smith chart drawing class
 * Version:  0.1
 * Licence:  GPL2
 *
 * Owner: Kim Kristo
 * Date creation : 4.10.2013
 *
 ***********************************************************/
package oh3ebf.lib.gui.primitives;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public class SmithChartGrid extends DrawingObject {

    private Ellipse2D ell_outer = new Ellipse2D.Float();
    private Circle outer = new RealCircle(0.0D);
    private double scale = 200.0D;
    private double offsetX = 50.0D;
    private double offsetY = 50.0D;
    private double Z0 = 50.0D;
    private static Font labelFont = new Font("Tahoma", 0, 11);

    public SmithChartGrid() {
    }

    /** Function returns current scaling factor
     * 
     * @return scale
     */
    
    public double getScale() {
        return scale;
    }

    /** Function sets new scaling factor
     * 
     * @param scale to set
     */
    
    public void setScale(double scale) {
        this.scale = scale;
    }

    /** Function returns current x offset
     * 
     * @return x offset
     */
    
    public double getOffsetX() {
        return offsetX;
    }

    /** Function sets new x offset
     * 
     * @param x offset
     */
    
    public void setOffsetX(double x) {
        offsetX = x;
    }

    /** Function returns current y offste
     * 
     * @return y offset
     */
    
    public double getOffsetY() {
        return offsetY;
    }

    /** Function sets new y offset
     * 
     * @param y offset
     */
    
    public void setOffsetY(double y) {
        offsetY = y;
    }

    /** Function implements drawing  
     *
     * @param g graphics from object to draw
     *
     */
    
    @Override
    public void draw(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        AffineTransform at = new AffineTransform();
        at.setToTranslation(scale + offsetX, scale + offsetY);
        g.setTransform(at);

        generateGrid(g);
    }

    /** Function implement drawing algorithm for coordinate system
     *
     * @param g graphics object to paint
     */
    
    private void generateGrid(Graphics2D g) {
        double[] values1 = {0.0D, 0.2D, 0.5D, 1.0D, 2.0D, 5.0D};
        double[] mainGrid = {0.0D, 0.05D, 0.1D, 0.15D, 0.2D};
        double[] mainGrid2 = {0.0D, 0.1D, 0.2D, 0.3D, 0.4D, 0.5D};
        double[] mainGrid3 = {0.0D, 0.1D, 0.2D, 0.3D, 0.4D, 0.5D, 0.6D, 0.7D, 0.8D, 0.9D, 1.0D};
        double[] mainGrid4 = {0.0D, 0.2D, 0.4D, 0.6D, 0.8D, 1.0D, 1.2D, 1.4D, 1.6D, 1.8D, 2.0D};
        double[] mainGrid5 = {0.0D, 1.0D, 2.0D, 3.0D, 4.0D, 5.0D};
        double[] mainGrid6 = {0.0D, 5.0D, 10.0D, 20.0D, 50.0D};
        ArrayList labels = new ArrayList();

        drawPartOfGrid(mainGrid, g);
        drawPartOfGrid(mainGrid2, g);
        drawPartOfGrid(mainGrid3, g);
        drawPartOfGrid(mainGrid4, g);
        drawPartOfGrid(mainGrid5, g);
        drawPartOfGrid(mainGrid6, g);

        g.setClip(null);

        ell_outer = outer.getEllipse(scale);
        // ulkokehän piirto
        outer.drawCircle(g, scale);
        // vaakaviivan piirto
        g.drawLine((int) Math.round(-scale), 0, (int) Math.round(scale), 0);

        g.setClip(this.ell_outer);

        //Circle c50 = new RealCircle(50.0D);
        //c50.drawCircle(g, scale);

        //Circle cp50j = new ImaginaryCircle(50.0D);
        //cp50j.drawCircle(g, scale);

        //Circle cn50j = new ImaginaryCircle(-50.0D);
        //cn50j.drawCircle(g, scale);

        g.setClip(null);

        g.setColor(Color.GRAY);
        g.setFont(labelFont);

        for (int i = 0; i < values1.length; i++) {
            labels.add(new SmithLabel(Double.toString(values1[i]), values1[i], 0.0D));
            labels.add(new SmithLabel(Double.toString(values1[i]), 0.0D, values1[i]));

            if (values1[i] != 0.0D) {
                labels.add(new SmithLabel("-" + Double.toString(values1[i]), 0.0D, -values1[i]));
            }
        }

        for (int i = 0; i < labels.size(); i++) {
            SmithLabel tmp = (SmithLabel) labels.get(i);
            tmp.drawLabel(g, scale, Z0);
        }

    }

    /** Function draws real and imaginary part circles defined in given table
     * 
     * @param mainGrid table of circles to draw
     * @param g graphics to draw
     */
    private void drawPartOfGrid(double[] mainGrid, Graphics2D g) {
        Ellipse2D leftReal = new Ellipse2D.Float();

        // get left bordering circle
        Circle leftRealBorder = new RealCircle(mainGrid[0]);

        // set framing rectangle to define circle at start of table values on real axis
        leftReal.setFrame((int) Math.round(-scale * leftRealBorder.getR()),
                (int) Math.round(-scale * leftRealBorder.getR()),
                (int) Math.round(scale * 2.0D * leftRealBorder.getR()),
                (int) Math.round(scale * 2.0D * leftRealBorder.getR()));

        // create area from circle
        Area area_leftRealBorder = new Area(leftReal);

        Ellipse2D rightReal = new Ellipse2D.Float();

        // get index to end of defining table
        int indexMainGrid = mainGrid.length - 1;

        // get rigth bordering circle
        Circle rightRealBorder = new RealCircle(mainGrid[indexMainGrid]);

        // set framing rectangle to define circle at end of table values on real axis
        rightReal.setFrame((int) Math.round(-(2.0D * rightRealBorder.getR() - 1.0D) * scale),
                (int) Math.round(-rightRealBorder.getR() * scale),
                (int) Math.round(rightRealBorder.getR() * 2.0D * scale),
                (int) Math.round(rightRealBorder.getR() * 2.0D * this.scale));

        // create area from circle
        Area area_rightReBorder = new Area(rightReal);

        Ellipse2D positiveImagenary = new Ellipse2D.Float();

        // get bordering circle on positive imaginary side
        Circle positiveImagenaryBorder = new ImaginaryCircle(mainGrid[indexMainGrid]);

        // set framing rectangle to define circle on positive imaginary side
        positiveImagenary.setFrame((int) Math.round(scale * (1.0D - positiveImagenaryBorder.getR())),
                (int) Math.round(-2.0D * scale * positiveImagenaryBorder.getR()),
                (int) Math.round(2.0D * scale * positiveImagenaryBorder.getR()),
                (int) Math.round(2.0D * scale * positiveImagenaryBorder.getR()));

        // create area from circle
        Area area_positiveImagenary = new Area(positiveImagenary);

        Ellipse2D negativeImagenary = new Ellipse2D.Float();

        // get bordering circle on negative imaginary side
        Circle negativeImagenaryBorder = new ImaginaryCircle(-mainGrid[indexMainGrid]);

        // set framing rectangle to define circle on negative imaginary side
        negativeImagenary.setFrame((int) Math.round(scale * (1.0D - negativeImagenaryBorder.getR())), 0.0D,
                (int) Math.round(2.0D * scale * negativeImagenaryBorder.getR()),
                (int) Math.round(2.0D * scale * negativeImagenaryBorder.getR()));

        // create area from circle
        Area area_negativeImagenary = new Area(negativeImagenary);

        // create clones for area clip calculation
        Area zone1 = (Area) area_leftRealBorder.clone();
        Area zone2 = (Area) area_leftRealBorder.clone();

        // calculate upper and lover clipping areas
        zone1.subtract(area_positiveImagenary);
        zone1.subtract(area_negativeImagenary);

        // calculate rigth clipping area
        zone2.subtract(area_rightReBorder);

        // set clipping area on left side tarvitaanko tätä...
        //g.setClip(area_leftRealBorder);

        g.setColor(Color.BLACK);

        // preserve space for real circles 
        Circle[] reCircles = new Circle[mainGrid.length];

        // preserve space for imaginary circles 
        Circle[] posCircles = new Circle[mainGrid.length];
        Circle[] negCircles = new Circle[mainGrid.length];

        // set imaginary clipping to drawing component
        g.setClip(zone1);

        // draw real circles
        for (int i = 1; i < mainGrid.length; i++) {
            reCircles[i] = new RealCircle(mainGrid[i]);
            reCircles[i].drawCircle(g, scale);
        }

        // set real clipping on drawing component
        g.setClip(zone2);

        // draw imagenary circles
        for (int i = 0; i < mainGrid.length; i++) {
            if (mainGrid[i] != 0.0D) {
                posCircles[i] = new ImaginaryCircle(mainGrid[i]);
                negCircles[i] = new ImaginaryCircle(-mainGrid[i]);
                posCircles[i].drawCircle(g, scale);
                negCircles[i].drawCircle(g, scale);
            }
        }
    }
}
