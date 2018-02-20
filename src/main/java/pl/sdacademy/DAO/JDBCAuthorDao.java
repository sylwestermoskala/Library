package pl.sdacademy.DAO;

import pl.sdacademy.dbtools.ConnectionFactory;
import pl.sdacademy.dbtools.DBSettings;
import pl.sdacademy.domain.Author;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCAuthorDao implements AuthorDao {

    private final Connection connection = ConnectionFactory.getConnection(
            DBSettings.DB_CONNECTIONS,
            DBSettings.DB_USER,
            DBSettings.DB_PASSWORD
    );

    private static final String INSERT = "INSERT INTO library.\"authors\"(name, surname) VALUES (?, ?);";
    private static final String UPDATE = "UPDATE library.\"authors\" SET authorid=?, name=?, surname=? WHERE authorid=?;";
    private static final String FIND_ALL = "SELECT * FROM library.\"authors\"";
    private final static String DELETE = "DELETE FROM library.authors WHERE authorid=?";
    private final static String FIND_BY_ID = "SELECT * FROM library.authors WHERE authorid=?";

    @Override
    public void insert(Author author) {
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(INSERT);
            statement.setString(1, author.getName());
            statement.setString(2, author.getSurname());

            int insertCount = statement.executeUpdate();
            if (insertCount == 1) {
                Statement statement1 = connection.createStatement();
                ResultSet resultSet = statement1.executeQuery("SELECT MAX(authorid) FROM library.authors");
                if (resultSet.next()) {
                    Integer authorid = resultSet.getInt("max");
                    author.setAuthorid(authorid);
                }
                statement1.close();
            }

            statement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            ;
        }
    }

    @Override
    public void update(Author author) {
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(UPDATE);
            statement.setInt(1, author.getAuthorid());
            statement.setString(2, author.getName());
            statement.setString(3, author.getSurname());
            statement.setInt(4, author.getAuthorid());

            statement.executeQuery();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean delete(Author author) {
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(DELETE);
            statement.setInt(1, author.getAuthorid());

            return !statement.execute();
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
        return false;
    }


    private List<Author> getFromProductFromResultSet(ResultSet resultSet) {

        List<Author> result = new ArrayList<Author>();
        try {
            while (resultSet.next()) {
                Integer authorId = resultSet.getInt("authorid");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                Author author = new Author(authorId, name, surname);

                author.setAuthorid(authorId);
                author.setName(name);
                author.setSurname(surname);
                result.add(author);
            }
        } catch (java.sql.SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    @Override
    public List<Author> findAll() {
        Statement statement = null;
        List<Author> result = new ArrayList<Author>();
        try {
            statement = connection.createStatement();
            result = getFromProductFromResultSet(statement.executeQuery(FIND_ALL));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            statement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }



    @Override
    public Optional<Author> findById(int id) {
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(FIND_BY_ID);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getAuthor(resultSet));
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


    private Author getAuthor(ResultSet resultSet) throws SQLException {
        Integer authorid = resultSet.getInt("authorid");
        String name = resultSet.getString("name");
        String surname = resultSet.getString("surname");

        Author author = new Author(name, surname);
        author.setAuthorid(authorid);

        return author;
    }
}
