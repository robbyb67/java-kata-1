package org.echocat.kata.java.part1.api;

import java.util.List;

import org.echocat.kata.java.part1.model.Publication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface LibraryService {

	@GetMapping("/publications")
	public List<Publication> getInventory();

	@GetMapping("publication/{isbn}")
	public ResponseEntity<Publication> getByIsbn(@PathVariable("isbn") String isbn);

	@GetMapping("publication/{type}/{isbn}")
	public ResponseEntity<Publication> getByIsbn(@PathVariable("isbn") String isbn, @PathVariable("type") String publicationType);

	@GetMapping("publications/{email}")
	public List<Publication> getByAuthorsEmail(@PathVariable("email") String email);

}
