// 개발목적: 재난 문자 API 연결 클래스
// 개발기간: 2025년 12월 01일  ~  2025년 12월 14일

package API;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import common.*;

public class MessageAPI {
	// 긴급재난문자
	private static final String SERVICE_KEY = PrivateData.MESSAGE_SERVICE_KEY;
	private static final String API_URL = "https://www.safetydata.go.kr/V2/api/DSSP-IF-00247";
	
	ArrayList<Message> messages = new ArrayList<>();
	
	int pageNo, numOfRows, totalCount;
	String cityName = null;
	
	// 응답 메소드
	public ArrayList<Message> request(String _cityName) {
		pageNo = 1;
		numOfRows = 1;
		totalCount = -1;
		cityName = _cityName;
		
		connectAPI();
		
		if(totalCount != -1) {
			messages.clear();
			numOfRows = 100;
			
			for(int i = 0; i < (totalCount / numOfRows) + 1; i++) {
				JsonArray body = connectAPI();
				pageNo += 1;
				
				for(int j = 0; j < body.size(); j++) {
					JsonObject obj = body.get(j).getAsJsonObject();
					
					// 재해구분
		        	// AI, 가뭄, 가축질병, 강풍, 건조, 교통, 교통사고, 교통통제, 금융, 기타, 대설, 미세먼지, 민방공, 붕괴, 산불, 산사태, 수도, 안개
		        	// 에너지, 전염병, 정전, 지진, 지진해일, 태풍, 테러, 통신, 폭발, 폭염, 풍랑, 한파, 호우, 홍수, 화재, 환경오염사고, 황사
					
					Message message = new Message();
					message.setContent(obj.get("MSG_CN").getAsString());	// 내용
					message.setRegion(obj.get("RCPTN_RGN_NM").getAsString());	// 수신 지역
					message.setStep(obj.get("EMRG_STEP_NM").getAsString());		// 긴급 단계
					message.setSection(obj.get("DST_SE_NM").getAsString());	// 재해 구분
					LocalDateTime CRT_DT = LocalDateTime.parse(obj.get("CRT_DT").getAsString(), DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
					message.setDate(CRT_DT);		// 일시
					
					messages.add(message);
				}
			}
		}
		
		// 시간 순 정렬
		Collections.sort(messages, new Comparator<Message>() {
			@Override
			public int compare(Message m1, Message m2) {
				return m1.getDate().compareTo(m2.getDate());
			}
		});
		
		return messages;
	}
	
	// API 연결 메소드
	private JsonArray connectAPI() {
		try {
			StringBuilder urlBuilder = new StringBuilder(API_URL); /*URL*/
	        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + URLEncoder.encode(SERVICE_KEY,"UTF-8"));				// 서비스 키
	        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode(String.valueOf(pageNo), "UTF-8"));		// 페이지 번호
	        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode(String.valueOf(numOfRows), "UTF-8"));	// 페이지 당 데이터 수
	        urlBuilder.append("&" + URLEncoder.encode("resultType","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8"));					// 결과형식(xml/json)
	        urlBuilder.append("&" + URLEncoder.encode("crtDt", "UTF-8") + "=" + URLEncoder.encode(LocalDate.now().withDayOfMonth(1).format(DateTimeFormatter.ofPattern("yyyyMMdd")), "UTF-8"));	// 조회 시작 일자
	        urlBuilder.append("&" + URLEncoder.encode("rgnNm", "UTF-8") + "=" + URLEncoder.encode(cityName, "UTF-8"));						// 지역명
	        
	        URI uri = new URI(urlBuilder.toString());
	        URL url = uri.toURL();
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("Accept", "application/json");
	        
	        System.out.println("Response code: " + conn.getResponseCode());
	    
	        BufferedReader br = new BufferedReader(
	                new InputStreamReader(conn.getInputStream())
	        );
	        StringBuilder sb = new StringBuilder();
	        String line;
	        while ((line = br.readLine()) != null) {
	            sb.append(line);
	        }
	        br.close();
	        conn.disconnect();
	          
	        JsonObject root = JsonParser.parseString(sb.toString()).getAsJsonObject();
	        JsonArray body = root.getAsJsonArray("body");
	        
	        totalCount = root.get("totalCount").getAsInt();
	        
	        return body;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}