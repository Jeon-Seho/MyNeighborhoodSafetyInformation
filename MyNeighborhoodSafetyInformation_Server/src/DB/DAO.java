// 개발목적: 데이터베이스 접근 객체
// 개발기간: 2025년 12월 01일  ~  2025년 12월 14일

package DB;

import java.util.ArrayList;
import java.sql.*;

import common.*;

public class DAO {
	private DAO() {}	// 싱글톤 패턴 사용
	
	private static DAO instance = new DAO();	// 인스턴스 생성
	
	// 인스턴스 반환 메소드
	public static DAO getInstance() {
		return instance;
	}
	
	// 도시명을 입력하면 도시 객체를 반환하는 메소드
	// 매개변수: 도시명, 반환값: 도시 객체
	public City findCity(String _cityName) {
		City city = null;
		Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
		String sql = "SELECT * FROM chungnam_city WHERE name LIKE ? ";
		
		try {
			conn = DBC.connect();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + _cityName + "%");	// 와일드카드 문자 사용
            rs = pstmt.executeQuery();
            
            if(rs.next()) {
            	city = new City();
            	city.setId(rs.getInt("id"));
            	city.setName(rs.getString("name"));
            	city.setLatitude(rs.getDouble("latitude"));
            	city.setLongitude(rs.getDouble("longitude"));
            }
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			DBC.close(conn, pstmt, rs);
		}
		
		return city;
	}
	
	// 도시명을 입력하면 해당 도시의 대피소 객체를 ArrayList로 반환하는 메소드
	// 매개변수: 도시명, 반환값: 대피소 ArrayList
	public ArrayList<Shelter> findShelter(String _address){
		ArrayList<Shelter> shelters = new ArrayList<Shelter>();
		Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        String sql = "SELECT * FROM chungnam_shelter WHERE address LIKE ? ";
        
        try {
        	conn = DBC.connect();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + _address + "%");	// 와일드카드 문자 사용
            rs = pstmt.executeQuery();
            
            while(rs.next()) {
            	Shelter shelter = new Shelter();
            	
            	shelter.setId(rs.getInt("id"));
            	shelter.setName(rs.getString("name"));
            	shelter.setAddress(rs.getString("address"));
            	shelter.setArea(rs.getDouble("area"));
            	shelter.setCapacity(rs.getInt("capacity"));
            	
            	shelters.add(shelter);
            }
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
        finally {
        	DBC.close(conn, pstmt, rs);
        }
        
        return shelters;
	}
}