import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private Map<String, Socket> userSockets; // 사용자명과 소켓 정보를 저장할 맵
    private Map<String, Boolean> clientStatus; // 클라이언트의 로그인 상태를 저장할 맵 (사용자명, 로그인 여부)

    public UserManager() {
        // 사용자 정보를 저장할 맵 초기화
        userSockets = new HashMap<>();
        clientStatus = new HashMap<>();
    }

    // 사용자 로그인 메서드
    public boolean login(String username, String password, Socket socket) {
        // 사용자명과 비밀번호가 일치하는지 확인하여 로그인 처리
        if (userSockets.containsKey(username) && userSockets.get(username).equals(password)) {
            userSockets.put(username, socket); // 사용자명과 소켓 정보를 맵에 저장
            clientStatus.put(username, true); // 클라이언트 로그인 상태를 true로 설정
            return true;
        } else {
            return false;
        }
    }

    // 사용자 로그아웃 메서드
    public void setUserLogoutStatus(String username) {
        // 클라이언트의 로그인 상태를 로그아웃으로 변경
        clientStatus.put(username, false);
    }

    // 클라이언트 연결 메서드
    public void setClientConnected(String username) {
        // 클라이언트가 연결되면 로그인 상태로 설정
        clientStatus.put(username, true);
    }

    // 사용자 등록 메서드
    public void registerUser(String username, Socket socket) {
        // 사용자 등록 (사용자명과 소켓 정보를 맵에 저장)
        userSockets.put(username, socket);
        // 사용자 등록 시 로그인 상태를 false로 설정
        clientStatus.put(username, false);
    }
}
