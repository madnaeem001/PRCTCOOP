import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String email;
    private String password;
    private List<Post> posts;
    private List<Comment> comments;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.posts = new ArrayList<>();
        this.comments = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void createPost(String content) {
        Post newPost = new Post(content, this);
        this.posts.add(newPost);
    }

    public void addCommentToPost(Post post, String content) {
        Comment newComment = new Comment(content, this);
        post.addComment(newComment);
        this.comments.add(newComment);
    }

}
