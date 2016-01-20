/***********************************************************
 * Software: instrument gui library
 * Module:   linear frequency scale class
 * Version:  0.1
 * Licence:  GPL2
 *
 * Owner: Kim Kristo
 * Date creation : 6.9.2013
 *
 ***********************************************************/
package oh3ebf.lib.gui.models;

public class LinearFrequencyScale extends ScaleBase {
    protected double frequencySpan;
    protected double frequencyStart;
    protected double frequencyEnd;
    protected double pixWidth;
    
    public LinearFrequencyScale(double span) {
        super();
        frequencyStart = 1.0D;
        frequencyEnd = 1.0D;
        frequencySpan = 1.0D;
        pixWidth = 1;
    }

    /** Creates a new instance of LinearFrequencyScale */
    public LinearFrequencyScale(double width, double start, double end) {
        super();
        pixWidth = width;
        frequencyStart = start;
        frequencyEnd = end;
        frequencySpan = end - start;        
        
        updateVariables();
    }

    /** Function sets new frequency scale in seconds
     *
     * @param start of scale
     * @param end of scale
     *
     */
    public void setFrequencyScale(double start, double end) {
        
        frequencyStart = start;
        frequencyEnd = end;   
        
        updateVariables();
    }

    @Override
    public void updateScale(int width) {
        pixWidth = width;
        
        updateVariables();
    }
    
    private void updateVariables() {
        // calculate how meny pixels per sample is needed
        pix = pixWidth / frequencySpan;
        System.out.println("fr pix " + pix);
    }
}
