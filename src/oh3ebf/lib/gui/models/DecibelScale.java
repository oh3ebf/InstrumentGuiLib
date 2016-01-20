/***********************************************************
 * Software: instrument gui library
 * Module:   decibel scale class
 * Version:  0.1
 * Licence:  GPL2
 *
 * Owner: Kim Kristo
 * Date creation : 6.9.2013
 *
 ***********************************************************/
package oh3ebf.lib.gui.models;

import java.util.Iterator;
import java.util.Vector;

public class DecibelScale extends ScaleBase {
    protected double minValue = 0.0D;
    protected double maxValue = 1.0D;
    protected double pixHeight = 1.0D;

    /** Creates a new instance of DecibelScale */
    public DecibelScale(double pix) {
        pix = 1.0D;
    }

    /** Creates a new instance of DecibelScale */
    public DecibelScale(double scale, double min, double max) {
        // set scale variables
        minValue = min;
        maxValue = max;

        // calculate how meny pixels per sample is needed
        pix = scale / Math.abs(maxValue - minValue);
    }

    /** Function sets new decibel scale min value
     *
     * @param min set new decibel scale minimum value
     *
     */
    public void setDecibelScaleMin(double min) {
        // set scale variables
        minValue = min;

        // calculate how meny pixels per sample is needed
        pix = pixHeight / Math.abs(maxValue - minValue);
    }

    /** Function sets new decibel scale max value
     *
     * @param max set new decibel scale maximum value
     *
     */
    public void setDecibelScaleMax(double max) {
        // set scale variables
        minValue = max;

        // calculate how meny pixels per sample is needed
        pix = pixHeight / Math.abs(maxValue - minValue);
    }

    /** Function updates scaling when heigth changes
     * 
     * @param scale new value
     */
    @Override
    public void updateScale(int scale) {
        // calculate how meny pixels per sample is needed
        pix = scale / Math.abs(maxValue - minValue);
        System.out.println("de pix " + pix);
    }

    /** Function returns scaled values of vector data
     * 
     * @param b vector to scale
     * @return scaled vector
     * 
     */
    @Override
    public Vector<Integer> getScaledValues(Vector<Double> b) {
        Vector<Integer> scaledData = new Vector<Integer>();
        double d = 0.0D;

        Iterator i = b.iterator();
        while (i.hasNext()) {

            d = -((Double) i.next()).doubleValue() * pix;
            scaledData.add((int) Math.round(d));
        }

        return (scaledData);
    }
}
