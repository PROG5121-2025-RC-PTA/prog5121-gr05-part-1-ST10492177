import org.junit.Test;
import static org.junit.Assert.*;

public class PlayerTest {

    @Test
    public void testScoreAddition() {
        Player p = new Player("TestPlayer");
        p.addScore(10);
        assertEquals(10, p.getScore());
    }

    @Test
    public void testMultipleScoreAdditions() {
        Player p = new Player("TestPlayer");
        p.addScore(5);
        p.addScore(15);
        assertEquals(20, p.getScore());
    }

    @Test
    public void testWinnerSelection() {
        Player p1 = new Player("Alice");
        Player p2 = new Player("Bob");
        p1.addScore(5);
        p2.addScore(15);
        assertTrue(p2.getScore() > p1.getScore());
    }

    @Test
    public void testComputerGuessWithinRange() {
        ComputerPlayer cpu = new ComputerPlayer("CPU");
        int guess = cpu.makeGuess(20);
        assertTrue(guess >= 1 && guess <= 20);
    }

    @Test
    public void testPlayerNameEncapsulation() {
        Player p = new Player("EncapsulatedPlayer");
        assertEquals("EncapsulatedPlayer", p.getName());
    }
}
