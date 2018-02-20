package pl.sdacademy.old_version.users;

public class Client extends User{

    private int borrowedBooks = 0;
    private final Permission permission = Permission.CLIENT;

    public Client(int id, String firstName, String lastName, String login, String password) {
        super(id, firstName, lastName, login, password);
    }

    public int getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(int borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    @Override
    public Permission getPermission() {
        return permission;
    }

    public Client() {

    }
}
