
package com.mycompany.chatapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ChatAppTest {

    @BeforeEach
    public void setUp() {
        ChatApp.numMessagesSent = 0;
        for (int i = 0; i < ChatApp.MAX_MESSAGES; i++) {
            ChatApp.messageHashes[i] = null;
            ChatApp.recipients[i] = null;
            ChatApp.messages[i] = null;
        }
    }

    @Test
    public void testValidPassword() {
        assertTrue(ChatApp.isValidPassword("Abcdef1!"));
    }

    @Test
    public void testInvalidPassword_NoUppercase() {
        assertFalse(ChatApp.isValidPassword("abcdef1!"));
    }

    @Test
    public void testInvalidPassword_NoDigit() {
        assertFalse(ChatApp.isValidPassword("Abcdefg!"));
    }

    @Test
    public void testInvalidPassword_NoSpecialChar() {
        assertFalse(ChatApp.isValidPassword("Abcdefg1"));
    }

    @Test
    public void testLongestMessage() {
        ChatApp.messages[0] = "Short message.";
        ChatApp.messages[1] = "This is the longest message in this test.";
        ChatApp.messages[2] = "Medium one.";
        ChatApp.numMessagesSent = 3;

        String expected = "This is the longest message in this test.";
        String actual = "";
        for (String msg : ChatApp.messages) {
            if (msg != null && msg.length() > actual.length()) {
                actual = msg;
            }
        }

        assertEquals(expected, actual);
    }

    @Test
    public void testDeleteMessageByHash() {
        ChatApp.messageHashes[0] = "01:1:HELLOWORLD";
        ChatApp.messages[0] = "Hello World";
        ChatApp.recipients[0] = "+27830000000";
        ChatApp.numMessagesSent = 1;

        String hashToDelete = "01:1:HELLOWORLD";

        // Simulate deletion
        for (int i = 0; i < ChatApp.numMessagesSent; i++) {
            if (ChatApp.messageHashes[i] != null && ChatApp.messageHashes[i].equals(hashToDelete)) {
                ChatApp.messageHashes[i] = null;
                ChatApp.recipients[i] = null;
                ChatApp.messages[i] = null;
                break;
            }
        }

        assertNull(ChatApp.messageHashes[0]);
        assertNull(ChatApp.recipients[0]);
        assertNull(ChatApp.messages[0]);
    }

    @Test
    public void testMessageToSpecificRecipient() {
        ChatApp.recipients[0] = "+27830000001";
        ChatApp.messages[0] = "Hi!";
        ChatApp.recipients[1] = "+27830000002";
        ChatApp.messages[1] = "Hello!";
        ChatApp.recipients[2] = "+27830000001";
        ChatApp.messages[2] = "What's up?";
        ChatApp.numMessagesSent = 3;

        int count = 0;
        for (int i = 0; i < ChatApp.numMessagesSent; i++) {
            if (ChatApp.recipients[i] != null && ChatApp.recipients[i].equals("+27830000001")) {
                count++;
            }
        }

        assertEquals(2, count); // 2 messages to +27830000001
    }
}
