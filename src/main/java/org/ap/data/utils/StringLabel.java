package org.ap.data.utils;

public class StringLabel {

	/* ATTRIBUTES */

	private String baseLabel;
	private String[] words;

	/* CONSTRUCTORS */

	public StringLabel(String s) {
		this(s, false);
	}
	public StringLabel(String s, boolean canBeOneWord) {
		setLabel(s, canBeOneWord);
		this.baseLabel = s;
	}

	/* METHODS */

	private void setLabel(String s, boolean canBeOneWord) {
		if (s.contains("_")) {
			// We assume that the separator is '_' (source = snake case)
			words = s.split("_");
		} else if (s.contains(" ")) {
			// We assume that the separator is ' '  (source = capital case)
			words = s.split(" ");
		} else if (canBeOneWord && !s.matches(".*[a-z].*")) {
			// Maybe there was only one word in the end
			words = new String[] { s };
		} else {
			// We assume that the separator is upper case letters  (source = camel or pascal case)
			words = s.split("(?=\\p{Lu})");
		}
	}

	public String toSnakeCase() {
		String result = "";
		boolean first = true;
		for (String w : words) {
			if (!w.trim().equals("")) {
				if (first) {
					first = false;
					result += w.toLowerCase();
				} else {
					result += "_" + w.toLowerCase();
				}
			}
		}
		return result;
	}
	
	public String toSnakeUpper() {
		return toSnakeCase().toUpperCase();
	}

	
	public String toWormCase() {
		String result = "";
		boolean first = true;
		for (String w : words) {
			if (!w.trim().equals("")) {
				if (first) {
					first = false;
					result += w.toLowerCase();
				} else {
					result += "-" + w.toLowerCase();
				}
			}
		}
		return result;
	}

	public String toCamelCase() {
		String result = "";
		boolean first = true;
		for (String w : words) {
			if (!w.trim().equals("")) {
				if (first) {
					first = false;
					result += w.toLowerCase();
				} else {
					result += StringUtils.capitalizeWord(w);
				}
			}
		}
		return result;
	}

	public String toPascalCase() {
		String result = "";
		for (String w : words) {
			if (!w.trim().equals("")) {
				result += StringUtils.capitalizeWord(w);
			}
		}
		return result;
	}

	public String toCapitalCase() {
		String result = "";
		boolean first = true;
		for (String w : words) {
			if (!w.trim().equals("")) {
				if (first) {
					first = false;
					result += w.toUpperCase();
				} else {
					result += " " + w.toUpperCase();
				}
			}
		}
		return result;
	}
	
	
	public boolean equals(Object o) {
		if (o instanceof StringLabel) {
			return ((StringLabel)o).toString().equals(this.toString());
		}
		return false;
	}
	public String toString() {
		return this.baseLabel;
	}
}