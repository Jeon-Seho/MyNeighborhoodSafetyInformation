// 개발목적: 클라이언트가 송신한 메시지를 분리하는 클래스
// 개발기간: 2025년 12월 01일  ~  2025년 12월 14일

package Socket;

import java.util.StringTokenizer;

public class ReceivedMSGTokenizer {
	StringTokenizer st;				// 문자열을 분리하기 위한 객체 선언
	MSGTable mt = new MSGTable();	// 메시지 테이블 객체 생성
	
	// 메시지 타입을 탐색하는 메소드
	int detection(String _msg) {
		int result = -1;	// 결과를 저장할 변수
		String tag;
		
		st = new StringTokenizer(_msg, "//");	// 메시지를 "//"를 구분자로 하여 분리
		tag = st.nextToken();					// 태그를 저장
		
		for(int i = 0; i < mt.numberOfMSG; i++) {	// 메시지 테이블에 정의된 메시지의 수만큼 반복
			if(tag.equals(mt.MSGtags[i])){			// 태그가 동일하다면
				result = i;	// 해당 인덱스(메시지 타입)를 저장
				break;
			}
		}
		
		return result;	// 저장한 메시지 타입 반환
	}
	
	// 클라이언트가 입력한 도시명을 찾는 메소드
	String findCityName(String _msg) {
		String cityName = null;
		st = new StringTokenizer(_msg, "//");
		
		st.nextToken();
		cityName = st.nextToken();
		
		return cityName;
	}
	
	// 클라이언트의 요청 종류를 찾는 메소드
	String findRequest(String _msg) {
		String request = null;
		st = new StringTokenizer(_msg, "//");
		
		st.nextToken();
		request = st.nextToken();
		
		return request;
	}
	
	// 클라이언트가 전송한 주소를 찾는 메소드
	String findAddress(String _msg) {
		String address = null;
		st = new StringTokenizer(_msg, "//");
		
		st.nextToken();
		st.nextToken();
		address = st.nextToken();
		
		return address;
	}
}