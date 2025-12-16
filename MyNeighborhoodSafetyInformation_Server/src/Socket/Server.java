// 개발목적: 클라이언트의 소켓 연결 요청을 수락하고 관리하는 메인 클래스
// 개발기간: 2025년 12월 01일  ~  2025년 12월 14일

package Socket;

import java.net.*;
import java.util.ArrayList;

public class Server {
	ServerSocket ss = null;	// 서버 소켓 객체 선언
	ArrayList<ConnectedClient> clients = new ArrayList<ConnectedClient>();	// 연결된 클라이언트 정보를 저장하는 ArrayList 생성
	
	public static void main(String[] args) {
		Server server = new Server();	// 객체 생성
		
		try {
			server.ss = new ServerSocket(55555);                    	// 포트를 55555로 하여 서버 소켓 생성
			System.out.println("Server> Server Socket is created....");
			
			while(true) {                                               // 서버 메인 스레드
				Socket socket = server.ss.accept();	                    // 클라이언트의 접속을 수락하고 소켓 생성
				ConnectedClient c = new ConnectedClient(server, socket);	// 연결된 소켓을 매개변수로 하여 ConnectedClient 객체 생성
				server.clients.add(c);									// clients ArrayList에 생성한 객체를 삽입
				c.start();												// 스레드 실행
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}