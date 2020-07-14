package org.echocat.kata.java.part1.impl;

import org.echocat.kata.java.part1.model.Book;
import org.echocat.kata.java.part1.model.Magazine;

public enum PublicationType {
	BOOK,
	MAGAZINE;

	Class publicationClass;

	public static PublicationType ofClass(Object o) {
		if ( o instanceof Book) {
			return BOOK;
		} else if( o instanceof Magazine ) {
			return MAGAZINE;
		} else {
			throw new IllegalArgumentException("Unknown publication class " + o.getClass().getSimpleName());
		}
	}
}
