package pl.sdacademy.old_version.display;

import pl.sdacademy.old_version.books.Book;
import pl.sdacademy.old_version.books.Genre;
import pl.sdacademy.old_version.books.TooLargeNumberException;
import pl.sdacademy.old_version.users.Client;
import pl.sdacademy.old_version.users.Employee;
import pl.sdacademy.old_version.users.Permission;
import pl.sdacademy.old_version.users.User;

import java.util.ArrayList;
import java.util.List;


public class App 
{
    public static List<Book> bookDatabase = new ArrayList<>();
    public static List<User> usersList = new ArrayList<>();

    private static void loadUsers() {
        Employee rciesla = new Employee(1, "Rafał", "Cieśla", "rciesla", "111", Permission.BOSS);
        Employee artpoz = new Employee(2, "Artur", "Poznański", "artpoz", "222", Permission.BOSS);
        Employee nakrecacz = new Employee(3, "Tomasz", "Treppa", "nakrecacz", "333", Permission.SALESMAN);

        Client jnowak = new Client(4, "Jan", "Nowak", "jnowak", "444");
        Client pkowalski = new Client(5, "Piotr", "Kowalski", "pkowalski", "555");
        Client amleczko = new Client(6, "Agnieszka", "Mleczko", "amleczko", "777");

        Employee test = new Employee(7, "test", "test", "q", "q", Permission.SALESMAN);

        usersList.add(rciesla);
        usersList.add(artpoz);
        usersList.add(nakrecacz);

        usersList.add(jnowak);
        usersList.add(pkowalski);
        usersList.add(amleczko);

        usersList.add(test);
    }

    private static void loadBookDatabase() {
        bookDatabase.add(Book.newBook("Harry Potter i Kamień Filozoficzny",
                "J. K. Rowling",
                Genre.FICTION,
                10,
                "Znak",
                "Książka przedstawiająca początek historii młodego czarodzieja Harry’ego Pottera"));
        bookDatabase.add(Book.newBook("Fastlane Milionera",
                "MJ DeMarco",
                Genre.NON_FICTION,
                2,
                "Złote myśli",
                "Historia amerykańskiego milionera"));
        bookDatabase.add(Book.newBook("Zbrodnia i kara",
                "Fiodor Dostojewski",
                Genre.FICTION,
                6,
                "Dragon",
                "Tematem powieści są losy byłego studenta, Rodiona Raskolnikowa, który planuje zamordować i obrabować starą lichwiarkę"));
        bookDatabase.add(Book.newBook("Dywizjon 303",
                "Arkady Fiedler",
                Genre.NON_FICTION,
                5,
                "Bernardinum",
                "Książka poświęcona pierwszemu okresowi działań bojowych Dywizjonu 303"));
        bookDatabase.add(Book.newBook("Przygody Sherlocka Holmesa",
                "A. Conan Doyle",
                Genre.FICTION,
                6,
                "Algo",
                "Pierwszy z pięciu zbiorów opowiadań o przygodach detektywa Sherlocka Holmesa"));
        bookDatabase.add(Book.newBook("Ania z Zielonego Wzgórza",
                "Lucy Maud Montgomery",
                Genre.FICTION,
                8,
                "Greg",
                "Powieść młodzieżowa opowiadająca o losach Ani Shirley"));
        bookDatabase.add(Book.newBook("Zemsta",
                "Aleksander Fredro",
                Genre.FICTION,
                10,
                "Greg",
                "Komedia opowiadająca o sporze dwóch sąsiadów"));
    }

    public static void main(String[] args) throws TooLargeNumberException {
        loadUsers();
        loadBookDatabase();
        Menu.loginScreen();

    }
}
