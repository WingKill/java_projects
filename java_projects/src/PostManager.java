import java.util.ArrayList;
import java.util.List;

public class PostManager {
    private List<Post> posts;

    public PostManager() {
        this.posts = new ArrayList<>();
    }

    // 게시글 추가
    public void addPost(Post post) {
        posts.add(post);
    }

    // 게시글 수정
    public void updatePost(int index, Post updatedPost) {
        if (index >= 0 && index < posts.size()) {
            posts.set(index, updatedPost);
        } else {
            System.out.println("해당 인덱스에 게시글이 존재하지 않습니다.");
        }
    }

    // 게시글 삭제
    public void deletePost(int index) {
        if (index >= 0 && index < posts.size()) {
            posts.remove(index);
        } else {
            System.out.println("해당 인덱스에 게시글이 존재하지 않습니다.");
        }
    }
}
