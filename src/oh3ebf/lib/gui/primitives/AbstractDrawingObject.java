/***********************************************************
 * Software: instrument gui library
 * Module:   drawing items abstract base class
 * Version:  0.1
 * Licence:  GPL2
 *
 * Owner: Kim Kristo
 * Date creation : 4.9.2012
 *
 ***********************************************************/

package oh3ebf.lib.gui.primitives;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

public abstract class AbstractDrawingObject {
    
        
    public abstract void setPenColor(Color c);
    public abstract void setSize(Dimension size);
    public abstract void setSize(int width, int heigth);
    public abstract void setInsets(int top, int bottom, int left, int rigth);
    public abstract void draw(Graphics2D g);
}
