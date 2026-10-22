package com.votingbooth.mobilebff.model.exception;

public class InvalidPageException extends RuntimeException {

    public InvalidPageException(int page, int size) {
        super("Invalid page request: page=" + page + ", size=" + size + ".");
    }

}
