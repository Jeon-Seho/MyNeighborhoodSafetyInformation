// 개발목적: 재난 속보 정보를 저장하기 위한 공유 클래스
// 개발기간: 2025년 11월 24일  ~  2025년 12월 14일

package common;

import java.io.*;
import java.time.*;

public class News implements Serializable {
	String category;	// 재난유형
	LocalDateTime date;	// 재난일시
	String address;		// 주소
	String content;		// 내용
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getCategory() {
		return category;
	}
	
	public LocalDateTime getDate() {
		return date;
	}
	
	public String getAddress() {
		return address;
	}
	
	public String getContent() {
		return content;
	}
}