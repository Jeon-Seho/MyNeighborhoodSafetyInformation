// 개발목적: 대피소 정보를 저장하기 위한 공유 클래스
// 개발기간: 2025년 11월 24일  ~  2025년 12월 14일

package common;

import java.io.*;

public class Shelter implements Serializable {
	int id;			// 아이디
	String name;	// 시설명
	String address;	// 주소
	Double area;	// 면적
	int capacity;	// 수용인원
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public void setArea(Double area) {
		this.area = area;
	}
	
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getAddress() {
		return address;
	}
	
	public Double getArea() {
		return area;
	}
	
	public int getCapacity() {
		return capacity;
	}
}