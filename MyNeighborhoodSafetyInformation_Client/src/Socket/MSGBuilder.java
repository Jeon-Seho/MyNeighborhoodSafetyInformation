// 개발목적: 서버에게 보내는 메시지를 생성하는 클래스
// 개발기간: 2025년 12월 01일  ~  2025년 12월 14일

package Socket;

public class MSGBuilder {
	// 입력 도시 전달 메시지 생성 메소드
	public String inputCityMSG(String _cityName) {
		return "City_C//" + _cityName + "//End";
	}
	
	// 재난 속보 요청 메시지 생성 메소드
	public String newsMSG() {
		return "Request_C//News//End";
	}
	
	// 재난 문자 요청 메시지 생성 메소드
	public String messageMSG() {
		return "Request_C//Message//End";
	}
	
	// 대피소 요청 메시지 생성 메소드
	public String shelterMSG() {
		return "Request_C//Shelter//End";
	}
	
	// 지도 요청 메시지 생성 메소드
	public String mapMSG(String _address) {
		return "Request_C//Map//" + _address + "//End";
	}
	
	// 프로그램 종료 메시지 생성 메소드
	public String exitMSG() {
		return "Exit_C//End";
	}
}