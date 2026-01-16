import java.util.Random;
import java.util.Scanner;

// Base class (Inheritance Example)
class Player {
    private String name;   // Information hiding
    private int score;     // Encapsulation

    public Player(String name) {
        this.name = name;
        this.score = 0;
    }

    public String getName() { return name; }
    public int getScore() { return score; }
    public void addScore(int points) { score += points; }
}

// Derived class for human players
class HumanPlayer extends Player {
    public HumanPlayer(String name) {
        super(name);
    }
}

// Derived class for computer players
class ComputerPlayer extends Player {
    private Random rand = new Random();

    public ComputerPlayer(String name) {
        super(name);
    }

    public int makeGuess(int max) {
        return rand.nextInt(max) + 1;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random rand = new Random();

        System.out.println("🎮 Welcome to Number Guessing Tournament 🎮");
        System.out.print("Enter number of players: ");
        int numPlayers = sc.nextInt();
        sc.nextLine(); // consume newline

        Player[] players = new Player[numPlayers];

        // Create players (Inheritance + Constructors)
        for (int i = 0; i < numPlayers; i++) {
            System.out.print("Enter player name (or type 'CPU' for computer): ");
            String name = sc.nextLine();
            if (name.equalsIgnoreCase("CPU")) {
                players[i] = new ComputerPlayer("Computer_" + (i+1));
            } else {
                players[i] = new HumanPlayer(name);
            }
        }

        // Game Rounds
        for (int round = 1; round <= 3; round++) {
            System.out.println("\n--- Round " + round + " ---");
            int secret = rand.nextInt(20) + 1; // number between 1-20
            boolean guessed = false;

            for (Player p : players) {
                int guess;

                if (p instanceof HumanPlayer) {
                    // ✅ Input validation for human players
                    guess = -1;
                    boolean valid = false;
                    while (!valid) {
                        System.out.print(p.getName() + ", enter your guess (1-20): ");
                        if (sc.hasNextInt()) {
                            guess = sc.nextInt();
                            if (guess >= 1 && guess <= 20) {
                                valid = true;
                            } else {
                                System.out.println("⚠ Please enter a number between 1 and 20.");
                            }
                        } else {
                            System.out.println("⚠ Invalid input. Please enter a number!");
                            sc.next(); // discard invalid input
                        }
                    }
                } else {
                    // Computer player makes a random guess
                    guess = ((ComputerPlayer) p).makeGuess(20);
                    System.out.println(p.getName() + " guessed: " + guess);
                }

                // Check guess
                if (guess == secret) {
                    System.out.println("✅ " + p.getName() + " guessed correctly!");
                    p.addScore(10);
                    guessed = true;
                } else {
                    System.out.println("❌ Wrong guess.");
                }
            }

            if (!guessed) {
                System.out.println("Nobody guessed correctly. Secret was: " + secret);
            }
        }

        // Final Report
        System.out.println("\n=== TOURNAMENT REPORT ===");
        for (Player p : players) {
            System.out.println(p.getName() + " - Score: " + p.getScore());
        }

        // Find Winner
        Player winner = players[0];
        for (Player p : players) {
            if (p.getScore() > winner.getScore()) {
                winner = p;
            }
        }
        System.out.println("🏆 WINNER: " + winner.getName() + " with " + winner.getScore() + " points!");
    }
}
