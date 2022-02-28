package API;


import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Entity.Location;
import Error.RequestError;
import Tools.JsonReader;


////////////////////////////////////////////////////////////////////////////////////////////////////////
public class WeatherAPI {

	private Location[] location_list;
	private final String url = "https://api.data.gov.sg/v1/environment/2-hour-weather-forecast";

	//Singleton design pattern
	private static final WeatherAPI INSTANCE = new WeatherAPI();

	public static WeatherAPI getInstance() {
		return INSTANCE;
	}

	private WeatherAPI() {

		try {
			JSONObject result = JsonReader.readJsonFromUrl(this.url);

			if (result.length() == 0) {
				throw new RequestError("Unable to retrieve weather information");
			}

			JSONArray area_metadata = (JSONArray) result.get("area_metadata");

			this.location_list = new Location[area_metadata.length()];

			for (int i = 0; i < area_metadata.length(); i++) {

				JSONObject area = area_metadata.getJSONObject(i);
				String name = area.getString("name");
				JSONObject label_location = area.getJSONObject("label_location");
				double latitude = label_location.getDouble("latitude");
				double longitude = label_location.getDouble("longitude");
				this.location_list[i] = new Location(name, latitude, longitude);

			}
		}
		catch (JSONException | RequestError e) {
			e.printStackTrace();
		}

	}

	private int getAreaIndex(double latitude, double longitude) {

		double min_distance = 0;
		int min_index = 0;

		for (int i = 0; i < this.location_list.length; i++) {

			double distance = Math.sqrt(
					Math.pow((latitude - this.location_list[i].getLatitude()), 2) +
							Math.pow((longitude - this.location_list[i].getLongitude()), 2));

			if (i == 0) {
				min_distance = distance;
			}
			else {
				if (distance < min_distance) {
					min_distance = distance;
					min_index = i;
				}
			}

		}

		return min_index;

	}

	public String requestWeather(double latitude, double longitude) {

		try {
			JSONObject result = JsonReader.readJsonFromUrl(url);

			if (result.length() == 0) {
				return "";
			}

			JSONArray items = (JSONArray) result.get("items");
			JSONObject item = (JSONObject) items.get(0);
			JSONArray forecasts = (JSONArray) item.get("forecasts");
			JSONObject forecast = (JSONObject) forecasts.get(this.getAreaIndex(latitude, longitude));

			return forecast.getString("forecast");
		}
		catch (JSONException e) {
			e.printStackTrace();
			return "";
		}

	}

	public static void main(String[] args) throws IOException, JSONException, RequestError {
		System.out.println(WeatherAPI.getInstance().requestWeather(1.432, 103.786));
	}

}
