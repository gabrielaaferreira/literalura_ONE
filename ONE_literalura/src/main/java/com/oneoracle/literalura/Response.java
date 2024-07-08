package com.oneoracle.literalura;

import java.util.List;

public class Response {

    private List<BookResult> results;

    public List<BookResult> getResults() {
        return results;
    }

    public void setResults(List<BookResult> results) {
        this.results = results;
    }

    public static class BookResult {
        private String title;
        private List<Person> authors;
        private String[] languages;
        private int download_count;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<Person> getAuthors() {
            return authors;
        }

        public void setAuthors(List<Person> authors) {
            this.authors = authors;
        }

        public String[] getLanguages() {
            return languages;
        }

        public void setLanguages(String[] languages) {
            this.languages = languages;
        }

        public int getDownload_count() {
            return download_count;
        }

        public void setDownload_count(int download_count) {
            this.download_count = download_count;
        }
    }

    public static class Person {
        private String name;
        private int birth_year;
        private int death_year;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getBirth_year() {
            return birth_year;
        }

        public void setBirth_year(int birth_year) {
            this.birth_year = birth_year;
        }

        public int getDeath_year() {
            return death_year;
        }

        public void setDeath_year(int death_year) {
            this.death_year = death_year;
        }
    }
}
