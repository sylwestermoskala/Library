package pl.sdacademy.old_version.display;

import pl.sdacademy.old_version.books.BookOperations;
import pl.sdacademy.old_version.books.TooLargeNumberException;
import pl.sdacademy.old_version.users.Permission;
import pl.sdacademy.old_version.users.User;

import java.util.Scanner;

public class Menu {

    public static void menuClient(User client) throws TooLargeNumberException {
        String option;
        do {
            System.out.println("\nMenu dla klienta");
            System.out.println("===================");
            System.out.println("1.Wyszukaj książkę");
            System.out.println("2.Wypożycz książkę");
            System.out.println("3.Twoje wypożyczone książki");
            System.out.println("4.Wyloguj się");
            System.out.println("0.Wyjście");
            System.out.println("");

            System.out.print("Podaj opcję: ");
            Scanner read = new Scanner(System.in);
            option = read.nextLine();
            switch (option) {
                case "1":
                    BookOperations.search(client);
                    break;
                case "2":
                    BookOperations.borrowBook(client);
                    break;
                case "3":
                    client.checkBorrowed();
                    break;
                case "4":
                    loginScreen();
                    break;
                case "0":
                    System.exit(0);
                default:
                    System.out.println("\nPodano złą wartość...");
                    break;
            }
        } while(true);
    }

    private static void menuEmployee(User employee) throws TooLargeNumberException {
        String option;
        do {
            System.out.println("\nMenu dla pracownika");
            System.out.println("===================");
            System.out.println("1.Dodaj książkę");
            System.out.println("2.Edytuj książkę");
            System.out.println("3.Wyszukaj książkę");
            System.out.println("4.Wyloguj się");
            System.out.println("0.Wyjście");
            System.out.println("");

            System.out.print("Podaj opcję: ");
            Scanner read = new Scanner(System.in);
            option = read.nextLine();
            switch (option) {
                case "1":
                    BookOperations.addBook();
                    break;
                case "2":
                     BookOperations.editBook();
                    break;
                case "3":
                    BookOperations.search(employee);
                    break;
                case "4":
                    loginScreen();
                    break;
                case "0":
                    System.exit(0);
                case "99":
                    BookOperations.addTestingBooks();
                    break;
                default:
                    System.out.println("\nPodano złą wartość...");
                    break;
            }
        } while(true);
    }

    private static void menuBoss(User employee) throws TooLargeNumberException {
        String option;
        do {
            System.out.println("\nMenu dla szefa");
            System.out.println("===================");
            System.out.println("1.Dodaj książkę");
            System.out.println("2.Edytuj książkę");
            System.out.println("3.Wyszukaj książkę");
            System.out.println("4.Usuń książkę");
            System.out.println("5.Wyloguj się");
            System.out.println("0.Wyjście");
            System.out.println("");

            System.out.print("Podaj opcję: ");
            Scanner read = new Scanner(System.in);
            option = read.nextLine();
            switch (option) {
                case "1":
                    BookOperations.addBook();
                    break;
                case "2":
                    BookOperations.editBook();
                    break;
                case "3":
                    BookOperations.search(employee);
                    break;
                case "4":
                    BookOperations.removeBook();
                    break;
                case "5":
                    loginScreen();
                    break;
                case "0":
                    System.exit(0);
                case "99":
                    BookOperations.addTestingBooks();
                    break;
                default:
                    System.out.println("\nPodano złą wartość...");
                    break;
            }
        } while(true);
    }

    public static void loginScreen() throws TooLargeNumberException {
        System.out.println("[]=====================[]");
        System.out.println("[] B I B L I O T E K A []");
        System.out.println("[]=====================[]");

        String login;
        String password;
        do {
            System.out.print("Podaj swój login: ");
            Scanner read = new Scanner(System.in);
            login = read.nextLine();
            System.out.print("Podaj swoje hasło: ");
            password = read.nextLine();
            boolean authorization = false;

            for(User user : App.usersList) {
                if(user.getLogin().equals(login) && user.getPassword().equals(password)) {
                    if(user.getPermission().equals(Permission.SALESMAN)) {
                        authorization = true;
                        menuEmployee(user);
                    } else if(user.getPermission().equals(Permission.BOSS)) {
                        authorization = true;
                        menuBoss(user);
                    } else {
                        authorization = true;
                        menuClient(user);
                    }
                }
            }
            if(!authorization) {
                System.out.println("Podany login lub hasło jest nieprawidłowe.\n");
            }
        } while(true);
    }

}
