/***********************************************************
 * Software: instrument gui library
 * Module:   Smith chart imagenary circle drawing class
 * Version:  0.1
 * Licence:  GPL2
 *
 * Owner: Kim Kristo
 * Date creation : 4.10.2013
 *
 ***********************************************************/
package oh3ebf.lib.gui.primitives;

public class ImaginaryCircle extends Circle {

    public ImaginaryCircle(double im_value) {
        super(1.0D, 1.0D / im_value, Math.abs(1.0D / im_value));
    }

    public void setImaginary(double im_value) {
        y0 = (1.0D / im_value);
        r0 = Math.abs(1.0D / im_value);
    }
}
