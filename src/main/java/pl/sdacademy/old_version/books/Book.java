package pl.sdacademy.old_version.books;

public class Book {
   private String title;
   private String author;
   private Genre genre;
   private String description;
   private Boolean availability;
   private Integer amount;
   private String publisher;

   public Book(String title, String author, Genre genre, String description, Boolean availability, Integer amount, String publisher) {
      this.title = title;
      this.author = author;
      this.genre = genre;
      this.description = description;
      this.availability = availability;
      this.amount = amount;
      this.publisher = publisher;
   }

   public Book() {

   }

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public String getAuthor() {
      return author;
   }

   public void setAuthor(String author) {
      this.author = author;
   }

   public String getGenre() {
       if (genre==Genre.FICTION) return "A";
       if (genre==Genre.NON_FICTION) return "B";
       return "";
   }

   public void setGenre(Genre genre) {
      this.genre = genre;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public Boolean getAvailability() {
      return availability;
   }

   public void setAvailability(Boolean availability) {
      this.availability = availability;
   }

   public Integer getAmount() {
      return amount;
   }

   public void setAmount(Integer amount) {
      this.amount = amount;
   }

   public String getPublisher() {
      return publisher;
   }

   public void setPublisher(String publisher) {
      this.publisher = publisher;
   }


   @Override
   public String toString() {
      return "\n===================================================" +
              "\nTYTUŁ     : " + title +
              "\nAUTOR     : " + author +
              "\nTYP       : " + genre +
              "\nDOSTĘPNOŚĆ: " + availability +
              "\nILOŚĆ     : " + amount +
              "\nWYDAWCA   : " + publisher +
              "\nOPIS      : " + description +
              "\n=================================================="
              ;
   }

   public static Book newBook(String title, String author, Genre genre, int amount, String publisher, String description) {
      return new Book(title, author, genre, description, true, amount, publisher);
   }
}
