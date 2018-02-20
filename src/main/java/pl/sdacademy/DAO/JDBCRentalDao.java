package pl.sdacademy.DAO;

import pl.sdacademy.dbtools.ConnectionFactory;
import pl.sdacademy.dbtools.DBSettings;
import pl.sdacademy.domain.Rental;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class JDBCRentalDao implements DAO<Rental> {
    private final Connection connection = ConnectionFactory.getConnection(
            DBSettings.DB_CONNECTIONS,
            DBSettings.DB_USER,
            DBSettings.DB_PASSWORD);

    private final static String INSERT = "INSERT INTO library.rental (clientid, bookid, date, returned) VALUES (?, ?, ?, FALSE)";
    private final static String UPDATE_BY_ID = "UPDATE library.rental SET clientid=?, bookid=?, date=?, returned=? WHERE rentalid=?";
    private final static String DELETE_BY_ID = "DELETE FROM library.rental WHERE rentalid=?";
    private final static String FIND_ALL = "SELECT * FROM library.rental";
    private final static String FIND_BY_ID = "SELECT * FROM library.rental WHERE rentalid=?";
    private static final String DELETE_ALL = "DELETE FROM library.rental";



    @Override
    public boolean insert(Rental rental) {
        int insertCount = 0;
        PreparedStatement preparedStatement = null;
        Statement statement = null;

        try {
            preparedStatement = connection.prepareStatement(INSERT);

            preparedStatement.setInt(1, rental.getClientid());
            preparedStatement.setInt(2, rental.getBookid());
            preparedStatement.setDate(3, Date.valueOf(rental.getDate()));
            insertCount = preparedStatement.executeUpdate();
            if (insertCount == 1) {
                statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT MAX(rentalid) FROM library.rental");
                if (resultSet.next()) {
                    Integer rentalid = resultSet.getInt("max");
                    rental.setRentalid(rentalid);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return insertCount == 1;
    }

    @Override
    public boolean update(Rental rental) {
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(UPDATE_BY_ID);
            statement.setInt(1, rental.getClientid());
            statement.setInt(2, rental.getBookid());
            statement.setDate(3, Date.valueOf(rental.getDate()));
            statement.setBoolean(4, rental.getReturned());
            statement.setInt(5, rental.getRentalid());

            return statement.executeUpdate() == 1;
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

    @Override
    public boolean delete(int id) {
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(DELETE_BY_ID);
            statement.setInt(1, id);

            return statement.executeUpdate() == 1;
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

    @Override
    public boolean deleteAll() {
        Statement statement = null;
        try {
            statement = connection.createStatement();

            return statement.executeUpdate(DELETE_ALL) > 0;
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

    @Override
    public Optional<Rental> findById(int id) {
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(FIND_BY_ID);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getRental(resultSet));
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

    @Override
    public List<Rental> findAll() {
        Statement statement = null;
        List<Rental> result = new LinkedList<>();

        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(FIND_ALL);
            while (resultSet.next()) {
                result.add(getRental(resultSet));
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
        return result;
    }


    private Rental getRental(ResultSet resultSet) throws SQLException {
        Integer rentalid = resultSet.getInt("rentalid");
        Integer clientid = resultSet.getInt("clientid");
        Integer bookid = resultSet.getInt("bookid");
        LocalDate date = resultSet.getDate("date").toLocalDate();
        Boolean returned = resultSet.getBoolean("returned");

        Rental rental = new Rental(clientid, bookid);
        rental.setRentalid(rentalid);
        rental.setReturned(returned);
        rental.setDate(date);

        return rental;
    }
}
