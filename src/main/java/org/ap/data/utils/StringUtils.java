package org.ap.data.utils;

public class StringUtils {

	public static String capitalizeWord(String s) {
		if (s != null && s.length() > 0) {
			return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
		}
		return ""; 
	}
	
	public static String removePrefix(String s, String prefix) {
		if (s.startsWith(prefix)) {
			return s.substring(prefix.length());
		}
		return s;
	}
	
	public static String removeSuffix(String s, String suffix) {
		if (s.endsWith(suffix)) {
			return s.substring(0, s.length() - suffix.length());
		}
		return s;
	}
	
	public static String removePrefixSuffix(String s, String fix) {
		String result = removePrefix(s, fix);
		return removeSuffix(result, fix);
	}
}
