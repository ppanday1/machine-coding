package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.config.Constant;
import org.example.model.Book;
import org.example.model.Order;
import org.example.model.User;
import org.example.respository.BookRepository;
import org.example.respository.OrderRepository;
import org.example.respository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LibraryService {
    BookRepository bookRepository;
    UserRepository userRepository;
    OrderRepository orderRepository;

    public LibraryService(BookRepository bookRepository,
                          UserRepository userRepository,
                          OrderRepository orderRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    public String borrowBook(String userId, String bookName) {
        User user = userRepository.getUser(userId);
        if (user == null || user.getMembershipEndDate().isBefore(LocalDate.now())) {
            log.error("User not exists or has expired");
            userRepository.deleteUser(userId);
            throw new IllegalArgumentException("User not found");
        }
        List<Order> allActiveOrderForUser = orderRepository.getAllActiveOrdersForUser(userId);
        for (Order order : allActiveOrderForUser) {
            if (Objects.equals(order.getBookName(), bookName)) {
                log.error("Book already rented for the same user");
                return null;
            }
        }
        if (allActiveOrderForUser.size() == Constant.BorrowPerUser) {
            log.error("User already borrow two books");
            return null;
        }

        if (!bookRepository.rentBook(bookName)) {
            log.error("Some error occurred");
            return null;
        }
        LocalDate oneMonthLater = LocalDate.now().plusDays((Constant.rentPeriod));
        LocalDate returnDate = user.getMembershipEndDate().isBefore(oneMonthLater) ? user.getMembershipEndDate() : oneMonthLater;
        Order order = new Order(userId, bookName, returnDate);
        orderRepository.addToActiveOrder(userId, order);
        return bookName;
    }

    public String returnBook(String userId, String bookName) {
        List<Order> activeOrders = orderRepository.getAllActiveOrdersForUser(userId);
        Order order = null;
        for (Order actOrder : activeOrders) {
            if (actOrder.getBookName().equals(bookName)) {
                order = actOrder;
                break;
            }
        }
        if (order == null) {
            log.error("No order for this user and book {} {}", userId, bookName);
            return "";
        }
        bookRepository.addBook(bookName);
        orderRepository.removeFromActiveOrder(userId, order);
        orderRepository.addToPastOrder(userId, order);
        double paymentAmount = 0;
        if (order.getReturnDate().isBefore(LocalDate.now())) {
            paymentAmount = Constant.rentRatePerDay * (ChronoUnit.DAYS.between(order.getReturnDate(), LocalDate.now()));
        }
        //Make payment
        return bookName;
    }

    public List<String> getAllActiveBooksForUser(String userId) {
        return orderRepository.getAllActiveOrdersForUser(userId).stream().map(Order::getBookName).collect(Collectors.toList());
    }

    public List<String> getAllPastBooksForUser(String userId) {
        return orderRepository.getAllPastOrdersForUser(userId).stream().map(Order::getBookName).collect(Collectors.toList());
    }

    public List<Book> showInventory() {
        return bookRepository.getAllAvailableBooks();
    }
}
