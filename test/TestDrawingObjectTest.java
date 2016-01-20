/***********************************************************
 * Software: instrument gui library
 * Module:   Test cases for instrument gui library
 * Version:  0.1
 * Licence:  GPL2
 *
 * Owner: Kim Kristo
 * Date creation : 4.9.2012
 *
 ***********************************************************/

import junit.framework.*;

public class TestDrawingObjectTest extends TestCase {
    
    public TestDrawingObjectTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws java.lang.Exception {
    }

    @Override
    protected void tearDown() throws java.lang.Exception {
    }

    public static junit.framework.Test suite() {
        junit.framework.TestSuite suite = new junit.framework.TestSuite(TestDrawingObjectTest.class);
        
        return suite;
    }

    /**
     * Test of main method, of class TestDrawingObject.
     */
    public void testMain() {
        System.out.println("testMain");
        
        // TODO add your test code below by replacing the default call to fail.
        //fail("The test case is empty.");
        testScope();
        //testSpectrum();
    }
    
    // TODO add test methods here. The name must begin with 'test'. For example:
    public void testScope() {
        
        TestScopeObject t = new TestScopeObject();
        t.setSize(640,480);
        t.setLocationRelativeTo(null);
        t.setVisible(true);
        while(t.exit());                
    }
    
    public void testSpectrum() {        
        TestSpectrumObject t = new TestSpectrumObject();
        t.setSize(565,480);
        t.setLocationRelativeTo(null);
        t.setVisible(true);
        while(t.exit());                
    }
    
}
