package com.example.demo3;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HelloApplication extends Application {
    private TextField signupUserTextField;
    private PasswordField signupPasswordField;
    private TextField signupEmailTextField;

    UserManager userManager;
    PostManager postManager;
    Timeline timeline;

    @Override
    public void start(Stage stage) {
        stage.setTitle("My Application");

        userManager = new UserManager();
        postManager = new PostManager();
        timeline = new Timeline();

        GridPane gridPane = createLoginPage();

        Scene loginScene = new Scene(gridPane, 600, 400);

        stage.setScene(loginScene);
        stage.show();
    }

    private GridPane createLoginPage() {
        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        Text title = new Text("Welcome to login page");
        title.setFont(new Font("Time new roman", 30));

        Label label = new Label("User Name");
        Label label1 = new Label("Password");

        TextField userTextField = new TextField();
        PasswordField passwordField = new PasswordField();

        gridPane.add(title, 0, 0, 2, 1);
        gridPane.add(label, 0, 1);
        gridPane.add(label1, 0, 2);
        gridPane.add(userTextField, 1, 1);
        gridPane.add(passwordField, 1, 2);

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> System.out.println("Exit Button Clicked"));

        Label messageLabel = new Label();
        Label messageLabel1 = new Label();
        Button loginButton = new Button("Login");

        loginButton.setOnAction(actionEvent -> {
            if (userManager.login(userTextField.getText(), passwordField.getText())) {
                // Login successful, show the timeline
                showTimeline(userTextField.getText());
            } else {
                messageLabel.setText("Invalid credentials");
            }
        });

        Button signupButton = new Button("Signup");
        signupButton.setOnAction(e -> openSignupPage());

        HBox loginHBox = new HBox();
        loginHBox.getChildren().addAll(loginButton, signupButton, exitButton);
        loginHBox.setSpacing(10);
        loginHBox.setAlignment(Pos.CENTER_RIGHT);

        gridPane.add(loginHBox, 1, 5);
        gridPane.add(messageLabel, 1, 4);
        gridPane.add(messageLabel1, 1, 5);

        gridPane.setPadding(new Insets(20, 20, 20, 20));
        gridPane.setVgap(15);
        gridPane.setHgap(15);

        return gridPane;
    }

    private void openSignupPage() {
        Stage signupStage = new Stage();
        GridPane signupGridPane = createSignupPage();
        Scene signupScene = new Scene(signupGridPane, 600, 400);
        signupStage.setScene(signupScene);
        signupStage.show();
    }

    private GridPane createSignupPage() {
        GridPane signupGridPane = new GridPane();
        signupGridPane.setVgap(10);
        Text title = new Text("Welcome to signup page");
        title.setFont(new Font("Time new roman", 30));

        Label label = new Label("User Name");
        Label label1 = new Label("Password");
        Label label2 = new Label("Email");

        signupUserTextField = new TextField();
        signupPasswordField = new PasswordField();
        signupEmailTextField = new TextField();

        signupGridPane.add(title, 0, 0, 2, 1);
        signupGridPane.add(label, 0, 1);
        signupGridPane.add(label1, 0, 2);
        signupGridPane.add(label2, 0, 3);
        signupGridPane.add(signupUserTextField, 1, 1);
        signupGridPane.add(signupPasswordField, 1, 2);
        signupGridPane.add(signupEmailTextField, 1, 3);

        Button signupSubmitButton = new Button("Signup");
        signupSubmitButton.setOnAction(e -> handleSignup());

        HBox signupHBox = new HBox();
        signupHBox.getChildren().addAll(signupSubmitButton);
        signupHBox.setSpacing(10);
        signupHBox.setAlignment(Pos.CENTER_RIGHT);

        signupGridPane.add(signupHBox, 1, 4);

        signupGridPane.setPadding(new Insets(20, 20, 20, 20));
        signupGridPane.setVgap(15);
        signupGridPane.setHgap(15);

        return signupGridPane;
    }

    private void handleSignup() {
        String username = signupUserTextField.getText();
        String password = signupPasswordField.getText();
        String email = signupEmailTextField.getText();

        if (userManager.signUp(username, password, email)) {
            // Signup successful, show the timeline
            showTimeline(username);
        } else {
            System.out.println("Signup failed. Please choose another username.");
        }
    }

    private void showTimeline(String username) {
        Stage timelineStage = new Stage();
        GridPane timelineGridPane = createTimelinePage(username);
        Scene timelineScene = new Scene(timelineGridPane, 800, 600);
        timelineStage.setScene(timelineScene);
        timelineStage.show();
    }

    private GridPane createTimelinePage(String username) {
        GridPane timelineGridPane = new GridPane();
        timelineGridPane.setVgap(10);
        Text title = new Text("Welcome to your timeline, " + username + "!");
        title.setFont(new Font("Time new roman", 20));

        TextArea postTextArea = new TextArea();
        postTextArea.setPromptText("Write a new post...");

        Button postButton = new Button("Post");
        postButton.setOnAction(e -> handlePost(username, postTextArea.getText()));

        timelineGridPane.add(title, 0, 0, 2, 1);
        timelineGridPane.add(postTextArea, 0, 1);
        timelineGridPane.add(postButton, 1, 1);

        HBox timelineHBox = new HBox();
        timelineHBox.getChildren().addAll(postButton);
        timelineHBox.setSpacing(10);
        timelineHBox.setAlignment(Pos.CENTER_RIGHT);

        timelineGridPane.add(timelineHBox, 1, 2);

        timelineGridPane.setPadding(new Insets(20, 20, 20, 20));
        timelineGridPane.setVgap(15);
        timelineGridPane.setHgap(15);

        // Load and display posts in the timeline
        loadAndDisplayPosts(username, timelineGridPane);

        return timelineGridPane;
    }

    private void handlePost(String username, String content) {
        String postId = "P" + System.currentTimeMillis(); // Simple way to generate a unique post ID
        postManager.createPost(postId, content, username);

        // Create a new timeline and display posts
        loadAndDisplayPosts(username, createTimelinePage(username));
    }


    private void loadAndDisplayPosts(String username, GridPane timelineGridPane) {
        List<Post> userPosts = postManager.getPostsByUser(username);

        // Clear existing posts
        timelineGridPane.getChildren().removeIf(node -> node instanceof TextArea || node instanceof Button);

        int row = 3;
        for (Post post : userPosts) {
            Label postLabel = new Label("Post ID: " + post.getPostId() +
                    "\nContent: " + post.getContent() +
                    "\nAuthor: " + post.getAuthor());

            timelineGridPane.add(postLabel, 0, row++);
        }
    }


    public static void main(String[] args) {
        launch();
    }
}
