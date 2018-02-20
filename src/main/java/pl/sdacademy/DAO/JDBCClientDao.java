package pl.sdacademy.DAO;

import pl.sdacademy.dbtools.ConnectionFactory;
import pl.sdacademy.dbtools.DBSettings;
import pl.sdacademy.domain.Book;
import pl.sdacademy.domain.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCClientDao implements ClientDao {

    private final Connection connection = ConnectionFactory.getConnection(
            DBSettings.DB_CONNECTIONS,
            DBSettings.DB_USER,
            DBSettings.DB_PASSWORD
    );


    PreparedStatement insert = null;
    PreparedStatement update = null;
    Statement delete = null;
    Statement findAll = null;

    String INSERT_STRING = "INSERT INTO library.clients(name, surname) VALUES (?, ?)";
    String UPDATE_STRING;
    String FIND_ALL_STRING = "SELECT * FROM library.clients;";
    String FIND_BY_ID = "SELECT * FROM library.clients WHERE clientid=?";


    public int insert(Client client) {
        int new_id = -1;
        ResultSet resultSet = null;
        try {
            insert = connection.prepareStatement(INSERT_STRING);
            insert.setString(1, client.getName());
            insert.setString(2, client.getSurname());
            insert.executeUpdate();
            resultSet = connection.createStatement().executeQuery("SELECT max(clientid) FROM library.clients");
            resultSet.next();
            new_id = resultSet.getInt(1);
            insert.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        client.setId(new_id);
        return new_id;
    }

    public void update(Client client) {
        UPDATE_STRING = "UPDATE library.clients SET name=?, surname=? WHERE clientid=" + client.getId();

        try {
            update = connection.prepareStatement(UPDATE_STRING);
            update.setString(1, client.getName());
            update.setString(2, client.getSurname());
            update.executeUpdate();
            update.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {

        String DELETE_STRING = "DELETE FROM library.clients WHERE clientid=" + id;

        try {
            delete = connection.createStatement();
            delete.executeUpdate(DELETE_STRING);
            delete.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Client> findAll() {
        List<Client> allClients = new ArrayList<>();
        ResultSet resultSet = null;
        try {
            findAll = connection.createStatement();
            resultSet = findAll.executeQuery(FIND_ALL_STRING);
            while (resultSet.next()) {
                Client client = new Client();
                client.setId(resultSet.getInt("clientid"));
                client.setName(resultSet.getString("name"));
                client.setSurname(resultSet.getString("surname"));
                allClients.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            findAll.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allClients;
    }



    @Override
    public Optional<Client> findById(int id) {
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(FIND_BY_ID);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getClient(resultSet));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        return Optional.empty();
    }


    private Client getClient(ResultSet resultSet) throws SQLException {
        Integer clientid = resultSet.getInt("clientid");
        String name = resultSet.getString("name");
        String surname = resultSet.getString("surname");


        Client client = new Client(name, surname);
        client.setId(clientid);

        return client;
    }

}
