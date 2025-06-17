package com.mycompany.chatapp;

import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ChatApp {

    static class User {
        String username;
        String password;
        String name;
        String surname;
        String phone;

        User(String username, String password, String name, String surname, String phone) {
            this.username = username;
            this.password = password;
            this.name = name;
            this.surname = surname;
            this.phone = phone;
        }
    }

    static Map<String, User> users = new HashMap<>();
    static User currentUser = null;

    static final int MAX_MESSAGES = 100;
    static int numMessagesSent = 0;

    static String[] messageHashes = new String[MAX_MESSAGES];
    static String[] recipients = new String[MAX_MESSAGES];
    static String[] messages = new String[MAX_MESSAGES];

    static Scanner scanner = new Scanner(System.in);
    static Random random = new Random();

    public static void main(String[] args) {
        while (true) {
            Object[] mainOptions = {"Register", "Login", "Exit"};
            int mainChoice = JOptionPane.showOptionDialog(null, "Welcome to QuickChat", "Main Menu",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                    mainOptions, mainOptions[0]);

            switch (mainChoice) {
                case 0 -> registerUser();
                case 1 -> {
                    login();
                    if (currentUser != null) userSession();
                }
                case 2, JOptionPane.CLOSED_OPTION -> {
                    JOptionPane.showMessageDialog(null, "Goodbye!");
                    System.exit(0);
                }
                default -> JOptionPane.showMessageDialog(null, "Invalid option.");
            }
        }
    }

    static void registerUser() {
        String name = JOptionPane.showInputDialog("Enter your name:");
        String surname = JOptionPane.showInputDialog("Enter your surname:");
        String username;
        while (true) {
            username = JOptionPane.showInputDialog("Enter username (<=5 characters, include _):");
            if (username != null && username.contains("_") && username.length() <= 5 && !users.containsKey(username)) {
                break;
            }
            JOptionPane.showMessageDialog(null, "Invalid or taken username.");
        }

        String password;
        while (true) {
            password = JOptionPane.showInputDialog("Enter a strong password (8+ chars, upper, digit, special):");
            if (isValidPassword(password)) break;
            JOptionPane.showMessageDialog(null, "Password too weak.");
        }

        String phone;
        while (true) {
            phone = JOptionPane.showInputDialog("Enter cellphone (+27... format, <=10 digits after):");
            if (phone != null && phone.matches("\\+27\\d{9}")) break;
            JOptionPane.showMessageDialog(null, "Invalid format.");
        }

        users.put(username, new User(username, password, name, surname, phone));
        JOptionPane.showMessageDialog(null, "Registration successful!");
    }

    static void login() {
        String username = JOptionPane.showInputDialog("Enter your username:");
        String password = JOptionPane.showInputDialog("Enter your password:");
        User user = users.get(username);

        if (user != null && user.password.equals(password)) {
            currentUser = user;
            JOptionPane.showMessageDialog(null, "Welcome " + user.name + " " + user.surname + "! You are now logged in.");
        } else {
            JOptionPane.showMessageDialog(null, "Login failed.");
        }
    }

    static boolean isValidPassword(String password) {
        if (password == null || password.length() < 8) return false;
        boolean upper = false, digit = false, special = false;
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) upper = true;
            if (Character.isDigit(c)) digit = true;
            if ("!@#$%^&*()_+=<>?/{}[];:'\",.".contains(String.valueOf(c))) special = true;
        }
        return upper && digit && special;
    }

    static void userSession() {
        while (true) {
            Object[] options = {
                    "Send Messages", "Show Sent", "Delete Message", "Report",
                    "Longest Message", "Store Later (File)", "Logout"
            };
            int choice = JOptionPane.showOptionDialog(null, "Choose an option:", "User Menu",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

            switch (choice) {
                case 0 -> sendMessages();
                case 1 -> showMessages();
                case 2 -> deleteMessage();
                case 3 -> displayReport();
                case 4 -> displayLongestMessage();
                case 5 -> storeForLater();
                case 6 -> {
                    currentUser = null;
                    JOptionPane.showMessageDialog(null, "You are now logged out.");
                    return;
                }
                default -> JOptionPane.showMessageDialog(null, "Invalid option.");
            }
        }
    }

    static void sendMessages() {
        int count;
        try {
            count = Integer.parseInt(JOptionPane.showInputDialog("How many messages to send?"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid number.");
            return;
        }

        for (int i = 0; i < count; i++) {
            if (numMessagesSent >= MAX_MESSAGES) {
                JOptionPane.showMessageDialog(null, "Message limit reached.");
                return;
            }

            String id = String.format("%010d", random.nextInt(1000000000));
            String recipient;
            while (true) {
                recipient = JOptionPane.showInputDialog("Enter recipient number (start with +):");
                if (recipient != null && recipient.matches("\\+\\d{10,}")) break;
                JOptionPane.showMessageDialog(null, "Invalid recipient format.");
            }

            String message;
            while (true) {
                message = JOptionPane.showInputDialog("Enter message (max 250 chars):");
                if (message != null && message.length() <= 250) break;
                JOptionPane.showMessageDialog(null, "Too long!");
            }

            String[] words = message.split("\\s+");
            String first = words.length > 0 ? words[0].toUpperCase() : "MSG";
            String last = words.length > 1 ? words[words.length - 1].toUpperCase() : first;
            String hash = id.substring(0, 2) + ":" + (numMessagesSent + 1) + ":" + first + last;

            Object[] sendOptions = {"Send", "Disregard", "Store for Later"};
            int sendChoice = JOptionPane.showOptionDialog(null, "Send or Store?", "Options",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    sendOptions, sendOptions[0]);

            switch (sendChoice) {
                case 0 -> {
                    messageHashes[numMessagesSent] = hash;
                    recipients[numMessagesSent] = recipient;
                    messages[numMessagesSent] = message;
                    numMessagesSent++;
                    JOptionPane.showMessageDialog(null, "Message sent!\nHash: " + hash);
                }
                case 1 -> JOptionPane.showMessageDialog(null, "Message disregarded.");
                case 2 -> {
                    writeToFile("stored_messages.txt", message);
                    JOptionPane.showMessageDialog(null, "Stored for later.");
                }
            }
        }
    }

    static void showMessages() {
        String recipient = JOptionPane.showInputDialog("Enter recipient number:");
        StringBuilder sb = new StringBuilder("Messages to " + recipient + ":\n");

        boolean found = false;
        for (int i = 0; i < numMessagesSent; i++) {
            if (recipients[i] != null && recipients[i].equals(recipient)) {
                sb.append("- ").append(messages[i]).append("\n");
                found = true;
            }
        }
        if (!found) sb.append("No messages found.");
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    static void deleteMessage() {
        String hash = JOptionPane.showInputDialog("Enter hash to delete:");
        for (int i = 0; i < numMessagesSent; i++) {
            if (messageHashes[i] != null && messageHashes[i].equals(hash)) {
                messageHashes[i] = null;
                recipients[i] = null;
                messages[i] = null;
                JOptionPane.showMessageDialog(null, "Deleted successfully.");
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Message not found.");
    }

    static void displayReport() {
        StringBuilder sb = new StringBuilder("---- SENT MESSAGE REPORT ----\n");
        for (int i = 0; i < numMessagesSent; i++) {
            if (messages[i] != null) {
                sb.append("Hash: ").append(messageHashes[i]).append("\n");
                sb.append("Recipient: ").append(recipients[i]).append("\n");
                sb.append("Message: ").append(messages[i]).append("\n\n");
            }
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    static void displayLongestMessage() {
        String longest = "";
        for (String msg : messages) {
            if (msg != null && msg.length() > longest.length()) longest = msg;
        }
        JOptionPane.showMessageDialog(null, "Longest message:\n" + longest);
    }

    static void storeForLater() {
        String message = JOptionPane.showInputDialog("Enter message to store in file:");
        if (message != null) {
            writeToFile("stored_messages.txt", message);
            JOptionPane.showMessageDialog(null, "Message stored to file.");
        }
    }

    static void writeToFile(String filename, String content) {
        try (FileWriter fw = new FileWriter(filename, true)) {
            fw.write(content + "\n");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "File write error.");
        }
    }
}
