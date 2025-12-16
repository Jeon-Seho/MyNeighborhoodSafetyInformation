// 개발목적: 재난 문자 패널 GUI
// 개발기간: 2025년 12월 01일  ~  2025년 12월 14일

package GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.border.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import Socket.*;
import common.*;

public class MessagePanel extends JPanel {
	// 컴포넌트 선언
	private JTable table;
	private DefaultTableModel tableModel;
	private JButton refreshButton;
	private JLabel informationLabel, updateLabel;
	
	MainFrame mainFrame;
	MSGBuilder msgBuilder = new MSGBuilder();
	
	// 생성자
	public MessagePanel(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(0, 20, 20, 20));
		
		refreshButton = new JButton("갱신");
		refreshButton.setBackground(Color.WHITE);
		refreshButton.setFocusPainted(false);
		refreshButton.setPreferredSize(new Dimension(80, 35));	// 컴포넌트 크기 설정
		refreshButton.addActionListener(new ActionListener() {	// 익명 객체를 사용한 ActionListener
			@Override
			public void actionPerformed(ActionEvent ae) {
				mainFrame.sendMSG(msgBuilder.messageMSG());	// 갱신 요청
			}
		});
		
		updateLabel = new JLabel("");
		
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		informationLabel = new JLabel("재난 문자");
		informationLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 25));
		leftPanel.add(informationLabel);
		
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		rightPanel.add(updateLabel);
		rightPanel.add(refreshButton);
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		topPanel.add(leftPanel, BorderLayout.WEST);
		topPanel.add(rightPanel, BorderLayout.EAST);
		
		tableModel = new DefaultTableModel() {	// TableModel 생성
			@Override
			public boolean isCellEditable(int r, int c) {	// 수정 불가능 처리
				return false;
			}
		};
		
		// 테이블 열 추가
		tableModel.addColumn("단계");
		tableModel.addColumn("구분");
		tableModel.addColumn("일시");
		tableModel.addColumn("내용");
		
		table = new JTable(tableModel) {	// 테이블 생성
			@Override
			public String getToolTipText(MouseEvent e) {	// 툴팁 설정
				Point p = e.getPoint();
				
				int row = rowAtPoint(p);
				int col = columnAtPoint(p);
				
				return getValueAt(row, col).toString();
			}
		};
		JScrollPane tableScrollPane = new JScrollPane(table);
		tableScrollPane.setPreferredSize(new Dimension(800, 500));
		tableScrollPane.setBorder(new TitledBorder("재난 문자"));
		
		table.setRowHeight(30);	// 행 높이 설정
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);	// 테이블 자동 크기 조절 끄기
		table.getColumnModel().getColumn(0).setPreferredWidth(100);	// 첫 번째 열 너비 설정
		table.getColumnModel().getColumn(1).setPreferredWidth(100);	// 두 번째 열 너비 설정
		table.getColumnModel().getColumn(2).setPreferredWidth(150);	// 세 번째 열 너비 설정
		table.getColumnModel().getColumn(3).setPreferredWidth(450);	// 세 번째 열 너비 설정
		table.getTableHeader().setReorderingAllowed(false);			// 열 순서 변경 금지 처리
		table.getTableHeader().setResizingAllowed(false);			// 열 너비 변경 금지 처리
		
		table.getTableHeader().setFont(new Font("Malgun Gothic", Font.BOLD, 15));	// 테이블 헤더 폰트 설정
		table.setFont(new Font("Malgun Gothic", Font.BOLD, 15));
		
		// 패널에 추가
		add(topPanel, BorderLayout.NORTH);
		add(tableScrollPane, BorderLayout.CENTER);
	}
	
	// 테이블 설정 메소드
	public void setTable(ArrayList<Message> messages) {
		informationLabel.setText(mainFrame.cityName + " 재난 문자");
		updateLabel.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss 갱신")));	// 갱신 일자 표시
		
		tableModel.setRowCount(0);	// TableModel 초기화
		
		for(int i = 0; i < messages.size(); i++) {	// ArrayList의 크기만큼 반복
			Object[] data = new Object[4];
			
			data[0] = messages.get(i).getStep();
			data[1] = messages.get(i).getSection();
			data[2] = messages.get(i).getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
			data[3] = messages.get(i).getContent();
			
			tableModel.addRow(data);	// TableModel에 행을 추가
		}
	}
}