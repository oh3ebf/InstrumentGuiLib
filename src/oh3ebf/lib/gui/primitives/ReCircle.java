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

public class ReCircle extends Circle {

    public ReCircle(double re_value) {
        super(re_value / (1.0D + re_value), 0.0D, 1.0D / (1.0D + re_value));
    }

    public void setRe(double value) {
        x0 = (value / (1.0D + value));
        y0 = 0.0D;
        r0 = (1.0D / (1.0D + value));
    }
}
