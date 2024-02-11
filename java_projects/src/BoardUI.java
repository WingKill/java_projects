import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class BoardUI {
    private BufferedReader reader;
    private PrintWriter writer;
    private Socket socket;

    public BoardUI(Socket socket) {
        this.socket = socket;
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void display() {
        System.out.println("게시판 화면");
        System.out.println("1. 게시글 목록 보기");
        System.out.println("2. 게시글 추가");
        System.out.println("3. 게시글 수정");
        System.out.println("4. 게시글 삭제");
        System.out.println("0. 종료");

        int choice = -1;
        while (choice != 0) {
            System.out.print("메뉴 선택: ");
            try {
                choice = Integer.parseInt(reader.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }

            switch (choice) {
                case 1:
                    displayPostList();
                    break;
                case 2:
                    addPost();
                    break;
                case 3:
                    updatePost();
                    break;
                case 4:
                    deletePost();
                    break;
                case 0:
                    System.out.println("프로그램을 종료합니다.");
                    break;
                default:
                    System.out.println("잘못된 선택입니다. 다시 입력해주세요.");
                    break;
            }
        }
    }

    private void displayPostList() {
        // 서버에게 게시글 목록 요청
        writer.println("GET_POSTS");

        // 서버로부터 받은 게시글 목록 출력
        try {
            String response;
            while (!(response = reader.readLine()).equals("END")) {
                System.out.println(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addPost() {
        // 사용자로부터 게시글 정보 입력 받기
        try {
            System.out.print("게시글 내용 입력: ");
            String content = reader.readLine();
            System.out.print("작성자 입력: ");
            String author = reader.readLine();

            // 서버에게 게시글 추가 요청
            writer.println("ADD_POST " + content + " " + author);

            // 서버로부터 응답 받기
            String response = reader.readLine();
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updatePost() {
        // 사용자로부터 수정할 게시글 ID와 내용 입력 받기
        try {
            System.out.print("수정할 게시글 ID 입력: ");
            int postId = Integer.parseInt(reader.readLine());
            System.out.print("수정할 내용 입력: ");
            String newContent = reader.readLine();
            System.out.print("작성자 입력: ");
            String author = reader.readLine();

            // 서버에게 게시글 수정 요청
            writer.println("UPDATE_POST " + postId + " " + newContent + " " + author);

            // 서버로부터 응답 받기
            String response = reader.readLine();
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deletePost() {
        // 사용자로부터 삭제할 게시글 ID 입력 받기
        try {
            System.out.print("삭제할 게시글 ID 입력: ");
            int postId = Integer.parseInt(reader.readLine());

            // 서버에게 게시글 삭제 요청
            writer.println("DELETE_POST " + postId);

            // 서버로부터 응답 받기
            String response = reader.readLine();
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
