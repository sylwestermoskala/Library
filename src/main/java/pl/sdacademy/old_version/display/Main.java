package pl.sdacademy.old_version.display;

import pl.sdacademy.DAO.*;
import pl.sdacademy.domain.Book;


public class Main {

    public static void main(String[] args) {

        BookDao daoBook = new JDBCBookDao();
        ClientDao daoClient = new JDBCClientDao();
        AuthorDao daoAuthor = new JDBCAuthorDao();

        //System.out.println(daoBook.findById(4).get());
        //System.out.println(daoClient.findById(3).get());
        //System.out.println(daoAuthor.findById(1).get());


        Book bookNumber1 = new Book("Kozaczki", 2,"science fiction", "znikajace kozaczki");
        bookNumber1.setBookId(1);

        daoBook.update(bookNumber1);

    }
}
