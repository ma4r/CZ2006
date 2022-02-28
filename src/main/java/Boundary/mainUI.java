package Boundary;

import Control.RouteManager;
import Entity.Location;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * The mainUI class creates a {@code Scene} that the application will first
 * set its {@code Stage} to.
 * <p>
 * The {@code Scene} contains several JavaFX elements that will 
 * be useful to the user.
 * <p>
 * The user can enter his/her start and destination addresses into their 
 * {@code TextField} and find the route between the two addresses in
 * the routeUI {@code Scene}.
 * <p>
 * The user can also choose to view details about the saved {@code Profile}
 * in the profileUI {@code Scene} through the profile {@code Button}.
 *
 */
public class mainUI {
	
	/**
	 * Saves the addresses of the user's starting point and destination.
	 */
	public static String startPoint, endPoint;
	
	/**
	 * Saves the latitude and longitude coordinates of the user's starting 
	 * point and destination.
	 */
	public static Location start, end;
	
	/**
	 * Returns the {@code Scene} containing the JavaFX elements
	 * to be displayed by the primary stage initialized by the
	 * application.
	 * @param stage {@code Stage} initialized by the application, 
	 * 				cannot be {@code null}.
	 * @return 		{@code Scene} that the stage sets to.
	 */
	public static Scene create(Stage stage)
	{
    	//Main Page scene Element Initialization
    	GridPane mainGrid = new GridPane();
    	Button routeButton = new Button("Get route");
    	HBox routeHBtn = new HBox(10);
    	Button profileButton = new Button("Profile");
    	HBox profileHBtn = new HBox(10);
    	final Text actiontarget = new Text();
    	Text maintitle = new Text("Welcome");
    	Label startPointText = new Label("Start Point:");
    	TextField startTextField = new TextField();
    	Label endPointText = new Label("End Point:");
    	TextField endTextField = new TextField();
    	
    	//Variable Initialization
    	int row, column;
    	row = column = 0;

    	//Main Page
    	
    	//Scene Element Properties
    	mainGrid.setAlignment(Pos.CENTER);
    	mainGrid.setHgap(10);
    	mainGrid.setVgap(10);
    	mainGrid.setPadding(new Insets(25, 25, 25, 25));
    	routeHBtn.setAlignment(Pos.BOTTOM_RIGHT);
    	routeHBtn.getChildren().add(routeButton);
    	profileHBtn.setAlignment(Pos.TOP_RIGHT);
    	profileHBtn.getChildren().add(profileButton);
    	maintitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
    	
    	//Adding elements to GridPane
    	mainGrid.add(routeHBtn, column+1, row+5);
    	mainGrid.add(profileHBtn, column+1, row);
    	mainGrid.add(actiontarget, column, row+6, 2, 1);    	
    	mainGrid.add(maintitle, column, row+2, 2, 1);    	
    	mainGrid.add(startPointText, column, row+3);    	
    	mainGrid.add(startTextField, column+1, row+3);    	
    	mainGrid.add(endPointText, column, row+4);    	
    	mainGrid.add(endTextField, column+1, row+4);
    	
    	//Element Logic
    	routeButton.setOnAction(new EventHandler<ActionEvent>() {
    		public void handle(ActionEvent args) {
    			if (startTextField.getText().trim().isEmpty() | endTextField.getText().trim().isEmpty()) {
    				actiontarget.setFill(Color.FIREBRICK);
    				actiontarget.setText("Start and end points cannot be empty!");
    			}
    			else if (startTextField.getText().equalsIgnoreCase(endTextField.getText())) {
    				actiontarget.setFill(Color.FIREBRICK);
    				actiontarget.setText("Start and end points cannot be the same!");
    			}
    			else {
    				startPoint = '"' + startTextField.getText() + '"';
					start = RouteManager.getLatLongPositions(startTextField.getText());
					if (start.getLatitude() == 0 | start.getLongitude() == 0) {
						actiontarget.setFill(Color.FIREBRICK);
						actiontarget.setText("Unable to get start point!");
						return;
					}
					
        			endPoint = '"' + endTextField.getText() + '"';
					end = RouteManager.getLatLongPositions(endTextField.getText());
					if (end.getLatitude() == 0 | end.getLongitude() == 0) {
						actiontarget.setFill(Color.FIREBRICK);
						actiontarget.setText("Unable to get end point!");
						return;
					}
					
					stage.setScene(routeUI.create(stage));
				}
    		}
    	});
    	profileButton.setOnAction(e -> stage.setScene(profileUI.create(stage)));
    	
    	return new Scene(mainGrid, 300, 400);
	}
}
