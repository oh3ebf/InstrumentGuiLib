/***********************************************************
 * Software: instrument gui library
 * Module:   smith chart data point class
 * Version:  0.1
 * Licence:  GPL2
 *
 * Owner: Kim Kristo
 * Date creation : 7.10.2013
 *
 ***********************************************************/
package oh3ebf.lib.gui.primitives;

import java.awt.Point;
import netlib.math.complex.Complex;

public class SmithPoint {

    protected Complex sp;
    protected double re;
    protected double im;
    protected double Z0 = 1.0D;
    private boolean isImpedance;

    public SmithPoint(double r, double i) {
        sp = new Complex(r, i);
        // default to normalized impedances
        Z0 = 1.0D;
        // define if this object is impedance or admittance
        isImpedance = true;
        re = r;
        im = i;
    }

    public SmithPoint(String str, double r, double i, double z0, boolean s) {
        // 
        sp = new Complex(r, i);
        re = r;
        im = i;
        Z0 = z0;
        isImpedance = s;
    }

    
    public Complex getComplexPoint() {
        return (sp);
    }

    public double getRe() {
        return (sp.re);
    }

    public double getReNormalized() {
        if (isImpedance) {
            return (sp.re / Z0);
        }

        return (sp.re * Z0);
    }

    public double getIm() {
        return (sp.im);
    }

    public double getImNormalized() {
        if (isImpedance) {
            return (sp.im / Z0);
        }

        return (sp.im * Z0);
    }

    // palauttaa vektorin |Z| pituuden...
    public double getZ() {
        // tämä uudelleen laskentaan...
        return (sp.abs());
    }

    /** Function returns points position in x,y coordinates
     *  result has to be multiplied with scaling factor to get x/y-values
     * 
     * @return position
     */
    public Point.Double getPosition() {
        Point.Double p = new Point.Double();

        // check real infinity
        if (sp.re == (1.0D / 0.0D)) {
            // set positive end of real axel
            p.x = 1.0D;
            p.y = 0.0D;
        } else {

            if (sp.im == 0.0D) {
                // calculate point in real axel                
                p.x = (getReNormalized() - 1.0D) / (1.0D + getReNormalized());
                p.y = 0.0D;

            } else {
                // get impedance circles
                Circle re_c = new RealCircle(getReNormalized());
                Circle im_c = new ImaginaryCircle(getImNormalized());

                // calculate intersection points
                double[] tmpArray = re_c.intersectionCircle(im_c);

                if (sp.im > 0.0D) {
                    p.x = tmpArray[2];
                    p.y = tmpArray[3];
                } else {
                    p.x = tmpArray[0];
                    p.y = tmpArray[1];
                }
            }
        }
        //System.out.println("re: " + sp.re + " im: " + sp.im);
        //System.out.println("p.x: " + p.x + " p.y: " + p.y);
        System.out.println("p.x: " + p.x * 200 + " p.y: " + p.y * 200);
        return (p);
    }

    /** Function converts coodinates to impedance value
     *  coodinates must be normalized using scaling factor
     * 
     * @param x coordinate 
     * @param y coordinate
     */
    
    public void setPointByXY(double x, double y) {
        Complex reference = new Complex();
        Complex target = new Complex();

        // mites tässä nyt sitten impedanssi / admittanssi???
        if (isImpedance == true) {
            reference.re = x;
            reference.im = y;            
        } else {
            reference.re = -x;
            reference.im = -y;           
        }

        double denominator = Math.pow(1.0D - reference.re, 2.0D) + Math.pow(reference.im, 2.0D);  

        if (denominator == 0.0D) {
            // real part is infinity
            target.re = (1.0D / 0.0D);
            // im is then zero
            target.im = 0.0D;
        } else {
            // othercaeses needs to be calculated
            target.re = (1.0D - Math.pow(reference.re, 2.0D) - Math.pow(reference.im, 2.0D)) / (denominator * Z0);
            target.im = 2.0D * reference.im / (denominator * Z0);
        }

        // real part cannot be less than zero
        if (target.re < 0.0D) {            
            target.re = 0.0D;
        }

        // miksi tässä on uusi piste????
        SmithPoint tmpZ = new SmithPoint("", target.re, target.im, Z0, true);

        if (isImpedance == true) {
            // new value is impedance
            sp.re = tmpZ.getRe();
            sp.im = -tmpZ.getIm();
        } else {
            // new value is admittance
            SmithPoint tmpY = tmpZ.invertPoint();
            sp.re = tmpY.getRe();
            sp.im = tmpY.getIm();
            isImpedance = tmpY.isImpedance;
        }

        System.out.println("Re: " + target.re + " Im: " + target.im);
    }

    /** Function conversion between impedance and admittance
     * 
     * @return inverted point
     */
    
    public SmithPoint invertPoint() {
        SmithPoint returnPoint;
        
        // special case when both are zeros
        if ((sp.re == 0.0D) && (sp.im == 0.0D)) {        
            if (isImpedance == true) {
                returnPoint = new SmithPoint("", (1.0D / 0.0D), 0.0D, Z0, false);
            } else {
                returnPoint = new SmithPoint("", (1.0D / 0.0D), 0.0D, Z0, true);
            }
        } else {
            // special case when iresistance is zero
            if (sp.im == 0.0D) {
                if (isImpedance == true) {
                    returnPoint = new SmithPoint("", 1.0D / sp.re, 0.0D, Z0, false);
                } else {
                    returnPoint = new SmithPoint("", 1.0D / sp.re, 0.0D, Z0, true);
                }
            } else {
                // case when real part is infinity
                if (sp.re == (1.0D / 0.0D)) {
                    if (isImpedance == true) {
                        returnPoint = new SmithPoint("", 0.0D, 0.0D, Z0, false);
                    } else {
                        returnPoint = new SmithPoint("", 0.0D, 0.0D, Z0, true);
                    }
                } else {
                    // other wise calculate inverted values
                    double normal = sp.norm();
                    if (isImpedance == true) {                        
                        returnPoint = new SmithPoint("", sp.re / normal, -sp.im / normal, Z0, false);
                    } else {
                        returnPoint = new SmithPoint("", sp.re / normal, -sp.im / normal, Z0, true);
                    }
                }
            }
        }
        
        return returnPoint;
    }
}
