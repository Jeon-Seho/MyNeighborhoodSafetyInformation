// 개발목적: 지도 및 상세 정보 GUI
// 개발기간: 2025년 12월 01일  ~  2025년 12월 14일

package GUI;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.time.format.DateTimeFormatter;

import common.*;

public class MapDialog extends JDialog {
	private ImageIcon mapImage;
	private JTextArea infoArea;
	private String title;
	
	// 재난 속보 생성자
	public MapDialog(JFrame frame, String title, News news, ImageIcon mapImage) {
		super(frame, title, true);	// 모달 설정
		this.mapImage = mapImage;	// 이미지 저장
		
		this.title = news.getAddress();	// 제목 설정
		initComponent();	// 컴포넌트 배치
		
		// 상세 정보 설정
		infoArea.append("구분: " + news.getCategory() + "\n\n");
		infoArea.append("시간: " + news.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + "\n\n");
		infoArea.append("주소: " + news.getAddress() + "\n\n");
		infoArea.append("내용: " + news.getContent() + "\n");
		
		setVisible(true);
	}
	
	// 대피소 생성자
	public MapDialog(JFrame frame, String title, Shelter shelter, ImageIcon mapImage) {
		super(frame, title, true);	// 모달 설정
		this.mapImage = mapImage;	// 이미지 저장
		
		this.title = shelter.getAddress();	// 제목 설정
		initComponent();	// 컴포넌트 배치
		
		// 상세 정보 설정
		infoArea.append("이름: " + shelter.getName() + "\n\n");
		infoArea.append("주소: " + shelter.getAddress() + "\n\n");
		infoArea.append("면적: " + shelter.getArea() + "㎡\n\n");
		infoArea.append("수용 인원: " + shelter.getCapacity() + "명\n");
		
		setVisible(true);
	}
	
	// 컴포넌트 배치
	private void initComponent() {
		setLayout(new BorderLayout());
		
		JLabel topLabel = new JLabel(title);
		topLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 20));
		topLabel.setBorder(new EmptyBorder(10, 10, 0, 0));
		
		JLabel mapImageLabel = new JLabel(mapImage);
		mapImageLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		infoArea = new JTextArea(0, 15);
		infoArea.setFont(new Font("Malgun Gothic", Font.PLAIN, 18));
		infoArea.setEditable(false);
		infoArea.setLineWrap(true);	// 자동 줄바꿈 설정
		
		// 패널에 추가
		add(topLabel, BorderLayout.NORTH);
		add(mapImageLabel, BorderLayout.CENTER);
		add(infoArea, BorderLayout.EAST);
				
		pack();												// 크기 자동 맞춤
		setResizable(false);								// 크기 조정
		setLocationRelativeTo(null);						// 모니터 중앙 표시
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);	// 종료 시 처리
	}
}