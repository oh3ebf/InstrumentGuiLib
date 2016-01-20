/***********************************************************
 * Software: instrument gui library
 * Module:   linear voltage scale class
 * Version:  0.1
 * Licence:  GPL2
 *
 * Owner: Kim Kristo
 * Date creation : 10.9.2012
 *
 ***********************************************************/

package oh3ebf.lib.gui.models;

import java.util.Iterator;
import java.util.Vector;

public class LinearVoltageScale extends VoltageScale {        
    
    /** Creates a new instance of LinearScale */
    public LinearVoltageScale() {
        super();              
    }

    /** Function returns scaled values of parameter vector
     * 
     * @param b vector to scale
     * @return scaled vector
     * 
     */
    
    @Override
    public Vector<Integer> getScaledValues(Vector<Double> b) {
        Vector<Integer> scaledData = new Vector<Integer>();
        
        Iterator i = b.iterator();
        while(i.hasNext()) {
            // calculate voltage to pixels
            double d = ((Double) i.next()).doubleValue() * pix;
            scaledData.add((int)d);
        }
        
        return(scaledData);
    }   
}
