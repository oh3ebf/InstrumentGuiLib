/***********************************************************
 * Software: instrument gui library
 * Module:   polar plot drawing class
 * Version:  0.1
 * Licence:  GPL2
 *
 * Owner: Kim Kristo
 * Date creation : 22.12.2012
 *
 ***********************************************************/
package oh3ebf.lib.gui;

import java.awt.Color;
import oh3ebf.lib.gui.primitives.Axis;
import oh3ebf.lib.gui.primitives.PolarAxis;

public class PolarPlot extends Plot {

    protected PolarAxis p;    

    /** Creates a new instance of PolarPlot */
    public PolarPlot() {
        // init some data structures
        super();

        p = new PolarAxis(PolarAxis.AXIS_X, Color.GREEN, Axis.GRID_SCOPE);
        p.setGridState(true);
        p.setDrawBorders(false);
        p.setInsets(20, 20, 20, 20);

        add(p);

    }
}
