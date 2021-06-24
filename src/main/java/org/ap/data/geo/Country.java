package org.ap.data.geo;

import java.util.HashSet;
import java.util.Set;

import org.ap.data.utils.csv.CsvField;
import org.ap.data.utils.data.DataField;

public class Country {

	/* ATTRIBUTES */
	
	@CsvField(name="ISO_ALPHA2")
	@DataField(isUnique=true)
	public String isoAlpha2;
	
	@CsvField(name="ISO_ALPHA3")
	@DataField(isUnique=true)
	public String isoAlpha3;
	
	@CsvField(name="ISO_NUMERIC")
	@DataField(isUnique=true)
	public String isoNumeric;
	
	@CsvField(name="COUNTRY_NAME")
	@DataField(isUnique=true)
	public String name;
	
	@CsvField(name="COUNTRY_NAMES")
	public Set<String> names;
	
	/* CONSTRUCTORS */
	
	public Country() {
		names = new HashSet<String>();
	}

	/* METHODS */
	
	public boolean hasName(String name) {
		return names.contains(name);
	}

	public void addName(String name) {
		names.add(name);
	}
}
