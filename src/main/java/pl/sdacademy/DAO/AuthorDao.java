package pl.sdacademy.DAO;

import pl.sdacademy.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {
    void insert(Author author);

    void update(Author author);

    boolean delete(Author author);

    List<Author> findAll();

    Optional<Author> findById(int id);
}
