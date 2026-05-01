package todolist;

import java.util.ArrayList;

public class User{
    private static int counter = 0;
    public static ArrayList<User> users = new ArrayList<User>();
    private int id;
    private String username;
    private String password;

    public User(String username, String password){
        this.id = ++counter;
        this.username = username;
        this.password = password;
    }

    private User(){}

    public static User createUser(String username, String password){
         if(username.isEmpty() || password.isEmpty()) return null;
        return new User(username, password);
    }

    public int getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }

    public static void printUsers() {
        for(User user : AuthManager.getAllUsers()) {
            String info = "ID: " + user.getId() + ", Username: " + user.getUsername() + ", Password: " + user.getPassword();
            TaskController.messageJOptionPanel(null, info);
        }
    }

}