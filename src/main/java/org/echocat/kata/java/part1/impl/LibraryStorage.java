package org.echocat.kata.java.part1.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.echocat.kata.java.part1.model.Author;
import org.echocat.kata.java.part1.model.Publication;
import org.echocat.kata.java.part1.util.DataReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LibraryStorage implements ApplicationListener<ApplicationReadyEvent> {
	public static final String AUTHOR_CSV = "org/echocat/kata/java/part1/data/authors.csv";
	public static final String BOOKS_CSV = "org/echocat/kata/java/part1/data/books.csv";
	public static final String MAGAZINES_CSV = "org/echocat/kata/java/part1/data/magazines.csv";

	private List<Author> authors;
	private List<Publication> publications;

	private DataReader dataReader;

	public LibraryStorage(@Autowired DataReader dataReader) {
		this.dataReader = dataReader;
		publications = new ArrayList<>();
	}

	public List<Publication> getInventory() {
		return publications.stream()
				.sorted(Comparator.comparing(Publication::getTitle))
				.collect(Collectors.toList());
	}

	public Publication getByIsbn(String isbn) {
		return publications.stream()
				.filter(s -> s.getIsbn().equalsIgnoreCase(isbn))
				.collect(Collectors.toList()).get(0);
	}

	public Publication getByIsbn(String isbn, PublicationType type) {
		return publications.stream()
				.filter(s -> type == PublicationType.ofClass(s) )
				.filter(s -> s.getIsbn().equalsIgnoreCase(isbn))
				.collect(Collectors.toList()).get(0);
	}

	public List<Publication> getByAuthorsEmail(String email) {
		return publications.stream()
				.filter(s -> {
					for( Author author : s.getAuthors() ) {
						if (author.getEmail().equalsIgnoreCase(email)) {
							return true;
						}
					}
					return false;
				})
				.collect(Collectors.toList());
	}

	@Override
	public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
		this.authors = dataReader.readAuthors(ClassLoader.getSystemResourceAsStream(AUTHOR_CSV));
		this.publications.addAll(dataReader.readBooks(ClassLoader.getSystemResourceAsStream(BOOKS_CSV), authors));
		this.publications.addAll(dataReader.readMagazines(ClassLoader.getSystemResourceAsStream(MAGAZINES_CSV), authors));

		log.info("Loaded publications list");
		for( Publication publication : publications) {
			log.info(publication.toString());
		}
	}
}
