package customcode;

import com.ibm.rational.test.lt.kernel.services.ITestExecutionServices;

/**
 * @author unknown
 */
public class MixSearchTerms implements com.ibm.rational.test.lt.kernel.custom.ICustomCode2 {

	/**
	 * Instances of this will be created using the no-arg constructor.
	 */
	public MixSearchTerms() {
	}

	/**
	 * For javadoc of ICustomCode2 and ITestExecutionServices interfaces, select 'Help Contents' in the
	 * Help menu and select 'Extending Rational Performance Tester functionality' -> 'Extending test execution with custom code'
	 */
	public String exec(ITestExecutionServices tes, String[] args) {

		String searchTerm = args[ 0 ];
		String mixedSearchTerm = args[ 1 ];
		String combinedTerm = searchTerm + " " + mixedSearchTerm;
		
		return combinedTerm;
	}


}
