package pl.sdacademy.DAO;

import pl.sdacademy.dbtools.ConnectionFactory;
import pl.sdacademy.dbtools.DBSettings;
import pl.sdacademy.domain.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCBookDao implements BookDao {

    private final Connection connection = ConnectionFactory.getConnection(
            DBSettings.DB_CONNECTIONS,
            DBSettings.DB_USER,
            DBSettings.DB_PASSWORD
    );


    private static final String INSERT_NEW =
            "INSERT INTO library.book(title, authorid, genre, description)VALUES (?, ?, ?, ?)";

    private static final String UPDATE =

            "UPDATE library.book SET title=?, authorid=?, genre=?, description=? WHERE bookid=?;";


    private static final String FIND_ALL = "SELECT * FROM library.book";

    private final static String DELETE = "DELETE FROM library.book WHERE bookid=?";
    private final static String FIND_BY_ID = "SELECT * FROM library.book WHERE bookid=?";



    @Override
    public void insert(Book book) {
        PreparedStatement statement;

        try {
            statement = connection.prepareStatement(INSERT_NEW);

            statement.setString(1, book.getTitle());
            statement.setInt(2, book.getAuthorId());
            statement.setString(3, book.getGenre());
            statement.setString(4, book.getDescription());

            statement.executeUpdate();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(Book book) {
        PreparedStatement statement;

        try {
            statement = connection.prepareStatement(UPDATE);


            statement.setString(1, book.getTitle());
            statement.setInt(2, book.getAuthorId());
            statement.setString(3, book.getGenre());
            statement.setString(4, book.getDescription());
            statement.setInt(5, book.getBookId());


            statement.executeUpdate();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    @Override
    public boolean delete(Book book) {
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(DELETE);
            statement.setInt(1, book.getBookId());

            return !statement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (statement != null){
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return false;
    }




    @Override
    public List<Book> findAll() {
        Statement statement = null;
        List<Book> result = new ArrayList<>();
        try {
            statement = connection.createStatement();
            result = getProductsFromResultSet(statement.executeQuery(FIND_ALL));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            statement.close();
        } catch (SQLException e1) {
            System.out.println(e1.getMessage());
        }
        return result;
    }


    private List<Book> getProductsFromResultSet(ResultSet resultSet) {
        List<Book> result = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Integer bookId = resultSet.getInt("bookid");
                String title = resultSet.getString("title");
                Integer authorId = resultSet.getInt("authorid");
                String genre = resultSet.getString("genre");
                String description = resultSet.getString("description");

                Book book = new Book(title, authorId, genre, description);
                book.setBookId(bookId);
//                book.setAuthorId(country);
//                product.setQuantity(quantity);
//                product.setTotalCost(totalCost);
//                product.setUnitCost(unitCost);

                result.add(book);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }




    @Override
    public Optional<Book> findById(int id) {
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(FIND_BY_ID);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getBook(resultSet));
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


    private Book getBook(ResultSet resultSet) throws SQLException {
        Integer bookId = resultSet.getInt("bookid");
        String title = resultSet.getString("title");
        Integer authorId = resultSet.getInt("authotid");
        String genre = resultSet.getString("genre");
        String description = resultSet.getString("description");

        Book book = new Book(title, authorId, genre, description);
        book.setBookId(bookId);

        return book;
    }


}
