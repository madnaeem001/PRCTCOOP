package com.example.demo3;

import java.util.ArrayList;
import java.util.List;

    public class Timeline {
        private List<Post> posts;

        public Timeline() {
            this.posts = new ArrayList<>();
        }

        public void addPost(Post post) {
            posts.add(post);
        }

        public List<Post> getPosts() {
            return posts;
        }

        public void displayTimeline() {
            if (posts.isEmpty()) {
                System.out.println("Timeline is empty.");
                return;
            }

            System.out.println("Timeline:");
            for (Post post : posts) {
                System.out.println("Post ID: " + post.getPostId());
                System.out.println("Content: " + post.getContent());
                System.out.println("Author: " + post.getAuthor());
                System.out.println("Comments:");

                for (Comment comment : post.getComments()) {
                    System.out.println("\tComment ID: " + comment.getCommentId());
                    System.out.println("\tContent: " + comment.getContent());
                    System.out.println("\tAuthor: " + comment.getAuthor());
                }
                System.out.println("---------------");
            }
        }
    }
