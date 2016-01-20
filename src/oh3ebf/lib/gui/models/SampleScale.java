/***********************************************************
 * Software: instrument gui library
 * Module:   sample scale class
 * Version:  0.1
 * Licence:  GPL2
 *
 * Owner: Kim Kristo
 * Date creation : 26.9.2013
 *
 ***********************************************************/
package oh3ebf.lib.gui.models;

public class SampleScale extends ScaleBase {

    protected double samples = 1.0D;
    protected double pixWidth = 1.0D;
    protected double unitsScale = 1.0D;
    
    public SampleScale() {
        super();        
    }

    /** Creates a new instance of SampleScale */
    public SampleScale(double width, double s) {
        super();

        pixWidth = width;
        samples = s;
        
        updateVariables();
    }

        /** Creates a new instance of SampleScale */
    public SampleScale(double width, double s, double unit) {
        super();

        pixWidth = width;
        samples = s;
        unitsScale = unit;
        updateVariables();
    }
    
    /** Function sets new samples value
     *
     * @param s number of samples
     *
     */
    public void setSampleScale(double s) {        
        samples = s;   
        
        updateVariables();
    }

    /** Function sets new unit scale
     *
     * @param s full scale value
     *
     */
    public void setUnitScale(double s) {        
        unitsScale = s;   
        
        //updateVariables();
    }

    /** Function converts pixels count to basic unit value
     * 
     * @param pixels to convert
     * @return basic unit value
     */
    
    public double getScaledUnits(int pixels) {
        return((unitsScale / pixWidth) * pixels);
    }
    
    /** Function updates scaling when width changes
     * 
     * @param width
     */
    
    @Override
    public void updateScale(int width) {
        pixWidth = width;
        
        updateVariables();
    }
    
    /** Function calculates pix value
     * 
     */
    private void updateVariables() {
        // calculate how meny pixels per sample is needed
        pix = pixWidth / samples;
        //System.out.println("fr pix " + pix);
    }
}
