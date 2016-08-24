package jar;

import org.apache.log4j.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CatracaView extends Application{
	static Logger LOGGER = Logger.getLogger(CatracaView.class);
	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) {
		try {
			LOGGER.info("=== Inicializando aplicacao... ===");
			//setUserAgentStylesheet(STYLESHEET_CASPIAN);
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/fxml/CatracaView.fxml"));
			Scene scene = new Scene(root,800,600);
			primaryStage.setScene(scene);
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/catraca.jpg")));
			primaryStage.show();
			LOGGER.info("=== Aplicacao pronta... ===");
		} catch(Exception e) {
			LOGGER.error("Erro ao inicializar", e);
			System.exit(1);
		}
	}
}
