// 개발목적: 메인 패널 GUI
// 개발기간: 2025년 12월 01일  ~  2025년 12월 14일

package GUI;

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;

import common.*;

public class MainPanel extends JPanel {
	// 패널 선언
	private NoticePanel noticePanel;
	private MessagePanel messagePanel;
	private ShelterPanel shelterPanel;
	
	// 생성자
	public MainPanel(MainFrame mainFrame) {
		setLayout(new BorderLayout());
		
		// 패널 생성
		noticePanel = new NoticePanel(mainFrame);
		messagePanel = new MessagePanel(mainFrame);
		shelterPanel = new ShelterPanel(mainFrame);
		
		JTabbedPane tabbedPane = new JTabbedPane();	// JTabbedPane 생성
		tabbedPane.setBackground(Color.WHITE);
		
		// 탭 추가
		tabbedPane.addTab("재난 속보", noticePanel);
		tabbedPane.addTab("재난 문자", messagePanel);
		tabbedPane.addTab("대피소", shelterPanel);
		
		add(tabbedPane);	// JTabbedPane을 패널에 추가
	}
	
	// 재난 속보 테이블 설정 메소드
	public void setNoticeTable(ArrayList<News> news) {
		noticePanel.setTable(news);
	}
	
	// 재난 문자 테이블 설정 메소드
	public void setMessageTable(ArrayList<Message> messages) {
		messagePanel.setTable(messages);
	}
	
	// 대피소 테이블 설정 메소드
	public void setShelterTable(ArrayList<Shelter> shelters) {
		shelterPanel.setTable(shelters);
	}
}