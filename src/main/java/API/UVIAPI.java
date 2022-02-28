package API;


import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Error.RequestError;
import Tools.JsonReader;


/////////////////////////////////////////////////////////////////////////////////////////////////////////
public class UVIAPI {

	private final String url = "https://api.data.gov.sg/v1/environment/uv-index";
	
	//Singleton design pattern
	private static final UVIAPI INSTANCE = new UVIAPI();
	
	public static UVIAPI getInstance() {
		return INSTANCE;
	}
	
	public int requestUVIndex() {
		
		try {
			JSONObject result = JsonReader.readJsonFromUrl(this.url);
			
			if (result.length() == 0) {
				return -1;
			}
			
			JSONArray items = (JSONArray) result.get("items");
			JSONObject item = (JSONObject) items.get(0);
			JSONArray indices = (JSONArray) item.get("index");
			JSONObject latest = (JSONObject) indices.get(0);
			return latest.getInt("value");
		} 
		catch (JSONException e) {
			e.printStackTrace();
			return -1;
		}
		
	}
	
	public static void main(String[] args) throws IOException, JSONException, RequestError {
		System.out.println(UVIAPI.getInstance().requestUVIndex());
	}
	
}
