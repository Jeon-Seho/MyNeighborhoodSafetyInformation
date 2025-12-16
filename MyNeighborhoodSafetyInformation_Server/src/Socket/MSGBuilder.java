// 개발목적: 클라이언트에게 보내는 메시지를 생성하는 클래스
// 개발기간: 2025년 12월 01일  ~  2025년 12월 14일

package Socket;

public class MSGBuilder {
	// 도시 확인 메시지 생성 메소드
	public String selectedCityMSG(String _cityName) {
		return "City_S//" + _cityName + "//End";
	}
	
	// 재난 속보 응답 메시지 생성 메소드
	public String newsMSG() {
		return "Response_S//News//End";
	}
	
	// 재난 문자 응답 메시지 생성 메소드
	public String messageMSG() {
		return "Response_S//Message//End";
	}
	
	// 대피소 응답 메시지 생성 메소드
	public String shelterMSG() {
		return "Response_S//Shelter//End";
	}
	
	// 지도 이미지 응답 메시지 생성 메소드
	public String mapMSG() {
		return "Response_S//Map//End";
	}
	
	// 프로그램 종료 메시지 생성 메소드
	public String exitMSG() {
		return "Exit_S//End";
	}
}