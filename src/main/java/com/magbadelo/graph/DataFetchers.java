package com.magbadelo.graph;

import com.magbadelo.dto.Author;
import com.magbadelo.dto.Book;
import com.magbadelo.repository.MicroStreamBookRepository;
import graphql.schema.DataFetcher;
import jakarta.inject.Singleton;

@Singleton
public class DataFetchers {

    private final MicroStreamBookRepository microStreamBookRepository;

    public DataFetchers(MicroStreamBookRepository microStreamBookRepository) {
        this.microStreamBookRepository = microStreamBookRepository;
    }

    public DataFetcher<Book> getBookByIdDataFetcher() {
        return dataFetchingEnvironment -> {
            String bookId = dataFetchingEnvironment.getArgument("id");
            return microStreamBookRepository.findAllBooks().stream()
                    .filter(book -> book.id().equals(bookId))
                    .findFirst()
                    .orElse(null);
        };
    }

    public DataFetcher<Author> getAuthorDataFetcher() {
        return dataFetchingEnvironment -> {
            Book book = dataFetchingEnvironment.getSource();
            Author authorBook = book.author();
            return microStreamBookRepository.findAllAuthors()
                    .stream()
                    .filter(author -> author.id().equals(authorBook.id()))
                    .findFirst()
                    .orElse(null);
        };
    }
}
