// 개발목적: 서버와 소켓으로 연결되어 메시지를 수신받아 메인 클래스에게 전달하는 클래스
// 개발기간: 2025년 12월 01일  ~  2025년 12월 14일

package Socket;

import java.io.*;

public class MessageListener extends Thread {
	Client client = null;
	InputStream inStream = null;			// 입력 스트림 객체 선언
	ObjectInputStream objectInStream = null;// ArrayList 또는 이미지를 받기 위한 객체 입력 스트림 객체 선언
	ReceivedMSGTokenizer msgController = new ReceivedMSGTokenizer();
	
	// 생성자
	public MessageListener(Client _c) {
		client = _c;
	}
	
	public void run() {
		int typeMSG = -1;
		
		try {
			inStream = client.mySocket.getInputStream();		// 소켓에서 입력 스트림을 가져옴
			objectInStream = new ObjectInputStream(inStream);	// 객체 입력 스트림 생성
			
			while(true) {
				String msg = receivedMSG();				// 서버로부터 메시지를 수신				
				typeMSG = msgController.detection(msg);	// 메시지 타입을 탐색
				msgBasedService(typeMSG, msg);			// 메시지 기반 서비스 메소드 실행
				System.out.println("Client> " + msg);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// 메시지를 처리하는 메소드
	public void msgBasedService(int _type, String _msg) {
		// 도시 선택
		if(_type == 0) {
			String cityName = msgController.findCityName(_msg);	// 메시지에서 도시명을 찾아 저장
			client.messageProcess(0, cityName);					// 메시지 처리 요청
		}
		// 서버 응답
		else if(_type == 1) {
			String response = msgController.findResponse(_msg);	// 메시지에서 응답 유형을 찾아 저장
			
			Object obj = receivedObject();	// 서버가 보낸 겍체를 읽어옴
			
			// 재난 속보
			if(response.equals("News")) {
				client.messageProcess(1, obj);	// 객체 처리 요청
			}
			// 재난 문자
			else if(response.equals("Message")) {
				client.messageProcess(2, obj);	// 객체 처리 요청
			}
			// 대피소
			else if(response.equals("Shelter")) {
				client.messageProcess(3, obj);	// 객체 처리 요청
			}
			// 지도
			else if(response.equals("Map")) {
				client.messageProcess(4, obj);	// 객체 처리 요청
			}
		}
		// 프로그램 종료
		else if(_type == 3) {
			System.out.println("프로그램을 종료합니다.");
		}
		// 예외 처리
		else {
			
		}
	}
	
	// 일반 메시지 수신 메소드
	public String receivedMSG() {
		try {
			String msg = null;
			msg = objectInStream.readUTF();
			return msg;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// 객체 수신 메소드
	public Object receivedObject() {
		try {
			Object obj = null;
			obj = objectInStream.readObject();
			return obj;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}