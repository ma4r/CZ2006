package Entity;

import java.util.ArrayList;
import java.util.List;

import Entity.WeatherInfo.Weather;

public class Profile {

	public static enum Health {HEALTHY, ASTHMA, SKIN_CANCER, OTHERS};
	
	private String name;
	private List<Health> healthCondition;
	private boolean notifyPM25;
	private boolean notifyUVI;
	private boolean notifyPSI;
	private boolean notifyWeather;
	private int PM25;
	private int UVI;
	private int PSI;
	private List<Weather> weather;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<Health> getHealthCondition() {
		return healthCondition;
	}

	public void setHealthCondition(List<Health> healthCondition) {
		this.healthCondition = healthCondition;
	}
	
	public void setHealthCondition(String[] healthCondition) {
		List<Health> h = new ArrayList<Health>();
		for (String s: healthCondition) {
			h.add(Health.valueOf(s));
		}
		this.healthCondition = h;
	}
	
	public boolean isNotifyPM25() {
		return notifyPM25;
	}

	public void setNotifyPM25(boolean notifyPM25) {
		this.notifyPM25 = notifyPM25;
	}

	public boolean isNotifyUVI() {
		return notifyUVI;
	}

	public void setNotifyUVI(boolean notifyUVI) {
		this.notifyUVI = notifyUVI;
	}

	public boolean isNotifyPSI() {
		return notifyPSI;
	}

	public void setNotifyPSI(boolean notifyPSI) {
		this.notifyPSI = notifyPSI;
	}

	public boolean isNotifyWeather() {
		return notifyWeather;
	}

	public void setNotifyWeather(boolean notifyWeather) {
		this.notifyWeather = notifyWeather;
	}

	public int getPM25() {
		return PM25;
	}

	public void setPM25(int pM25) {
		PM25 = pM25;
	}

	public int getUVI() {
		return UVI;
	}

	public void setUVI(int uVI) {
		UVI = uVI;
	}

	public int getPSI() {
		return PSI;
	}

	public void setPSI(int pSI) {
		PSI = pSI;
	}

	public List<Weather> getWeather() {
		return weather;
	}

	public void setWeather(List<Weather> weather) {
		this.weather = weather;
	}
	
	public void setWeather(String[] weather) {
		List<Weather> w = new ArrayList<Weather>();
		for (String s: weather) {
			w.add(Weather.valueOf(s));
		}
		this.weather = w;
	}
	
}
