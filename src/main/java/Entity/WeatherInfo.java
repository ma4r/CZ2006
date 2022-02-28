package Entity;

public class WeatherInfo {

	public static enum Weather {ALL, THUNDERY, RAIN, SHOWERS, CLOUDY, WINDY, HAZY, FAIR};
	public static enum Rainy_wthr {THUNDERY, RAIN, SHOWERS};

	private int pm25; 
	private int psi;
	private int uvi;
	private String weather_str;
	private Weather weather;
	
	public WeatherInfo(int pm25, int psi, int uvi, String weather_str, Weather weather) {
		super();
		this.pm25 = pm25;
		this.psi = psi;
		this.uvi = uvi;
		this.weather_str = weather_str;
		this.weather = weather;
	}

	public int getPM25() {
		return pm25;
	}
	
	public int getPSI() {
		return psi;
	}
	
	public int getUVI() {
		return uvi;
	}
	
	public String getWeather_str() {
		return weather_str;
	}
	
	public Weather getWeather() {
		return weather;
	}
	
	
}
