package customcode;

import com.ibm.rational.test.lt.kernel.services.ITestExecutionServices;

/**
 * @author unknown
 */
public class GetSearchTermLength implements com.ibm.rational.test.lt.kernel.custom.ICustomCode2 {

	/**
	 * Instances of this will be created using the no-arg constructor.
	 */
	public GetSearchTermLength() {
	}

	/**
	 * For javadoc of ICustomCode2 and ITestExecutionServices interfaces, select 'Help Contents' in the
	 * Help menu and select 'Extending Rational Performance Tester functionality' -> 'Extending test execution with custom code'
	 */
	public String exec(ITestExecutionServices tes, String[] args) {
		
		int keywordTermLength = args[0].length();
		tes.getTestLogManager().reportMessage( "GetSearchTermLength: searchTerm = " + args[0] );
		tes.getTestLogManager().reportMessage( "GetSearchTermLength: keywordTermLength = " + keywordTermLength );

		return String.valueOf(keywordTermLength);
	}

}
