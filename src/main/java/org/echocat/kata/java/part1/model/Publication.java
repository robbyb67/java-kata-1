package org.echocat.kata.java.part1.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public abstract class Publication {
	private List<Author> authors;
	private String title;
	private String isbn;

	public String toString() {
		return "authors=" + this.getAuthors() + ", title=" + this.getTitle() + ", isbn=" + this.getIsbn();
	}
}
