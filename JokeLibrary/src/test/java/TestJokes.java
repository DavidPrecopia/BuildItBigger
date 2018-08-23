import com.example.jokelibrary.Jokes;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TestJokes {
    @Test
    public void getJokesIndexIsValid() {
        int x = 0;
        while (x < 50) {
            int index = Jokes.TestingJokes.testGetIndex();
            assertTrue (index >= 0 && index < Jokes.TestingJokes.testListSize());
            x++;
        }
    }
}
