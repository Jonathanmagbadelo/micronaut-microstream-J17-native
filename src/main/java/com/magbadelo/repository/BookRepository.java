package com.magbadelo.repository;

import com.magbadelo.dto.Author;
import com.magbadelo.dto.Book;

import java.util.List;

public interface BookRepository {
    public void add(Book book);
    public List<Book> findAllBooks();
    public List<Author> findAllAuthors();
    public void deleteAll();
    public void storeAll();
}
