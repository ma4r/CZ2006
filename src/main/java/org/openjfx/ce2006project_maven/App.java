package org.openjfx.ce2006project_maven;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import Boundary.mainUI;

public class App extends Application{
	
    @Override
    public void start(@SuppressWarnings("exports") Stage primaryStage) throws Exception {
    	primaryStage.setTitle("CE2006 Application");  	
    	primaryStage.setScene(mainUI.create(primaryStage));
    	primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}