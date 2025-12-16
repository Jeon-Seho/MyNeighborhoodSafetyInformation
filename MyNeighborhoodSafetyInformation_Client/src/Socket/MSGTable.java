// 개발목적: 서버 -> 클라이언트 메시지 형식 정의 클래스
// 개발기간: 2025년 12월 01일  ~  2025년 12월 14일

/*
	City_S//(도시명)//End - 도시 정보 확인 성공
	City_S//Error//End - 도시 정보 확인 실패
	Response_S//News//End - 재난 속보 응답
	Response_S//Message//End - 재난 문자 응답
	Response_S//Shelter//End - 대피소 정보 응답
	Response_S//Map//End - 지도 이미지 응답
	Exit_S//End - 프로그램 종료
 */

package Socket;

public class MSGTable {
	String[] MSGtags = {"City_S", "Response_S", "Exit_S"};
	int numberOfMSG = MSGtags.length;	// 배열의 길이를 기반으로 메시지 태그 개수 저장
}