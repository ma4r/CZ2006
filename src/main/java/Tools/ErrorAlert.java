package Tools;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ErrorAlert {

	public static void errorAlert(String arg) {
		
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setContentText(arg);
		alert.showAndWait();
		
	}
	
}
