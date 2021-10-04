package customcode;

import com.ibm.rational.test.lt.kernel.services.ITestExecutionServices;

public class GetPartialSearchterm implements com.ibm.rational.test.lt.kernel.custom.ICustomCode2 {

	public GetPartialSearchterm() {}

	public String exec(ITestExecutionServices tes, String[] args) {
		/*
		 * 	Return continue  if partial_searchterm is still smaller than searchterm. Processing need to continue
		 *  Return stop      if partial_searchterm is already equal to searchterm. Processing need to stop
		*/
		
		String searchterm, partial_searchterm;
		searchterm = args[ 0 ];
		partial_searchterm = args[ 1 ];
		
		
		// If partial_searchterm is empty and we're able to add 3 characters, then do so and return
		if ( partial_searchterm.length() == 0 ) {
			if ( searchterm.length() > 3 ) {
				partial_searchterm = searchterm.substring( 0, 3 );
				tes.getTestLogManager().reportMessage( "We took 3 characters from searchterm to partial_searchterm" );
				tes.getTestLogManager().reportMessage( "Partial searchterm =  " + partial_searchterm );
				tes.setValue( "partial_searchterm", ITestExecutionServices.STORAGE_USER, partial_searchterm );
				return "continue";
			}
			if ( searchterm.length() == 3 ) {
				tes.getTestLogManager().reportMessage( "We took 3 characters from searchterm to partial_searchterm" );
				tes.getTestLogManager().reportMessage( "Partial searchterm =  " + searchterm );
				tes.setValue( "partial_searchterm", ITestExecutionServices.STORAGE_USER, searchterm );
				return "stop";
			}
			// Here, we're sure that searchterm is less than 3 characters
			tes.getTestLogManager().reportMessage( "We took all the characters from searchterm to partial_searchterm as it's less than 3 characters" );
			tes.getTestLogManager().reportMessage( "Partial searchterm =  " + searchterm );
			tes.setValue( "partial_searchterm", ITestExecutionServices.STORAGE_USER, searchterm );
			return "stop";
		}
		// If partial and full search term string are equal, then return 
		if ( searchterm.equals( partial_searchterm ) ) {
			tes.setValue( "partial_searchterm", ITestExecutionServices.STORAGE_USER, searchterm );
			tes.getTestLogManager().reportMessage( "Both strings are equal" );
			tes.getTestLogManager().reportMessage( "Partial searchterm =  " + searchterm );
			return "stop";
		}
		
		// If not equal, then add 3 characters from the full to the partial
		if ( partial_searchterm.length() + 3 < searchterm.length() ) {
			partial_searchterm += searchterm.substring( partial_searchterm.length(), partial_searchterm.length() + 3 );
			tes.setValue( "partial_searchterm", ITestExecutionServices.STORAGE_USER, partial_searchterm );
			tes.getTestLogManager().reportMessage( "Adding 3 characters to partial_searchterm is bigger than searchterm" );
			tes.getTestLogManager().reportMessage( "Partial searchterm =  " + partial_searchterm );
			return "continue";
		}
		
		// Here, we're sure that the remaining characters from searchterm are less than 3, so we add all of them and stop the processing
		partial_searchterm = searchterm;
		tes.getTestLogManager().reportMessage( "We're still able to add 3 characters to partial_searchterm" );
		tes.getTestLogManager().reportMessage( "Partial searchterm =  " + partial_searchterm );
		tes.setValue( "partial_searchterm", ITestExecutionServices.STORAGE_USER, partial_searchterm );
		return "stop";
	}
}
