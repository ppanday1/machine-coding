package org.example.respository;

import org.example.model.Book;
import org.example.model.Genre;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class BookRepository {
    private final ConcurrentHashMap<String, Book> books;

    public BookRepository() {
        books = new ConcurrentHashMap<>();
        Book book1 = new Book("Harry", "A1", Genre.HORROR, 20.0, new AtomicInteger(1));
        Book book2 = new Book("Harry2", "A1", Genre.HORROR, 20.0, new AtomicInteger(3));
        Book book3 = new Book("Harry3", "A1", Genre.HORROR, 20.0, new AtomicInteger(5));
        Book book4 = new Book("Harry4", "A1", Genre.HORROR, 20.0, new AtomicInteger(0));
        books.put(book1.getName(), book1);
        books.put(book2.getName(), book2);
        books.put(book3.getName(), book3);
        books.put(book4.getName(), book4);
    }

    public Book getBookByName(String name) {
        return books.get(name);
    }

    public boolean addBook(String bookName, String author, String genre, double price) {
        books.computeIfAbsent(bookName, k -> new Book(bookName, author, Genre.valueOf(genre), price, new AtomicInteger(0))).addBook();
        return true;
    }

    public boolean addBook(String bookName) {
        if (!books.containsKey(bookName)) {
            return false;
        }
        books.get(bookName).addBook();
        return true;
    }

    public boolean rentBook(String bookName) {
        Book book = books.get(bookName);
        if (book == null) {
            return false;
        }
        return book.rentBook();
    }

    public List<Book> getAllAvailableBooks() {
        List<Book> allBooks = new ArrayList<>(books.values());
        return allBooks.stream().filter(e -> e.getStockCount().get() > 0).collect(Collectors.toList());
    }
}
