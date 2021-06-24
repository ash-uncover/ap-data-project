package org.ap.data.utils.csv;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CsvField {
	String name();
	boolean isId() default false;
	boolean isUnique() default false;
	String format() default "dd.MM.yyyy";
}