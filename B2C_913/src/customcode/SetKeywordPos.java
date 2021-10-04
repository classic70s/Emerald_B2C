package customcode;

import com.ibm.rational.test.lt.kernel.services.ITestExecutionServices;

/**
 * @author unknown
 */
public class SetKeywordPos implements com.ibm.rational.test.lt.kernel.custom.ICustomCode2 {

	/**
	 * Instances of this will be created using the no-arg constructor.
	 */
	public SetKeywordPos() {
	}

	/**
	 * For javadoc of ICustomCode2 and ITestExecutionServices interfaces, select 'Help Contents' in the
	 * Help menu and select 'Extending Rational Performance Tester functionality' -> 'Extending test execution with custom code'
	 */
	public String exec(ITestExecutionServices tes, String[] args) {
		
		String keywordTermPos = args[0];
		
		int ikeywordTermPos = Integer.valueOf(keywordTermPos);
		
		int iKeywordTermNewPos = ikeywordTermPos + 1;
		
		tes.setValue( "keywordTermPos", ITestExecutionServices.STORAGE_USER, String.valueOf(iKeywordTermNewPos) );

		return String.valueOf(iKeywordTermNewPos);
	}

}
