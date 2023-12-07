package com.example.demo3;
import java.io.Serializable;

    public class Comment implements Serializable {
        private String commentId;
        private String postId;
        private String content;
        private String author;

        public Comment(String commentId, String postId, String content, String author) {
            this.commentId = commentId;
            this.postId = postId;
            this.content = content;
            this.author = author;
        }

        public String getCommentId() {
            return commentId;
        }

        public void setCommentId(String commentId) {
            this.commentId = commentId;
        }

        public String getPostId() {
            return postId;
        }

        public void setPostId(String postId) {
            this.postId = postId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }
    }
