package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.BookDto;
import org.example.dto.UserDTO;
import org.example.model.Book;
import org.example.model.User;
import org.example.service.LibraryService;
import org.example.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("Library")
@Slf4j
public class LibraryController {
    LibraryService libraryService;
    UserService userService;

    public LibraryController(LibraryService libraryService,
                             UserService userService) {
        this.libraryService = libraryService;
        this.userService = userService;
    }


    @PostMapping(value = "/addUser", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addUser(@RequestBody UserDTO user) {
        userService.addUser(user.getEmailId(), user.getName(), user.getDays());
        return new ResponseEntity<>("User created0", HttpStatus.OK);
    }


    @PostMapping(value = "/user", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUser(@RequestBody String userId) {
        log.info("User is {}", userId);
        User user = userService.getUser(userId);
        log.info("User fetched {}", user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @PostMapping(value = "/borrow", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> borrow(@RequestBody BookDto bookDto) {
        String bookName = libraryService.borrowBook(bookDto.getUserId(), bookDto.getBookName());
        log.info("Book Borrowed {}", bookName);
        return new ResponseEntity<>(bookName, HttpStatus.OK);
    }


    @PostMapping(value = "/return", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> returnBook(@RequestBody BookDto bookDto) {
        String bookName = libraryService.returnBook(bookDto.getUserId(), bookDto.getBookName());
        log.info("Book returned {}", bookName);
        return new ResponseEntity<>(bookName, HttpStatus.OK);
    }


    @PostMapping(value = "/activeOrders", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getActiveOrder(@RequestBody String userId) {
        List<String> books = libraryService.getAllActiveBooksForUser(userId);
        log.info("Book actives {}", books);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }


    @PostMapping(value = "/pastOrders", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getPastOrder(@RequestBody String userId) {
        List<String> books = libraryService.getAllPastBooksForUser(userId);
        log.info("Book past {}", books);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }


    @PostMapping(value = "/inventory", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Book>> showInventory() {
        List<Book> books = libraryService.showInventory();
        log.info("Book past {}", books);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }


}
