package customcode;

import com.ibm.rational.test.lt.kernel.services.ITestExecutionServices;

/**
 * @author unknown
 */
public class BuildPartialTerm implements com.ibm.rational.test.lt.kernel.custom.ICustomCode2 {

	/**
	 * Instances of this will be created using the no-arg constructor.
	 */
	public BuildPartialTerm() {
	}

	/**
	 * For javadoc of ICustomCode2 and ITestExecutionServices interfaces, select 'Help Contents' in the
	 * Help menu and select 'Extending Rational Performance Tester functionality' -> 'Extending test execution with custom code'
	 */
	public String exec(ITestExecutionServices tes, String[] args) {
		
		
		String searchTerm = args[0];
		String pos = args[1];
		int iKeywordTermPos = Integer.parseInt(pos);
		String partial_searchterm = new String();
		
		if (searchTerm.length() >= iKeywordTermPos) {
			partial_searchterm = searchTerm.substring(0,iKeywordTermPos);
		
			tes.setValue( "partial_searchterm", ITestExecutionServices.STORAGE_USER, partial_searchterm);
			tes.setValue( "keywordTermPos", ITestExecutionServices.STORAGE_USER, String.valueOf(iKeywordTermPos) );

			tes.getTestLogManager().reportMessage( "BuildPartialTerm: partial_searchterm = " + partial_searchterm );
			tes.getTestLogManager().reportMessage( "BuildPartialTerm: keywordTermPos = " + String.valueOf(iKeywordTermPos) );
		}
	
		return partial_searchterm;
	}

}
