// 개발목적: 대피소 패널 GUI
// 개발기간: 2025년 12월 01일  ~  2025년 12월 14일

package GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.border.*;
import java.util.ArrayList;

import common.*;

public class ShelterPanel extends JPanel {
	// 컴포넌트 선언
	private JTable table;
	private DefaultTableModel tableModel;
	private TableRowSorter<DefaultTableModel> sorter;
	private JButton checkButton;
	private JTextField inputField;

	private ArrayList<Shelter> shelters;
	
	// 생성자
	public ShelterPanel(MainFrame mainFrame) {
		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(0, 20, 20, 20));
		
		inputField = new JTextField(20);
		checkButton = new JButton("검색");
		checkButton.setBackground(Color.WHITE);
		checkButton.setFocusPainted(false);
		
		JPanel topPanel = new JPanel();
		topPanel.add(new JLabel("주소: "));
		topPanel.add(inputField);
		topPanel.add(checkButton);
		topPanel.setBorder(new EmptyBorder(20, 0, 10, 0));
		
		tableModel = new DefaultTableModel() {		// TableModel 생성
			@Override
			public boolean isCellEditable(int r, int c) {	// 수정 불가능 처리
				return false;
			}
		};
		
		// 테이블에 열 추가
		tableModel.addColumn("시설명");
		tableModel.addColumn("주소");
		
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
		tableScrollPane.setBorder(new TitledBorder("대피소"));
		
		table.setRowHeight(30);	// 행 높이 설정
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);	// 테이블 자동 크기 조절 끄기
		table.getColumnModel().getColumn(0).setPreferredWidth(300);	// 첫 번째 열 너비 설정
		table.getColumnModel().getColumn(1).setPreferredWidth(400);	// 두 번째 열 너비 설정
		table.getTableHeader().setReorderingAllowed(false);			// 열 순서 변경 금지 처리
		table.getTableHeader().setResizingAllowed(false);			// 열 너비 변경 금지 처리
		
		table.getTableHeader().setFont(new Font("Malgun Gothic", Font.BOLD, 15));	// 테이블 헤더 폰트 설정
		table.setFont(new Font("Malgun Gothic", Font.BOLD, 15));
		
		sorter = new TableRowSorter<>(tableModel);	// TableRowSorter 생성
		table.setRowSorter(sorter);					// TableRowSorter 등록
		
		table.addMouseListener(new MouseAdapter() {	// 익명 객체를 사용한 MouseAdapter
			@Override
			public void mouseClicked(MouseEvent e) {	// 마우스 클릭 이벤트만 오버라이드
				if(e.getClickCount() == 2) {	// 더블 클릭 시
					int selectedIndex = table.getSelectedRow();	// 선택된 행의 인덱스를 저장
					
					if(selectedIndex != -1) {	// 선택된 행이 존재할 시
						int selectedModelIndex = table.convertRowIndexToModel(selectedIndex);	// TableModel의 인덱스로 변환
						Shelter selectedShelter = shelters.get(selectedModelIndex);	// 선택한 대피소 저장
						
						mainFrame.prepareForMapDialog(selectedShelter);	// MapDialog 준비 요청
					}
				}
			}
		});
		
		inputField.addActionListener(new ActionListener() {	// 익명 객체를 사용한 ActionListener
			@Override
			public void actionPerformed(ActionEvent ae) {
				search();
			}
		});
		
		checkButton.addActionListener(new ActionListener() {// 익명 객체를 사용한 ActionListener
			@Override
			public void actionPerformed(ActionEvent ae) {
				search();
			}
		});
		
		// 패널에 추가
		add(topPanel, BorderLayout.NORTH);
		add(tableScrollPane, BorderLayout.CENTER);
	}
	
	// 테이블 설정 메소드
	public void setTable(ArrayList<Shelter> shelters) {
		this.shelters = shelters;
		
		tableModel.setRowCount(0);	// TableModel 초기화
		
		for(int i = 0; i < shelters.size(); i++) {	// ArrayList의 크기만큼 반복
			Object[] data = new Object[2];
			
			data[0] = shelters.get(i).getName();
			data[1] = shelters.get(i).getAddress();
			
			tableModel.addRow(data);	// TableModel에 행을 추가
		}
	}
	
	// 검색(필터링) 메소드
	private void search() {
		String inputText = inputField.getText().trim();	// 입력 검색 저장
		
		if(inputText.length() == 0) {	// 검색어의 길이가 0이라면
			sorter.setRowFilter(null);	// 모든 행을 출력
		}
		else {
			sorter.setRowFilter(RowFilter.regexFilter(inputText));	// 입력 검색어를 기준으로 행을 출력
		}
	}
}