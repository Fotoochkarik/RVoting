package com.project.voting.error;

public class NotFoundException extends IllegalRequestDataException {
    public NotFoundException(String message) {
        super(message);
    }
}