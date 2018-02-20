package pl.sdacademy.DAO;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {
    boolean insert(T t);

    boolean update(T t);

    boolean delete(int id);

    boolean deleteAll();

    Optional<T> findById(int id);

    List<T> findAll();
}
