package customcode;

//import java.util.concurrent.locks.Lock;
//import java.util.concurrent.locks.ReentrantLock;
import com.ibm.rational.test.lt.kernel.services.ITestExecutionServices;

public class PickUser implements com.ibm.rational.test.lt.kernel.custom.ICustomCode2 {
	public PickUser() {}
	public String exec(ITestExecutionServices tes, String[] args) {
		int min_user = Integer.parseInt( args[ 0 ] );
		int max_user = Integer.parseInt( args[ 1 ] );
		String prefix_username = args[ 2 ];
		int current_user = Integer.parseInt( args[ 3 ] );
		
		//Lock alock = new ReentrantLock();
		//alock.lock();
		
		// Next user
		current_user++;
		
		// Go to the top in case we're at the bottom
		if ( current_user > max_user ) {
			current_user = min_user;
		}
		
		//alock.unlock();
		
		String user = prefix_username + Integer.toString( current_user );
		tes.getTestLogManager().reportMessage( "User picked = " + user );
		
		// Update current_user outside of this code 
		tes.setValue( "current_user", ITestExecutionServices.STORAGE_USER, Integer.toString( current_user ) );
		
		return user;
	}
}
