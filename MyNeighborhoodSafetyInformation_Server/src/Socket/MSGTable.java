// 개발목적: 클라이언트 -> 서버 메시지 형식 정의 클래스
// 개발기간: 2025년 12월 01일  ~  2025년 12월 14일

/*
 	City_C//(입력한 도시명)//End - 선택 도시 전달
 	Request_C//News//End - 재난 속보 요청
 	Request_C//Message//End - 재난 문자 요청
 	Request_C//Shelter//End - 대피소 요청
 	Request_C//Map//(주소)//End - 지도 이미지 요청
 	Exit_C//End - 프로그램 종료
 */

package Socket;

public class MSGTable {
	String[] MSGtags = {"City_C", "Request_C", "Exit_C"};
	int numberOfMSG = MSGtags.length;	// 배열의 길이를 기반으로 메시지 태그 개수 저장
}