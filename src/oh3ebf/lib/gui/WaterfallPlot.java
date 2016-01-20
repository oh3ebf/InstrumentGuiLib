/***********************************************************
 * Software: instrument gui library
 * Module:   waterfall plot class
 * Version:  0.1
 * Licence:  GPL2
 *
 * Owner: Kim Kristo
 * Date creation : 22.8.2013
 *
 ***********************************************************/
package oh3ebf.lib.gui;

import java.util.Random;
import oh3ebf.lib.gui.interfaces.WaveformDataInterface;
import oh3ebf.lib.gui.primitives.DrawingObject;
import oh3ebf.lib.gui.primitives.WaterfallLine;

public class WaterfallPlot extends Plot /*implements WaveformDataInterface*/ {

    private int lines = 500;

    public WaterfallPlot() {

        //Random r = new Random();
        //for(int i = 0;i < lines;i++) {
        byte[] b = new byte[1000];


        for (int j = 0; j < b.length/2; j++) {
            System.out.println(Math.exp(j));
        //b[j] = (byte)Math.round(Math.log(i*j) * 255);
        }

        for (int j = 500; j < b.length; j++) {
            System.out.println(Math.exp(-4*j));
        //b[j] = (byte)Math.round(Math.log(i*j) * 255);
        }

    //updateData(b);
    //}                
    }

    /** Function adds new datavalues to waterfall draw
     * 
     * @param b data to display
     */
    //@Override
    public void updateData(byte[] b) {
        add(new WaterfallLine(b));
    }

    /** Functions sets number of displayed lines
     * 
     * @param n lines count
     */
    public void setLinesCount(int n) {
        lines = n;
    }

    /** Function adds new drawing object to plot
     *
     * @param d new drawing object to add
     *
     */
    @Override
    public void add(DrawingObject d) {
        DrawingObject obj;

        // check for waterfaal sample size
        if (drawingItems.size() > lines) {
            // remove oldest sample
            drawingItems.remove(0);
        }

        for (int i = 0; i < drawingItems.size(); i++) {
            if ((obj = drawingItems.get(i)) instanceof WaterfallLine) {
                ((WaterfallLine) obj).incrementY();
            }
        }


        // add new drawing component
        d.setSize(getPreferredSize());
        drawingItems.add(d);
    }
}
