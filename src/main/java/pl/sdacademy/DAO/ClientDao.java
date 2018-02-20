package pl.sdacademy.DAO;

import pl.sdacademy.domain.Author;
import pl.sdacademy.domain.Client;

import java.util.List;
import java.util.Optional;

public interface ClientDao {
    int insert(Client client);
    void update(Client client);
    void delete(int id);
    List<Client> findAll();
    Optional<Client> findById(int id);
}
