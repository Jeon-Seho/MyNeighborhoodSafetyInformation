// 개발목적: 긴급 재난 문자 정보를 저장하기 위한 공유 클래스
// 개발기간: 2025년 11월 24일  ~  2025년 12월 14일

package common;

import java.io.*;
import java.time.*;

public class Message implements Serializable {
	String content;		// 내용
	String region;		// 수신지역
	String step;		// 긴급단계
	String section;		// 재해구분
	LocalDateTime date;	// 일시
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public void setRegion(String region) {
		this.region = region;
	}
	
	public void setStep(String step) {
		this.step = step;
	}
	
	public void setSection(String section) {
		this.section = section;
	}
	
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	
	public String getContent() {
		return content;
	}
	
	public String getRegion() {
		return region;
	}
	
	public String getStep() {
		return step;
	}
	
	public String getSection() {
		return section;
	}
	
	public LocalDateTime getDate() {
		return date;
	}
}