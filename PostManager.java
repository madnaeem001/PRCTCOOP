package com.example.demo3;

import java.io.*;
import java.util.*;

    public class PostManager {
        private final String postsFolder = "posts";
        private final Map<String, Post> allPosts;

        public PostManager() {
            this.allPosts = new HashMap<>();
            loadPosts();
        }

        public List<Post> getPostsByUser(String username) {
            List<Post> userPosts = new ArrayList<>();

            for (Post post : allPosts.values()) {
                if (post.getAuthor().equals(username)) {
                    userPosts.add(post);
                }
            }

            return userPosts;
        }

        public boolean createPost(String postId, String content, String author) {
            if (allPosts.containsKey(postId)) {
                System.out.println("Post ID already exists. Please choose another.");
                return false;
            }

            Post newPost = new Post(postId, content, author);
            allPosts.put(postId, newPost);
            savePost(newPost);
            return true;
        }

        public void addCommentToPost(String postId, Comment comment) {
            if (allPosts.containsKey(postId)) {
                Post post = allPosts.get(postId);
                post.addComment(comment);
                savePost(post);
            } else {
                System.out.println("Post not found.");
            }
        }

        public List<Post> getAllPosts() {
            return new ArrayList<>(allPosts.values());
        }

        private void savePost(Post post) {
            try (FileOutputStream fileOut = new FileOutputStream(postsFolder + "/" + post.getPostId() + ".ser");
                 ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
                objectOut.writeObject(post);
            } catch (IOException e) {
                e.printStackTrace();
                // Handle file write error
            }
        }

        private void loadPosts() {
            File folder = new File(postsFolder);
            if (!folder.exists()) {
                folder.mkdirs();
                return;
            }

            File[] postFiles = folder.listFiles();
            if (postFiles != null) {
                for (File file : postFiles) {
                    try (FileInputStream fileIn = new FileInputStream(file);
                         ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
                        Post post = (Post) objectIn.readObject();
                        allPosts.put(post.getPostId(), post);
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                        // Handle file read error
                    }
                }
            }
        }
    }
