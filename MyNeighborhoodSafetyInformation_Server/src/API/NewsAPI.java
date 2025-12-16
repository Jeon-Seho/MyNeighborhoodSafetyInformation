// 개발목적: 재난 속보 API 연결 클래스
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

import common.*;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class NewsAPI {
	// 재난 속보
	private static final String SERVICE_KEY = PrivateData.NEWS_SERVICE_KEY;
	private static final String API_URL = "https://apis.data.go.kr/6440000/getDisasterSearch/disasterinfoSearch";
	
	ArrayList<News> news = new ArrayList<>();
	
	int pageNo, numOfRows, totalCount;
	
	// 응답 메소드
	public ArrayList<News> request(String _cityName) {
		pageNo = 1;
		numOfRows = 1;
		totalCount = -1;
		
		connectAPI();	// totalCount 확인
		
		if(totalCount != -1) {
			news.clear();
			numOfRows = 100;
			
			for(int i = 0; i < (totalCount / numOfRows) + 1; i++) {
				JsonArray itemArray = connectAPI();
				pageNo += 1;
				
				for(int j = 0; j < itemArray.size(); j++) {
					News findNews = new News();
					JsonObject obj = itemArray.get(j).getAsJsonObject();
					
					if(!obj.get("CLMT_NM").getAsString().equals("상황전파메시지")) {
						if(obj.get("AREA_NM").getAsString().contains(_cityName)) {
							// 구급, 국도사고, 고속도로사고, 화재, 구조, 산불, 기타
							findNews.setCategory(obj.get("CLMT_NM").getAsString());	// 재난 유형
							LocalDateTime CLMT_DT = LocalDateTime.parse(obj.get("CLMT_DT").getAsString(), DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
							findNews.setDate(CLMT_DT);		// 재난 일시
							String address = obj.get("AREA_NM").getAsString();
							String content = obj.get("CLMT_COMM").getAsString().replace(address, "").replace("&lt;", "").replace("br/", "").replace("&gt;", "").replace("  ", " ").trim();
							findNews.setContent(content);// 내용
							findNews.setAddress(address.replace("충청남도 ", "").replace(_cityName, "").replace("  ", " ").trim());	// 주소
							
							news.add(findNews);
						}
					}
				}
			}
		}
		
		return news;
	}
	
	// API 연결 메소드
	private JsonArray connectAPI() {
		try {
			StringBuilder urlBuilder = new StringBuilder(API_URL);
	        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + URLEncoder.encode(SERVICE_KEY,"UTF-8"));				// 서비스 키
	        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode(String.valueOf(pageNo), "UTF-8"));		// 페이지 번호
	        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode(String.valueOf(numOfRows), "UTF-8"));	// 페이지 당 데이터 수
	        urlBuilder.append("&" + URLEncoder.encode("resultType","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8"));					// 결과 형식
	        urlBuilder.append("&" + URLEncoder.encode("CLMT_DATE", "UTF-8") + "=" + URLEncoder.encode(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")), "UTF-8"));	// 일시

	        URI uri = new URI(urlBuilder.toString());
	        URL url = uri.toURL();
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("Accept", "application/json");
	        
	        System.out.println("Response code: " + conn.getResponseCode());
	        
	        BufferedReader br = new BufferedReader(
	                new InputStreamReader(conn.getInputStream(), "UTF-8")
	        );
	        StringBuilder sb = new StringBuilder();
	        String line;
	        while ((line = br.readLine()) != null) {
	            sb.append(line);
	        }
	        br.close();
	        conn.disconnect();
	        
	        JsonObject root = JsonParser.parseString(sb.toString()).getAsJsonObject();
	        JsonObject response = root.getAsJsonObject("response");
	        JsonObject body = response.getAsJsonObject("body");
	        JsonObject items = body.getAsJsonObject("items");
	        JsonArray itemArray = items.getAsJsonArray("item");
	        
	        totalCount = body.get("totalCount").getAsInt();
	        
	        return itemArray;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}