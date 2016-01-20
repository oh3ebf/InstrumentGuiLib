/***********************************************************
 * Software: instrument gui library
 * Module:   spectrum waveform drawing class
 * Version:  0.1
 * Licence:  GPL2
 *
 * Owner: Kim Kristo
 * Date creation : 20.9.2013
 *
 ***********************************************************/
package oh3ebf.lib.gui.primitives;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Enumeration;


public class SpectrumWaveForm extends WaveForm {

    public SpectrumWaveForm(String name) {
        super(name);
    }
    
    /** Function implement drawing algorithm for waveform 
     *
     * @param g graphics object to paint
     */
    @Override
    public void draw(Graphics2D g) {
        Point p = null, p_old = null;

        // is this object currently drawn
        if (visible) {
            // calculate wave position
            drawingOffset = 0;

            // check if gnd level is in use
            if (gnd != null) {
                // draw gnd symbol
                gnd.setPosition(componentSize.width - insets.right, drawingOffset);
                gnd.draw(g);
            }

            // set current drawing color
            g.setColor(penColor);

            // pitäisi osata piirtää jatkuvaviiva, sin x/x, pisteet, mitä muuta
            // miten hoidetaan kumulatiivinen piirto???
            // käyttää malliin tallennettua piirtodataa
            
            //TODO tämä vain väliaikaisesti tässä
            
            
            try {
                dataPoints = waveModel.getScaledWaveForm();
                Enumeration e = dataPoints.elements();

                while (e.hasMoreElements()) {
                    p = (Point) e.nextElement();

                    if (p_old == null) {
                        g.drawLine(p.x + insets.left, p.y + drawingOffset, p.x + insets.left, p.y + drawingOffset);
                    } else {

                        g.drawLine(p_old.x + insets.left, p_old.y + drawingOffset, p.x + insets.left, p.y + drawingOffset);
                    }

                    p_old = p;
                }
            } catch (Exception e) {
                // TODO tässä pitäisi heittää herkut
                System.out.println(e.getMessage());
            }
        }

    }
}
