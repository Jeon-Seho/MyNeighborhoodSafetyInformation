// 개발목적: 데이터베이스 연결 객체
// 개발기간: 2025년 12월 01일  ~  2025년 12월 14일

package DB;

import javax.swing.*;
import java.sql.*;
import API.*;

public class DBC {
	public static final String databaseDriver = "com.mysql.cj.jdbc.Driver";
    public static final String databaseUrl = "jdbc:mysql://localhost:3306/myneighborhoodsafefyimpormation?serverTimezone=Asia/Seoul&characterEncoding=UTF8&useSSL=false&allowPublicKeyRetrieval=true";
    public static final String databaseUser = "root";
    public static final String databasePassword = PrivateData.ROOT_PASSWORD;

    // 테스트용 메인 메소드
    public static void main(String[] args) {
    	connect();
    	close(connect(), null, null);
    }
    
    // DB 연결 메소드
    public static Connection connect() {
    	Connection connection = null;
    	
    	try {
    		Class.forName(databaseDriver);
    		connection = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
    		
    		if(connection != null) {
    			System.out.println("DB 연결 성공");
    		}
    		else {
    			System.out.println("DB 연결 실패");
    		}
    	} catch(Exception e) {
    		JOptionPane.showMessageDialog(null, "데이터베이스 연결 실패! 비번이나 방이름을 확인하세요.", "경고", JOptionPane.WARNING_MESSAGE);
    		e.printStackTrace();
    	}
    	
    	return connection;
    }
   
    // DB 연결 해제 메소드
    public static void close(Connection connection, PreparedStatement pstmt, ResultSet rs) {
    	try {
    		if(rs != null) {
    			rs.close();
    		}
    		if(pstmt != null) {
    			pstmt.close();
    		}
    		if(connection != null) {
    			connection.close();
    			System.out.println("DB 연결 종료");
    		}
    	} catch(SQLException e) {
    		e.printStackTrace();
    	}
    }
}