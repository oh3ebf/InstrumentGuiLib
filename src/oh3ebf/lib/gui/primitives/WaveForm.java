/***********************************************************
 * Software: instrument gui library
 * Module:   waveform drawing class
 * Version:  0.1
 * Licence:  GPL2
 *
 * Owner: Kim Kristo
 * Date creation : 7.9.2012
 *
 ***********************************************************/
package oh3ebf.lib.gui.primitives;

import oh3ebf.lib.gui.models.DefaultWaveModel;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.Vector;
import oh3ebf.lib.gui.interfaces.DataTrackingInterface;

public class WaveForm extends DrawingObject implements DataTrackingInterface {

    protected DefaultWaveModel waveModel = null;
    protected int offset = 0;
    protected int drawingOffset = 0;
    protected GndSymbol gnd = null;
    protected String name = null;
    protected Vector<Point> dataPoints;

    /** Creates a new instance of WaveForm */
    public WaveForm(String name) {
        // set name for this waveform
        this.name = name;
    }

    /** Function add new wave model to waveform
     *
     * @param model new waveform data model
     */
    public void addWaveModel(DefaultWaveModel model) {
        waveModel = model;
    }

    /** Function return waveform data model in use
     *
     * @return data model
     */
    public DefaultWaveModel getWaveModel() {
        return (waveModel);
    }

    /** Function add zero level symbol to waveform
     *
     * @param symbol to show zero level
     *
     */
    public void addZeroLevelSymbol(GndSymbol symbol) {
        gnd = symbol;
    }

    /** Function returns waveform location on screen in pixels
     *
     * @return offset in pixels
     */
    public int getDrawingOffset() {
        return drawingOffset;
    }

    /** Function sets waveform location on screen
     *
     * @param offset new offset calculated from zero level 
     *
     */
    public void setDrawingOffset(int offset) {
        this.offset = offset;
    }

    /** Function returns waveform value at given point
     *
     * @return waveform value as integer
     * 
     */
    @Override
    public int getCurrentValue(int position) {
        Point p = null, p_next = null;
        int y = 0;

        try {
            // get visible data points
            Enumeration e = dataPoints.elements();
            p = (Point) e.nextElement();

            while (e.hasMoreElements()) {
                // get next point
                p_next = (Point) e.nextElement();
                // find point relative to cursor location 
                if (p_next.x > position) {
                    break;
                }

                p = p_next;
            }
        } catch (NoSuchElementException e) {
        // TODO tässä pitäisi heittää herkut
        }

        // interpolate posiotion from data point to cursor location
        y = p.y + ((p_next.y - p.y) / (p_next.x - p.x) * (position - p.x));

        //System.out.println("py: "+ p.y +" py+1: " + p_next.y + " px: " + p.x + " px+1: " + p_next.x+ " pos: " + position);
        return (y);
    }

    /** Function sets component size
     *
     * @param size component size in dimension format
     *
     */
    @Override
    public void setSize(Dimension size) {
        super.setSize(size);

        // update view area size
        setSize(size.width, size.height);
    }

    /** Function sets component size
     *
     * @param width component total width
     * @param heigth component total heigth
     *
     */
    @Override
    public void setSize(int width, int heigth) {
        super.setSize(width, heigth);

        // check valid object
        if (waveModel != null) {
            // update view area size
            waveModel.setViewSpan(componentSize.width - insets.left - insets.right);
            waveModel.getVerticalScale().updateScale(componentSize.height - insets.top - insets.bottom);
            waveModel.getHorizontalScale().updateScale(componentSize.width - insets.left - insets.right);
        }
    }

    /** Function implement drawing algorithm for waveform 
     *
     * @param g graphics object to paint
     */
    @Override
    public void draw(Graphics2D g) {
        Point p = null, p_old = null;

        // is this object currently drawn
        if (visible) {
            // calculate wave position
            drawingOffset = (componentSize.height / 2) - offset;

            // check if gnd level is in use
            if (gnd != null) {
                // draw gnd symbol
                gnd.setPosition(componentSize.width - insets.right, drawingOffset);
                gnd.draw(g);
            }

            // set current drawing color
            g.setColor(penColor);

            // pitäisi osata piirtää jatkuvaviiva, sin x/x, pisteet, mitä muuta
            // miten hoidetaan kumulatiivinen piirto???
            // käyttää malliin tallennettua piirtodataa

            try {
                dataPoints = waveModel.getScaledWaveForm();
                Enumeration e = dataPoints.elements();

                while (e.hasMoreElements()) {
                    p = (Point) e.nextElement();

                    if (p_old == null) {
                        g.drawLine(p.x + insets.left, p.y + drawingOffset, p.x + insets.left, p.y + drawingOffset);
                    } else {

                        g.drawLine(p_old.x + insets.left, p_old.y + drawingOffset, p.x + insets.left, p.y + drawingOffset);
                    }

                    p_old = p;
                }
            } catch (Exception e) {
                // TODO tässä pitäisi heittää herkut
                System.out.println(e.getMessage());
            }
        }
    }
}
