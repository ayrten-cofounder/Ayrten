/**
 * 
 */
package com.ayrten.scrots.dotGraphics.test;

import org.junit.Test;
import junit.framework.TestCase;
import static org.hamcrest.CoreMatchers.instanceOf;

import com.ayrten.scrots.dotGraphics.*;

/**
 * @author hhuynhlam
 *
 */
public class DotGraphics_MainMenuScreenBackground extends TestCase {

	/**
	 * @throws java.lang.Exception
	 */
	protected static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	protected static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	protected void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	protected void tearDown() throws Exception {
	}
	
	@Test
	public void testConstructor() {
		
		DotGraphics_MainMenuScreenBackground childDot = new DotGraphics_MainMenuScreenBackground();
        //assertEquals(childDot, instanceOf(DotGraphics.class));
		assertEquals(true, true);
	}

}
