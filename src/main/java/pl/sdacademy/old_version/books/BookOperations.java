package pl.sdacademy.old_version.books;

import pl.sdacademy.old_version.display.App;
import pl.sdacademy.old_version.display.Menu;
import pl.sdacademy.old_version.users.Employee;
import pl.sdacademy.old_version.users.User;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookOperations {

    public static void addBook() throws TooLargeNumberException{
        Book book = new Book();

        Scanner input = new Scanner(System.in);

        System.out.print("\nPodaj tytuł książki: ");
        String tittle = input.nextLine();
        book.setTitle(tittle);

        System.out.print("Podaj autora książki: ");
        String author = input.nextLine();
        book.setAuthor(author);
        String genre;
        do {
            System.out.print("Wybierz Gatunek: A - Fikcja; B - Literatura Faktu: ");
            genre = input.nextLine();
            switch (genre) {
                case "A":
                    book.setGenre(Genre.FICTION);
                    break;
                case "B":
                    book.setGenre(Genre.NON_FICTION);
                    break;
                default:
                    System.out.println("Podano nieistniejący gatunek");
                    break;
            }
        } while (!genre.equals("A") && !genre.equals("B"));

        do {
            System.out.print("Ile książek chcesz dodać?: ");
            String amount = input.nextLine();
            int amountInt;
            if(checkIfEmpty(amount) == 0) {
                continue;
            }
            if(isNumeric(amount)) {
                double temp = Double.valueOf(amount);
                if(temp > Integer.MAX_VALUE) {
                    throw new TooLargeNumberException("Panie, gdzie ja to pomieszcze??");
                }
                amountInt = Integer.parseInt(amount);
            } else {
                wrongValue();
                continue;
            }
            book.setAmount(amountInt);
            book.setAvailability(true);
            break;

        }while(true);

        System.out.print("Wydawca: ");
        String publisher = input.nextLine();
        book.setPublisher(publisher);

        System.out.print("Podaj opis: ");
        String description = input.nextLine();
        book.setDescription(description);

        App.bookDatabase.add(book);
        System.out.println("\nDodano " + book.toString());
        waitForAction();

    }

    public static void search(User user) {
        List<Book> searchResults = new ArrayList<>();

        final String title = "title";
        final String author = "author";
        final String publisher = "publisher";
        final String genre = "genre";

        showSearchOptions();

        int decision = searchDecision();

        switch(decision) {
            case 1:
                searchResults = searchFor(title);
                break;
            case 2:
                searchResults = searchFor(author);
                break;
            case 3:
                searchResults = searchFor(publisher);
                break;
            case 4:
                searchResults = searchFor(genre);
                break;
            default:
                wrongValue();
                break;
        }

        if(searchResults.isEmpty()) {
            System.out.println("Nie znaleziono książki o podanych parametrach");
            waitForAction();
        } else {
            System.out.println("\nOto znalezione książki:");
            int index = 1;
            for (Book book : searchResults) {
                System.out.println(index + book.toString());
                index++;
            }
            if (user instanceof Employee) {
                waitForAction();
            } else {
                do {
                    System.out.println("\nPodaj numer książki, którą chcesz wypożyczyć, lub 0, by wrócić do menu: ");
                    Scanner scan = new Scanner(System.in);
                    String choice = scan.nextLine();
                    if (!choice.equals("0")) {
                        if (isNumeric(choice)) {
                            borrowSearched(choice, searchResults, user);
                            waitForAction();
                            break;
                        } else {
                            wrongValue();
                        }
                    }
                    break;
                } while (true);
            }
        }
    }

    private static void borrowSearched(String choice, List<Book> searchResults, User user) {
        int choiceInt = Integer.parseInt(choice) - 1;
        Book choosedBook = searchResults.get(choiceInt);
        if(choosedBook.getAmount().equals(1)) {
            choosedBook.setAvailability(false);
        }
        choosedBook.setAmount(choosedBook.getAmount() - 1);
        user.borrowedBooksList.add(choosedBook);
        System.out.println("Wypożyczono książkę: \n" + choosedBook.getAuthor() + " - " + choosedBook.getTitle());
    }

    private static int searchDecision() {
        Scanner scan = new Scanner(System.in);
        int resultInt;
        do {
            String result = scan.nextLine();
            if(checkIfEmpty(result) == 0) {
                continue;
            }
            if (isNumeric(result)) {
                resultInt = Integer.parseInt(result);
                if(resultInt < 1 || resultInt > 4) {
                    System.out.println("Podano złą wartość!");
                    System.out.println("Wybierz liczbę z zakresu 1-4");
                    continue;
                }
                return resultInt;
            } else {
                wrongValue();
            }
        }while(true);
    }

    private static void showSearchOptions() {
        System.out.println("\nPo czym chcesz wyszukać książkę? \n");
        System.out.println("1. Tytuł");
        System.out.println("2. Autor");
        System.out.println("3. Wydawnictwo");
        System.out.println("4. Gatunek");
    }

    private static List<Book> searchFor(String thing) {
        List<Book> searchResults = new ArrayList<>();
        Scanner scan = new Scanner(System.in);
        switch(thing) {
            case "title":
                System.out.print("Podaj tytuł, którego szukasz: ");
                String title = scan.nextLine();
                for(Book book : App.bookDatabase) {
                    if(book.getTitle().toLowerCase().equals(title.toLowerCase())) {
                        searchResults.add(book);
                    }
                }
                break;
            case "author":
                System.out.print("Podaj autora, którego szukasz: ");
                String author = scan.nextLine();
                for(Book book : App.bookDatabase) {
                    if(book.getAuthor().toLowerCase().equals(author.toLowerCase())) {
                        searchResults.add(book);
                    }
                }
                break;
            case "publisher":
                System.out.print("Podaj wydawnictwo, którego szukasz: ");
                String publisher = scan.nextLine();
                for(Book book : App.bookDatabase) {
                    if(book.getPublisher().toLowerCase().equals(publisher.toLowerCase())) {
                        searchResults.add(book);
                    }
                }
                break;
            case "genre":
                System.out.print("Podaj gatunek, którego szukasz (A - Literatura fikcji, B - Literatura faktu : ");
                String genre = scan.nextLine();
                for(Book book : App.bookDatabase) {
                    if(book.getGenre().equals(genre)) {
                        searchResults.add(book);
                    }
                }
                break;
        }
        return searchResults;
    }

    public static void editBook() {
        System.out.println("Edycja książki");

        if (App.bookDatabase.size() == 0) {
            System.out.println("Baza książek jest pusta");
            waitForAction();
            return;
        }
        printAllBooks();
        System.out.print("Wybierz numer książki którą chcesz edytować: ");
        Scanner read = new Scanner(System.in);
        int index;
        do {
            String option = read.nextLine();
            if(checkIfEmpty(option) == 0) {
                continue;
            }
            if(isNumeric(option)) {
                index = Integer.parseInt(option) - 1;
                break;
            } else {
                wrongValue();
            }
        }while(true);
        if (index < App.bookDatabase.size() && index >=0) {
            Book book = App.bookDatabase.get(index);

            do {

                Scanner input = new Scanner(System.in);
                System.out.println("Które pole chcesz edytować:");
                System.out.println("1.Tytuł");
                System.out.println("2.Autora");
                System.out.println("3.Wydawcę");
                System.out.println("4.Opis");
                System.out.println("5.Ilość sztuk");
                System.out.println("0.Wyjście do menu");
                System.out.print("Wybierz opcję:");
                String menuOption = input.nextLine();
                switch (menuOption) {
                    case "1":
                        System.out.println("\nPodaj tytuł książki: ");
                        String tittle = input.nextLine();
                        book.setTitle(tittle);
                        System.out.println("Zatwierdzono zmiany.");
                        break;
                    case "2":
                        System.out.println("Podaj autora książki: ");
                        String author = input.nextLine();
                        book.setAuthor(author);
                        System.out.println("Zatwierdzono zmiany.");
                        break;
                    case "3":
                        System.out.println("Wydawca: ");
                        String publisher = input.nextLine();
                        book.setPublisher(publisher);
                        System.out.println("Zatwierdzono zmiany.");
                        break;
                    case "4":
                        System.out.println("Podaj opis: ");
                        String description = input.nextLine();
                        book.setDescription(description);
                        System.out.println("Zatwierdzono zmiany.");
                        break;
                    case "5":
                        do {
                            System.out.println("Podaj nową ilość dostępnych książek: ");
                            String value = input.nextLine();
                            if (checkIfEmpty(value) == 0) {
                                continue;
                            }
                            if (checkIfEmpty(value) == 0) {
                                continue;
                            }
                            if (isNumeric(value)) {
                                int valInt = Integer.parseInt(value);
                                book.setAmount(valInt);
                                System.out.println("Zatwierdzono zmiany.");
                                break;
                            } else {
                                wrongValue();
                            }
                        } while (true);
                        break;
                    case "0":
                        return;
                    default:
                        wrongValue();
                        break;
                }
                App.bookDatabase.set(index, book);
                System.out.println("Aby wprowadzać dalsze zmiany wciśnij Enter, aby wyjść do menu, podaj 0: ");
                String choice = input.nextLine();
                if(choice.equals("0")) {
                    break;
                }
            } while(true);
        } else
            System.out.println("Dopuszczalny zakres to 1-"+App.bookDatabase.size());

    }

    public static void addTestingBooks() {
        Book book = new Book("Zbrodnia i kara", "author", Genre.FICTION, "description", true, 1, "publisher");
        Book book2 = new Book("Przygody Shrelocka Holmesa", "author2", Genre.FICTION, "description", true, 1, "publisher");
        Book book3 = new Book("Ania z Zielonego Wzgórza", "author2", Genre.FICTION, "description", true, 1, "publisher");

        App.bookDatabase.add(book);
        App.bookDatabase.add(book2);
        App.bookDatabase.add(book3);

        System.out.println("Dodano " + App.bookDatabase.size() + " książek");
    }

    // dodanie książek
    private static void printAllBooks() {
        int index = 1;

        if (App.bookDatabase.size() == 0) {
            System.out.println("Baza książek jest pusta");
            waitForAction();
            return;
        }
        System.out.println("============================");
        System.out.println("NR.    TYTUŁ");
        System.out.println("============================");
        for(Book book : App.bookDatabase) {
            System.out.print(index + ": " + book.getTitle());
            System.out.println();
            index++;
        }
        System.out.println("============================");
    }

    public static void removeBook() {
        printAllBooks();
        if (App.bookDatabase.size() == 0) {
            return;
        }

        do {
            System.out.print("Wybierz numer książki którą chcesz skasować, lub 0 by wyjść do menu:");
            Scanner read = new Scanner(System.in);
            String option = read.nextLine();
            if(option.equals("0")) {
                return;
            }
            if(checkIfEmpty(option) == 0) {
                continue;
            }
            if(isNumeric(option)) {
                int index = Integer.parseInt(option) - 1;
                if (index < App.bookDatabase.size() && index >= 0) {
                    System.out.println("Usunięto książkę: \n" + App.bookDatabase.get(index).getAuthor()
                            + " - " + App.bookDatabase.get(index).getTitle());
                    App.bookDatabase.remove(index);
                    waitForAction();
                    break;
                } else {
                    System.out.println("Dopuszczalny zakres to 1.." + App.bookDatabase.size());
                }
            } else {
                System.out.println("Podano niewłaściwy format. Dopuszczalny zakres, to 1-" + App.bookDatabase.size());
            }
        }while(true);
    }

    public static void borrowBook(User user) throws TooLargeNumberException {
        Book choosedBook = bookChoice(user);
        user.borrowedBooksList.add(choosedBook);
        int numberOfBorrowedBooks = user.borrowedBooksList.size();
        System.out.println("\nWypożyczyłeś/aś książkę:\n");
        System.out.println(choosedBook.getAuthor() + " - " + choosedBook.getTitle());
        System.out.println("\nLiczba książek na Twoim koncie: " + numberOfBorrowedBooks);

        waitForAction();
    }

    private static Book bookChoice(User user) throws TooLargeNumberException {
        Scanner scan = new Scanner(System.in);
        List<Book> availableBooks = new ArrayList<>();
        System.out.println("Oto książki możliwe do wypożyczenia: \n");
        int index = 1;

        for(Book book : App.bookDatabase) {
            if(book.getAvailability()) {
                System.out.println(index + ". " + book.getAuthor() + " - " + book.getTitle() +
                        " - dostępna ilość: " + book.getAmount());
                availableBooks.add(book);
                index++;
            }
        }

        if(index == 1) {
            System.out.println("Brak dostępnych książek do wypożyczenia");
        }

        do {
            System.out.print("\nWybierz numer książki, którą chcesz wypożyczyć, lub wybierz 0, by wrócić do menu: ");
            String choice = scan.nextLine();
            if(checkIfEmpty(choice) == 0) {
                continue;
            }

            if(choice.equals("0")) {
                Menu.menuClient(user);
            }
            
            if(isNumeric(choice)) {
                int choiceInt = Integer.parseInt(choice);
                if (choiceInt >= 1 && choiceInt <= index) {
                    int amoutOfChoosedBook = availableBooks.get(choiceInt - 1).getAmount();
                    if (amoutOfChoosedBook == 1) {
                        availableBooks.get(choiceInt - 1).setAvailability(false);
                    }
                    availableBooks.get(choiceInt - 1).setAmount(amoutOfChoosedBook - 1);
                    return availableBooks.get(choiceInt - 1);
                } else {
                    wrongValue();
                }
            } else {
                wrongValue();
            }
        } while(true);
    }

    private static boolean isNumeric(String str)
    {
        NumberFormat formatter = NumberFormat.getInstance();
        ParsePosition pos = new ParsePosition(0);
        formatter.parse(str, pos);
        return str.length() == pos.getIndex();
    }

    public static void waitForAction() {
        System.out.println("\nWciśnij Enter, by kontynuować lub 0 by wyjść z programu:");
        Scanner scan = new Scanner(System.in);
        String choice = scan.nextLine();
        if(choice.equals("0")) {
            System.exit(0);
        }
    }

    private static void wrongValue() {
        System.out.println("Podano złą wartość!");
    }

    private static int checkIfEmpty(String line) {
        if(line.equals("")) {
            wrongValue();
            return 0;
        }
        return 1;
    }
}
