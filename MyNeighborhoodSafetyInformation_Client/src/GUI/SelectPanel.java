// 개발목적: 도시 선택 패널 GUI
// 개발기간: 2025년 12월 01일  ~  2025년 12월 14일

package GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import Socket.*;

public class SelectPanel extends JPanel {
	// 컴포넌트 선언
	private JTextField inputField;
	private JButton checkButton;
	
	MSGBuilder msgBuilder = new MSGBuilder();
	
	// 생성자
	public SelectPanel(MainFrame mainFrame) {
		setLayout(new BorderLayout());
		
		JLabel topLabel = new JLabel("충남 우리동네 안전정보", JLabel.CENTER);
		topLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 30));
		topLabel.setBorder(new EmptyBorder(50, 10, 0, 10));
		
		inputField = new JTextField(10);
		inputField.setFont(new Font("Malgun Gothic", Font.PLAIN, 20));
		
		checkButton = new JButton("확인");
		checkButton.setBackground(Color.WHITE);
		checkButton.setFocusPainted(false);
		
		JPanel inputPanel = new JPanel();
		inputPanel.add(inputField);
		inputPanel.add(checkButton);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new BorderLayout());
		bottomPanel.add(new JLabel("지역명을 입력해주세요.(시, 군 단위)", JLabel.CENTER), BorderLayout.NORTH);
		bottomPanel.add(inputPanel, BorderLayout.CENTER);
		bottomPanel.setBorder(new EmptyBorder(0, 10, 50, 10));
		
		ActionListener regionListener = new ActionListener() {	// ActionListener 생성
			@Override
			public void actionPerformed(ActionEvent ae) {
				String inputCityName = inputField.getText().trim();	// 입력한 도시명을 저장
				
				if(inputCityName.length() < 2) {	// 2글자 미만이라면
					JOptionPane.showMessageDialog(mainFrame, "올바르지 않은 입력입니다.", "확인", JOptionPane.WARNING_MESSAGE);
					return;	// 종료
				}
				
				mainFrame.sendMSG(msgBuilder.inputCityMSG(inputCityName));	// 도시 확인 요청
			}
		};
		
		// ActionListener 등록
		inputField.addActionListener(regionListener);
		checkButton.addActionListener(regionListener);
		
		// 패널에 추가
		add(topLabel, BorderLayout.NORTH);
		add(bottomPanel, BorderLayout.SOUTH);
	}
}