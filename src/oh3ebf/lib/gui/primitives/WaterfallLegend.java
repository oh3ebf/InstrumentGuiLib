/***********************************************************
 * Software: instrument gui library
 * Module:   Waterfaal plot color legend base class
 * Version:  0.1
 * Licence:  GPL2
 *
 * Owner: Kim Kristo
 * Date creation : 23.8.2013
 *
 ***********************************************************/
package oh3ebf.lib.gui.primitives;

import java.awt.Color;

public class WaterfallLegend {

    public static float max;
    static final float DEFAULTMAX = 200;  	// Default value for max
    static Color[] colors; 			// set of colors to use
    static final int NCOLORS = 256;            // Numbers of steps in the colorscale
    private static boolean log;
    private static boolean instantiated = false;
    private static WaterfallLegend wl;

    private WaterfallLegend(float setmax) {
        colors = new Color[NCOLORS + 1];
        float h = 0.0F;
        
        for (int i = 0; i < 255; i++) {
            // Setting up a color scale
            // Goes along the egdes of the RGB color cube
            // cf http://www.poirrier.be/~jean-etienne/info/clibs/gd-rainbow.php
            // Red (255,0,0) to yellow (255,255,0)
            //colors[NCOLORS - i] = makeARGB(0, 255, i, 0);
            // Yellow to green (0,255,0)
            //colors[NCOLORS - (i + 256)] = makeARGB(0, 255 - i, 255, 0);
            // Green to cyan (0,255,255)
            //colors[NCOLORS - (i + 512)] = makeARGB(0, 0, 255, i);
            // cyan to blue (0,0,255)
            //colors[NCOLORS - (i + 768)] = makeARGB(0, 0, 255 - i, 255);
            // blue to mangenta( 255,0,255)
            //colors[NCOLORS - (i + 1024)] = makeARGB(0, i, 0, 255);
            // Should go back to red, but
            // add the first -i to go down into black (0,0,0)
            // rather than back to red (255,0,0) to make an unique color scale
            //colors[NCOLORS - (i + 1280)] = makeARGB(0, 255 - i, 0, 255 - i); // Fades down into black
            
            //colors[i] = Color.getHSBColor((float)i+100, 0.5F,  0.5F);
            
            colors[i] = new Color(255 * i / 255, 255 * (255 - i) / 255, 0);
           
            
        }

        max = setmax;
    }

    private Color makeARGB(int a, int r, int g, int b) {
        // Creates an integer color value from an argb quartet. (0-255) 
        // (a=alpha cannel, transparency, set to 0 for non transparent colors
        System.out.println("r " + r +", g "+ g +", b " +    b);
        return new Color(r, g, b, a);
    }

    public static WaterfallLegend getWaterfallLegend(float max) {
        if (instantiated == false) {
            wl = new WaterfallLegend(max);
            // we now have instance
            instantiated = true;
        }

        return (wl);
    }

    public float getMax() {
        return log ? (float) Math.log(max) : max;
    }

    public void setLog(boolean setlog) {
        log = setlog;
    }

    public boolean getLog() {
        return log;
    }

    public Color getColorValue(int index) throws Exception {
        if(instantiated != true) {
            throw new Exception();
        }
        
        return (colors[index]);
    }
}
