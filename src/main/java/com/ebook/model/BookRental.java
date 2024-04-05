package com.ebook.model;

import java.util.Date;

public class BookRental {
    private int rentalsId;
    private Book book;
    private Date rentalDate;
    private Date returnDate;

    public BookRental(int rentalsId, Book book, Date rentalDate, Date returnDate) {
        this.rentalsId = rentalsId;
        this.book = book;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
    }

    public Book getBook() {
        return book;
    }

    public Date getRentalDate() {
        return rentalDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void setRentalDate(Date rentalDate) {
        this.rentalDate = rentalDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public int getRentalsId() {
        return rentalsId;
    }

    public void setRentalsId(int rentalsId) {
        this.rentalsId = rentalsId;
    }
}
