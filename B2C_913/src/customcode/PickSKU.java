package customcode;

//import java.util.Arrays;
import java.util.Iterator;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonStreamParser;
import com.ibm.rational.test.lt.kernel.services.ITestExecutionServices;

public class PickSKU implements com.ibm.rational.test.lt.kernel.custom.ICustomCode2 {

	public PickSKU() {}
	public String exec(ITestExecutionServices tes, String[] args) {
		String json_str = args[ 0 ].toString().trim();
		if ( ! json_str.contains( "contents" ) ) {
			tes.getTestLogManager().reportMessage( "contents does not exist - at json_str.contains( \"contents\" )" );
			return "-1";
		}
		JsonStreamParser p = new JsonStreamParser( json_str );
		int nbr_values = 0;
		String id, identifier, partnumber, category_id; 
		String ids = "", identifiers = "", partnumbers = "", category_ids = "";
		String[] parts;
				
		JsonElement e = p.next();
		if ( e.isJsonNull() ) {
			tes.getTestLogManager().reportMessage( "Json is null - at JsonElement e = p.next()" );
			return "-1";
		}
		
		JsonObject content = e.getAsJsonObject();
		JsonArray oneContent = content.get( "contents" ).getAsJsonArray();
		if ( oneContent.isJsonNull()	) {
			tes.getTestLogManager().reportMessage( "Json is null - at JsonArray oneContent = content.get( \"contact\" ).getAsJsonArray()" );
			return "-1";
		}
		
		JsonElement element;
		JsonObject json_object;
		Iterator<JsonElement> iterator = oneContent.iterator();
		element = iterator.next();
		if ( element.isJsonNull() ) {
			tes.getTestLogManager().reportMessage( "Json is null" );
			return "-1";
		}
				
		json_object = element.getAsJsonObject();
		if ( ! json_object.has( "items" ) ) {
			tes.getTestLogManager().reportMessage( "Json does not have items" );
			return "-1";
		}
		
		JsonArray items = json_object.get( "items" ).getAsJsonArray();
		if ( items.isJsonNull()	) {
			tes.getTestLogManager().reportMessage( "Json is null - at JsonArray items = content.get( \"items\" ).getAsJsonArray()" );
			return "-1";
		}
		
		JsonElement elem;
		Iterator<JsonElement> iter = items.iterator();
		while ( iter.hasNext() ) {
			elem = iter.next();
			if ( elem.isJsonNull() ) {
				tes.getTestLogManager().reportMessage( "Json is null - at elem = iterator.next()" );
				return "-1";
			}
			
			json_object = elem.getAsJsonObject();
			
			id = json_object.get( "id" ).getAsString();
			// tes.getTestLogManager().reportMessage( "id          = " + id );
			
			if ( id.isEmpty() ) {
				tes.getTestLogManager().reportMessage( "There is no id" );
				return "-1";
			}
			
			identifier = json_object.get( "name" ).getAsString();
			// identifier = json_object.get( "seo" ).toString().replace("{\"href\":\"/", "").replace("\"}", "");
			// tes.getTestLogManager().reportMessage( "identifier          = " + identifier );
			
			if ( identifier.isEmpty() ) {
				tes.getTestLogManager().reportMessage( "There is no identifier" );
				return "-1";
			}
			
			partnumber = json_object.get( "partNumber" ).getAsString();
			// tes.getTestLogManager().reportMessage( "partnumber          = " + partnumber );
			
			if ( partnumber.isEmpty() ) {
				tes.getTestLogManager().reportMessage( "There is no partnumber" );
				return "-1";
			}
			
			// category_id = json_object.get( "parentCatalogGroupID" ).getAsString();
			category_id = json_object.get( "parent" ).getAsString();
			if ( category_id.isEmpty() ) {
				tes.getTestLogManager().reportMessage( "There is no category_id" );
				return "-1";
			}
			tes.getTestLogManager().reportMessage( "parentCatalogGroupID         = " + category_id );
			
			// parts = category_id.split( "/" );
			parts = category_id.split( "=" );
			if ( parts.length > 0 ) {
				category_id = parts[ 1 ];
			} 
			else {
				tes.getTestLogManager().reportMessage( "There is no '=' in category_id" );
				return "-1";
			}
			// category_id = category_id.substring( 0, category_id.length() - 2 );
			tes.getTestLogManager().reportMessage( "category_id         = " + category_id );
			
			nbr_values++;
			ids += id + ",";
			identifiers += identifier + ",";
			partnumbers += partnumber + ",";
			category_ids += category_id + ",";
		}
		
		if ( nbr_values > 0 ) {
			identifiers  = identifiers.substring( 0, identifiers.length() - 1 );
			ids          = ids.substring( 0, ids.length() - 1 );
			partnumbers  = partnumbers.substring( 0, partnumbers.length() - 1 );
			category_ids = category_ids.substring( 0, category_ids.length() - 1 );
			
			// tes.getTestLogManager().reportMessage( "ids          = " + ids );
			// tes.getTestLogManager().reportMessage( "identifiers  = " + identifiers );
			// tes.getTestLogManager().reportMessage( "part_numbers = " + partnumbers );
			// tes.getTestLogManager().reportMessage( "category_ids = " + category_ids );
			
			// Get one random category
			int n = (int) Math.floor( Math.random() * nbr_values );
			String[] a_ids = ids.split( "," );
			String[] a_identifiers = identifiers.split( "," );
			String[] a_part_numbers = partnumbers.split( "," );
			String[] a_category_ids = category_ids.split( "," );
			
			// tes.getTestLogManager().reportMessage( "a_ids       = " + Arrays.toString( a_ids ) );
			
			id = a_ids[ n ];
			identifier = a_identifiers[ n ];
			partnumber = a_part_numbers[ n ];
			category_id = a_category_ids[ n ];
			
			tes.setValue( "sku_identifier", ITestExecutionServices.STORAGE_USER, identifier );
			tes.setValue( "sku_id", ITestExecutionServices.STORAGE_USER, id );
			tes.setValue( "sku_partnumber", ITestExecutionServices.STORAGE_USER, partnumber );
			tes.setValue( "category_id", ITestExecutionServices.STORAGE_USER, category_id );
			
			tes.getTestLogManager().reportMessage( "part_number picked = " + partnumber );
			
			return "0";
		}
		else {
			tes.getTestLogManager().reportMessage( "Number of items is zero" );
			return "-1";
		}
	}
}
