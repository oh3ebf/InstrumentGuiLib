/***********************************************************
 * Software: instrument gui library
 * Module:   scale base class
 * Version:  0.1
 * Licence:  GPL2
 *
 * Owner: Kim Kristo
 * Date creation : 10.9.2012
 *
 ***********************************************************/

package oh3ebf.lib.gui.models;

import java.util.Vector;

public class ScaleBase {
    protected double pix = 0;
    //protected double pixDiv = 10.0D;
    
    /** Creates a new instance of ScaleBase */
    public ScaleBase() {
        //this.pixDiv = 1.0D;
    }
    
    /** Creates a new instance of ScaleBase */
    //public ScaleBase(double pixDiv) {
        //this.pixDiv = pixDiv;
    //}
    
    /** Function returns current scaling factor
     * 
     * @return scaling factor
     * 
     */     
    
    public double getScale() {
        return(pix);
    }
    
    /** Function updates scaling when heigth changes
     * 
     * @param height
     */
    public void updateScale(int height) {
        
    }
    
    /** Function returns scaled values of vector data
     * 
     * @param b vector to scale
     * @return scaled vector
     * 
     */
        
    public Vector<Integer> getScaledValues(Vector<Double> b) {
        return(null);
    }           
}
