// 개발목적: 메인 GUI 프레임
// 개발기간: 2025년 12월 01일  ~  2025년 12월 14일

package GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

import Socket.*;
import common.*;

public class MainFrame extends JFrame {
	// 컴포넌트 선언
	private Container ct;
	private CardLayout cardLayout;
	private JMenu optionMenu;
	
	// 패널 선언
	private SelectPanel selectPanel;
	private MainPanel mainPanel;
	
	// 객체 선언
	private Client client = null;
	String cityName = null;
	
	private News selectedNews = null;
	private Shelter selectedShelter = null;

	MSGBuilder msgBuilder = new MSGBuilder();
	
	// 생성자
	public MainFrame(Client _c) {
		client = _c;
		ct = getContentPane();	// 컨테이너 저장
		
		cardLayout = new CardLayout();	// 레이아웃 설정
		ct.setLayout(cardLayout);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		optionMenu = new JMenu("설정");
		optionMenu.setVisible(false);	// 메뉴 비활성화
		menuBar.add(optionMenu);
		
		JMenuItem reselectMenuItem = new JMenuItem("재선택");
		optionMenu.add(reselectMenuItem);
		reselectMenuItem.addActionListener(new ActionListener() {	// 익명 객체를 활용한 ActionListener
			@Override
			public void actionPerformed(ActionEvent ae) {
				cardLayout.show(ct, "선택");	// 카드 레이아웃 패널변경
				optionMenu.setVisible(false);	// 메뉴 비활성화
				setTitle("우리동네 안전정보 - 지역 선택");
				setSize(500, 300);
				setLocationRelativeTo(null);
			}
		});
		
		JMenuItem exitMenuItem = new JMenuItem("종료");
		optionMenu.addSeparator();	// 구분자 추가
		optionMenu.add(exitMenuItem);
		exitMenuItem.addActionListener(new ActionListener() {	// 익명 객체를 활용한 ActionListener
			@Override
			public void actionPerformed(ActionEvent ae) {
				int var = JOptionPane.showConfirmDialog(null, "정말 종료하시겠습니까?", "종료", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
				
				if(var == JOptionPane.YES_OPTION) {	// "Yes"를 선택했다면
					dispose();	// 프레임 종료
				}
			}
		});
		
		// 패널 생성
		selectPanel = new SelectPanel(this);
		mainPanel = new MainPanel(this);
		
		// 패널 추가
		ct.add(selectPanel, "선택");
		ct.add(mainPanel, "메인");
		
		setTitle("우리동네 안전정보 - 지역 선택");
		setSize(500, 300);
		setResizable(false);							// 크기 조정 불가 처리
		setLocationRelativeTo(null);					// 모니터 중앙 표시
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// 종료 시 처리
		setVisible(true);
	}
	
	// 카드 레이아웃 패널 변경 메소드
	public void changePanel() {
		cardLayout.show(ct, "메인");	// 카드 레이아웃 패널 변경
		setTitle("우리동네 안전정보 - 메인(" + cityName + ")");
		pack();
		setLocationRelativeTo(null);
		optionMenu.setVisible(true);
	}
	
	// 도시 이름 설정 메소드
	public void setCityName(String _cityName) {
		this.cityName = _cityName;
	}
	
	// 메시지 전송 요청 메소드
	public void sendMSG(String _msg) {
		client.sendMSG(_msg);
	}
	
	// 재난 속보 테이블 설정 메소드
	public void setNoticeTable(ArrayList<News> news) {
		mainPanel.setNoticeTable(news);
	}
	
	// 재난 문자 테이블 설정 메소드
	public void setMessageTable(ArrayList<Message> messages) {
		mainPanel.setMessageTable(messages);
	}
	
	// 대피소 테이블 설정 메소드
	public void setShelterTable(ArrayList<Shelter> shelters) {
		mainPanel.setShelterTable(shelters);
	}
	
	// 지도 Dialog를 준비하는 메소드
	public void prepareForMapDialog(News news) {
		selectedNews = news;
		sendMSG(msgBuilder.mapMSG(selectedNews.getAddress()));
	}
	
	// 지도 Dialog를 준비하는 메소드(메소드 오버로딩)
	public void prepareForMapDialog(Shelter shelter) {
		selectedShelter = shelter;
		sendMSG(msgBuilder.mapMSG(selectedShelter.getAddress()));
	}
	
	// 지도 Dialog를 표시하는 메소드
	public void showMapDialog(byte[] image) {
		ImageIcon mapImage = new ImageIcon(image);	// byte[] 배열을 ImageIcon으로 변환
		
		if(selectedNews != null) {
			new MapDialog(this, "상세 정보", selectedNews, mapImage);
			selectedNews = null;
		}
		else if(selectedShelter != null) {
			new MapDialog(this, "상세 정보", selectedShelter, mapImage);
			selectedShelter = null;
		}
	}
}