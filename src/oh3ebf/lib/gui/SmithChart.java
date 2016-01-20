/***********************************************************
 * Software: instrument gui library
 * Module:   smith chart drawing class
 * Version:  0.1
 * Licence:  GPL2
 *
 * Owner: Kim Kristo
 * Date creation : 22.12.2012
 *
 ***********************************************************/
package oh3ebf.lib.gui;

import oh3ebf.lib.gui.primitives.SmithChartGrid;

public class SmithChart extends Plot {

    private SmithChartGrid sGrid;
    
    /** Creates a new instance of SmithChart */
    public SmithChart() {
        // init some data structures
        super();

        sGrid = new SmithChartGrid();
        add(sGrid);
    }
}
