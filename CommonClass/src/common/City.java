// 개발목적: 도시 정보를 저장하기 위한 공유 클래스
// 개발기간: 2025년 11월 24일  ~  2025년 12월 14일

package common;

import java.io.*;

public class City implements Serializable {	// [JAVA] 객체 직렬화란?(Serializable)(https://blog.naver.com/ka28/221938124123) 및
											// [JAVA] 객체 직렬화를 통한 네트워크 전송(https://lold2424.tistory.com/248) 참고
	int id;				// 아이디
	String name;		// 도시명
	Double latitude;	// 위도
	Double longitude;	// 경도
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public Double getLatitude() {
		return latitude;
	}
	
	public Double getLongitude() {
		return longitude;
	}
}