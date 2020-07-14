package org.echocat.kata.java.part1.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.echocat.kata.java.part1.model.Author;
import org.echocat.kata.java.part1.model.Book;
import org.echocat.kata.java.part1.model.Magazine;
import org.springframework.stereotype.Component;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DataReader {

	private CSVParser parser;
	public DataReader() {
		parser = new CSVParserBuilder()
				.withSeparator(';')
				.withIgnoreQuotations(true)
				.build();
	}

	public List<Author> readAuthors(InputStream is) {
		List<Author> authors = new ArrayList<>();
		try(InputStreamReader reader = new InputStreamReader(is)) {
			CSVReader csvReader = new CSVReaderBuilder(reader)
					.withSkipLines(1)
					.withCSVParser(parser)
					.build();
			String[] line;
			while ((line = csvReader.readNext()) != null) {
				try {
					authors.add(Author.builder()
							.email(line[0])
							.firstName(line[1])
							.lastName(line[2])
							.build());
				} catch(Exception e) {
					log.warn("Error reading author: {}", line);
				}
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		return authors;
	}

	public List<Book> readBooks(InputStream is, List<Author> knownAuthors) {
		List<Book> books = new ArrayList<>();
		try(InputStreamReader reader = new InputStreamReader(is)) {
			CSVReader csvReader = new CSVReaderBuilder(reader)
					.withSkipLines(1)
					.withCSVParser(parser)
					.build();
			String[] line;
			while ((line = csvReader.readNext()) != null) {
				try {
					books.add(Book.builder()
							.title(line[0])
							.isbn(line[1])
							.authors(getAuthorsFromEmail(line[2].split(","), knownAuthors))
							.description(line[3])
							.build());
				} catch(Exception e) {
					log.warn("Error reading book: {}", line);
				}
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		return books;
	}

	public List<Magazine> readMagazines(InputStream is, List<Author> knownAuthors) {
		List<Magazine> magazines = new ArrayList<>();
		try(InputStreamReader reader = new InputStreamReader(is)) {
			CSVReader csvReader = new CSVReaderBuilder(reader)
					.withSkipLines(1)
					.withCSVParser(parser)
					.build();
			String[] line;
			while ((line = csvReader.readNext()) != null) {
				try {
					magazines.add(Magazine.builder()
							.title(line[0])
							.isbn(line[1])
							.authors(getAuthorsFromEmail(line[2].split(","), knownAuthors))
							.publishedAt(LocalDate.parse(line[3], DateTimeFormatter.ofPattern("dd.MM.yyyy")))
							.build());
				} catch(Exception e) {
					log.warn("Error reading magazine: {}", line);
				}
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		return magazines;
	}

	private List<Author> getAuthorsFromEmail(String[] authorEmails, List<Author> knownAuthors) {
		List<Author> authors = new ArrayList<>();
		authors.addAll(knownAuthors.stream()
					.filter(s -> {
						for(String email : authorEmails) {
							if (s.getEmail().equalsIgnoreCase(email)) {
								return true;
							}
						}
						return false;
					})
					.collect(Collectors.toList())
		);
		return authors;
	}
}
