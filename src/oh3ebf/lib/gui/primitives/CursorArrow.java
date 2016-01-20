/***********************************************************
 * Software: instrument gui library
 * Module:   cursor arrow driwing class
 * Version:  0.1
 * Licence:  GPL2
 *
 * Owner: Kim Kristo
 * Date creation : 6.9.2012
 *
 ***********************************************************/

package oh3ebf.lib.gui.primitives;

import java.awt.Polygon;

public class CursorArrow {
    private Polygon p;
    private int n;
    private int[] x,y;
    private int origo_x, origo_y;

            /** Creates a new instance of Arrow */
    public CursorArrow(int origo_x, int origo_y) {
        x = new int[5];
        y = new int[5];                
        n = 5;         
        this.origo_x = origo_x;
        this.origo_y = origo_y;
    }
    
    public Polygon getArrowX() {
        // Make a arrow
        x[0] = origo_x - 3;
        x[1] = origo_x - 3;
        x[2] = origo_x;
        x[3] = origo_x + 3;
        x[4] = origo_x + 3;
        
        y[0] = origo_y;
        y[1] = origo_y + 6;
        y[2] = origo_y + 8;
        y[3] = origo_y + 6;
        y[4] = origo_y;
        
        p = new Polygon(x, y, n);
        
        return(p);
    }
    
    public Polygon getArrowY() {
        // Make a arrow
        x[0] = origo_x;
        x[1] = origo_x + 6;
        x[2] = origo_x + 8;
        x[3] = origo_x + 6;
        x[4] = origo_x;
        
        y[0] = origo_y - 3;
        y[1] = origo_y - 3;
        y[2] = origo_y;
        y[3] = origo_y + 3;
        y[4] = origo_y + 3;
        
        p = new Polygon(x, y, n);
        
        return(p);
    }    
}
