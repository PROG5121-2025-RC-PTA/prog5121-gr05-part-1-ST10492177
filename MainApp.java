
import javax.swing.*;
import java.util.HashMap;


public class MainApp {
    public static void main(String[] args) {
        HashMap<String, User> registeredUsers = new HashMap<>();
        Login login = new Login();
        MessageManager manager = new MessageManager();
        boolean exitApp = false;

        while (!exitApp) {
            String menu = JOptionPane.showInputDialog(
                "Welcome to QuickChat!\nChoose an option:\n1) Register\n2) Login\n3) Exit");

            switch (menu) {
                case "1":
                    String newUsername = JOptionPane.showInputDialog("Register - Enter username:");
                    String newPassword = JOptionPane.showInputDialog("Enter password (must contain at least 8 characters, a capital letter, a number, and a special character):");
                    String newCell = JOptionPane.showInputDialog("Enter cellphone number (+countrycode):");
                    String newFname = JOptionPane.showInputDialog("Enter first name:");
                    String newLname = JOptionPane.showInputDialog("Enter last name:");
                    User newUser = new User(newUsername, newPassword, newCell, newFname, newLname);
                    String registerMsg = login.registerUser(newUser);
                    JOptionPane.showMessageDialog(null, registerMsg);
                    if (registerMsg.equals("User successfully registered.")) {
                        registeredUsers.put(newUser.username, newUser);
                    }
                    break;

                case "2":
                    if (registeredUsers.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "No users registered. Please register first.");
                        break;
                    }

                    String loginUsername = JOptionPane.showInputDialog("Enter username:");
                    String loginPassword = JOptionPane.showInputDialog("Enter password:");
                    User userToLogin = registeredUsers.get(loginUsername);

                    if (userToLogin != null) {
                        login = new Login();
                        login.injectRegisteredUser(userToLogin);
                        login.loginUser(loginUsername, loginPassword);
                        JOptionPane.showMessageDialog(null, login.returnLoginStatus());
                    } else {
                        JOptionPane.showMessageDialog(null, "Username not found.");
                        break;
                    }

                    if (login.isLoggedIn()) {
                        boolean session = true;
                        while (session) {
                            String subMenu = JOptionPane.showInputDialog(
                                "1) Send Messages\n2) Log Out");

                            switch (subMenu) {
                                case "1":
                                    int numMessages = Integer.parseInt(JOptionPane.showInputDialog("How many messages to send?"));
                                    for (int i = 0; i < numMessages; i++) {
                                        String rec = JOptionPane.showInputDialog("Enter recipient number (+code):");
                                        String msg = JOptionPane.showInputDialog("Enter message:");

                                        Message m = new Message(rec, msg, i);
                                        if (!m.checkRecipientCell()) {
                                            JOptionPane.showMessageDialog(null, "Invalid recipient number.");
                                            continue;
                                        }
                                        if (!m.checkMessageLength()) {
                                            JOptionPane.showMessageDialog(null, "Message exceeds 250 characters.");
                                            continue;
                                        }

                                        String action = JOptionPane.showInputDialog("Choose: Send / Store / Disregard");
                                        manager.handleMessage(m, action);

                                        if (action.equalsIgnoreCase("send")) {
                                            JOptionPane.showMessageDialog(null, "MessageID: " + m.id +
                                                    "\nHash: " + m.createMessageHash() +
                                                    "\nRecipient: " + m.recipient +
                                                    "\nMessage: " + m.text);
                                        }
                                    }
                                    JOptionPane.showMessageDialog(null, "Total messages sent: " + manager.getTotalSent());
                                    break;

                                case "2":
                                    JOptionPane.showMessageDialog(null, "Logged out successfully.");
                                    session = false;
                                    break;

                                default:
                                    JOptionPane.showMessageDialog(null, "Invalid option. Try again.");
                            }
                        }
                    }
                    break;

                case "3":
                    exitApp = true;
                    JOptionPane.showMessageDialog(null, "Goodbye!");
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Invalid choice.");
            }
        }
    }
}