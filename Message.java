import java.util.Random;

public class Message {
    String id;
    String recipient;
    String text;
    int number;
    String status; // Sent, Stored, Disregarded

    public Message(String recipient, String text, int number) {
        this.recipient = recipient;
        this.text = text;
        this.number = number;
        this.id = String.valueOf(new Random().nextInt(900000000) + 100000000);
    }

    public boolean checkMessageLength() {
        return text.length() <= 250;
    }

    public boolean checkRecipientCell() {
        return recipient.matches("^\\+\\d{1,3}\\d{10}$");
    }

    public String createMessageHash() {
        String[] words = text.split(" ");
        return id.substring(0, 2) + ":" + number + ":" + words[0].toUpperCase() + words[words.length - 1].toUpperCase();
    }

    public void setStatus(String status) {
        this.status = status;
    }

    
    
}
