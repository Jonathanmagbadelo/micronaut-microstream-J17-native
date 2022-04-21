package com.magbadelo.repository;

import com.magbadelo.dto.Author;
import com.magbadelo.dto.Book;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Singleton;
import one.microstream.storage.embedded.types.EmbeddedStorage;
import one.microstream.storage.embedded.types.EmbeddedStorageManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class MicroStreamBookRepository implements BookRepository {

    private static final Logger LOG = LoggerFactory.getLogger(MicroStreamBookRepository.class);

    private List<Book> books;
    private final EmbeddedStorageManager storageManager;

    public MicroStreamBookRepository() {
        this.storageManager = EmbeddedStorage.start();
        loadAll();
    }


    @Override
    public void add(Book book) {
        this.books.add(book);
        this.storeAll();
    }

    @Override
    public List<Book> findAllBooks() {
        return this.books;
    }

    @Override
    public List<Author> findAllAuthors() {
        return findAllBooks().stream()
                .map(Book::author)
                .toList();
    }

    @Override
    public void deleteAll() {
        this.books.clear();
        this.storeAll();
    }

    @Override
    public void storeAll() {
        this.storageManager.storeAll(this.books);
    }

    @Override
    public void loadAll() {
        var root = storageManager.root();
        if (root instanceof List<?> loadedBooks) {
            this.books = loadedBooks.stream().filter(e -> e instanceof Book).map(e -> (Book) e).toList();
            LOG.info("Started preloaded MicroStreamBookRepository with {} books!", books.size());
        } else {
            this.books = new ArrayList<>();
            storageManager.setRoot(books);
            add(new Book("1", "Book-One", 100, new Author("1", "Jon", "M")));
            add(new Book("2", "Book-Two", 200, new Author("2", "Jon", "Ma")));
            add(new Book("3", "Book-Three", 300, new Author("3", "Jon", "Mag")));
            add(new Book("4", "Book-Four", 400, new Author("4", "Jon", "Magu")));
            storageManager.storeRoot();
            LOG.info("Started MicroStreamBookRepository with {} books!", books.size());
        }
    }

    @PreDestroy
    public void shutdown() {
        storageManager.shutdown();
    }

}
