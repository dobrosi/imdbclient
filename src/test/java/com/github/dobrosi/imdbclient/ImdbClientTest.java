package com.github.dobrosi.imdbclient;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ImdbClientTest {

    @Test
    void getMovie() {
        ImdbClient.Movie m = new ImdbClient().getMovie("tt7286456");
        assertEquals("https://www.imdb.com/title/tt7286456", m.link);
        assertEquals("Joker", m.title);
        assertEquals("2019", m.year);
        assertEquals("8.4", m.rating);
        assertEquals(null, m.director);
    }
}