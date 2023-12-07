package com.example.demo3;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private final String usersFolder = "users"; // Folder to store user data
    private final Map<String, User> registeredUsers;

    public UserManager() {
        this.registeredUsers = new HashMap<>();
        loadUsers();
    }

    public boolean signUp(String username, String password, String email) {
        if (registeredUsers.containsKey(username)) {
            System.out.println("Username already exists. Logging in...");
            return false;
        }

        User newUser = new User(username, password, email);
        registeredUsers.put(username, newUser);
        saveUser(newUser);
        System.out.println("Signup successful. Welcome, " + username + "!");
        return true;
    }

    public boolean login(String username, String password) {
        if (!registeredUsers.containsKey(username)) {
            System.out.println("User not found. Please sign up.");
            return false;
        }

        User user = registeredUsers.get(username);
        if (!user.getPassword().equals(password)) {
            System.out.println("Invalid password.");
            return false;
        }

        System.out.println("Login successful. Welcome, " + username + "!");
        return true;
    }

    private void saveUser(User user) {
        try (FileOutputStream fileOut = new FileOutputStream(usersFolder + "/" + user.getUsername() + ".ser");
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
            objectOut.writeObject(user);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle file write error
        }
    }

    private void loadUsers() {
        File folder = new File(usersFolder);
        if (!folder.exists()) {
            folder.mkdirs();
            return;
        }

        File[] userFiles = folder.listFiles();
        if (userFiles != null) {
            for (File file : userFiles) {
                try (FileInputStream fileIn = new FileInputStream(file);
                     ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
                    User user = (User) objectIn.readObject();
                    registeredUsers.put(user.getUsername(), user);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    // Handle file read error
                }
            }
        }
    }
}
