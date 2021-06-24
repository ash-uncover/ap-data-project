package org.ap.data.utils.csv;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Stream;

public abstract class CsvContent<T> {
	
	/* ATTRIBUTES */
	
	private List<T> _content;
	
	/* CONSTRCUTORS */
	
	protected CsvContent() {
		_content = new ArrayList<T>();
	}

	/* METHODS */
	
	@SuppressWarnings("unchecked")
	public Class<T> getInnerClass() {
		return (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append(CsvUtils.toCsvHeaders(getInnerClass())).append("\n");
		for (T row: _content) {
			result.append(CsvUtils.toCsvString(row)).append("\n");
		}
		return result.toString();
	}
	
	public void writeToFile(String path) throws IOException {
		writeToFile(path, new CsvOptions());
	}
	public void writeToFile(String path, CsvOptions options) throws IOException {
		FileOutputStream fos = new FileOutputStream(path);
		OutputStreamWriter osw = new OutputStreamWriter(fos, options.getCharset());
		try {
			// Write headers
			osw.append(toString());
		
		} catch (IOException e) {
			throw e;
		} finally {
			osw.close();
			fos.close();
		}
	}

	public void loadFromFile(String sPath) {
		loadFromFile(sPath, new CsvOptions());
	}
	
	public void loadFromFile(String sPath, CsvOptions options) {
		Path path = Paths.get(sPath);
		Stream<String> stream = null;		
		try {
			stream = Files.lines(path, options.getCharset());
			String[] headers = CsvUtils.readLine(stream.findFirst().get(), options);
			stream.close();
			
			stream = Files.lines(path, options.getCharset());	
			stream.skip(1).forEach(line -> {
				try {
					String[] cells = CsvUtils.readLine(line, options);
					T object = (T)getInnerClass().newInstance();
					for (int i = 0 ; i < headers.length ; i++) {
						String header = headers[i];
						String cell = cells.length >= i + 1 ? cells[i] : null;
						if (cell != null) {							
							for (Field field: getInnerClass().getDeclaredFields()) {
								field.setAccessible(true);
								if (field.isAnnotationPresent(CsvField.class)) {
									CsvField csvAnnotation = field.getAnnotation(CsvField.class);
									String name = csvAnnotation.name();
									String format = csvAnnotation.format();
									if (name.equals(header)) {
										Class<?> type = field.getType();
										if (type.equals(boolean.class)) {
											field.set(object, new Boolean(cell.trim()));
										} else if (type.equals(byte.class)) {
											field.set(object, new Byte(cell.trim()));
										} else if (type.equals(short.class)) {
											field.set(object, new Short(cell.trim()));
										} else if (type.equals(int.class)) {
											field.set(object, new Integer(cell.trim()));
										} else if (type.equals(long.class)) {
											field.set(object, new Long(cell.trim()));
										} else if (type.equals(float.class)) {
											field.set(object, new Float(cell.trim()));
										} else if (type.equals(double.class)) {
											field.set(object, new Double(cell.trim()));
										} else if (type.equals(Date.class)) {
											try {
												DateFormat dateFormat = options.getDateFormat();
												if (format != null) {													
													dateFormat = new SimpleDateFormat(format, Locale.ENGLISH);
												}
												field.set(object, dateFormat.parse(cell.trim()));
											} catch (ParseException e) {
												// We simply don't set the field
											}
										} else if (type.equals(Set.class)) {
											Set<String> set = new HashSet<String>();
											for (String value : cell.toString().split(",")) {
												set.add(value);
											}
											field.set(object, set);
										} else {							
											field.set(object, cell.toString());
										}
										break;
									}
								}
							}
						}
					}
					_content.add(object);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				}
			});

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (stream != null) {				
				stream.close();
			}
		}
	}
	
	public Collection<T> values() {
		return _content;
	}
	
	
	public void remove(T value) {
		_content.remove(value);
	}
}
