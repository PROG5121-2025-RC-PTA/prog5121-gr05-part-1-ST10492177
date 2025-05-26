import java.util.regex.*;
public class Login {
    
 

    private User registeredUser;
    private boolean isLoggedIn = false;

    public boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    public boolean checkPasswordComplexity(String password) {
        return password.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$");
    }

    public boolean checkCellPhoneNumber(String number) {
        return number.matches("^\\+\\d{1,3}\\d{10}$");
    }

    public String registerUser(User user) {
        if (!checkUserName(user.username)) {
            return "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        if (!checkPasswordComplexity(user.password)) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        if (!checkCellPhoneNumber(user.cellNumber)) {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }
        registeredUser = user;
        return "User successfully registered.";
    }

    public boolean loginUser(String username, String password) {
        if (registeredUser != null && registeredUser.username.equals(username) && registeredUser.password.equals(password)) {
            isLoggedIn = true;
            return true;
        }
        return false;
    }

    public String returnLoginStatus() {
        return isLoggedIn ? 
            "Welcome " + registeredUser.firstName + ", " + registeredUser.lastName + " it is great to see you again." : 
            "Username or password incorrect, please try again.";
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public User getUser() {
        return registeredUser;
    }

    public void injectRegisteredUser(User user) {
        this.registeredUser = user;
    }
}

