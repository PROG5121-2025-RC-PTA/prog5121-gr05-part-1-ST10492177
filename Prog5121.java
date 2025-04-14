
package proga1;
 
import java.util.regex.*;
import java.util.Objects;
import java.util.Scanner;
 // assisted by openAI(2025).chatGPT
// chat registration app
public class Prog5121 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Register your account:"); // user to register for account
        System.out.print("Enter your username: "); // requesting user to enter userName
        String username = scanner.nextLine();

        System.out.print("Enter your password: "); // requesting user to enter password
        String password = scanner.nextLine();

        System.out.print("Enter cell number (+27): "); // requesting user for cellphone number 
        String cell = scanner.nextLine();

        System.out.print("Enter your first name: ");  // requesting user for firstName
        String firstName = scanner.nextLine();

        System.out.print("Enter your last name: ");  // requesting user for lastName
        String lastName = scanner.nextLine();

        Login login = new Login(username, password, cell, firstName, lastName);
        System.out.println(login.registerUser());
            // requesting user to enter details
        System.out.println("\nLogin:");
        System.out.print("Enter your username: ");
        String loginUser = scanner.nextLine();

        System.out.print("Enter your password: ");
        String loginPass = scanner.nextLine();

        boolean success = login.loginUser(loginUser, loginPass);
        System.out.println(login.returnLoginStatus(success));
    }
}

class Login {
    private String username;
    private String password;
    private String cellphoneNumber;
    private String firstName;
    private String lastName;

    public Login(String username, String password, String cellphoneNumber, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.cellphoneNumber = cellphoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
    }
     // checking if details entered are correct
    public boolean checkUserName() {
        return username.contains("_") && username.length() <= 10;
    }

    public boolean checkPassword() {
        return password.matches("^(?=.[A-Z])(?=.\\d)(?=.*[@#$%^&+=!]).{8,}$");// got it from openAI.chatGPT,2025
    }

    public boolean checkCellPhoneNumber() {
        Pattern phoneNumber = Pattern.compile("^\\+27\\d{9}$");
        Matcher matcher = phoneNumber.matcher(cellphoneNumber);
        return matcher.matches();
    }
      // checking if user enter correct details
    public String registerUser() {
        boolean usernameValid = checkUserName();
        boolean passwordValid = checkPassword();
        boolean phoneValid = checkCellPhoneNumber();

        if (!usernameValid) {
            return " The Username is not correctly formatted, please ensure that your username contains an underscore and is no more than ten characters in length.";
        }

        if (!passwordValid) {
            return "Password is not correctly formatted, please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }

        if (!phoneValid) {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }

        return "Username successfully captured.\nPassword successfully captured.\nCell phone number successfully added.";
    }
       // checking if user account is registered
    public boolean loginUser(String usernameInput, String passwordInput) {
        return this.username.equals(usernameInput) && this.password.equals(passwordInput);
    }

    public String returnLoginStatus(boolean loginSuccess) {
        if (loginSuccess) {
            return "Welcome " + this.firstName + ", " + this.lastName + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}
    

