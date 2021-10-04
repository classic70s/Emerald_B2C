package customcode;

import com.ibm.rational.test.lt.kernel.services.ITestExecutionServices;

public class ShouldWeDeleteFromCart implements com.ibm.rational.test.lt.kernel.custom.ICustomCode2 {
	public ShouldWeDeleteFromCart() {}
	
	// Get randomly a yes to delete from cart or a no to avoid deleting from cart 
	
	public String exec(ITestExecutionServices tes, String[] args) {
		// If we have 1 or less items in the cart, we should go to delete items from cart
		if ( Integer.parseInt( args[ 0 ] ) <= 1 ) {
			return "no";
		}
		// Get a random number between 0 and 1. If it's greater than 0.8, delete, if not, avoid deleting 
		double i = Math.random();
		tes.getTestLogManager().reportMessage( "random i = " + String.valueOf( i ) );
		
		String res;
		if ( i >= 0.8 ) {
			res = "yes";
		}
		else {
			res = "no";
		}
		return res;
	}
}
