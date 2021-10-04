package customcode;

import java.util.Arrays;
import java.util.Random;
import com.ibm.rational.test.lt.kernel.services.ITestExecutionServices;

/*
     Inputs:
        - min_items_in_cart: Min in the cart
        - max_items_in_cart: Max in the cart
        - fixed_number_of_items_in_cart: 
            - fixed  => To make the number of items in the cart fixed. It's set to max_items_in_cart  
            - random => To make the number of items in the cart random. It's randomly between min and max
    Output:
        - 0 :  All good. Number of items in the cart is set (whether it's fixed or random)
        - -1:  Run will be stopped. Either min > max or min <= 0 or fixed_number_of_items_in_cart not in ( random, fixed )   
*/

public class SetNumberOfItemsInCart implements com.ibm.rational.test.lt.kernel.custom.ICustomCode2 {
	public SetNumberOfItemsInCart() {}
	// If a user set the argument to 0, 1 or < 0, the returned value will be 1 
	public String exec(ITestExecutionServices tes, String[] args) {
		String ns;
		int arg_min = Integer.parseInt( args[ 0 ] );
		int arg_max = Integer.parseInt( args[ 1 ] );
		String fixed_or_random = args[ 2 ].toLowerCase();
		String[] a_accepted_values = new String[]{ "fixed", "random" };
		
		if ( arg_min <= 0 || arg_min > arg_max ) {
			tes.getTestLogManager().reportMessage( "Error: min_items_in_cart is either less than zero or greater than the max_items_in_cart. Run will be stopped" );
			return "-1";
		}
		if ( ! Arrays.asList( a_accepted_values ).contains( fixed_or_random ) ) {
			tes.getTestLogManager().reportMessage( "Error: fixed_or_random can be equal to either to \"fixed\" or \"random\". Run will be stopped" );
			return "-1";
		}
		if ( fixed_or_random.equals( "random" ) ) {
			Random randomGenerator = new Random();
			int n = randomGenerator.nextInt( arg_max - arg_min + 1 ) + arg_min;
			if ( n == 0 ) { 
				n = 1;
			}
			ns = String.valueOf( n );
		}
		else {
			ns = args[ 1 ];
		}
		tes.getTestLogManager().reportMessage( "current_nbr_items_in_cart = " + ns );
		tes.setValue( "current_nbr_items_in_cart", ITestExecutionServices.STORAGE_USER, ns );
		return "0";
	}
}
