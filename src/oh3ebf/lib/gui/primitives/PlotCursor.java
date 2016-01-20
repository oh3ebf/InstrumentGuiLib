/***********************************************************
 * Software: instrument gui library
 * Module:   plot cursor class
 * Version:  0.1
 * Licence:  GPL2
 *
 * Owner: Kim Kristo
 * Date creation : 6.9.2012
 *
 ***********************************************************/

package oh3ebf.lib.gui.primitives;

import oh3ebf.lib.gui.interfaces.DataTrackingInterface;
import oh3ebf.lib.gui.interfaces.PlotCursorInterface;
import oh3ebf.lib.gui.models.ScaleBase;
import oh3ebf.lib.gui.models.VoltageScale;
import oh3ebf.lib.gui.models.SampleScale;
import java.awt.*;

public class PlotCursor extends DrawingObject {
    public static final int CURSOR_NONE = 0;
    public static final int CURSOR_X = 1;
    public static final int CURSOR_Y = 2;
    public static final int CURSOR_XY = 3;
    
    public static final int CURSOR_SELECT_NONE = 0;
    public static final int CURSOR_SELECT_X1 = 1;
    public static final int CURSOR_SELECT_X2 = 2;
    public static final int CURSOR_SELECT_Y1 = 3;
    public static final int CURSOR_SELECT_Y2 = 4;
        
    private int cursorMode = CURSOR_NONE;
    private int cursorSelected = 0;
    private int cursorX1 = 100;    
    private int cursorX2 = 120;    
    private int cursorY1 = 100;    
    private int cursorY2 = 120;
    private int cursorX1Binding = 0;
    private int cursorX2Binding = 0;
    private int cursorY1Binding = 0;
    private int cursorY2Binding = 0;
    
    private int horizontalZeroPoint = 0;
    private int verticalZeroPoint = 0;
    private int verticalRefPoint1 = 0;
    private int verticalRefPoint2 = 0;
    private ScaleBase horizontalScale = null;
    private ScaleBase[] verticalScale = null;
    private double dx1 = 0.0D, dx2 = 0.0D, dy1 = 0.0D, dy2 = 0.0D;
    private int ix1 = 0, ix2 = 0, iy1 = 0, iy2 = 0;
    private int snapZone = 1;
    
    private PlotCursorInterface cursorAction = null;
    private DataTrackingInterface tracking = null;
            
    /** Creates a new instance of Cursor */
    public PlotCursor(int chCnt, PlotCursorInterface inf) {
        cursorAction = inf;
        verticalScale = new ScaleBase[chCnt];
    }
    
    public PlotCursor(int chCnt, int mode, PlotCursorInterface inf) {
        cursorMode = mode;
        cursorAction = inf;
        verticalScale = new ScaleBase[chCnt];
    }
    
    /** Function adds horizontal scale 
     *
     * @param scale to add
     *
     */
    
    public void addHorizontalScale(ScaleBase scale) {
        horizontalScale = scale;        
    }

    /** Function adds vertical scale 
     *
     * @param scale to add
     * @throws ArrayIndexOutOfBoundsException
     * 
     */
    
    public void addVerticalScale(int ch, ScaleBase scale) throws ArrayIndexOutOfBoundsException {
        // check index bounds
        if(ch > verticalScale.length)
            throw new ArrayIndexOutOfBoundsException();
        
        // add scaling reference
        verticalScale[ch] = scale;                
    }
    
    /** Function bind tracking to measurement channel
     *
     * @param cursor to bind channel
     * @param ch to use
     *
     */
    
    public void bindToChannel(int cursor, int ch) throws ArrayIndexOutOfBoundsException {
        // check index bounds
        if(ch > verticalScale.length)
            throw new ArrayIndexOutOfBoundsException();
        
        switch (cursor) {
            case CURSOR_SELECT_X1:
                cursorX1Binding = ch;
                break;
            case CURSOR_SELECT_X2:
                cursorX2Binding = ch;
                break;
            case CURSOR_SELECT_Y1:
                cursorY1Binding = ch;
                break;
            case CURSOR_SELECT_Y2:
                cursorY2Binding = ch;
                break;
            default:
                break;
        }
    }
    
    // mihin käytetään
    public void setVerticalRefPoint(int a, int ref) {
        
    }
            
    /** Function set new cursor mode
     *
     * @param mode new cursor mode
     *
     */
    
    public void setCursorMode(int mode) {
        // update cusor mode
        cursorMode = mode;
    }
            
    /** Function set new position to cursor 
     *
     * @param cursor selected to set
     * @param value new position
     *
     */
    
    public void setCursor(int cursor, int value) {
                
        switch(cursor) {
            case CURSOR_SELECT_X1:
                if(value > insets.left && (value < componentSize.width - insets.right)) {
                    cursorX1 = value;
                }
                break;
            case CURSOR_SELECT_X2:
                if(value > insets.left && (value < componentSize.width - insets.right)) {
                    cursorX2 = value;
                }
                break;
            case CURSOR_SELECT_Y1:
                if(value > insets.top && (value < componentSize.height - insets.bottom)) {
                    cursorY1 = value;
                }
                break;
            case CURSOR_SELECT_Y2:
                if(value > insets.top && (value < componentSize.height - insets.bottom)) {
                    cursorY2 = value;
                }
                break;
        } 
        
        if (tracking != null) {
            System.out.println(tracking.getCurrentValue(value));
        }
    }
          
    /** Function get position of cursor 
     *
     * @param cursor selected to get
     *
     */
    
    public int getCursor(int cursor) {
        switch(cursor) {
            case CURSOR_SELECT_X1:
                return(cursorX1);
            case CURSOR_SELECT_X2:
                return(cursorX2);
            case CURSOR_SELECT_Y1:
                return(cursorY1);
            case CURSOR_SELECT_Y2:
                return(cursorY2);
        }
        
        return(0);
    }
           
    /** Function sets selected cursor
     *
     * @param selection set selected cursor
     *
     */
    
    public void setSelectedCursor(int selection) {
        cursorSelected = selection;        
    }
    
    /** Function gets selected cursor
     * 
     * @return number of selected cursor
     */
     
    
    public int getSelectedCursor() {
        return(cursorSelected);
    }
    
    /** Function returns horizontal zero point
     * 
     * @return horizontal zero point in use
     */
    
    
    public int getHorizontalZeroPoint() {
        return horizontalZeroPoint;
    }

    /** Function sets horizontal reference point
     *
     * @param zeroPoint reference point to cursors
     *
     */
    
    /*public void setHorizontalZeroPoint(int zeroPoint) {
        horizontalZeroPoint = zeroPoint;
    }*/
    
    /** Function activates cursor selection
     *
     * @param p current mouse location point
     *
     */
    
    public int activateCursor(Point p) {
        if((p.x >= cursorX1 - snapZone) && (p.x <= cursorX1 + snapZone) && (p.x > insets.left) && (p.x < componentSize.width - insets.right))
            cursorSelected = CURSOR_SELECT_X1;
        
        if((p.x >= cursorX2 - snapZone) && (p.x <= cursorX2 + snapZone) && (p.x > insets.left) && (p.x < componentSize.width - insets.right))
            cursorSelected = CURSOR_SELECT_X2;
        
        if((p.y >= cursorY1 - snapZone) && (p.y <= cursorY1 + snapZone) && (p.y > insets.top) && (p.y < componentSize.height - insets.bottom))
            cursorSelected = CURSOR_SELECT_Y1;
        
        if((p.y >= cursorY2 - snapZone) && (p.y <= cursorY2 + snapZone) && (p.y > insets.top) && (p.y < componentSize.height - insets.bottom))
            cursorSelected = CURSOR_SELECT_Y2;
        
        return(cursorSelected);
    }
    
    /** Function implement drawing algorithm for cursors
     *
     * @param g graphics object to paint
     */
    // TODO hiiren päällä olo muuttaa monikulmion väriä
    @Override
    public void draw(Graphics2D g) {
        int h = componentSize.height;
        int w = componentSize.width;
        
        // get middle point of screen, default zero location
        horizontalZeroPoint = w / 2;
        verticalZeroPoint = h / 2;
        
        if(isVisible()) {
            // check installed callbacks
            if((cursorAction != null) && (cursorMode != CURSOR_NONE)) {
                // update cursor values
                calculateCursors();
                
                // calculate current positions
                cursorAction.cursorPositionRawValues(ix1, ix2, iy1, iy2);
                cursorAction.cursorPositionScaledValues(dx1, dx2, dy1, dy2);                
            }
        
            // set cursor draving color
            g.setColor(penColor);
            
            switch(cursorMode) {
                case CURSOR_X:
                    // X cursors
                    g.fillPolygon((new CursorArrow(cursorX1, insets.top).getArrowX()));
                    g.drawLine(cursorX1, insets.top, cursorX1, h - insets.bottom);
                    g.fillPolygon((new CursorArrow(cursorX2, insets.top).getArrowX()));
                    g.drawLine(cursorX2, insets.top, cursorX2, h - insets.bottom);
                    
                    // draw labels for cursors
                    g.drawString("X1", cursorX1 - g.getFontMetrics().stringWidth("X1"), insets.top - 5);
                    g.drawString("X2", cursorX2 - g.getFontMetrics().stringWidth("X2"), insets.top - 5);
                    break;
                case CURSOR_Y:
                    // Y cursors
                    g.fillPolygon((new CursorArrow(insets.left, cursorY1).getArrowY()));
                    g.drawLine(insets.left, cursorY1, w - insets.right, cursorY1);
                    g.fillPolygon((new CursorArrow(insets.left, cursorY2).getArrowY()));
                    g.drawLine(insets.left, cursorY2, w - insets.right, cursorY2);
                    
                    // draw labels for cursors
                    g.drawString("Y1", insets.left - g.getFontMetrics().stringWidth("Y1"), cursorY1);
                    g.drawString("Y2", insets.left - g.getFontMetrics().stringWidth("Y2"), cursorY2);
                    break;
                case CURSOR_XY:
                    // both X and Y cursors
                    g.fillPolygon((new CursorArrow(cursorX1, insets.top).getArrowX()));
                    g.drawLine(cursorX1, insets.top, cursorX1, h - insets.bottom);
                    g.fillPolygon((new CursorArrow(cursorX2, insets.top).getArrowX()));
                    g.drawLine(cursorX2, insets.top, cursorX2, h - insets.bottom);
                    
                    // draw labels for cursors
                    g.drawString("X1", cursorX1 - g.getFontMetrics().stringWidth("X1"), insets.top - 5);
                    g.drawString("X2", cursorX2 - g.getFontMetrics().stringWidth("X2"), insets.top - 5);
                    
                    g.fillPolygon((new CursorArrow(insets.left, cursorY1).getArrowY()));
                    g.drawLine(insets.left, cursorY1, w - insets.right, cursorY1);
                    g.fillPolygon((new CursorArrow(insets.left, cursorY2).getArrowY()));
                    g.drawLine(insets.left, cursorY2, w - insets.right, cursorY2);
                    
                    // draw labels for cursors
                    g.drawString("Y1", insets.left - g.getFontMetrics().stringWidth("Y1"), cursorY1);
                    g.drawString("Y2", insets.left - g.getFontMetrics().stringWidth("Y2"), cursorY2);
                    break;
            }
        }
    }    
    
    /** Function calculates cursor positions relative to reference zero 
     *
     */
    
    private void calculateCursors() {
        ix1 = cursorX1 - horizontalZeroPoint;
        ix2 = cursorX2 - horizontalZeroPoint;
        iy1 = verticalZeroPoint - verticalRefPoint1 - cursorY1;
        iy2 = verticalZeroPoint - verticalRefPoint2 - cursorY2;
        
        // check installed horizontal scaling
        if(horizontalScale != null) {
            // check if it timescale
            /*if(horizontalScale instanceof TimeScale) {
                TimeScale t = (TimeScale)horizontalScale;
                dx1 = t.getScaledValue(ix1);
                dx2 = t.getScaledValue(ix2);                
            }*/
            
            if(horizontalScale instanceof SampleScale) {                
                dx1 = ((SampleScale)horizontalScale).getScaledUnits(ix1);
                dx2 = ((SampleScale)horizontalScale).getScaledUnits(ix2);              
            }
        }       
        
        // check installed horizontal scaling for cursor Y1
        if (verticalScale[cursorX1Binding] instanceof VoltageScale) {
            VoltageScale v = (VoltageScale) verticalScale[cursorX1Binding];
            dy1 = v.getScaledValue(iy1);
            System.out.println("y:" + iy1 + " dy1 "+dy1);
        }
        
        // check installed horizontal scaling  for cursor Y2
        if (verticalScale[cursorX2Binding] instanceof VoltageScale) {
            VoltageScale v = (VoltageScale) verticalScale[cursorX2Binding];
            dy2 = v.getScaledValue(iy2);
        }         
    }
            
}
