package Control;

import Entity.Profile;
import Entity.WeatherInfo;
import Entity.WeatherInfo.Weather;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;


public final class WarningLogic {
	
    private static Profile profile;
    private static WeatherInfo weather;


    public WarningLogic(WeatherInfo wthr) throws IOException {
        profile = ProfileManager.getProfile();
        weather = wthr;
    }

    public TextFlow getWarning(){
        TextFlow warning = new TextFlow();
        warning.getChildren().add(checkUV());
        warning.getChildren().add(checkPM2_5());
        warning.getChildren().add(checkPSI());
        warning.getChildren().add(checkWeather());

        return warning;
    }

    private static Text checkUV(){
        Text s = new Text();
        if (weather.getUVI() == -1)
        	s.setText("UV index unavailable\n");
        else
        	s.setText("UV index is at: "+weather.getUVI()+"\n");
        if(profile.isNotifyUVI()){
            if(weather.getUVI() >= profile.getUVI()){
                s.setFill(Color.RED);
            }
        }
        return s;
    }

    private static Text checkPM2_5(){
        Text s = new Text();
        if (weather.getPM25() == -1)
        	s.setText("PM2.5 value is unavailable\n");
        else
        	s.setText("PM2.5 value is at: "+weather.getPM25()+" \n");
        if(profile.isNotifyPM25()) {
            if (weather.getPM25() >= profile.getPM25()) {
                s.setFill(Color.RED);
            }
        }
        return s;
    }

    private static Text checkPSI(){
        Text s = new Text();
        if (weather.getPSI() == -1)
        	s.setText("PSI value is unavailable\n");
        else
        	s.setText("PSI value is at: "+weather.getPSI()+"\n");
        if(profile.isNotifyPSI()) {
            if (weather.getPSI() >= profile.getPSI()) {
                s.setFill(Color.RED);
            }
        }
        return s;
    }

    private static Text checkWeather(){
        Text s = new Text();
        if (weather.getWeather() == Weather.ALL)
        	s.setText("Weather information unavailable\n");
        else
        	s.setText("It is currently: "+weather.getWeather_str()+"\n");
        if(profile.isNotifyWeather()){
            if(profile.getWeather().contains(weather.getWeather())){
                s.setFill(Color.RED);
            }
        }
        return s;
    }
    
    public TextFlow recommendation(){
        TextFlow rcmd = new TextFlow();
        int psi = weather.getPSI();
        int pm25 = weather.getPM25();
        int uvi = weather.getUVI();
        String wthr_cond = weather.getWeather_str();

        if ((wthr_cond.equals(WeatherInfo.Weather.RAIN.toString()))
        |(wthr_cond.equals(WeatherInfo.Weather.THUNDERY.toString()))
        |(wthr_cond.equals(WeatherInfo.Weather.SHOWERS.toString()))){
            Text s = new Text();
            s.setText("Bring your umbrella/raincoat\n");
            rcmd.getChildren().add(s);
        }

        //for asthmatic user
        if(profile.getHealthCondition().contains(Profile.Health.ASTHMA)) {
            Text s = new Text();
            Text psi_txt = new Text();
            Text pm25_txt = new Text();
            s.setText("Always keep inhaler within your reach\n");
            rcmd.getChildren().add(s);

            if (psi > 101 & psi < 150) {
                psi_txt.setText("Value of PSI is unhealthy for sensitive group\n");
                rcmd.getChildren().add(psi_txt);
            }

            if (pm25 > 50 & pm25 < 100) {
                pm25_txt.setText("Value of PM2.5 is unhealthy for sensitive group\n");
                rcmd.getChildren().add(pm25_txt);
            }
        }
        //for skin cancer user
        if(profile.getHealthCondition().contains(Profile.Health.SKIN_CANCER)) {
            Text s = new Text();
            s.setText("Reduce exposure to sunlight\n");
            rcmd.getChildren().add(s);

            if (uvi > 3 & uvi < 7) {
                Text uvi_txt = new Text();
                uvi_txt.setText("UVI level might have risk of harm based on your health condition\n");
                rcmd.getChildren().add(uvi_txt);
            }

        }
        //for normal users
        if (pm25 >= 100) {
            {   Text pm25_txt = new Text();
                pm25_txt.setText("Value of PM2.5 is hazardous!!\n");
                rcmd.getChildren().add(pm25_txt);
            }
            if (psi >= 150)
            {
                Text psi_txt = new Text();
                psi_txt.setText("Value of PSI is hazardous!!\n");
                rcmd.getChildren().add(psi_txt);
            }
            if (uvi >=7){
                Text uvi_txt = new Text();
                uvi_txt.setText("Value of UVI is hazardous!!\n");
                rcmd.getChildren().add(uvi_txt);
            }
        }

        if (psi >= 150 | pm25 >= 100){
            Text psi_txt = new Text();
            psi_txt.setText("Avoid/Reduce outdoor activities\nWear face mask when going outdoor\n");
            rcmd.getChildren().add(psi_txt);
        }
        if (uvi >= 7){
            Text uvi_txt = new Text();
            uvi_txt.setText("Apply broad spectrum SPF 30+ sunscreen every 2 hours\n" +
                    "If outdoors, seek shade and wear Sun protective clothing, a wide-brimmed hat, and UV-blocking sunglasses.\n");
            rcmd.getChildren().add(uvi_txt);
        }
        return rcmd;
    }
}

