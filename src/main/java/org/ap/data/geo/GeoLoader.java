package org.ap.data.geo;

import java.nio.charset.Charset;

import org.ap.data.utils.csv.CsvContent;
import org.ap.data.utils.csv.CsvOptions;

public class GeoLoader {

	public static class CountriesCsv extends CsvContent<Country> {}
	
	/* ATTRIBUTES */
	
	private CountriesCsv countriesCsv = new CountriesCsv();
	
	/* CONSTRUCTOR */
	
	public GeoLoader () {
		
	}
	
	public void load() {
		String countriesFile = "C:\\Temp\\data\\COUNTRIES.csv";
	}
	
	/* METHODS */
	
	public static void main(String[] args) {
		
		String countriesFile = "C:\\Temp\\data\\COUNTRIES - Copy.csv";
		String countriesTargetFile = "C:\\Temp\\data\\COUNTRIES.csv";
		CsvOptions countriesOptions = new CsvOptions().separator(";").delimiter("").charset(Charset.forName("UTF-8"));
		
		try {
			GeoLoader extractor = new GeoLoader();
			
			// Load data
			extractor.countriesCsv.loadFromFile(countriesFile, countriesOptions);
			for (Country c : extractor.countriesCsv.values()) {
				c.addName(c.name);
			}
			System.out.println(extractor.countriesCsv);
			extractor.countriesCsv.writeToFile(countriesTargetFile, countriesOptions);
			/*
			extractor.csvCovidCountries.loadFromFile(covidCountriesFile, covidCountriesOptions);
			
			for (CovidCountriesEntity entity : extractor.csvCovidCountries.values()) {
				Country c = extractor.csvCountries.get(entity.country);
				if (c == null) {
					c = new Country(entity.country);
					extractor.csvCountries.add(c);
				}
				c.addName(entity.country);
			}
			System.out.println(extractor.csvCovidCountries);
			
			extractor.csvCountries.writeToFile(countriesFile);
			*/
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
