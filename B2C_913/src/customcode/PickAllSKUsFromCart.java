package customcode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.ibm.rational.test.lt.kernel.services.ITestExecutionServices;

/**
 * @author Noureddine
 */
public class PickAllSKUsFromCart implements com.ibm.rational.test.lt.kernel.custom.ICustomCode2 {
	public PickAllSKUsFromCart() {}
	public String exec(ITestExecutionServices tes, String[] args) {
		String content = args[ 0 ];
		String ids = "";
		
		Pattern pattern = Pattern.compile( "\"productId\":\"(.*?)\"" );
		Matcher matcher = pattern.matcher( content );
		while ( matcher.find() ) {
			ids += matcher.group( 1 ) + ",";
		}
		if ( ids.length() > 0 ) {
			ids = ids.substring( 0, ids.length() - 1 );
		}
		else {
			ids = "-1";
		}
		return ids;
	}
}
