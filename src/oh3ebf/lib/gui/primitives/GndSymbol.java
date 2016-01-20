/***********************************************************
 * Software: instrument gui library
 * Module:   gnd symbol drawing class
 * Version:  0.1
 * Licence:  GPL2
 *
 * Owner: Kim Kristo
 * Date creation : 7.9.2012
 *
 ***********************************************************/

package oh3ebf.lib.gui.primitives;

import java.awt.Graphics2D;

public class GndSymbol extends DrawingObject {
    private int x = 0, y = 0;
    private String name;
    
    /** Creates a new instance of GndSymbol */
    public GndSymbol(String name) {
        this.name = name;
    }
    
    public GndSymbol(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }
    
    /** Function sets drawing position of symbol
     *
     * @param x horizontal position
     * @param y vertical position
     *
     */
    
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    /** Function implement drawing algorithm for gnd symbol
     *
     * @param g graphics object to paint
     */
    
    @Override
    public void draw(Graphics2D g) {                
         
        // set current drawing color
        g.setColor(penColor);
        
        g.drawLine(x, y, x + 7, y);
        g.drawLine(x + 7, y, x + 7, y + 7);
        g.drawLine(x, y + 7, x + 14, y + 7);
        g.drawLine(x + 3, y + 9, x + 11, y + 9);
        g.drawLine(x + 5, y + 11, x + 9, y + 11);
        
        g.drawString(name, x, y - 2);
    }
}
