package client;

public class User {
    String username;
    String email;
    String regNo;
    boolean isAuthenticated = false;
    boolean isStudent = true;
    String output;

    public void logout() {
        this.username = "";
        this.email = "";
        this.isAuthenticated = false;
    }

    public void login(String username, String email, boolean isStudent) {
        this.username = username;
        this.email = email;
        this.isStudent = isStudent;
        this.isAuthenticated = true;
    }
}
