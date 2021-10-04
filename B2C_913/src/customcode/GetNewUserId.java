package customcode;

import com.ibm.rational.test.lt.kernel.services.ITestExecutionServices;

public class GetNewUserId implements com.ibm.rational.test.lt.kernel.custom.ICustomCode2 {

	public GetNewUserId() {}
	
	
	public String exec(ITestExecutionServices tes, String[] args) {
		String current_s = args[ 0 ];
		String end_s = args[ 1 ];
		
		int current = Integer.parseInt( current_s ) + 1;
		int end = Integer.parseInt( end_s );
		String domain = args[ 2 ];
		
		if ( current > end ) {
			tes.getLoopControl().breakLoop();
		}
		
		current_s = String.valueOf( current );
		
		tes.setValue( "userid", ITestExecutionServices.STORAGE_USER, "hclu" + current_s + "@" + domain );
		tes.setValue( "userid_current", ITestExecutionServices.STORAGE_USER, current_s );
		tes.setValue( "lastname", ITestExecutionServices.STORAGE_USER, "u" + current_s );
		
		tes.getTestLogManager().reportMessage( "userid = " + current_s );
		
		return null;
	}
}
