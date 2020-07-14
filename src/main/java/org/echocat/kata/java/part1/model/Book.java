package org.echocat.kata.java.part1.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Book extends Publication{
	private String description;

	@Builder
	public Book(List<Author> authors, String title, String isbn, String description) {
		super(authors, title, isbn);
		this.description = description;
	}

	@Override
	public String toString() {
		return "Book(" + super.toString() + ", description=" + this.getDescription() + ")";
	}
}
