import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {
    public static void main(String[] args) {
        // PostManager와 UserManager 생성
        PostManager postManager = new PostManager();
        UserManager userManager = new UserManager();

        // 서버 소켓 생성 및 시작
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(8888);
            System.out.println("서버가 시작되었습니다.");

            // 클라이언트 요청 대기
            while (true) {
                Socket socket = serverSocket.accept();
                // 클라이언트와 통신을 위한 쓰레드 생성 및 시작
                Thread clientThread = new Thread(new ClientHandler(socket, postManager, userManager));
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
