package com.example.jokelibrary;

import java.util.ArrayList;
import java.util.List;

public class Jokes {
    private static final List<String> jokesList;

    static {
        jokesList = new ArrayList<>();
        jokesList.add("Knock knock.\nRace condition.\nWho's there?");
        jokesList.add("A guy walks into a bar and asks for 1.4 root beers. The bartender says \"I'll have to charge you extra, that's a root beer float\". The guy says \"In that case, better make it a double.\"");
        jokesList.add("A SQL statement walks into a bar and sees two tables. It approaches, and asks “may I join you?”");
    }

    private Jokes() {
    }

    public static String getJoke() {
        return jokesList.get(randomNumber());
    }

    private static int randomNumber() {
        return (int) (Math.floor(Math.random() * jokesList.size()));
    }
}
