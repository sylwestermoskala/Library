package pl.sdacademy;

import pl.sdacademy.DAO.*;
import pl.sdacademy.domain.Author;
import pl.sdacademy.domain.Book;
import pl.sdacademy.domain.Client;
import pl.sdacademy.domain.Rental;

import java.util.*;

public class LibraryManager {

    private AuthorDao authors = new JDBCAuthorDao();
    private BookDao books = new JDBCBookDao();
    private ClientDao clients = new JDBCClientDao();
    private DAO<Rental> rentals = new JDBCRentalDao();


    public LibraryManager() {
        mainMenu();
    }

    private void mainMenu() {
        while (true) {
            System.out.println("[]=====================[]");
            System.out.println("[] B I B L I O T E K A []");
            System.out.println("[]=====================[]");
            System.out.println();
            System.out.println("1: Wyszukaj ksiazke");
            System.out.println("2: wyswietl wszytkie ksiazki");
            System.out.println("3: zaloguj sie");
            System.out.println("4: exit");
            switch (readInt()) {
                case 1:
                    menuFindBooks();
                    break;
                case 2:
                    showBooks(books.findAll());
                    break;
                case 3:
                    login();
                    break;
                case 4:
                    return;
            }
        }
    }

    private void menuFindBooks() {
        System.out.println("Podaj tytuł:");
        String title = readString(40);
        List<Book> resul = new LinkedList<>();
        for (Book book : books.findAll()) {
            if (book.getTitle().contains(title)) {
                resul.add(book);
            }
        }
        showBooks(resul);
    }

    private void login() {
        System.out.println("login: ");
        String login = readString(20);
        System.out.println("haslo: ");
        String password = readString(20);

        if (!login.equals("admin") || !password.equals("admin")) {
            System.out.println("incorect login or password");
            return;
        }
        while (true) {
            System.out.println("1: dodaj nowego klienta");
            System.out.println("2: dodaj nowa ksiazke");
            System.out.println("3: wypozycz ksiazke");
            System.out.println("4: wyszukaj ksiazki");
            System.out.println("5: wyswietl wszystkie ksiazki");
            System.out.println("6: wyloguj");
            switch (readInt()) {
                case 1:
                    addNewClient();
                    break;
                case 2:
                    addNewBook();
                    break;
                case 3:
                    addNewRental();
                    break;
                case 4:
                    menuFindBooks();
                    break;
                case 5:
                    showBooks(books.findAll());
                    break;
                case 6:
                    return;
            }

        }
    }

    private void addNewRental() {
        //todo
    }

    private void addNewBook() {
        System.out.println("Dodawanie nowej ksiazki");
        System.out.println("tytul:");
        String title = readString(40);
        System.out.println("gatunek:");
        String genre = readString(20);
        for (Author author : authors.findAll()) {
            System.out.println(author.getAuthorid() + ": " + author.getName() + " " + author.getSurname());
        }
        Author author = null;
        System.out.println("0: Dodaj nowego autora");
        int authorid = readInt();
        if (authorid == 0) {
            System.out.println("podaj imie autora");
            String authorName = readString(20);
            System.out.println("podaj nazwisko autora");
            String authorSurname = readString(20);
            author = new Author(authorName, authorSurname);
            authors.insert(author);
        } else {
            author = authors.findById(authorid).get();
        }
        Book newBook = new Book(title, author.getAuthorid(), genre, "");
        books.insert(newBook);
        if (newBook.getBookId() != null) {
            System.out.println("dodano z powodzeniem");
        }
    }

    private void addNewClient() {
        System.out.println("Dodawanie nowego klienta");
        System.out.println("imie:");
        String name = readString(20);
        System.out.println("nazwisko:");
        String surname = readString(20);
        Client client = new Client(name, surname);
        if (clients.insert(client) > 0) { //fixme change client::insert to return boolean
            System.out.println("id: " + client.getId());
        }
    }

    private int readInt() {
        Scanner scanner = new Scanner(System.in);
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("invalid data. expected: number. Try again.");
            return readInt();
        }
    }

    private String readString(int length) {
        Scanner scanner = new Scanner(System.in);
        try {
            return scanner.next(String.format("\\w{3,%d}", length));
        } catch (NoSuchElementException e) {
            System.out.println("invalid data. excepted: at least 3 chars. Try again.");
            return readString(length);
        }
    }

    private void showBooks(List<Book> bookList) {
        if (bookList.isEmpty()) {
            System.out.println("Brak ksiazek");
            return;
        }
        System.out.println("+-------+------------------------------------------+------------------------------------------+");
        System.out.println("|    id | Tytul                                    |                  Autor                   |");
        System.out.println("+-------+------------------------------------------+------------------------------------------+");

        for (Book book : bookList) {
            Author author = authors.findById(book.getAuthorId()).get();
            System.out.println(String.format("| %5d | %40s | %40s |", book.getBookId(),
                    book.getTitle(), author.getName().trim() + " " + author.getSurname()));
        }
        System.out.println("+-------+------------------------------------------+------------------------------------------+");
    }
}
