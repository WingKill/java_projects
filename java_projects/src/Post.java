public class Post {
    private static int nextId = 0; // 다음 게시글의 ID를 생성하기 위한 정적 변수
    private int id; // 게시글 ID
    private String author; // 게시글 작성자
    private String title;   // 게시글 제목
    private String content; // 게시글 내용

    // 생성자
    public Post(String author, String title, String content) {
        this.id = nextId++; // 다음 게시글 ID 부여 후 증가
        this.author = author;
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	// ID getter
    public int getId() {
        return id;
    }

    // 작성자 getter
    public String getAuthor() {
        return author;
    }

    // 작성자 setter
    public void setAuthor(String author) {
        this.author = author;
    }

    // 내용 getter
    public String getContent() {
        return content;
    }

    // 내용 setter
    public void setContent(String content) {
        this.content = content;
    }

	public void setId(int id) {
		this.id = id;
	}
	
	
}
