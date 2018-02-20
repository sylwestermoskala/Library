package pl.sdacademy.domain;

import java.time.LocalDate;

public class Rental {
    private Integer rentalid;
    private Integer clientid;
    private Integer bookid;
    private LocalDate date = LocalDate.now();
    private Boolean returned = false;

    public Rental(Integer clientid, Integer bookid) {
        this.clientid = clientid;
        this.bookid = bookid;
    }

    public Integer getRentalid() {
        return rentalid;
    }

    public void setRentalid(Integer rentalid) {
        this.rentalid = rentalid;
    }

    public Integer getClientid() {
        return clientid;
    }

    public void setClientid(Integer clientid) {
        this.clientid = clientid;
    }

    public Integer getBookid() {
        return bookid;
    }

    public void setBookid(Integer bookid) {
        this.bookid = bookid;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Boolean getReturned() {
        return returned;
    }

    public void setReturned(Boolean returned) {
        this.returned = returned;
    }

    @Override
    public String toString() {
        return "Rental{" +
                "rentalid=" + rentalid +
                ", clientid=" + clientid +
                ", bookid=" + bookid +
                ", date=" + date +
                ", returned=" + returned +
                '}';
    }
}
