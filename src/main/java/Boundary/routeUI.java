package Boundary;

import Error.RequestError;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.json.JSONException;
import org.openjfx.ce2006project_maven.App;

import java.io.IOException;

/**
 * The routeUI class creates a {@code Scene} for the {@code Stage} where
 * the route between the start and destination addresses is calculated by
 * the Google's Direction API, and displayed in a JavaFX {@code webview}.
 * <p>
 * Details about PM 2.5, PSI, UVI and Weather readings in the user determined
 * addresses and recommended actions to take should any of the readings
 * exceed any values set in the saved profile, will be available to the user 
 * in this scene.
 * <p>
 * The user is able to return to the mainUI {@code Scene} from here.
 */
public class routeUI {
	
	/**
	 * The JavaFX {@code WebEngine} non-visual object to load the html file
	 * containing the Google Directions API in JavaScript.
	 */
	public static WebEngine webengine;
	
	/**
	 * The JavaFX {@code WebView} component which displays the loaded html
	 * file.
	 */
	public static WebView webview;
	
	/**
	 * The {@code Scene} is created when the Route {@code Button} is pressed 
	 * and there are valid addresses entered in to their individual 
	 * {@code TextField}, calling this function.
	 * @param stage {@code Stage} initialized by the application.
	 * @return {@code Scene} that the stage sets to.
	 */
	public static Scene create(Stage stage)
	{
    	//Route Scene Element Initialization	
    	webview = new WebView();
    	webengine = webview.getEngine();
        webengine.getLoadWorker().stateProperty().addListener(
        		new ChangeListener<State>() {
        			@SuppressWarnings("rawtypes")
					public void changed(ObservableValue ov, State oldState, State newState) {
        				if (newState == State.SUCCEEDED) {
        					webengine.executeScript("addressInput(\"" + mainUI.start.getName() + "\", \"" + mainUI.end.getName() + "\")");
        				}
        			}
        		}
        		);
    	Button backButton2 = new Button("Back");
    	HBox backHBox2 = new HBox(10);
    	AnchorPane routePane = new AnchorPane();
    	HBox warningHBox = new HBox();
    	HBox reccHBox = new HBox();
    	VBox reccVBox = new VBox();
    	TextFlow reccMessage = new TextFlow();
    	
    	//Route Scene
        
        //Scene Element Properties
    	webview.setVisible(true);
    	webengine.setJavaScriptEnabled(true);
    	webengine.load(App.class.getResource("/googlemaps2.html").toExternalForm());
    	backHBox2.getChildren().add(backButton2);
    	backHBox2.setPadding(new Insets(10));
    	AnchorPane.setLeftAnchor(backHBox2, 0.0);
    	reccHBox.getChildren().add(reccVBox);
    	reccVBox.getChildren().add(reccMessage);
    	String style = "-fx-background-color: rgba(255, 255, 230, 1);";
    	String border_style = 
    			"-fx-padding: 10;" + "-fx-border-style: solid inside;"
                + "-fx-border-width: 2;" + "-fx-border-insets: 5;"
                + "-fx-border-radius: 5;" + "-fx-border-color: black;";
    	warningHBox.setStyle(style);
    	reccHBox.setStyle(style + border_style);
    	reccHBox.setMinWidth(270);
    	reccHBox.setMinHeight(150);
    	reccHBox.setPadding(new Insets(10));
    	AnchorPane.setLeftAnchor(warningHBox, 10.0);
    	AnchorPane.setBottomAnchor(warningHBox, 10.0);
    	AnchorPane.setTopAnchor(reccHBox, 0.0);
    	AnchorPane.setRightAnchor(reccHBox, 0.0);
    	
    	AnchorPane.setLeftAnchor(webview, 0.0);
    	AnchorPane.setBottomAnchor(webview, 0.0);
    	AnchorPane.setTopAnchor(webview, 0.0);
    	AnchorPane.setRightAnchor(webview, 0.0);
        
    	//Adding elements to grids
        routePane.getChildren().add(webview);
        routePane.getChildren().add(backHBox2);
        routePane.getChildren().add(warningHBox);
        routePane.getChildren().add(reccHBox);
               
        
        //Element Logics
        backButton2.setOnAction(e -> stage.setScene(mainUI.create(stage)));
        //warningMessage.setWrapText(true);
        //reccMessage.setWrapText(true);
		try {
			ShowWeatherInfo show_wthr = new ShowWeatherInfo(mainUI.start, mainUI.end);
			warningHBox.getChildren().addAll(show_wthr.weatherMsg());
			TextFlow recc = show_wthr.reccMsg();
			reccHBox.getChildren().add(recc);

		}
		catch (IOException | JSONException | RequestError e) {
			e.printStackTrace();
		}
		
    	return new Scene(routePane);
	}
}
