package com.appspot.natanedwin.app;

public class AppError extends RuntimeException {

    private final String description;

    public AppError(String caption, String description) {
        super(caption);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
