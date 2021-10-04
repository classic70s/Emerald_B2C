package customcode;

import java.util.Iterator;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonStreamParser;
import com.ibm.rational.test.lt.kernel.services.ITestExecutionServices;

public class PickOneFacet implements com.ibm.rational.test.lt.kernel.custom.ICustomCode2 {
	public PickOneFacet() {}
	public String exec(ITestExecutionServices tes, String[] args) {
		String json_str = args[ 0 ].toString().trim();
		
		JsonStreamParser p = new JsonStreamParser( json_str );
		String value, name, label;
		String values = "", names = "", labels = "";
				
		// Get content json object
		JsonElement e = p.next();
		JsonObject content = e.getAsJsonObject();
		JsonArray oneContent = content.get( "facets" ).getAsJsonArray();
		if( oneContent.isJsonNull() ) {
			tes.getTestLogManager().reportMessage( "String \"facets\" does not exist in the json file" );
			return "-1";
		}
		
		JsonArray oneFacet;
		
		int nbr_values = 0;
		JsonElement elem;
		JsonObject facets;
		Iterator<JsonElement> iterator = oneContent.iterator();
		while ( iterator.hasNext() ) {
			elem = iterator.next();
			facets = elem.getAsJsonObject();
			
			value = facets.get( "value" ).getAsString();
			name  = facets.get( "name" ).getAsString();
			
			nbr_values++;

			values += value + ",";
			names += name + ",";
			
			tes.getTestLogManager().reportMessage( "Value = " + value + ", Name  = " + name );
		}
		if ( nbr_values > 0 ) {
			tes.getTestLogManager().reportMessage( "Number of facets found = " + String.valueOf( nbr_values ) );
			values = values.substring( 0, values.length() - 1 );
			names  = names.substring( 0, names.length() - 1 );

			// Get one random facet object
			int n = (int) Math.floor( Math.random() * nbr_values );
			
			String[] a_names  = names.split( "," );
			String[] a_values = values.split( "," );
			String[] a_labels;
			
			name  = a_names[ n ];
			value = a_values[ n ];
			
			tes.getTestLogManager().reportMessage( "Facet picked, name  = " + name + ", value = " + value );
			
			// Loop on facets and pick the one it was already picked above
			JsonStreamParser p2 = new JsonStreamParser( json_str );
			JsonElement e2 = p2.next();
			JsonObject content2 = e2.getAsJsonObject();
			JsonArray oneContent2 = content2.get( "facets" ).getAsJsonArray();
			if ( content2.isJsonNull() ) {
				tes.getTestLogManager().reportMessage( "String \"facets\" does not exist in the json file" );
				return "-1";
			}
			Iterator<JsonElement> iterator2 = oneContent2.iterator();
			while ( iterator2.hasNext() ) {
				elem = iterator2.next();
				facets = elem.getAsJsonObject();
				
				tes.getTestLogManager().reportMessage( "Current name  = " + facets.get( "name" ).getAsString() );
				
				if ( value.equals( facets.get( "value" ).getAsString() ) && name.equals( facets.get( "name" ).getAsString() ) ) {
					// Loop through the entries (facets)
					oneFacet = facets.get( "entry" ).getAsJsonArray();
					Iterator<JsonElement> facet_iterator = oneFacet.iterator();
					nbr_values = 0;
					values = "";
					while ( facet_iterator.hasNext() ) {
						elem = facet_iterator.next();
						facets = elem.getAsJsonObject();
						if ( facets.get( "count" ).getAsString().equals( "0" ) ) {
							continue;
						}
						value = facets.get( "value" ).getAsString();
						label = facets.get( "label" ).getAsString();
						
						tes.getTestLogManager().reportMessage( "Facet value = " + value );
						
						nbr_values++;
						values += value + ",";
						labels += label + ",";
					}
					if ( nbr_values > 0 ) {
						tes.getTestLogManager().reportMessage( "values = " + values );
						
						values = values.substring( 0, values.length() - 1 );
						
						if ( nbr_values == 1 ) { 
							value = values;
							label = labels;
						}
						else {
							// Get one random facet
							n = (int) Math.floor( Math.random() * nbr_values );
							a_values = values.split( "," );
							a_labels = labels.split( "," );
							value = a_values[ n ];
							label = a_labels[ n ];
						}
						
						tes.getTestLogManager().reportMessage( "Facet picked: value = " + value + ", label = " + label );
						
						if ( name.contains( "price" ) ) {
							label = label.replace( "({","" );
							label = label.replace( "])","" );
							a_labels = label.split( " TO " );
							value = "maxPrice=" + a_labels[ 1 ] + "&minPrice=" + a_labels[ 0 ];
						}
						else {
							//if ( name.contains( "ParentCatalogGroup" ) || name.contains( "Brand" ) ) {
							if ( name.contains( "ParentCatalogGroup" ) ) {
								value = "facet=path.tree:" + value;
							}
							else {
								value = "facet=" + value;
							}
						}
						tes.getTestLogManager().reportMessage( "Facet picked: value = " + value );
						tes.setValue( "facet", ITestExecutionServices.STORAGE_USER, value );
						return value;
					}
					else {
						tes.getTestLogManager().reportMessage( "We didn't find any facet in the search response" );
						return "-1";
					}
				}
			}
		}
		tes.getTestLogManager().reportMessage( "We didn't find any facet in the search response" );
		return "-1";
	}
}
