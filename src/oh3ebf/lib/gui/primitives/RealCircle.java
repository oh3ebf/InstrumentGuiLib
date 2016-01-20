/***********************************************************
 * Software: instrument gui library
 * Module:   Smith chart real circle drawing class
 * Version:  0.1
 * Licence:  GPL2
 *
 * Owner: Kim Kristo
 * Date creation : 4.10.2013
 *
 ***********************************************************/

package oh3ebf.lib.gui.primitives;

public class RealCircle extends Circle {

    /** Function creates circle on real axel on complex coordinates
     * 
     * @param re_value real value of circle
     */
    
    public RealCircle(double re_value) {
        // set and normalize real value
        super(re_value / (1.0D + re_value), 0.0D, 1.0D / (1.0D + re_value));
    }

    /** Function sets real value
     * 
     * @param value real value of circle
     */
    
    public void setReal(double value) {
        // normalize values
        x0 = (value / (1.0D + value));
        y0 = 0.0D;
        r0 = (1.0D / (1.0D + value));
    }
}
