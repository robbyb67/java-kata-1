package org.echocat.kata.java.part1.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Magazine extends Publication{
	LocalDate publishedAt;

	@Builder
	public Magazine(List<Author> authors, String title, String isbn, LocalDate publishedAt) {
		super(authors, title, isbn);
		this.publishedAt = publishedAt;
	}

	@Override
	public String toString() {
		return "Magazine(" + super.toString()  + ", publishedAt=" + this.getPublishedAt().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + ")";
	}
}
