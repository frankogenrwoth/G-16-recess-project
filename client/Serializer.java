package client;


import org.json.JSONObject;

import java.util.Scanner;

public class Serializer {
    User user;

    public Serializer(User user) {
        this.user = user;
    }

    public String login() {
        if (this.user.isAuthenticated) {
            return "Session already authenticated";
        }

        // collect user login details
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter user login details (username and email)");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.println("\n");
        String[] tokens = new String[3];
        tokens[0] = "login";
        tokens[1] = username;
        tokens[2] = email;

        JSONObject obj = new JSONObject();
        obj.put("command", "login");
        obj.put("isAuthenticated", false);
        obj.put("tokens", tokens);
        obj.put("isStudent", false);

        return obj.toString(4);
    }

    public String register(String[] arr) {
        JSONObject obj = new JSONObject();
        obj.put("command", "register");
        obj.put("isAuthenticated", user.isAuthenticated);
        obj.put("tokens", arr);
        obj.put("isStudent", user.isStudent);


        return obj.toString(4);
    }

    public String attemptChallenge(String[] tokens) {
        JSONObject obj = new JSONObject();
        obj.put("command", "attemptChallenge");
        obj.put("isAuthenticated", false);
        obj.put("tokens", tokens);
        obj.put("isStudent", true);


        return obj.toString(4);
    }

    public String viewApplicants() {
        JSONObject obj = new JSONObject();
        obj.put("command", "viewApplicants");
        obj.put("isAuthenticated", false);
        obj.put("tokens", JSONObject.NULL);
        obj.put("isStudent", true);


        return obj.toString(4);
    }

    public String confirm(String[] arr) {
        JSONObject obj = new JSONObject();
        obj.put("command", "confirm");
        obj.put("isAuthenticated", false);
        obj.put("tokens", arr);
        obj.put("isStudent", true);


        return obj.toString(4);
    }

    public String viewChallenges() {
        JSONObject obj = new JSONObject();
        obj.put("command", "viewChallenges");
        obj.put("isAuthenticated", false);
        obj.put("tokens", JSONObject.NULL);
        obj.put("isStudent", true);


        return obj.toString(4);
    }

    public String logout() {
        this.user.isAuthenticated = false;
        return null;
    }

    public String serialize(String command) {
        String[] tokens = command.split("\\s+");

        if (!user.isAuthenticated && tokens[0].equals("register")) {
            return this.register(tokens);
        }

        if (!user.isAuthenticated && tokens[0].equals("login")) {
            return this.login();
        }

        if (!user.isAuthenticated) {
            return "Session unauthenticated first login by entering command login";
        }

        if (user.isStudent) {
            switch (tokens[0]) {
                case "logout":
                    return this.logout();

                case "viewChallenges":
                    return this.viewChallenges();

                case "attemptChallenge":
                    return this.attemptChallenge(tokens);

                default:
                    return "Invalid student command";
            }
        } else {
            switch (tokens[0]) {
                case "logout":
                    return this.logout();

                case "confirm":
                    return this.confirm(tokens);

                case "viewApplicants":
                    return this.viewApplicants();

                default:
                    return "Invalid school representative command";
            }
        }

    }

    public static void main (String[] args) {
        Serializer sample = new Serializer(new User());
        sample.serialize("login frank ogenrwothjimfrank@gmail.com");
    }
}
