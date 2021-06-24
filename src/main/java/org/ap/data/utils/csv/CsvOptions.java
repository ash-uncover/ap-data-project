package org.ap.data.utils.csv;

import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class CsvOptions {

	/* STATIC */
	
	public static final Charset CHARSET = Charset.forName("UTF-16");
	public static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
	
	public static final String CSV_SEPARATOR = ";";
	public static final String CSV_DELIMITER= "\"";
	
	/* ATTRIBUTES */
	
	private Charset charset = CHARSET;
	private DateFormat dateFormat = DATE_FORMAT;
	private String separator = CSV_SEPARATOR;
	private String delimiter = CSV_DELIMITER;
	
	/* CONSTRUCTORS */
	
	public CsvOptions() {}
	
	public CsvOptions charset(Charset charset) {
		this.charset = charset;
		return this;
	}
	public CsvOptions dateFormat(DateFormat dateFormat) {
		this.dateFormat = dateFormat;
		return this;
	}
	public CsvOptions separator(String separator) {
		this.separator = separator;
		return this;
	}
	public CsvOptions delimiter(String delimiter) {
		this.delimiter = delimiter;
		return this;
	}
	
	/* METHODS */

	public Charset getCharset() {
		return charset;
	}
	public DateFormat getDateFormat() {
		return dateFormat;
	}
	public String getSeparator() {
		return separator;
	}
	public String getDelimiter() {
		return delimiter;
	}
}
