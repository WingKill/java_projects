import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private PostManager postManager; // 게시글 관리자
    private UserManager userManager; // 사용자 관리자

    public ClientHandler(Socket socket, PostManager postManager, UserManager userManager) {
        this.socket = socket;
        this.postManager = postManager;
        this.userManager = userManager;
    }

    @Override
    public void run() {
    	try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    		    PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()))) {

    		    String request;
    		    while ((request = reader.readLine()) != null) {
    		        String response = processRequest(request);
    		        writer.println(response); // 클라이언트에 응답 전송
    		    }
    		    // 클라이언트 소켓의 로그아웃 상태 설정
    		    String username = extractUsername(request);
    		    userManager.setUserLogoutStatus(username);
    		} catch (IOException e) {
    		    e.printStackTrace();
    		}

    }

    private String processRequest(String request) {
        // 클라이언트 요청에 따른 처리 로직을 구현합니다.
        String[] tokens = request.split("\\s+"); // 공백을 기준으로 요청 분리
        String command = tokens[0];
        switch (command) {
            case "ADD_POST":
                return addPost(tokens);
            case "UPDATE_POST":
                return updatePost(tokens);
            case "DELETE_POST":
                return deletePost(tokens);
            case "LOGIN":
                return login(tokens);
            case "LOGOUT":
                return logout(request);
            default:
                return "Unknown request";
        }
    }

    private String addPost(String[] tokens) {
        // 게시글 추가 로직을 구현합니다.
        if (tokens.length < 3) {
            return "Error: Not enough parameters for ADD_POST command";
        }
        // 게시글 내용 추출
        String content = tokens[1];
        // 작성자 추출
        String author = tokens[2];
        
        // 여기에 데이터베이스에 게시글을 추가하는 로직을 구현합니다.
        // postManager.addPost(content, author);

        return "New post added"; // 임시 응답
    }

    private String updatePost(String[] tokens) {
        // 게시글 수정 로직을 구현합니다.
        if (tokens.length < 4) {
            return "Error: Not enough parameters for UPDATE_POST command";
        }
        // 수정할 게시글의 ID 추출
        int postId = Integer.parseInt(tokens[1]);
        // 수정할 내용 추출
        String newContent = tokens[2];
        // 작성자 추출
        String author = tokens[3];
        
        // 여기에 데이터베이스에서 게시글을 업데이트하는 로직을 구현합니다.
        // postManager.updatePost(postId, newContent, author);

        return "Post updated"; // 임시 응답
    }

    private String deletePost(String[] tokens) {
        // 게시글 삭제 로직을 구현합니다.
        if (tokens.length < 2) {
            return "Error: Not enough parameters for DELETE_POST command";
        }
        // 삭제할 게시글의 ID 추출
        int postId = Integer.parseInt(tokens[1]);
        
        // 여기에 데이터베이스에서 게시글을 삭제하는 로직을 구현합니다.
        // postManager.deletePost(postId);

        return "Post deleted"; // 임시 응답
    }

    private String login(String[] tokens) {
        // 로그인 로직을 구현합니다.
        if (tokens.length < 3) {
            return "Error: Not enough parameters for LOGIN command";
        }
        // 사용자명 추출
        String username = tokens[1];
        // 비밀번호 추출
        String password = tokens[2];
        
        // 여기에 사용자 관리자를 사용하여 로그인을 처리하는 로직을 구현합니다.
        // boolean loginSuccess = userManager.login(username, password);

        // 로그인 성공 여부에 따른 응답 반환
        // if (loginSuccess) {
        //     return "Login successful";
        // } else {
        //     return "Login failed";
        // }
        return "Login successful"; // 임시 응답
    }

    private String logout(String request) {
    	// 로그아웃 로직을 구현합니다.
        String username = extractUsername(request);
        userManager.setUserLogoutStatus(username); // 사용자의 로그인 상태를 로그아웃으로 변경
        
        try {
            socket.close(); // 소켓 닫기
            reader.close(); // 입력 스트림 닫기
            writer.close(); // 출력 스트림 닫기
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return "Logout successful"; // 클라이언트에게 응답 전송
    }

    private String extractUsername(String request) {
        // 현재 요청에서 사용자명 추출
        // 예시: ADD_POST username content
        String[] tokens = request.split("\\s+");
        if (tokens.length >= 2) {
            return tokens[1];
        }
        return null; // 추출 실패
    }
}
