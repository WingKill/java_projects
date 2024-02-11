import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager{
    private String url = "jdbc:oracle:thin:@localhost:1521:xe";
    private String databaseID = "user123";
    private String databasePassword = "password123";
    private User user;
    private ArrayList<Post> posts;
    private String condition;

    public DatabaseManager(User user) {
        this.user = user;
    }
    
    public DatabaseManager(User user, ArrayList<Post> posts) {
    	this.user = user;
    	this.posts = posts;
    }
    
    public ResultSet executeQuery(String condition) {
        this.condition = condition;
        String query;
        switch (condition) {
            case "ADD_USER":
                query = "INSERT INTO users (username, password) VALUES (?, ?)";
                break;
            case "LOGIN":
                query = "SELECT * FROM users WHERE username = ? AND password = ?";
                break;
            case "ADD_POST":
                query = "INSERT INTO posts (title, content, author) VALUES (?, ?, ?)";
                break;
            case "UPDATE_POST":
                query = "UPDATE posts SET content = ? WHERE id = ?";
                break;
            case "DELETE_POST":
                query = "DELETE FROM posts WHERE author = ? AND content = ?";
                break;
            case "FETCH_POSTS":
                query = "SELECT * FROM posts";
                break;
            case "MY_POSTS":
            	query = "SELECT * FROM posts WHERE author = ?";
            	break;
            default:
                throw new IllegalArgumentException("Invalid condition: " + condition);
        }
        
        try (Connection conn = DriverManager.getConnection(url, databaseID, databasePassword);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
               // 쿼리에 따라 필요한 처리를 수행합니다.
               // 여기서는 PreparedStatement를 사용하여 쿼리문을 동적으로 설정합니다.
               switch (condition) {
                   case "ADD_USER":
                       pstmt.setString(1, user.getUsername());
                       pstmt.setString(2, user.getPassword());
                       break;
                   case "LOGIN":
                       pstmt.setString(1, user.getUsername());
                       pstmt.setString(2, user.getPassword()); 
                       break;
                   case "ADD_POST":
                       pstmt.setString(1, "New Title");
                       pstmt.setString(2, "New Content");
                       pstmt.setString(3, "user123");
                       break;
                   case "UPDATE_POST":
                       pstmt.setString(1, "Updated Content");
                       pstmt.setInt(2, 1); // 예시로 1번 게시글을 수정합니다.
                       break;
                   case "DELETE_POST":
                       pstmt.setString(1,posts.get(0).getAuthor()); 
                       pstmt.setString(2,posts.get(0).getContent());
                       break;
                   case "MY_POSTS":
                   	pstmt.setString(1, user.getUsername());
                   	break;
                   // FETCH_POSTS 쿼리는 매개변수가 없으므로 추가적인 처리가 필요하지 않습니다.
               }
               
               // 쿼리 실행 및 결과 반환
               return pstmt.executeQuery();
           } catch (SQLException e) {
               System.err.println("Database connection error: " + e.getMessage());
               return null;
           }
    }
    
    public List<Post> getPosts() {
    	posts = new ArrayList<>();
        ResultSet rs = executeQuery(condition);
        try {
            while (rs.next()) {
            	
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String content = rs.getString("content");
                String author = rs.getString("author");
                Post post = new Post(author, title, content);
                post.setId(id);
                posts.add(post);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching posts: " + e.getMessage());
        }
        return posts;
    }
}
