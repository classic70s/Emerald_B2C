package customcode;

import java.util.Iterator;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonStreamParser;
import com.ibm.rational.test.lt.kernel.services.ITestExecutionServices;

public class GetAddressId implements com.ibm.rational.test.lt.kernel.custom.ICustomCode2 {
	public GetAddressId() {}
	public String exec(ITestExecutionServices tes, String[] args) {
		String json_str = args[ 0 ].toString().trim();
		if ( ! json_str.contains( "contact" ) ) {
			tes.getTestLogManager().reportMessage( "contact does not exist - at json_str.contains( \"contact\" )" );
			return "-1";
		}
		JsonStreamParser p = new JsonStreamParser( json_str );
		int nbr_values = 0;
		String addressId, addressIds = "";
		
		JsonElement e = p.next();
		if ( e.isJsonNull() ) {
			tes.getTestLogManager().reportMessage( "Json is null - at JsonElement e = p.next()" );
			return "-1";
		}
		JsonObject contacts = e.getAsJsonObject();
		JsonArray oneContact = contacts.get( "contact" ).getAsJsonArray();
		if ( oneContact.isJsonNull() ) {
			tes.getTestLogManager().reportMessage( "Json is null - at JsonArray oneContent = content.get( \"contact\" ).getAsJsonArray()" );
			return "-1";
		} 
		
		JsonElement element;
		JsonObject json_object;
		Iterator<JsonElement> iterator = oneContact.iterator();
		
		while ( iterator.hasNext() ) {
			element = iterator.next();
			if ( element.isJsonNull() ) {
				tes.getTestLogManager().reportMessage( "Json is null - at element = iterator.next()" );
				return "-1";
			}
						
			json_object = element.getAsJsonObject();
			
			addressId = json_object.get( "addressId" ).getAsString();
			tes.getTestLogManager().reportMessage( "addressId          = " + addressId );
			
			if ( addressId.isEmpty() ) {
				tes.getTestLogManager().reportMessage( "There is no addressId or addressId is empty" );
				return "-1";
			}
			
			addressIds += addressId + ",";
			nbr_values++;
		}
		if ( nbr_values > 0 ) {
			addressIds  = addressIds.substring( 0, addressIds.length() - 1 );
			
			// Get one random category
			int n = (int) Math.floor( Math.random() * nbr_values );
			String[] a_addressIds = addressIds.split( "," );
						
			addressId = a_addressIds[ n ];
			
			tes.setValue( "addressId", ITestExecutionServices.STORAGE_USER, addressId );
			tes.getTestLogManager().reportMessage( "addressId picked = " + addressId );
			
			return addressId;
		}
		else {
			tes.getTestLogManager().reportMessage( "Number of contacts is zero" );
			return "-1";
		}
		
		/*
		// ------------------------------------------------------------------------------------------------
		String pattern = "\"addressId\":\"([0-9]+?)\",";
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
		*/
	}
}
