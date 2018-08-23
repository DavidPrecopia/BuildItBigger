package com.example.jokelibrary;

import java.util.ArrayList;
import java.util.List;

public class Jokes {
    private static final List<String> jokesList;
    private static int jokeIndex;

    static {
        jokeIndex = 0;

        jokesList = new ArrayList<>();
        jokesList.add("Knock knock.\nRace condition.\nWho's there?");
        jokesList.add("A guy walks into a bar and asks for 1.4 root beers. The bartender says \"I'll have to charge you extra, that's a root beer float\". The guy says \"In that case, better make it a double.\"");
        jokesList.add("A SQL statement walks into a bar and sees two tables. It approaches, and asks “may I join you?”");
    }

    private Jokes() {
    }

    public static String getJoke() {
        return jokesList.get(getIndex());
    }

    private static int getIndex() {
        if (jokeIndex == jokesList.size()) {
            jokeIndex = 0;
        }
        return jokeIndex++;
    }


    public static final class TestingJokes extends Jokes {
        public static int testListSize() {
            return Jokes.jokesList.size();
        }
        public static int testGetIndex() {
            return Jokes.getIndex();
        }
    }
}