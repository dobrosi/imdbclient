package com.github.dobrosi.imdbclient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.HttpStatusException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static java.lang.String.format;

public class ImdbClient {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Movie {
        public static class Rate {
            @JsonProperty("Source")
            public String source;
            @JsonProperty("Value")
            public String value;
        }
        @JsonProperty("imdbID")
        public String id;
        @JsonProperty("Title")
        public String title;
        @JsonProperty("Year")
        public String year;
        public String director;
        @JsonProperty("Poster")
        public String image;
        @JsonProperty("Ratings")
        public List<Rate> ratings;
        @JsonProperty
        public String getLink() {
            return String.format("https://www.imdb.com/title/%s", id);
        }
    }

    public Movie getMovie(String imdbid) {
        try(HttpClient client = HttpClient.newHttpClient()) {
            return new ObjectMapper().readValue(client.send(
                    HttpRequest.newBuilder()
                            .uri(URI.create(getLink(imdbid)))
                            .GET()
                            .build(),
                    HttpResponse.BodyHandlers.ofString()).body(), Movie.class);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public String getLink(final String imdbid) {
        return format("http://www.omdbapi.com/?i=%s&apikey=e7bd1fc7", imdbid);
    }
}
