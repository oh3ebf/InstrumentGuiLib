/***********************************************************
 * Software: instrument gui library
 * Module:   Oscilloscope plot class
 * Version:  0.1
 * Licence:  GPL2
 *
 * Owner: Kim Kristo
 * Date creation : 6.9.2003
 *
 ***********************************************************/
package oh3ebf.lib.gui;

import oh3ebf.lib.gui.interfaces.PlotCursorInterface;
import oh3ebf.lib.gui.interfaces.WaveformDataInterface;
import oh3ebf.lib.gui.models.VoltageScale;
import oh3ebf.lib.gui.models.DefaultWaveModel;
import oh3ebf.lib.gui.models.ScopeWaveModel;
import oh3ebf.lib.gui.models.SampleScale;
import oh3ebf.lib.gui.primitives.Axis;
import oh3ebf.lib.gui.primitives.PlotCursor;
import oh3ebf.lib.gui.primitives.GndSymbol;
import oh3ebf.lib.gui.primitives.WaveForm;
import java.awt.*;
import java.util.*;

public class ScopePlot extends Plot {

    protected Axis x;
    protected Axis y;
    protected PlotCursor cursor;
    protected Color cursorColor = Color.orange;
    
    /** Creates a new instance of ScopePlot */
    public ScopePlot() {
        // init some data structures
        super();

        //setBackground(bgColor);
        initComponents();
    }

    public ScopePlot(int chCnt, PlotCursorInterface p) {
        // init some data structures
        super();

        //setBackground(bgColor);

        // create x axis
        x = new Axis(Axis.AXIS_X, Color.GREEN);
        x.setGridState(true);
        x.setDrawBorders(true);
        x.setInsets(20, 20, 20, 30);
        add(x);

        // create y axis
        y = new Axis(Axis.AXIS_Y, Color.GREEN);
        y.setGridState(true);
        y.setDrawBorders(true);
        y.setInsets(20, 20, 20, 30);
        add(y);

        // time scale to use in all wave forms
        //LinearTimeScale t = new LinearTimeScale(0.002D, 1.0D / 8000);

        cursor = new PlotCursor(chCnt, p);
        cursor.setPenColor(cursorColor);
        cursor.setInsets(20, 20, 20, 30);
        //cursor.addHorizontalScale(t);
        add(cursor);

        // add needed count of waveform objects        
        for (int i = 0; i < chCnt; i++) {
            // create waveform for channel
            WaveForm wave = new WaveForm("Ch" + String.valueOf(i + 1));
            wave.setInsets(20, 20, 20, 30);
            wave.setPenColor(waveColors[i]);
            wave.addZeroLevelSymbol(new GndSymbol("Ch" + String.valueOf(i + 1)));

            // add default waveform data model to channel
            ScopeWaveModel waveModel = new ScopeWaveModel();

            // set wave model scales    
            //LinearVoltageScale v = new LinearVoltageScale();
            // add scaling to cursors
            //cursor.addVerticalScale(i, v);
            //waveModel.addVerticalScale(v);
            //waveModel.addHorizontalScale(t);
            //wave.addWaveModel(waveModel);

            // add wave to visual objects
            add(wave);
            waves.add(wave);
        }

        //cursor.bindToWaveform((DataTrackingInterface)waves.firstElement());
        initComponents();
    }

    /** Function returns waveform data interface to given channel
     * 
     * @param ch is channel to get interface
     * @return data interface
     */
    public WaveformDataInterface getDataInterface(int ch) {
        return ((WaveformDataInterface) waves.elementAt(ch).getWaveModel());
    }

    // TODO 
    /* kursori bindataan aina aaltomuotoon
     * kursori käyttää aaltomuodon skaalauskerrointa
     * oletuksena voisi olla kanava ch1
     * referenssi pisteen asetus, nyt ottaa piirtoalueen reunasta
     * 
     */
    
    /** Function sets cursor binding to input channel
     * 
     * @param c cursor to bind
     * @param ch selected channel
     * 
     */
    
    public void setCursorTrackingCh(int c, int ch) {
        cursor.bindToChannel(c, ch);
    }

    /** Function sets new memory view offset
     *
     * @param offset to data memory
     *
     */
    public void setMemoryOffset(int offset) {
        Iterator i = waves.iterator();
        while (i.hasNext()) {
            // set new view offset
            ((WaveForm) i.next()).getWaveModel().setViewOffset(offset);
        }

        super.repaint();
    }

    /** Function sets horizontal scaling
     *
     * @param sampleCount samples in scale
     *
     */
    public void setSampleScale(double sampleCount) {
        // TODO tämä korjattava, vain yksi object olemassa
        Iterator i = waves.iterator();
        while (i.hasNext()) {
            // set new time scale
            ((SampleScale) ((WaveForm) i.next()).getWaveModel().getHorizontalScale()).setSampleScale(sampleCount);
        }

        super.repaint();
    }

    /** Function sets horizontal scaling
     *
     * @param t time scale
     *
     */
    public void setTimeScale(double unitFactor) {
        // TODO tämä korjattava, vain yksi object olemassa
        Iterator i = waves.iterator();
        while (i.hasNext()) {
            try {
                // set new time scale
                //((TimeScale) ((WaveForm) i.next()).getWaveModel().getHorizontalScale()).setTimeScale(t);
                ((SampleScale) ((WaveForm) i.next()).getWaveModel().getHorizontalScale()).setUnitScale(unitFactor);
            } catch (Exception ex) {
                // todo tässä pitää tehdä jotakin
            }
        }

        super.repaint();
    }

    /** Function sets waveform vertical scaling
     *
     * @param ch channel number to set drawing point
     * @param v new vertical scale in volts
     *
     */
    public void setVoltageScale(int ch, double v) {
        // check supported channel count
        if (ch < waves.size()) {
            // set voltage scale
            ((VoltageScale) waves.elementAt(ch).getWaveModel().getVerticalScale()).setVoltageScale(v);
            // clean and draw again
            super.repaint();
        }
    }

    /** Function sets waveform drawing point on screen
     *
     * @param ch channel number to set drawing point
     * @param ref new position in volts
     *
     */
    public void setGndRef(int ch, double ref) {
        // check supported channel count
        if (ch < waves.size()) {
            // set new wave position on screen
            waves.elementAt(ch).setDrawingOffset(((VoltageScale) (waves.elementAt(ch).getWaveModel().getVerticalScale())).getVoltagePixels(ref));
            // clean and draw again
            super.repaint();
        }
    }

    /** Function sets channel usage
     *
     * @param ch channel number to set drawing point
     * @param state indicates if channel is in use
     *
     */
    public void setChActive(int ch, boolean state) {
        // check supported channel count
        if (ch < waves.size()) {
            waves.elementAt(ch).setVisible(state);
            // clean and draw again
            super.repaint();
        }
    }

    /** Function gets channel status
     *
     * @param ch channel number to set drawing point
     * @return true if channel is used and visible
     * 
     */
    public boolean getChActive(int ch) {
        // check array bounds
        if (ch < waves.size()) {
            return (waves.elementAt(ch).isVisible());
        }

        return (false);
    }

    /** Function sets cursor mode
     *
     * @param mode to set on dispaly
     *
     */
    public void setCursorMode(int mode) {
        // set cursor visibility
        if (mode == oh3ebf.lib.gui.primitives.PlotCursor.CURSOR_NONE) {
            cursor.setVisible(false);
        } else {
            cursor.setVisible(true);
        }

        // set actual mode
        cursor.setCursorMode(mode);
        super.repaint();
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
            w.setDrawingOffset(((VoltageScale) w.getWaveModel().getVerticalScale()).getVoltagePixels(0.0D));
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

    /** Function set new wavemodel to waveform object
     * 
     * @param ch channel number
     * @param wm new wavemodel
     */
    public void setWaveModel(int ch, DefaultWaveModel wm) {
        // TODO kursorit kanssa päivitettävä
        waves.elementAt(ch).addWaveModel(wm);
        cursor.addVerticalScale(ch, wm.getVerticalScale());
        cursor.addHorizontalScale(wm.getHorizontalScale());
    }

    /** Function returns wavemodel of channel
     * 
     * @param ch channel where to get wavemodel
     * @return wavemodel
     */
    
    public DefaultWaveModel getWavemodel(int ch) {
        return(waves.elementAt(ch).getWaveModel());
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setAutoscrolls(true);
        setMaximumSize(new java.awt.Dimension(160, 400));
        setMinimumSize(new java.awt.Dimension(160, 400));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
        });
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
    }// </editor-fold>//GEN-END:initComponents
    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
        // unselct all cursors
        cursor.setSelectedCursor(PlotCursor.CURSOR_SELECT_NONE);          
    }//GEN-LAST:event_formMouseReleased

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        // get current mouse point/
        Point p = evt.getPoint();
        // check cursor selection
        cursor.activateCursor(p);
        
    }//GEN-LAST:event_formMousePressed

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        // get mouse position
        Point p = evt.getPoint();

        // check position validity and make move
        if ((cursor.getSelectedCursor() == PlotCursor.CURSOR_SELECT_X1)) {
            cursor.setCursor(PlotCursor.CURSOR_SELECT_X1, evt.getPoint().x);
        }

        if ((cursor.getSelectedCursor() == PlotCursor.CURSOR_SELECT_X2)) {
            cursor.setCursor(PlotCursor.CURSOR_SELECT_X2, evt.getPoint().x);
        }

        if ((cursor.getSelectedCursor() == PlotCursor.CURSOR_SELECT_Y1)) {
            cursor.setCursor(PlotCursor.CURSOR_SELECT_Y1, evt.getPoint().y);
        }

        if ((cursor.getSelectedCursor() == PlotCursor.CURSOR_SELECT_Y2)) {
            cursor.setCursor(PlotCursor.CURSOR_SELECT_Y2, evt.getPoint().y);
        }

        repaint();        
    }//GEN-LAST:event_formMouseDragged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
