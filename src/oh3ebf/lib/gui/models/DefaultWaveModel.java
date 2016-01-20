/***********************************************************
 * Software: instrument gui library
 * Module:   wave model base class
 * Version:  0.1
 * Licence:  GPL2
 *
 * Owner: Kim Kristo
 * Date creation : 7.9.2012
 *
 ***********************************************************/
package oh3ebf.lib.gui.models;

import java.awt.Point;
import java.util.Vector;
import oh3ebf.lib.gui.interfaces.WaveformDataInterface;
import oh3ebf.lib.common.exceptions.genericException;

public class DefaultWaveModel implements WaveformDataInterface {

    protected int channel = 0;                      /* srorage channel */
    protected Vector<Double> data;                  /* data container */
    protected ScaleBase verticalScale = null;       /* vertical scaling object */
    protected ScaleBase horizontalScale = null;     /* horizontal scaling object */
    protected int viewSpan = 100;                   /* number of points in view */
    protected int viewOffset = 0;                   /* data showing stating point */
    
    
    /** Creates a new instance of DefaultWaveModel */
    public DefaultWaveModel(int ch) {
        channel = ch;
        data = new Vector<Double>();
    }

    /** Function updates measured data 
     * 
     * @param b byte array of new data
     */
    @Override
    public void updateData(byte b[]) {
        int j = 0;
        
        // remove old dataset
        data.clear();

        for (int i = 0; i < b.length; i++) {
            // add data to buffer
            data.add((double) b[i]);
        }
    }

    /** Function updates measured data 
     * 
     * @param b integer array of new data
     */
    @Override
    public void updateData(int b[]) {
        int j = 0;
        
        // remove old dataset
        data.clear();

        for (int i = 0; i < b.length; i++) {
            // add data to buffer
            data.add((double) b[i]);
        }
    }
    
    /** Function updates measured data 
     * 
     * @param b double array of new data
     */
    @Override
    public void updateData(double b[]) {
        int j = 0;
        
        // remove old dataset
        data.clear();

        for (int i = 0; i < b.length; i++) {
            // add data to buffer
            data.add(b[i]);
        }
    }
    
    /** Function adds single byte to result set
     *
     * @param b byte to add
     *
     */
    public int add(byte b) {
        data.add((double) b);

        return (data.size());
    }

    /** Function adds byte array to result set
     *
     * @param b byte array to add
     *
     */
    public int add(byte b[]) {
        for (int i = 0; i < b.length; i++) {
            data.add((double) b[i]);
        }

        return (data.size());
    }

    /** Function adds integer to result set
     *
     * @param d int to add
     *
     */
    public int add(int d) {
        data.add((double)d);

        return (data.size());
    }
    
    /** Function adds byte array to result set
     *
     * @param d integer array to add
     *
     */
    public int add(int d[]) {
        for (int i = 0; i < d.length; i++) {
            data.add((double)d[i]);
        }

        return (data.size());
    }
    
    /** Function adds integer to result set
     *
     * @param d int to add
     *
     */
    public int add(double d) {        
        data.add(d);

        return (data.size());
    }
    
    /** Function adds byte array to result set
     *
     * @param d integer array to add
     *
     */
    public int add(double d[]) {
        for (int i = 0; i < d.length; i++) {
            data.add(d[i]);
        }

        return (data.size());
    }
    
    /** Function returns wave data 
     * 
     * @return data as double vector
     */
    
    public Vector<Double> getData() {
        return(data);
    }
    
    /** Function adds scaling model to wave data
     *
     * @param s scaling object
     *
     */
    public void addVerticalScale(ScaleBase s) {
        verticalScale = s;
    }

    /** Function adds scaling model to wave data
     *
     * @param s scaling object
     *
     */
    public void addHorizontalScale(ScaleBase s) {
        horizontalScale = s;
    }

    /** Function returns current view port size
     *
     * @return view port size
     * 
     */
    public int getViewSpan() {
        return viewSpan;
    }

    /** Function sets new view port size
     *
     * @param viewSpan new viewport size
     *
     */
    public void setViewSpan(int viewSpan) {
        this.viewSpan = viewSpan;
    }

    /** Function returns current view offset position to data
     *
     *
     */
    public int getViewOffset() {
        return viewOffset;
    }

    /** Function sets data view offset
     *
     * @param viewOffset new point where data is drawn
     *
     */
    public void setViewOffset(int viewOffset) {
        this.viewOffset = viewOffset;
    }

    /** Function returns current vertical scale
     *
     * @return current vertical scale
     */
    public ScaleBase getVerticalScale() {
        return (verticalScale);
    }

    /** Function returns current horizontal scale
     *
     * @return current horizontal scale
     */
    public ScaleBase getHorizontalScale() {
        return (horizontalScale);
    }

    /** Function return current raw data beginning at viewOffset
     *
     * @throws genericException
     * @return raw data beginning at viewOffset
     */
    public Vector<Point> getRawWaveForm() throws genericException {
        Vector<Point> dataPoints = new Vector<Point>();

        /* check data existence */
        if (data == null) {
            throw new genericException(1, "DefaultWaveModel", "getRawWaveForm", "data missing", null);
        }

        // get time scale
        double pix = horizontalScale.getScale();

        for (int i = 0; i < viewSpan; i++) {
            // get data values starting from offset             
            dataPoints.add(new Point((int) (pix * i), (int)Math.round(data.get(viewOffset + i))));
        }

        return (dataPoints);
    }

    /** Function return current raw data beginning at viewOffset
     *
     * @throws genericException
     *
     */
    public Vector<Point> getScaledWaveForm() throws genericException {
        Vector<Point> scaledPoints = new Vector<Point>();
        Vector<Integer> scaledValues = new Vector<Integer>();

        /* check data existence */
        if (data == null) {
            throw new genericException(1, "DefaultWaveModel", "getScaledWaveForm", "data missing", null);
        }

        /* check data existence */
        if (verticalScale == null) {
            throw new genericException(1, "DefaultWaveModel", "getScaledWaveForm", "scale missing", null);
        }

        // set data to requested scale
        scaledValues = verticalScale.getScaledValues(data);
        double pix = horizontalScale.getScale();

        // tässä pitäisi palauttaa asetettu määrä pisteitä alkaen paikasta offset
        for (int i = 0; (i * pix) < viewSpan; i++) {
            // TODO tähän parempi ehto tai toiminta
            if (i == scaledValues.size()) {
                return (scaledPoints);
            }

            // get data values starting from offset
            int y = scaledValues.get(viewOffset + i);
            scaledPoints.add(new Point((int) (pix * i), y));
            //System.out.println("x: " +  i + " y: " + y);
        }

        //System.out.println(" span: " + viewSpan);
        return (scaledPoints);
    }
}
