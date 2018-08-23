import com.example.jokelibrary.Jokes;

import org.junit.Test;

public class TestJokes {
    @Test
    public void getJokesShouldNotError() {
        int x = 0;
        while (x < 50) {
            Jokes.getJoke();
            x++;
        }
    }
}