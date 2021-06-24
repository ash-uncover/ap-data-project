package org.ap.data.utils.csv;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Stream;

import org.ap.data.utils.StringUtils;

public class CsvUtils {

	public static String[] readLine(String line) {
		return readLine(line, new CsvOptions());
	}
	public static String[] readLine(String line, CsvOptions options) {
		Stream<String> stream = Arrays.stream(line.split(options.getSeparator()));
		stream = stream.map(x -> {
			if (x.equals("")) {
				return null;
			}
			return StringUtils.removePrefixSuffix(x, options.getDelimiter());
		});
		return stream.toArray(String[]::new);
	}
	
	public static String toCsvHeaders(Class<?> clazz) {
		return toCsvHeaders(clazz, new CsvOptions());
	}
	public static String toCsvHeaders(Class<?> clazz, CsvOptions options) {
		StringBuilder result = new StringBuilder();
		if (clazz != null ) {			
			boolean isFirst = true;
			for (Field f : clazz.getDeclaredFields()) {
				if (f.isAnnotationPresent(CsvField.class)) {
					String name = f.getAnnotation(CsvField.class).name().trim();
					if (isFirst) {
						isFirst = false;
					} else {
						result.append(options.getSeparator());
					}
					result.append(options.getDelimiter()).append(name).append(options.getDelimiter());
				}
			}
		}
		return result.toString();
	}

	public static String toCsvString(Object obj) {
		return toCsvString(obj, new CsvOptions());
	}
	public static String toCsvString(Object obj, CsvOptions options) {
		StringBuilder result = new StringBuilder();
		if (obj != null ) {			
			boolean isFirst = true;
			for (Field field : obj.getClass().getDeclaredFields()) {
				field.setAccessible(true);
				if (field.isAnnotationPresent(CsvField.class)) {
					CsvField fieldAnnotation = field.getAnnotation(CsvField.class);
					String name = fieldAnnotation.name();
					String format = fieldAnnotation.format();
					
					if (isFirst) {
						isFirst = false;
					} else {
						result.append(options.getSeparator());
					}
					
					Class<?> type = field.getType();
					
					try {
						Object value = field.get(obj);
						if (value != null) {
							result.append(options.getDelimiter());
							if (type.equals(Date.class)) {
								DateFormat dateFormat = options.getDateFormat();
								if (format != null) {													
									dateFormat = new SimpleDateFormat(format, Locale.ENGLISH);
								}
								value = dateFormat.format(value);
							} else if (type.equals(Set.class)) {
								String valueString = "";
								@SuppressWarnings("unchecked")
								Set<String> set = (Set<String>)value;
								boolean setIsFirst = true;
								for (String s : set) {
									if (!setIsFirst) {
										valueString += ",";
									} else {
										setIsFirst = false;
									}
									valueString += s;
								}
								value = valueString;
							}
							result.append(value);
							result.append(options.getDelimiter());
						}
					} catch (Exception e) {
						System.err.println("Failed to access '" + name + "' attribute.");
						e.printStackTrace();
					}
				}
			}
		}
		return result.toString();
	}
	
	public static String toCsvDetails(Object obj) {
		return toCsvDetails(obj, new CsvOptions());
	}
	public static String toCsvDetails(Object obj, CsvOptions options) {
		StringBuilder result = new StringBuilder();
		if (obj != null ) {	
			for (Field f : obj.getClass().getDeclaredFields()) {
				f.setAccessible(true);
				if (f.isAnnotationPresent(CsvField.class)) {
					String name = f.getAnnotation(CsvField.class).name();
					try {
						result.append(name).append(" : ").append(f.get(obj)).append("\n");
					} catch (Exception e) {
						System.err.println("Failed to access '" + name + "' attribute.");
					}
				}
			}
		}
		return result.toString();
	}
}
