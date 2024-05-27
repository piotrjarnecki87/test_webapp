package com.ebook.exceptions;

public class InvalidPublicationYearException extends IllegalArgumentException {
    private final int minYear;
    private final int maxYear;

    public InvalidPublicationYearException(int minYear, int maxYear) {
        super(String.format("Year of publication must be between %d and %d", minYear, maxYear));
        this.minYear = minYear;
        this.maxYear = maxYear;
    }

    public int getMinYear() {
        return minYear;
    }

    public int getMaxYear() {
        return maxYear;
    }
}
