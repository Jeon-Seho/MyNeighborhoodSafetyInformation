// 개발목적: 지도 API 연결 클래스
// 개발기간: 2025년 12월 01일  ~  2025년 12월 14일

package API;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.io.*;

public class MapAPI {
	private static final String SERVICE_KEY = PrivateData.MAP_SERVICE_KEY;
	private static final String API_URL = "https://maps.googleapis.com/maps/api/staticmap";
	
	int fileNumber = 1;	// 파일명에 사용할 파일 카운트
	
	// 응답 메소드
	public File request(String _address) {
		File file = null;
		
		try {
			StringBuilder urlBuilder = new StringBuilder(API_URL);
	        // center: 위도, 경도 or 문자열 주소(마커가 있으면 필요 없음)
			//
	        // markers: 마커, |로 구분자 사용 /  color:(색상), label:(단일 대문자 영숫자 문자), size:tiny or mid or small, 위도경도 or 문자열
	        urlBuilder.append("?" + URLEncoder.encode("markers","UTF-8") + "=" + URLEncoder.encode(_address,"UTF-8"));
	        // zoom: 확대/축소 수준(0 ~ 21)
	        urlBuilder.append("&" + URLEncoder.encode("zoom","UTF-8") + "=" + URLEncoder.encode("16", "UTF-8"));
	        // size: 이미지 크기(가로 x 세로)
	        urlBuilder.append("&" + URLEncoder.encode("size","UTF-8") + "=" + URLEncoder.encode("640x640", "UTF-8"));
	        // scale: 픽셀수(1 or 2)
	        //urlBuilder.append("&" + URLEncoder.encode("scale","UTF-8") + "=" + URLEncoder.encode("2", "UTF-8"));	// 용량이 2배 커짐
	        // format: 이미지 형식(기본값: png)
	        urlBuilder.append("&" + URLEncoder.encode("format","UTF-8") + "=" + URLEncoder.encode("jpg", "UTF-8"));
	        // maptype: 지도 유형(기본값: roadmap, 실제지형지물: terrain, 하이브리드: hybrid)
	        //
	        // key: 서비스 API 키
	        urlBuilder.append("&" + URLEncoder.encode("key","UTF-8") + "=" + URLEncoder.encode(SERVICE_KEY, "UTF-8"));
	        
	        URI uri = new URI(urlBuilder.toString());
	        URL url = uri.toURL();
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("Accept", "image/jpeg");
	        
	        System.out.println("Response code: " + conn.getResponseCode());
	        
	        // 이미지 저장
	        file = new File("Map" + String.format("%03d", fileNumber) + ".jpeg");				// 파일 객체 생성
	        byte[] buffer = new byte[4096];														// 버퍼 byte[] 배열 생성
	        BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());			// 입력 스트림 생성
	        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));	// 출력 스트림 생성
	        
	        int i = 0;
	        while((i = bis.read(buffer)) != -1){	// 입력 스트림에서 데이터를 읽어 버퍼에 저장
	        	bos.write(buffer, 0, i);			// 출력 스트림에 버퍼를 작성
	        }
	        bos.close();
	        
	        fileNumber += 1;	// 파일 넘버 증가
	        
	        return file;	// File 객체 반환
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}