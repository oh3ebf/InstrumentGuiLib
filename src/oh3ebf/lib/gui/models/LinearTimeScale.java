/***********************************************************
 * Software: instrument gui library
 * Module:   linear time scale class
 * Version:  0.1
 * Licence:  GPL2
 *
 * Owner: Kim Kristo
 * Date creation : 12.9.2012
 *
 ***********************************************************/

package oh3ebf.lib.gui.models;

public class LinearTimeScale extends TimeScale {
    
    protected double samplingTime = 1.0D;        /* data sampling time in s */
    
    /** Creates a new instance of LinearTimeScale */
    public LinearTimeScale(double time) {
        super(time);
    }
    
    /** Creates a new instance of LinearTimeScale */
    public LinearTimeScale(double time, double scale) {
        super(time, scale);
        
        // set scale variables
        //timeScale =  time;
        //this.scale = scale;
        
        // calculate how meny pixels per sample is needed
        //pix = pixDiv / (timeScale / samplingTime);
    }
    
       
    /** Function sets new time scale in seconds
     *
     * @param scale set new time scale
     *
     */
    
    @Override
    public void setTimeScale(double scale) {
        super.setTimeScale(scale);
             
        // calculate how meny pixels per sample is needed
        //pix = pixDiv / (timeScale / samplingTime);
    }
    
}
