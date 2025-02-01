package com.github.dobrosi.imdbclient;

public class ImdbRecordNotFound extends RuntimeException {
    public ImdbRecordNotFound(Object id, int code) {
        super("IMDB record not found by " + id + " (" + code + ")");
    }
}
