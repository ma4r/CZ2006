package Boundary;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import Control.ProfileManager;
import Entity.Profile;
import Entity.WeatherInfo;
import Entity.Profile.Health;
import Entity.WeatherInfo.Weather;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * The profileUI class creates a {@code Scene} for the {@code Stage}
 * where the user can view the details of the {@code Profile} that is saved
 * by the application.
 * <p>
 * The user will be able to return to the mainUI {@code Scene} from here.
 *
 */
public class profileUI {
	
	/**
	 * The {@code Scene} is created when the Profile {@code Button} is
	 * pressed, calling this function.
	 * @param stage {@code Stage} initialized by the application.
	 * @return {@code Scene} that the stage sets to.
	 */
	public static Scene create(Stage stage) {
		
		//Profile Page Scene Elements Initialization
		GridPane profileGrid = new GridPane();
		Button backButton = new Button("Back");
		HBox backHBtn = new HBox(10);
		TextField profileNameField = new TextField();
		MenuButton healthMenuButton = new MenuButton();
		Text text1 = new Text("Get Notification when:");
		Label profileLabel = new Label("Profile Name:");
		Label healthLabel = new Label("Health Condition:");
		CheckBox pmCheckBox = new CheckBox("PM 2.5 is above");
		CheckBox UVICheckBox = new CheckBox("UVI is above");
		CheckBox PSICheckBox = new CheckBox("PSI is above");
		CheckBox WeatherCheckBox = new CheckBox();
		TextField pmTextField = new TextField();
		TextField UVITextField = new TextField();
		TextField PSITextField = new TextField();
		MenuButton weatherMenuButton = new MenuButton();
		Button saveButton = new Button("Save");
		HBox saveHBtn = new HBox(10);
		final Text actiontarget2 = new Text();
		
		List<CheckMenuItem> healthCondition = new ArrayList<CheckMenuItem>();
		CheckMenuItem healthy = new CheckMenuItem(Health.HEALTHY.toString());
		for (Health h: Profile.Health.values()) {
			
			if (h != Health.HEALTHY) {
				
				CheckMenuItem item = new CheckMenuItem(h.toString());
				healthCondition.add(item);
				
				item.setOnAction(e -> {
					if (item.isSelected()) {
						healthy.setSelected(false);
					}
					List<String> selectedItems = healthMenuButton.getItems().stream()
				            .filter(i -> CheckMenuItem.class.isInstance(i) && CheckMenuItem.class.cast(i).isSelected())
				            .map(MenuItem::getText)
				            .collect(Collectors.toList());
					if (selectedItems.isEmpty())
						healthMenuButton.setText("Select conditions");
				    healthMenuButton.setText(String.join(", ", selectedItems));
				});
				
			}
			
			else {
				
				healthCondition.add(healthy);
				healthy.setOnAction(e -> {
					if (healthy.isSelected()) {
						for (CheckMenuItem hc: healthCondition) {
							if (hc.getText().compareTo(h.toString()) != 0) {
								hc.setSelected(false);
							}
						}
						healthMenuButton.setText(healthy.getText());
					}
					else
						healthMenuButton.setText("Select conditions");
				});
				
			}
		}
		
		List<CheckMenuItem> weatherCondition = new ArrayList<CheckMenuItem>();
		CheckMenuItem all = new CheckMenuItem(Weather.ALL.toString());
		for (Weather w: WeatherInfo.Weather.values()) {
			
			if (w != Weather.ALL) {
				
				CheckMenuItem item = new CheckMenuItem(w.toString());
				weatherCondition.add(item);
				
				item.setOnAction(e -> {
					if (item.isSelected())
						all.setSelected(false);
					List<String> selectedItems = weatherMenuButton.getItems().stream()
				            .filter(i -> CheckMenuItem.class.isInstance(i) && CheckMenuItem.class.cast(i).isSelected())
				            .map(MenuItem::getText)
				            .collect(Collectors.toList());
					if (selectedItems.isEmpty())
						weatherMenuButton.setText("Select conditions");
				    weatherMenuButton.setText(String.join(", ", selectedItems));
				});
				
			}
			
			else {
				
				weatherCondition.add(all);
				all.setOnAction(e -> {
					if (all.isSelected()) {
						for (CheckMenuItem wc: weatherCondition) {
							if (wc.getText().compareTo(w.toString()) != 0) {
								wc.setSelected(false);
							}
						}
						weatherMenuButton.setText(all.getText());
					}
					else
						weatherMenuButton.setText("Select conditions");
				});
				
			}
			
		}
	
		//Variable Initialization
		int row, column, columnwidth;
		row = column = 0;
		columnwidth = 100;
		
		//Profile Page
		
		//Element Properties
		profileGrid.setAlignment(Pos.TOP_LEFT);
		profileGrid.setHgap(10);
		profileGrid.setVgap(10);
		profileGrid.setPadding(new Insets(25, 25, 25, 25));
		profileGrid.getColumnConstraints().add(new ColumnConstraints(columnwidth));
		backHBtn.setAlignment(Pos.TOP_LEFT);
		backHBtn.getChildren().add(backButton);
		healthMenuButton.getItems().addAll(
				healthCondition
		);    	
		weatherMenuButton.getItems().addAll(
				weatherCondition
		);
		saveHBtn.setAlignment(Pos.BOTTOM_RIGHT);
		saveHBtn.getChildren().add(saveButton);
		
		//Adding elements onto the GridPane
		profileGrid.add(text1, column, row+3, 3, 1);
		profileGrid.add(backHBtn, column, row);
		profileGrid.add(profileLabel, column, row+1, 2, 1);
		profileGrid.add(profileNameField, column+1, row+1);
		profileGrid.add(healthLabel, column, row+2, 2, 1);
		profileGrid.add(healthMenuButton, column+1, row+2);
		profileGrid.add(pmCheckBox, column, row+4, 2, 1);
		profileGrid.add(UVICheckBox, column, row+5);
		profileGrid.add(PSICheckBox, column, row+6);
		profileGrid.add(WeatherCheckBox, column, row+7);
		profileGrid.add(pmTextField, column+1, row+4);
		profileGrid.add(UVITextField, column+1, row+5);
		profileGrid.add(PSITextField, column+1, row+6);
		profileGrid.add(weatherMenuButton, column+1, row+7);
		profileGrid.add(saveHBtn, column+1, row+8);
		profileGrid.add(actiontarget2, column, row+8, 2, 1);
	
		//Element Logics
		backButton.setOnAction(e -> stage.setScene(mainUI.create(stage)));
		saveButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent args) {
				if(profileNameField.getText().trim().isEmpty()) {
					actiontarget2.setFill(Color.FIREBRICK);
					actiontarget2.setText("Profile name cannot be empty!");
				}
				else if (pmCheckBox.isSelected() && !pmTextField.getText().matches("[0-9]+")) {
					actiontarget2.setFill(Color.FIREBRICK);
					actiontarget2.setText("Please enter values!");
				}
				else if (UVICheckBox.isSelected() && !UVITextField.getText().matches("[0-9]+")) {
					actiontarget2.setFill(Color.FIREBRICK);
					actiontarget2.setText("Please enter values!");
				}
				else if (PSICheckBox.isSelected() && !PSITextField.getText().matches("[0-9]+")) {
					actiontarget2.setFill(Color.FIREBRICK);
					actiontarget2.setText("Please enter values!");
				}
				else {
					boolean healthCheck = false;
					for (CheckMenuItem hc: healthCondition) {
						if (hc.isSelected()) {
							healthCheck = true;
							break;
						}
					}
					if (!healthCheck) {
						healthy.setSelected(true);
					}
					if (pmCheckBox.isSelected() && pmTextField.getText().trim().isEmpty()) {
						pmTextField.setText("150");
					}
					if (!pmCheckBox.isSelected()) {
						pmTextField.setText("150");
					}
					if (UVICheckBox.isSelected() && UVITextField.getText().trim().isEmpty()) {
						UVITextField.setText("6");
					}
					if (!UVICheckBox.isSelected()) {
						UVITextField.setText("6");
					}
					if (PSICheckBox.isSelected() && PSITextField.getText().trim().isEmpty()) {
						PSITextField.setText("100");
					}
					if (!PSICheckBox.isSelected()) {
						PSITextField.setText("100");
					}
					boolean weatherCheck = false;
					for (CheckMenuItem wc: weatherCondition) {
						if (wc.isSelected()) {
							weatherCheck = true;
							break;
						}
					}
					if (WeatherCheckBox.isSelected() && !weatherCheck) {
						all.setSelected(true);
					}
					
					List<String> healthList = new ArrayList<String>();
					for (CheckMenuItem hc: healthCondition) {
						if (hc.isSelected())
							healthList.add(hc.getText());
					}
					
					List<String> weatherList = new ArrayList<String>();
					for (CheckMenuItem wc: weatherCondition) {
						if (wc.isSelected())
							weatherList.add(wc.getText());
					}
					
					try {
						if(ProfileManager.saveProfile(profileNameField.getText(), healthList, pmCheckBox.isSelected(), UVICheckBox.isSelected(), PSICheckBox.isSelected(),
								WeatherCheckBox.isSelected(), Integer.parseInt(pmTextField.getText()), Integer.parseInt(UVITextField.getText()), Integer.parseInt(PSITextField.getText()),
								weatherList))
							actiontarget2.setText("Profile saved successfully");
						else {
							actiontarget2.setFill(Color.FIREBRICK);
							actiontarget2.setText("Unable to save profile");
						}
					} catch (NumberFormatException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		//Loading saved/default profile
		Profile profile = new Profile();
		
		try {
			profile = ProfileManager.getProfile();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		if(profile.getName() != null)
			profileNameField.setText(profile.getName());
		
		List<Health> healthCheck = profile.getHealthCondition();
		for (CheckMenuItem hc: healthCondition) {
			if (healthCheck.contains(Health.valueOf(hc.getText())))
				hc.setSelected(true);
		}
		
		List<String> selectedItems;
		selectedItems = healthMenuButton.getItems().stream()
	            .filter(i -> CheckMenuItem.class.isInstance(i) && CheckMenuItem.class.cast(i).isSelected())
	            .map(MenuItem::getText)
	            .collect(Collectors.toList());
		if (selectedItems.isEmpty())
			healthMenuButton.setText("Select conditions");
	    healthMenuButton.setText(String.join(", ", selectedItems));
	    

			pmCheckBox.setSelected(true);
		pmTextField.setText(Integer.toString(profile.getPM25()));
		if (profile.isNotifyUVI())
			UVICheckBox.setSelected(true);
		UVITextField.setText(Integer.toString(profile.getUVI()));
		if (profile.isNotifyPSI())
			PSICheckBox.setSelected(true);
		PSITextField.setText(Integer.toString(profile.getPSI()));
		if (profile.isNotifyWeather())
			WeatherCheckBox.setSelected(true);
		
		List<Weather> weatherCheck = profile.getWeather();
		for (CheckMenuItem wc: weatherCondition) {
			if (weatherCheck.contains(Weather.valueOf(wc.getText())))
				wc.setSelected(true);
		}
		
		selectedItems = weatherMenuButton.getItems().stream()
	            .filter(i -> CheckMenuItem.class.isInstance(i) && CheckMenuItem.class.cast(i).isSelected())
	            .map(MenuItem::getText)
	            .collect(Collectors.toList());
		if (selectedItems.isEmpty())
			weatherMenuButton.setText("Select conditions");
	    weatherMenuButton.setText(String.join(", ", selectedItems));
	    
		
		return new Scene(profileGrid, 300, 400);
		
		}
	}