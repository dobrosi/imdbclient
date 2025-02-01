
package com.github.dobrosi.imdbclient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import static java.lang.String.format;

public class ImdbClient {
    public static class Movie {
        public String id;
        public String title;
        public String year;
        public String director;
        public String actor;
        public String genre;
        public String link;
        public String description;
        public String rating;
        public String image;
    }

    public Movie getMovie(String imdbid) {
        try {
            Movie movie = new Movie();
            movie.id = imdbid;
            movie.link = getLink(imdbid);
            Document doc = Jsoup.connect(movie.link).get();
            Elements titleElement = doc.select("span.hero__primary-text");
            movie.title = titleElement.text();
            movie.image = getValue(doc, "image");
            //String[] words = getValue(doc, "title").split(" ⭐ ");
            //movie.year = words[0].split(" \\(")[1].split("\\)")[0];
            //movie.rating = words[1].split(" | ")[0];
            return movie;
        } catch (HttpStatusException e) {
            throw new ImdbRecordNotFound(imdbid, e.getStatusCode());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getLink(final String imdbid) {
        return format("https://www.imdb.com/title/%s", imdbid);
    }

    private String getValue(Document document, String key) throws UnsupportedEncodingException {
        return new String(document.selectXpath("//meta[@property=\"og:" + key + "\"]").attr("content").getBytes(), "UTF8");
    }
}
