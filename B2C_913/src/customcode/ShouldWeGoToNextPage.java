package customcode;

import com.ibm.rational.test.lt.kernel.services.ITestExecutionServices;

public class ShouldWeGoToNextPage implements com.ibm.rational.test.lt.kernel.custom.ICustomCode2 {
	public ShouldWeGoToNextPage() {}
	public String exec(ITestExecutionServices tes, String[] args) {
		// Get randomly a yes or a no
		double i = Math.random();
		tes.getTestLogManager().reportMessage( "random i = " + String.valueOf( i ) );
		
		String res;
		if ( i >= 0.5 ) {
			// Calculate the offset
			int current_page = Integer.parseInt( args[ 0 ] );
			int total_product = Integer.parseInt( args[ 1 ] );
			int number_of_pages = (int) Math.ceil( total_product / 12 );
			if ( current_page <= number_of_pages ) {
				res = "yes";
				current_page++;
				int offset = current_page * 12;  
				tes.setValue( "offset", ITestExecutionServices.STORAGE_USER, String.valueOf( offset ) );
				tes.setValue( "current_page", ITestExecutionServices.STORAGE_USER, String.valueOf( current_page ) );
				tes.getTestLogManager().reportMessage( "current_page = " + String.valueOf( current_page ) + ", offset = " + String.valueOf( offset ) );
			}
			else {
				res = "no";
			}
		}
		else {
			res = "no";
		}
		return res;
	}
}
