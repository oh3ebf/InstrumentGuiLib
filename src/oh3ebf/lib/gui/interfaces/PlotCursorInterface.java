/*
 * PlotCursorInterface.java
 *
 * Created on September 18, 2012, 2:09 PM
 */

package oh3ebf.lib.gui.interfaces;

/**
 *
 * @author kim
 */

public interface PlotCursorInterface {
    public void cursorPositionRawValues(int x1, int x2, int y1, int y2);
    public void cursorPositionScaledValues(double x1, double x2, double y1, double y2);
}
