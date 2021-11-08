package customcode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ibm.rational.test.lt.kernel.services.ITestExecutionServices;

public class GetTotalProducts implements com.ibm.rational.test.lt.kernel.custom.ICustomCode2 {
	public GetTotalProducts() {}
	public String exec(ITestExecutionServices tes, String[] args) {
		String pattern = "\"total\":([0-9]+)";
		String result = "";
		Pattern r = Pattern.compile( pattern );
		Matcher m = r.matcher( args[ 0 ] );
	    if ( m.find() ) {
	    	tes.getTestLogManager().reportMessage( m.group( 1 ) );
	    	result = m.group( 1 );
	    }
	    else {
	    	tes.getTestLogManager().reportMessage( "No result" );
	    	result = "-1";
	    }
		return result;
	}
}
