/***********************************************************
 * Software: instrument gui library
 * Module:   spectrum plot class
 * Version:  0.1
 * Licence:  GPL2
 *
 * Owner: Kim Kristo
 * Date creation : 21.2.2013
 *
 ***********************************************************/
package oh3ebf.lib.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Iterator;
import oh3ebf.lib.gui.interfaces.WaveformDataInterface;
import oh3ebf.lib.gui.models.DecibelScale;
import oh3ebf.lib.gui.models.SampleScale;
import oh3ebf.lib.gui.models.SpectrumWaveModel;
import oh3ebf.lib.gui.primitives.Axis;
import oh3ebf.lib.gui.primitives.SpectrumWaveForm;
import oh3ebf.lib.gui.primitives.WaveForm;

public class SpectrumPlot extends Plot {

    protected Axis x;
    protected Axis y;

    public SpectrumPlot() {
        // init some data structures
        super();

        x = new Axis(Axis.AXIS_X, Color.GREEN, Axis.GRID_SCOPE);
        x.setGridState(true);
        x.setDrawBorders(true);
        x.setInsets(20, 35, 20, 20);

        add(x);

        y = new Axis(Axis.AXIS_Y, Color.GREEN, Axis.GRID_SCOPE);
        y.setGridState(true);
        y.setDrawBorders(true);
        y.setInsets(20, 35, 20, 20);
        y.setYScale(-120, 0);
        y.setScaleState(true);
        add(y);

        // create waveform for channel
        WaveForm wave = new SpectrumWaveForm("Wave");
        wave.setInsets(20, 35, 20, 20);
        wave.setPenColor(waveColors[0]);

        // add waveform data model to plot
        SpectrumWaveModel waveModel = new SpectrumWaveModel();
        waveModel.testData();

        SampleScale s = new SampleScale(this.getWidth(), 417.0);
        waveModel.addHorizontalScale(s);

        DecibelScale d = new DecibelScale(this.getHeight(), 0, -120);
        waveModel.addVerticalScale(d);

        wave.addWaveModel(waveModel);

        // add wave to visual objects
        add(wave);
        waves.add(wave);        
    }

    /** Function returns waveform data interface to given channel
     * 
     * @param ch is channel to get interface
     * @return data interface
     */
    public WaveformDataInterface getDataInterface(int ch) {
        return ((WaveformDataInterface) waves.elementAt(ch).getWaveModel());
    }

    /** Function sets new reference level value
     * 
     * @param level to set
     */
    public void setReferenceLevel(double level) {
        Iterator i = waves.iterator();
        while (i.hasNext()) {
            WaveForm w = (WaveForm) i.next();
            // set new reference level
            ((DecibelScale) w.getWaveModel().getVerticalScale()).setDecibelScaleMax(level);
        }

        super.repaint();
    }

    public void setCenterFrequency(double center) {

    }

    public void setFrequencySpan(double span) {

    }

    /** Function sets component size
     *
     * @param width component total width
     * @param heigth component total heigth
     *
     */
    @Override
    public void setSize(int width, int heigth) {
        super.setSize(width, heigth);

        Iterator i = waves.iterator();
        while (i.hasNext()) {
            WaveForm w = (WaveForm) i.next();
            w.setSize(width, heigth);            
        }

        super.repaint();
    }

    /** Function sets component size
     *
     * @param size component size in dimension format
     *
     */
    @Override
    public void setSize(Dimension size) {
        //super.setSize(size);
        this.setSize(size.width, size.height);
    }
}
