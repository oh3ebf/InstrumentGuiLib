/***********************************************************
 * Software: instrument gui library
 * Module:   voltage scale class
 * Version:  0.1
 * Licence:  GPL2
 *
 * Owner: Kim Kristo
 * Date creation : 13.9.2012
 *
 ***********************************************************/

package oh3ebf.lib.gui.models;

public class VoltageScale extends ScaleBase {
    protected double voltageScale = 1.0D;   
    protected double scale = 1.0D;
    
    /** Creates a new instance of VoltageScale */
    public VoltageScale() {
        super();
    }
       
    /** Function updates scaling when heigth changes
     * 
     * @param scale
     */
    @Override
    public void updateScale(int scale) {
        this.scale = scale;
        
        // calculate how meny pixels per sample is needed
        pix = scale / voltageScale;
        //System.out.println("pix " + pix);
    }
    
    /** Function returns display voltage scale in use
     * 
     * @return display voltage scale in use
     * 
     */
    
    public double getVoltageScale() {
        return voltageScale;
    }

    /** Function sets display voltage scale and updates scaling factor
     *
     * @param voltageScale new full scale value
     *
     */
    
    public void setVoltageScale(double voltageScale) {
        this.voltageScale = voltageScale;
        
        // drawing area / drawing area full scale voltage
        pix = scale / voltageScale;
        
    }
           
    /** Function returns voltage value converted to pixels
     *
     * @param voltage data to scale in pixel format
     * @return voltage 
     */
    
    public int getVoltagePixels(double voltage) {
        return((int)(pix * voltage));
    }    
    
    /** Function returns pixel count converted to voltage
     *
     * @param pixels to convert
     * @return scaled value
     * 
     */
    
    public double getScaledValue(int pixels) {
        return((voltageScale / scale) * pixels);
    }    
}
