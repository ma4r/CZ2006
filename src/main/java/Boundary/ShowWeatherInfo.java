package Boundary;

import Control.WarningLogic;
import Control.WeatherManager;
import Entity.Location;
import Entity.WeatherInfo;
import Error.RequestError;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ShowWeatherInfo {
    
    private WeatherInfo[] weather;

    public ShowWeatherInfo(Location start, Location end) throws IOException, RequestError {
        weather = WeatherManager.getInstance().requestWeatherInfo(start, end);
    }

    public List<TextFlow> weatherMsg() throws IOException {

        Text start = new Text();
        Text end = new Text();
        String border_style = 
    			"-fx-padding: 10;" + "-fx-border-style: solid inside;"
                + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;" + "-fx-border-color: black;";

        TextFlow warning_Start = new TextFlow();
        start.setText("Starting Location\n");
        start.setFont(Font.font("calibri", FontWeight.BOLD, FontPosture.REGULAR, 20));
        start.setLineSpacing(2);
        warning_Start.getChildren().add(start);
        WarningLogic wl_start = new WarningLogic(this.weather[0]);
        warning_Start.getChildren().add(wl_start.getWarning());
        warning_Start.setStyle(border_style);

        TextFlow warning_End = new TextFlow();
        end.setText("Destination\n");
        end.setFont(Font.font("calibri", FontWeight.BOLD, FontPosture.REGULAR, 20));
        end.setLineSpacing(2);
        warning_End.getChildren().add(end);
        WarningLogic wl_end = new WarningLogic(this.weather[1]);
        warning_End.getChildren().add(wl_end.getWarning());
        warning_End.setStyle(border_style);

        return Arrays.asList(warning_Start, warning_End);
        }

    public TextFlow reccMsg() throws IOException {
        WeatherInfo wthr = WeatherManager.getInstance().extract_wthr(this.weather);
        WarningLogic rc = new WarningLogic(wthr);
        TextFlow message = new TextFlow();
        message.getChildren().add(rc.recommendation());
        return message;
        }

    }

