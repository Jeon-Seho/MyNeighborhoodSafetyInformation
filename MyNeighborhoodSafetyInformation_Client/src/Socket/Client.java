// 개발목적: 서버와 소켓으로 연결되어 메시지를 송신하고 GUI에 작업을 지시하는 메인 클래스
// 개발기간: 2025년 12월 01일  ~  2025년 12월 14일

package Socket;

import java.io.*;
import java.net.*;
import javax.swing.*;
import java.util.ArrayList;

import GUI.MainFrame;
import common.*;

public class Client {
	Socket mySocket = null;						// 서버와 연결할 소켓 선언
	OutputStream outStream = null;				// 출력 스트림 객체 선언
	ObjectOutputStream objectOutStream = null;	// 객체 출력 스트림 객체 선언
	
	MessageListener msgListener = null;
	MSGBuilder msgBuilder = new MSGBuilder();
	
	MainFrame mainFrame = null;
	
	public static void main(String[] args) {
		Client client = new Client();
		
		try {
			client.mySocket = new Socket("localhost", 55555); // 서버 IP는 자기 자신으로, 포트 번호는 55555으로 서버와 연결할 소켓 생성
			
			client.outStream = client.mySocket.getOutputStream();				// 소켓에서 출력 스트림을 가져옴
			client.objectOutStream = new ObjectOutputStream(client.outStream);	// 객체 출력 스트림 생성
			
			client.msgListener = new MessageListener(client);	// Client 객체를 매개 변수로 하여 스레드 객체 생성
			client.msgListener.setDaemon(true);	// 메인 스레드 종료 시 서브 스레드도 동시에 종료되게끔 데몬 스레드 설정
			client.msgListener.start();			// 메시지 수신 스레드 실행
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		SwingUtilities.invokeLater(new Runnable() {	// GUI 수정 작업을 전달하기 위한 익명 객체 생성
			@Override
			public void run() {
				client.mainFrame = new MainFrame(client);	// MainFrame 생성
			}
		});
	}
	
	// 메시지 송신 메소드
	public void sendMSG(String _msg) {
		try {
			objectOutStream.writeUTF(_msg);
			objectOutStream.flush();	// 버퍼를 강제로 비워 데이터를 전달
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// 메시지 처리 메소드
	public void messageProcess(int _type, Object _obj) {
		SwingUtilities.invokeLater(new Runnable() {	// GUI 수정 작업을 전달하기 위한 익명 객체 생성
			@Override
			public void run() {
				// 도시 확인
				if(_type == 0) {
					String cityName = (String)_obj;	// String으로 형 변환
					
					if(cityName.equals("Error")) {	// 지원하지 않는 도시명인 경우
						JOptionPane.showMessageDialog(mainFrame, "지원하지 않는 도시입니다.", "확인", JOptionPane.WARNING_MESSAGE);
					}
					else {
						int result = JOptionPane.showConfirmDialog(mainFrame, cityName + "을(를) 선택하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION);
						
						if(result == JOptionPane.YES_OPTION) {	// "Yes"를 선택한 경우
							mainFrame.setCityName(cityName);	// 도시명 설정
							mainFrame.changePanel();			// 프레임의 패널 변경(카드 레이아웃)
							
							// 초기 설정
							sendMSG(msgBuilder.newsMSG());
							sendMSG(msgBuilder.messageMSG());
							sendMSG(msgBuilder.shelterMSG());
						}
						else {
							JOptionPane.showMessageDialog(mainFrame, "다시 입력해주세요.", "확인", JOptionPane.PLAIN_MESSAGE);
						}
					}
				}
				// 재난 속보
				else if(_type == 1) {
					if(_obj instanceof ArrayList<?>) {	// [Java] Type Safety : unchecked cast(https://dwenn.tistory.com/91) 참고
						@SuppressWarnings("unchecked")	// 형 안전성이 확실하므로 경고 억제
						ArrayList<News> news = (ArrayList<News>)_obj;	// 형 변환
						mainFrame.setNoticeTable(news);	// 메인 프레임에 재난 속보 갱신 요청
					}
				}
				// 재난 문자
				else if(_type == 2) {
					if(_obj instanceof ArrayList<?>) {
						@SuppressWarnings("unchecked")
						ArrayList<Message> messages = (ArrayList<Message>)_obj;	// 형 변환
						mainFrame.setMessageTable(messages);	// 메인 프레임에 재난 문자 갱신 요청
					}	
				}
				// 대피소
				else if(_type == 3) {
					if(_obj instanceof ArrayList<?>) {
						@SuppressWarnings("unchecked")
						ArrayList<Shelter> shelters = (ArrayList<Shelter>)_obj;	// 형 변환
						mainFrame.setShelterTable(shelters);	// 메인 프레임에 대피소 정보 설정 요청
					}
				}
				// 지도
				else if(_type == 4) {
					if(_obj instanceof byte[]) {
						byte[] image = (byte[])_obj;	// 형 변환
						mainFrame.showMapDialog(image);	// 메인 프레임에 MapDialog 표시 요청
					}
				}
			}
		});
	}
}