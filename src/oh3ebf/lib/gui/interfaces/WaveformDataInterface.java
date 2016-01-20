/***********************************************************
 * Software: instrument gui library
 * Module:   waveform data interface class
 * Version:  0.1
 * Licence:  GPL2
 *
 * Owner: Kim Kristo
 * Date creation : 6.2.2013
 *
 ***********************************************************/
package oh3ebf.lib.gui.interfaces;

import java.util.Vector;

public interface WaveformDataInterface {

    void updateData(byte b[]);
    void updateData(int b[]);
    void updateData(double b[]);
    Vector<Double> getData();
}
