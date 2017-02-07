package jar;

import java.io.IOException;
import java.util.Calendar;

import org.apache.log4j.Logger;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CatracaView extends Application {
	static Logger LOGGER = Logger.getLogger(CatracaView.class);
	public static String HORA_ENTRADA;
	public static String HORA_SAIDA;

	public static void main(String[] args) {
		/**System.out.println("#################################################################################\n"
				+ "####     Sistema catraca FUTURO PRE-VESTIBULAR ALTERNATIVO						####\n"
				+ "####     								 										####\n"
				+ "####        CONFIGURAÇÕES DE HORÁRIOS PARA O DIA 29/10/2016						####\n"
				+ "####     								 										####\n"
				+ "## 	  As configurações inseridas aqui serão refletidas nos horários de entrada  ##\n"
				+ "## e saída dos alunos, preencha com cuidado.  	 								##\n"
				+ "#################################################################################");

		System.out.println("Horário de entrada (HH:mm:ss): ");
		Scanner s = new Scanner(System.in);
		HORA_ENTRADA = s.nextLine();

		System.out.println("Horário de Saída (HH:mm:ss): ");
		HORA_SAIDA = s.nextLine();
**/
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			LOGGER.info("=== Inicializando aplicacao... ===");
			LOGGER.info("CALENDAR - getTime " + Calendar.getInstance().getTime());
			primaryStage.setTitle("Futuro Pré-vestibular - Catraca");
			GridPane grid = new GridPane();
			grid.setAlignment(Pos.CENTER);
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(25, 25, 25, 25));

			Scene scene_input = new Scene(grid, 500, 300);
			primaryStage.setScene(scene_input);

			Text scenetitle = new Text("Configurações de inicialização da catraca");
			scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
			grid.add(scenetitle, 0, 0, 2, 1);

			Label horaEntrada = new Label("Hora Entrada (HH:mm):");
			grid.add(horaEntrada, 0, 1);

			TextField entradaTextField = new TextField();
			grid.add(entradaTextField, 1, 1);

			Label horaSaida = new Label("Hora Saída (HH:mm): ");
			grid.add(horaSaida, 0, 2);

			TextField saidaTextField = new TextField();
			grid.add(saidaTextField, 1, 2);

			Button btn = new Button("Começar");
			HBox hbBtn = new HBox(10);
			hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
			hbBtn.getChildren().add(btn);
			grid.add(hbBtn, 1, 4);

			btn.defaultButtonProperty().bind(btn.focusedProperty());
	        btn.setOnAction(new EventHandler<ActionEvent>() {

	    	    @Override
	    	    public void handle(ActionEvent e) {
	    	        HORA_ENTRADA = entradaTextField.getText();
	    	    	HORA_SAIDA = saidaTextField.getText();
	    	    	AnchorPane root;

					try {

						root = (AnchorPane) FXMLLoader.load(getClass().getResource("/fxml/CatracaView.fxml"));
						Scene scene = new Scene(root, 600, 400);
		    			primaryStage.setScene(scene);
		    			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/catraca.jpg")));
		    			primaryStage.show();
					} catch (IOException e1) {
						LOGGER.error("Erro ao inicializar", e1);
						System.exit(1);
					}
	    	    }
	    	});
	        primaryStage.show();

			LOGGER.info("=== Aplicacao pronta... ===");
		} catch (Exception e) {
			LOGGER.error("Erro ao inicializar", e);
			System.exit(1);
		}
	}
}
