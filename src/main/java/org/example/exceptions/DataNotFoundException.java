package org.example.exceptions;

public class DataNotFoundException extends Exception {
    public DataNotFoundException(String searchedValueName, int searchedValueId) {
        super(String.format("Data with %s %d not found!", searchedValueName, searchedValueId));
    }
}
