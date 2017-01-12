package jar;

import java.util.HashMap;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Paint;

public class EventHandlerController {

	@FXML
	private TextField matriculaTextField;

	@FXML
	private Label retornoLabel;

	private CatracaController catraca = new CatracaController();

	private String mensagem = "";
	private String color = "";

	@FXML
	private void handleKeyPressed(KeyEvent event) {
		if (event.getCode().equals(KeyCode.ENTER)) {
			try {

				String linhaDigitavel = matriculaTextField.getText();
				HashMap<String, Object> retorno = new HashMap<String, Object>();
				retorno = catraca.novoEvento(linhaDigitavel);

				if (!retorno.isEmpty()) {
					mensagem = (String) retorno.get("mensagem");
					String status = (String) retorno.get("status");
					if (status == null) {
						color = "#FF0000"; // vermelho
					} else if (status.equals("NOK")) {
						color = "#FF8000"; // laranja
					} else {
						color = "#00B300"; //verde
					}

					// labelSetText(mensagem);
				} else {
					mensagem = "Erro ao reproduzir mensagem, verifique o log da aplicação para mais informações";
					color = "#FF0000"; // vermelho
				}
			} catch (Exception e) {
				mensagem = "Houve um erro no processamento, tente novamente ou entre em contato com a Equipe do Futuro-Alternativo";
				color = "#FF0000"; // vermelho
			} finally {
				Task<Void> clearTextFieldTask = new Task<Void>() {
					@Override
					protected Void call() throws Exception {
						Thread.sleep(10);
						Platform.runLater(new Runnable() {
							public void run() {
								matriculaTextField.clear();
							}
						});

						return null;
					}
				};
				Thread textFieldThread = new Thread(clearTextFieldTask);
				textFieldThread.setDaemon(true);
				textFieldThread.start();

				Task<Void> labelSetTextTask = new Task<Void>() {
					@Override
					protected Void call() throws Exception {
						Thread.sleep(10);
						Platform.runLater(new Runnable() {
							public void run() {
								retornoLabel.setText(mensagem);
							}

						});

						return null;
					}
				};
				Thread labelThread = new Thread(labelSetTextTask);
				labelThread.setDaemon(true);
				labelThread.start();

				Task<Void> labelSetTextFillTask = new Task<Void>() {
					@Override
					protected Void call() throws Exception {
						Thread.sleep(10);
						Platform.runLater(new Runnable() {
							public void run() {
								retornoLabel.setTextFill(Paint.valueOf(color));
								java.awt.Toolkit.getDefaultToolkit().beep();
							}
						});
						return null;
					}
				};
				Thread textFillThread = new Thread(labelSetTextFillTask);
				textFillThread.setDaemon(true);
				textFillThread.start();
			}
		}
	}

	/**
	 * @return the matriculaTextField
	 */
	public TextField getMatriculaTextField() {
		return matriculaTextField;
	}

	/**
	 * @param matriculaTextField
	 *            the matriculaTextField to set
	 */
	public void setMatriculaTextField(TextField matriculaTextField) {
		this.matriculaTextField = matriculaTextField;
	}

	/**
	 * @return the catraca
	 */
	public CatracaController getCatraca() {
		return catraca;
	}

}