import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class LoginUI {
    private BufferedReader reader;
    private PrintWriter writer;
    private Socket socket;

    public LoginUI(Socket socket) {
        this.socket = socket;
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void display() {
        try {
            System.out.println("---------로그인 화면--------");
            System.out.print("아이디: ");
            String username = reader.readLine();
            System.out.print("비밀번호: ");
            String password = reader.readLine();

            // 서버로 로그인 요청을 전송합니다.
            writer.println("LOGIN " + username + " " + password);

            // 서버로부터 로그인 결과를 받아 처리합니다.
            String response = reader.readLine();
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
