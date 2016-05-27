package app.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CatracaView extends Application{

	@Override
	public void start(Stage primaryStage) {
		try {

			setUserAgentStylesheet(STYLESHEET_CASPIAN);
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("../resources/fxml/CatracaView.fxml"));
			Scene scene = new Scene(root,600,400);
			//scene.getStylesheets().add(getClass().getResource("../resources/fxml/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}



}
