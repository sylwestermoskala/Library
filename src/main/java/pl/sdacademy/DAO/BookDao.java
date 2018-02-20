package pl.sdacademy.DAO;


import pl.sdacademy.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {

    void insert(Book book);
    void update(Book book);
    boolean delete(Book book);
    List<Book> findAll();
    Optional<Book> findById(int id);
}
