package org.echocat.kata.java.part1.util;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.echocat.kata.java.part1.impl.LibraryStorage;
import org.echocat.kata.java.part1.model.Author;

import org.echocat.kata.java.part1.model.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
class DataReaderTest {

	@Autowired
	DataReader dataReader;

	@Test
	void readAuthors() {
		List<Author> authors = dataReader.readAuthors(ClassLoader.getSystemResourceAsStream(LibraryStorage.AUTHOR_CSV));
		assertNotNull(authors);
		Assertions.assertTrue(!authors.isEmpty());
	}

	@Test
	void readBooks() {
		List<Author> authors = dataReader.readAuthors(ClassLoader.getSystemResourceAsStream(LibraryStorage.AUTHOR_CSV));
		assertNotNull(authors);
		Assertions.assertTrue(!authors.isEmpty());

		List<Book> books = dataReader.readBooks(ClassLoader.getSystemResourceAsStream(LibraryStorage.BOOKS_CSV), authors);
		assertNotNull(books);
		Assertions.assertTrue(!books.isEmpty());
	}

	@Test
	void readMagazines() {
		List<Author> authors = dataReader.readAuthors(ClassLoader.getSystemResourceAsStream(LibraryStorage.AUTHOR_CSV));
		assertNotNull(authors);
		Assertions.assertTrue(!authors.isEmpty());

		List<Book> books = dataReader.readBooks(ClassLoader.getSystemResourceAsStream(LibraryStorage.MAGAZINES_CSV), authors);
		assertNotNull(books);
		Assertions.assertTrue(!books.isEmpty());
	}
}
