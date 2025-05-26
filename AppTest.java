
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class AppTest {

    Login login = new Login();

    @Test
    public void testValidUsername() {
        assertTrue(login.checkUserName("kyl_1"));
    }

    @Test
    public void testInvalidUsername() {
        assertFalse(login.checkUserName("kyle!!!!!!!"));
    }

    @Test
    public void testValidPassword() {
        assertTrue(login.checkPasswordComplexity("Ch&&sec@ke99!"));
    }

    @Test
    public void testInvalidPassword() {
        assertFalse(login.checkPasswordComplexity("password"));
    }

    @Test
    public void testValidPhone() {
        assertTrue(login.checkCellPhoneNumber("+27838968976"));
    }

    @Test
    public void testInvalidPhone() {
        assertFalse(login.checkCellPhoneNumber("08966553"));
    }

    @Test
    public void testMessageLengthSuccess() {
        Message m = new Message("+27830000000", "Short message", 1);
        assertTrue(m.checkMessageLength());
    }

    @Test
    public void testMessageLengthFail() {
        Message m = new Message("+27830000000", "x".repeat(300), 1);
        assertFalse(m.checkMessageLength());
    }

    @Test
    public void testMessageHash() {
        Message m = new Message("+27830000000", "Hi Mike, can you join us for dinner tonight", 0);
        String hash = m.createMessageHash();
        assertTrue(hash.endsWith("HITONIGHT"));
    }
}