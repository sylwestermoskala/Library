package pl.sdacademy.old_version.users;

import pl.sdacademy.old_version.books.Book;
import pl.sdacademy.old_version.books.BookOperations;

import java.util.ArrayList;
import java.util.List;

public abstract class User {
    protected int id;
    protected String firstName;
    protected String lastName;
    protected String login;
    protected String password;
    protected Permission permission;
    public List<Book> borrowedBooksList = new ArrayList<>();

    public User(int id, String firstName, String lastName, String login, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
    }

    protected User() {

    }

    public void setId(int id) {
        this.id = id;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void checkBorrowed() {

        if(borrowedBooksList.isEmpty()) {
            System.out.println("\nNie masz żadnych wypożyczonych książek.");
            BookOperations.waitForAction();
            return;
        }

        System.out.println("\nOto Twoje wypożyczone książki: \n");
        for(Book book : borrowedBooksList) {
            System.out.println(book.getAuthor() + " - " + book.getTitle());
        }

        System.out.println("\nLiczba książek: " + borrowedBooksList.size());

        BookOperations.waitForAction();
    }
}
