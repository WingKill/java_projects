import java.io.IOException;
import java.net.Socket;

public class MainClient {
    public static void main(String[] args) {
        final String SERVER_ADDRESS = "localhost"; // 서버 주소
        final int SERVER_PORT = 8080; // 서버 포트

        try {
            // 서버에 연결하는 소켓 생성
            Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            System.out.println("Connected to server");

            // 클라이언트 핸들러 생성 및 시작
            PostManager postManager = new PostManager(); // 예시로 생성
            UserManager userManager = new UserManager(); // 예시로 생성
            ClientHandler clientHandler = new ClientHandler(socket, postManager, userManager);
            Thread clientThread = new Thread(clientHandler);
            clientThread.start();
        } catch (IOException e) {
            System.err.println("Error: Failed to connect to server");
            e.printStackTrace();
        }
    }
}
