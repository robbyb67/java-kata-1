package org.echocat.kata.java.part1.impl;

import java.util.List;

import org.echocat.kata.java.part1.api.LibraryService;
import org.echocat.kata.java.part1.model.Publication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LibraryServiceImpl implements LibraryService {

	LibraryStorage storage;

	public LibraryServiceImpl(@Autowired LibraryStorage libraryStorage) {
		this.storage = libraryStorage;
	}

	@Override
	public List<Publication> getInventory() {
		return storage.getInventory();
	}

	@Override
	public ResponseEntity<Publication> getByIsbn(@PathVariable("isbn") String isbn) {
		try {
			return ResponseEntity.ok(storage.getByIsbn(isbn));
		} catch (IndexOutOfBoundsException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@Override
	public ResponseEntity<Publication> getByIsbn(@PathVariable("isbn") String isbn,
			@PathVariable("type") String publicationType) {
		try {
			return ResponseEntity.ok(storage.getByIsbn(isbn, PublicationType.valueOf(publicationType)));
		} catch (IndexOutOfBoundsException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@Override
	public List<Publication> getByAuthorsEmail(@PathVariable("email") String email) {
		return storage.getByAuthorsEmail(email);
	}
}
