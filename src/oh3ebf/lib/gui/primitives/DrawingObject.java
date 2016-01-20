/***********************************************************
 * Software: instrument gui library
 * Module:   drawing items base class
 * Version:  0.1
 * Licence:  GPL2
 *
 * Owner: Kim Kristo
 * Date creation : 4.9.2012
 *
 ***********************************************************/

package oh3ebf.lib.gui.primitives;

import java.awt.*;

public class DrawingObject extends AbstractDrawingObject {
    protected Color penColor;
    protected Dimension componentSize;
    protected Insets insets;
    protected boolean visible = true;
    
    /** Creates a new instance of DrawingObject */
    public DrawingObject() {        
        // variable defaults
        componentSize = new Dimension();
        penColor = Color.BLACK;
        insets = new Insets(0,0,0,0);
    }
    
    /** Function set new drawing color
     *
     * @param c color to use
     *
     */
    
    @Override
    public void setPenColor(Color c) {
        penColor = c;
    }
    
    /** Function sets insets for component
     *
     * @param top upper free space
     * @param left free space
     * @param bottom free space
     * @param rigth free space
     *
     */
    
    @Override
    public void setInsets(int top, int left, int bottom, int rigth) {
        insets.left = left;
        insets.right = rigth;
        insets.bottom = bottom;
        insets.top = top;
    }
    
    /** Function sets component size
     *
     * @param width component total width
     * @param heigth component total heigth
     *
     */
    
    @Override
    public void setSize(int width, int heigth) {
        componentSize.width = width;
        componentSize.height = heigth;
    }
    
    /** Function sets component size
     *
     * @param size component size in dimension format
     *
     */
    
    @Override
    public void setSize(Dimension size) {
        componentSize = size;
    }
    
    /** Function return current visibility of waveform
     *
     * @param b set visibility 
     */
    
    public void setVisible(boolean b) {
        visible = b;
    }
    
    /** Function return current visibility of waveform
     *
     * @return component visibility
     * 
     */
    
    public boolean isVisible() {
        return(visible);
    }
    
    /** Function implements default drawing  
     *
     * @param g graphics from object to draw
     *
     */
    
    @Override
    public void draw(Graphics2D g) {
        
    }
}
