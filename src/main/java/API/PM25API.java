package API;


import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Entity.Location;
import Error.RequestError;
import Tools.JsonReader;


////////////////////////////////////////////////////////////////////////////////////////////////////////
public class PM25API {
	
	private Location[] location_list;
	private final String url = "https://api.data.gov.sg/v1/environment/pm25";
	
	//Singleton design
	private static final PM25API INSTANCE = new PM25API();
	
	public static PM25API getInstance() {
		return INSTANCE;
	}
	
	private PM25API() {
		
		try {
			JSONObject result = JsonReader.readJsonFromUrl(this.url);
			
			if (result.length() == 0) {
				throw new RequestError("Unable to retrieve PM2.5 information");
			}
			
			JSONArray region_metadata = (JSONArray) result.get("region_metadata");
			this.location_list = new Location[region_metadata.length()];
			
			for (int i = 0; i < region_metadata.length(); i++) {
				
				JSONObject region = region_metadata.getJSONObject(i);
				String name = region.getString("name");
				JSONObject label_location = region.getJSONObject("label_location");
				double latitude = label_location.getDouble("latitude");
				double longitude = label_location.getDouble("longitude");
				this.location_list[i] = new Location(name, latitude, longitude);
				
			}
			
		}
		catch (JSONException | RequestError e) {
			e.printStackTrace();			
		}
		
	}
	
	private String getRegion(double latitude, double longitude) {
		
		double min_distance = 0;
		Location min_region = this.location_list[0];
		
		for (Location region: this.location_list) {
			
			double distance = Math.sqrt(
					Math.pow((latitude - region.getLatitude()), 2) + 
					Math.pow((longitude - region.getLongitude()), 2));
			
			if (region == this.location_list[0]) {
				min_distance = distance;
				continue;
			}

			if (distance < min_distance) {
				min_distance = distance;
				min_region = region;
			}
			
		}
		
		return min_region.getName();
		
	}
	
	public int requestPM25(double latitude, double longitude) {
		
		try {
			JSONObject result = JsonReader.readJsonFromUrl(url);
			
			if (result.length() == 0) {
				return -1;
			}
			
			JSONArray items = (JSONArray) result.get("items");
			JSONObject item = (JSONObject) items.get(0);
			JSONObject readings = (JSONObject) item.get("readings");
			JSONObject pm25_hourly = (JSONObject) readings.get("pm25_one_hourly");
			
			return pm25_hourly.getInt(this.getRegion(latitude, longitude));
		} 
		catch (JSONException e) {
			e.printStackTrace();
			return -1;
		}
	
	}
	
	public static void main(String[] args) throws IOException, JSONException, RequestError {
		System.out.println(PM25API.getInstance().requestPM25(1.3450101, 103.9832089));
	}

}
