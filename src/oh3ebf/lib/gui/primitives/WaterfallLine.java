/***********************************************************
 * Software: instrument gui library
 * Module:   Waterfaal plot line base class
 * Version:  0.1
 * Licence:  GPL2
 *
 * Owner: Kim Kristo
 * Date creation : 22.8.2013
 *
 ***********************************************************/
package oh3ebf.lib.gui.primitives;

import java.awt.Color;
import java.awt.Graphics2D;

public class WaterfallLine extends DrawingObject {

    private byte[] data;
    private int y;

    public WaterfallLine(byte[] b) {
        // copy drawing data
        data = b;
        y = 0;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public void incrementY() {
        y++;
    }

    /** Function implement drawing algorithm for gnd symbol
     *
     * @param g graphics object to paint
     */
    @Override
    public void draw(Graphics2D g) {
        WaterfallLegend wl = WaterfallLegend.getWaterfallLegend(256);

        try {
            for (int i = 0; i < componentSize.width; i++) {
                // set current drawing color
                g.setColor(wl.getColorValue(0xff & data[i]));

                g.drawLine(i, y, i, y);
            }
        } catch (Exception e) {
        e.printStackTrace();
        }
    }
}
