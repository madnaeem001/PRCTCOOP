import java.util.ArrayList;
import java.util.List;

public class Post {
    private String content;
    private User author;
    private List<Comment> comments;

    public Post(String content, User author) {
        this.content = content;
        this.author = author;
        this.comments = new ArrayList<>();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }
}
