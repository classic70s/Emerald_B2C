package customcode;

import com.ibm.rational.test.lt.kernel.services.ITestExecutionServices;
import java.util.Random;

public class PickSearchtermWithCacheMisses implements com.ibm.rational.test.lt.kernel.custom.ICustomCode2 {
	public PickSearchtermWithCacheMisses() {}
	public String exec(ITestExecutionServices tes, String[] args) {
		/*
		 * Original configuration - 
		 * 0 - Between term0000000* to term0001915* 	=> 7 or 8 products 
		 * 1 - Between term000000*  to term000190*   	=> 64 products
		 * 2 - term000*, term00*, term0*, term* 		=> 11465 products
		 * 3 - Between term00010* to 14* 				=> 727 products
		 * 4 - 20% of the time add a string from random_words datapool to searchterm 
		 * 
		 * New configuration - 
		 * 0 - 20% of the time add a string from datapool random_words to the searchterm
		 * 1 - Between term0000000* to term0001915* 	=> 7 or 8 products 
		 * 2 - Between term000000*  to term000190*   	=> 64 products
		 * 3 - term000*, term00*, term0*, term* 		=> 11465 products
		 * 4 - Between term00010* to 14* 				=> 727 products
		 *
		 * Using the old config now
		 *   
		 */
		
		int max_datapools = 4;		// Number of possibilities as shown in the comment above
		int low, high;
		String str = "";
		
		Random rand = new Random();
		// int r = rand.nextInt( max_datapools );
		int r = ( int )( Math.floor( max_datapools * Math.pow( Math.random(), 7 ) ) );
		
		tes.getTestLogManager().reportMessage( "1st r picked = " + r );
		
		switch ( r ) {
			case 4:
				low = 0;
				high = 1915;
				r = rand.nextInt( high - low ) + low;
				str = "term000" + Integer.toString( r ) + "*";
				break;
			case 0:
				r = rand.nextInt( max_datapools - 1 );
				tes.getTestLogManager().reportMessage( "2nd r picked = " + r );
				switch ( r ) {
					case 0:
						low = 0;
						high = 1915;
						r = rand.nextInt( high - low ) + low;
						str = "term000" + Integer.toString( r ) + "*";
						break;
					case 1:
						low = 0;
						high = 190;
						r = rand.nextInt( high - low ) + low;
						str = "term000" + Integer.toString( r ) + "*";
						break;
					case 2:
						r = rand.nextInt( 3 );
						str = new String(new char[ r ]).replace("\0", "0");
						str = "term" + str + "*";
						break;
					case 3:
						low = 10;
						high = 14;
						r = rand.nextInt( high - low ) + low;
						str = "term000" + Integer.toString( r ) + "*";
						break;
				}
				str += " " + args[ 0 ];
				break;
			case 1:
				low = 0;
				high = 190;
				r = rand.nextInt( high - low ) + low;
				str = "term000" + Integer.toString( r ) + "*";
				break;
			case 2:
				r = rand.nextInt( 3 );
				str = new String(new char[ r ]).replace("\0", "0");
				str = "term" + str + "*";
				break;
			case 3:
				low = 10;
				high = 14;
				r = rand.nextInt( high - low ) + low;
				str = "term000" + Integer.toString( r ) + "*";
				break;
		}
		tes.getTestLogManager().reportMessage( "searchterm picked = " + str );
		return str;
	}
}
