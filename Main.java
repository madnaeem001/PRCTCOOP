package com.example.demo3;

public class Main {
    public static void main(String[] args) {
        HelloApplication extendedApp = new HelloApplication();
        extendedApp.launch(HelloApplication.class);

        // Access the managers and timeline from the extended application
        UserManager userManager = extendedApp.userManager;
        PostManager postManager = extendedApp.postManager;
        Timeline timeline = extendedApp.timeline;

        // Simulating user signup and login
        userManager.signUp("user1", "password123", "user1@example.com");
        userManager.signUp("user2", "pass456", "user2@example.com");

        userManager.login("user1", "password123");

        // Creating posts and adding comments
        postManager.createPost("post1", "This is the content of post 1", "user1");
        postManager.createPost("post2", "Another post here", "user2");

        postManager.addCommentToPost("post1", new Comment("comment1", "post1", "Nice post!", "user2"));
        postManager.addCommentToPost("post2", new Comment("comment2", "post2", "Great content!", "user1"));

        // Displaying the timeline
        timeline.displayTimeline();
    }
}
