/***********************************************************
 * Software: instrument gui library
 * Module:   Scope wave model class
 * Version:  0.1
 * Licence:  GPL2
 *
 * Owner: Kim Kristo
 * Date creation : 5.9.2013
 *
 ***********************************************************/

package oh3ebf.lib.gui.models;


public class ScopeWaveModel extends DefaultWaveModel {
    
    public ScopeWaveModel() {
        super(0);
        
       
    }

    /** Function generates test data
     * 
     */
    public void testData() {
        int frequency = 1000;   // Hz
        int amplitude = 127;    // Vpp / 2
        int sampleRate = 8000;  // hz
        int duration = 1;       // s
        int numberOfSamples = duration * sampleRate;

        /* generate test data */
        for (int i = 0; i < numberOfSamples; i++) {
            data.add(new Double((int) (Math.sin(frequency * i * 2 * Math.PI / sampleRate) * amplitude)));
        //System.out.println((int)(Math.sin(frequency * i * 2 * Math.PI / sampleRate) * amplitude));
        }

        horizontalScale = new LinearTimeScale(0.002D, 1.0D / sampleRate);
    }

    
}
