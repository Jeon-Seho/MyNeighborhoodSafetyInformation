// 개발목적: 소켓으로 연결된 클라이언트의 요청을 응답하는 클래스
// 개발기간: 2025년 12월 01일  ~  2025년 12월 14일

package Socket;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.util.ArrayList;

import API.*;
import DB.*;
import common.*;

public class ConnectedClient extends Thread {
	Server server = null;
	Socket socket = null;
	OutputStream outStream = null;				// 출력 스트림 객체 선언
	ObjectOutputStream objectOutStream = null;	// 객체 출력 스트림 객체 선언
	InputStream inStream = null;				// 입력 스트림 객체 선언
	ObjectInputStream objectInStream = null;	// 객체 입력 스트림 객체 선언
	
	DAO dao = DAO.getInstance();	// 싱글톤 인스턴스
	
	ReceivedMSGTokenizer msgController = new ReceivedMSGTokenizer();
	MSGBuilder msgBuilder = new MSGBuilder();
	
	// API 객체 생성
	NewsAPI newsAPI = new NewsAPI();
	MessageAPI messageAPI = new MessageAPI();
	MapAPI mapAPI = new MapAPI();
	
	City city = null;
	
	// 생성자
	public ConnectedClient(Server _server, Socket _s) {
		server = _server;
		socket = _s;
	}
	
	// 스레드 실행 메소드
	public void run() {
		int typeMSG = -1;	// 메세지 타입 변수
		
		try {
			System.out.println("Server> " + this.socket.toString() + "에서의 접속이 연결되었습니다.");
			
			outStream = this.socket.getOutputStream();			// 소켓에서 출력 스트림을 가져옴
			objectOutStream = new ObjectOutputStream(outStream);// 객체 출력 스트림 생성
			
			inStream = this.socket.getInputStream();			// 소켓에서 입력 스트림을 가져옴
			objectInStream = new ObjectInputStream(inStream);	// 객체 입력 스트림 생성
			
			// 메시지 기반 서비스
			while(true) {
				String msg = objectInStream.readUTF();	// 클라이언트로부터 메시지를 수신
				typeMSG = msgController.detection(msg);	// 메시지 타입을 탐색
				msgBasedService(typeMSG, msg);			// 메시지 기반 서비스 메소드 실행
				System.out.println("Server> " + this.socket.toString() + ": " + msg);
			}			
		}
		catch(IOException e) {
			System.out.println("Server> " + this.socket.toString() + ": 연결해제됨");
			server.clients.remove(this);	// 자기 자신을 ArrayList에서 제거
			//e.printStackTrace();
		}
	}
	
	// 메시지 기반 서비스 메소드
	public void msgBasedService(int _type, String _msg) {
		// 도시 선택
		if(_type == 0) {
			String cityName = msgController.findCityName(_msg);		// 클라이언트의 메시지에서 도시명을 저장
			city = dao.findCity(cityName);						 	// 데이터베이스에서 도시를 조회
			if(city == null) {	// 도시명이 데이터베이스에 존재하지 않는다면
				sendMSG(msgBuilder.selectedCityMSG("Error"));	// 지원하지 않는 도시명 알림
			}
			else {
				sendMSG(msgBuilder.selectedCityMSG(city.getName()));	// 클라이언트에게 해당 도시가 맞는지 확인 요청
			}
		}
		// 클라이언트의 요청
		else if(_type == 1) {
			String request = msgController.findRequest(_msg);	// 클라이언트의 메시지에서 요청 항목을 저장
			
			// 재난 속보 요청
			if(request.equals("News")) {
				ArrayList<News> news = newsAPI.request(city.getName());	// API에서 재난 속보를 ArrayList로 요청
				sendMSG(msgBuilder.newsMSG());	// 재난 속보 응답 메시지 전송
				sendObject(news);				// 요청한 ArrayList를 클라이언트로 송신
			}
			// 재난 문자 요청
			else if(request.equals("Message")) {
				ArrayList<Message> messages = messageAPI.request(city.getName());	// API에서 재난문자를 ArrayList로 요청
				sendMSG(msgBuilder.messageMSG());	// 재난 문자 응답 메시지 전송
				sendObject(messages);				// 요청한 ArrayList를 클라이언트로 송신
			}
			// 대피소 요청
			else if(request.equals("Shelter")) {
				ArrayList<Shelter> shelters = dao.findShelter(city.getName());	// 데이터베이스에서 선택한 도시의 대피소를 탐색
				sendMSG(msgBuilder.shelterMSG());	// 대피소 응답 메시지 전송
				sendObject(shelters);				// 요청한 ArrayList를 클라이언트로 송신
			}
			// 지도 요청
			else if(request.equals("Map")) {
				String address = msgController.findAddress(_msg);	// 메시지에서 주소를 탐색
				File file = mapAPI.request(address);	// 주소를 기반으로 API에서 지도 이미지를 요청
				
				try {
					byte[] image = null;
					image = Files.readAllBytes(file.toPath());	// [Java]파일을 byte[]로 변환(https://blog.naver.com/hj_kim97/222309453794) 참조
					
					sendMSG(msgBuilder.mapMSG());	// 지도 이미지 응답 메시지 전송
					sendObject(image);				// 요청한 이미지를 클라이언트로 송신
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		// 프로그램 종료
		else if(_type == 2) {
			sendMSG(msgBuilder.exitMSG());	// 종료 메시지 전송
		}
		// 예외 처리
		else {
			
		}
	}
	
	// 메시지 전송 메소드
	public void sendMSG(String _msg) {
		try {
			objectOutStream.writeUTF(_msg);
			objectOutStream.flush();	// 강제로 버퍼를 비워 메시지 전송
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// 객체 전송 메소드
	public void sendObject(Object obj) {
		try {
			objectOutStream.writeObject(obj);
			objectOutStream.flush();	// 강제로 버퍼를 비워 객체 전송
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}