package Control;

import java.io.IOException;

import org.json.JSONException;

import API.PM25API;
import API.PSIAPI;
import API.UVIAPI;
import API.WeatherAPI;
import Entity.Location;
import Entity.WeatherInfo;
import Entity.WeatherInfo.Weather;
import Error.RequestError;

public class WeatherManager {



	//Singleton design pattern
	private static final WeatherManager INSTANCE = new WeatherManager();

	public static WeatherManager getInstance() {
		return INSTANCE;
	}

	//Facade design pattern
	public WeatherInfo[] requestWeatherInfo(Location start, Location dest) throws IOException, JSONException, RequestError {

		int start_pm25, start_psi, uvi, dest_pm25, dest_psi;
		String start_weather_str, dest_weather_str;
		Weather start_weather = null, dest_weather = null;

		start_pm25 = PM25API.getInstance().requestPM25(start.getLatitude(), start.getLongitude());
		if (start_pm25 != -1)
			dest_pm25 = PM25API.getInstance().requestPM25(dest.getLatitude(), dest.getLongitude());
		else dest_pm25 = -1;

		start_psi = PSIAPI.getInstance().requestPSI(start.getLatitude(), start.getLongitude());
		if (start_psi != -1)
			dest_psi = PSIAPI.getInstance().requestPSI(dest.getLatitude(), dest.getLongitude());
		else dest_psi = -1;

		uvi = UVIAPI.getInstance().requestUVIndex();

		start_weather_str = WeatherAPI.getInstance().requestWeather(start.getLatitude(), start.getLongitude());
		if (!start_weather_str.equals(""))
			dest_weather_str = WeatherAPI.getInstance().requestWeather(dest.getLatitude(), dest.getLongitude());
		else dest_weather_str = "";

		String weather_str = start_weather_str.toUpperCase();
		for (Weather weather: Weather.values()) {
			if (weather_str.contains(weather.toString())) {
				start_weather = weather;
				break;
			}
		}
		if (start_weather == null)
			start_weather = Weather.ALL;

		weather_str = dest_weather_str.toUpperCase();
		for (Weather weather: Weather.values()) {
			if (weather_str.contains(weather.toString())) {
				dest_weather = weather;
				break;
			}
		}
		if (dest_weather == null)
			dest_weather = Weather.ALL;

		WeatherInfo[] weathers = new WeatherInfo[2];
		weathers[0] = new WeatherInfo(start_pm25, start_psi, uvi, start_weather_str, start_weather);
		weathers[1] = new WeatherInfo(dest_pm25, dest_psi, uvi, dest_weather_str, dest_weather);
		//weathers[0] is the starting location weather info
		//weathers[1] is the destination weather info

		return weathers;

	}

	//extracting the Weather info
	//recommendation will based on higher value between two locations;
	public WeatherInfo extract_wthr(WeatherInfo[] weather){
		int max_pm25 = Math.max(weather[0].getPM25(),weather[1].getPM25());
		int max_psi = Math.max(weather[0].getPSI(),weather[1].getPSI());
		int max_uvi = Math.max(weather[0].getUVI(),weather[1].getUVI());
		String wthr = "ALL";

		//to check whether one of it is raining
		boolean if_rain = false;
		for (WeatherInfo.Rainy_wthr wth: WeatherInfo.Rainy_wthr.values()){
			if(weather[0].getWeather_str().equals(wth.toString())|
				weather[1].getWeather_str().equals(wth.toString())){
				if_rain = true;
			}
		}
		if (if_rain){
			wthr = "RAIN";
		}
		return new WeatherInfo(max_pm25,max_psi,max_uvi,wthr, Weather.valueOf(wthr));
	}
	}
