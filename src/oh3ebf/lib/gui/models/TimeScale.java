/***********************************************************
 * Software: instrument gui library
 * Module:   time scale class
 * Version:  0.1
 * Licence:  GPL2
 *
 * Owner: Kim Kristo
 * Date creation : 17.9.2012
 *
 ***********************************************************/
package oh3ebf.lib.gui.models;

public class TimeScale extends ScaleBase {

    protected double timeScale = 1.0D;           /* display scale in s */
    protected double scale = 1.0D;

    /** Creates a new instance of TimeScale */
    public TimeScale(double time) {
        timeScale = time;
    }

    public TimeScale(double time, double scale) {
        timeScale = time;
        this.scale = scale;
        
        // calculate how meny pixels per sample is needed
        pix = scale / timeScale;
    }
    
    /** Function updates scaling when heigth changes
     * 
     * @param scale
     */
    @Override
    public void updateScale(int scale) {
        // calculate how meny pixels per sample is needed
        pix = scale / timeScale;
        System.out.println("pix " + pix);
    }

    /** Function returns current time scale in seconds
     * 
     * @return timescale in use
     * 
     */
    public double getTimeScale() {
        return (timeScale);
    }

    /** Function sets new time scale in seconds
     *
     * @param time set new time scale
     *
     */
    public void setTimeScale(double time) {
        timeScale = time;
        // calculate how meny pixels per sample is needed
        pix = scale / timeScale;    
    }

    /** Function returns pixel count converted to time
     *
     * @param value to convert
     *
     */
    public double getScaledValue(int value) {
        return (value * pix);
    }
}
