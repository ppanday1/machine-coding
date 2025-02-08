package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@Data
@AllArgsConstructor
public class Book {
    private String name;
    private String author;
    private Genre genre;
    private double rentPrice;
    private AtomicInteger stockCount;

    public synchronized boolean rentBook() {
        if (stockCount.get() > 0) {
            stockCount.decrementAndGet();
            return true;
        }
        return false;
    }

    public synchronized boolean addBook() {
        stockCount.incrementAndGet();
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return name.equals(book.name) && author.equals(book.author) && genre == book.genre;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, author, genre);
    }
}
