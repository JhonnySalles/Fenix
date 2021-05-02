package comum.model.mask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.jfoenix.controls.JFXTextField;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Mascaras {

	/**
	 * <p>
	 * Função statica para que o text field ignore uma lista de botões.
	 * </p>
	 * <p>
	 * A função fica atrelada ao fild com um listener.
	 * </p>
	 * <p>
	 * São eles: F1, F2, F3, F4, F5, F6, F7, F8, F9, F10, F11, F12
	 * </p>
	 * 
	 * @param <b>TextField</b> que deseja ignorar as teclas.
	 * 
	 */
	public static void ignoreKeys(final TextField textField) {
		textField.addEventFilter(KeyEvent.KEY_PRESSED, (EventHandler<? super KeyEvent>) new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent keyEvent) {
				if (IGNORE_KEY_CODES.contains(keyEvent.getCode())) {
					keyEvent.consume();
				}
			}
		});
	}

	/**
	 * <p>
	 * Formata o textfield com a mascará de sérial.
	 * </p>
	 * <p>
	 * A função fica atrelada ao fild com um listener.
	 * </p>
	 * <p>
	 * São eles: XXXXX-XXXXX-XXXXX-XXXXX
	 * </p>
	 * 
	 * @param <b>TextField</b> que deseja formatar os dados.
	 * 
	 */
	public static void serialTextField(final TextField textField) {
		Mascaras.maxField(textField, 23);
		textField.lengthProperty()
				.addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
					if (newValue.intValue() < 24) {
						String value = textField.getText();
						value = value.replaceAll("[^\\w]", "");
						value = value.replaceFirst("(\\w{5})(\\w)", "$1-$2");
						value = value.replaceFirst("(\\w{5})\\-(\\w{5})(\\w)", "$1-$2-$3");
						value = value.replaceFirst("(\\w{5})\\-(\\w{5})\\-(\\w{5})(\\w)", "$1-$2-$3-$4");
						textField.setText(value.toUpperCase());

						if (textField.getText().length() != 0) {
							textField.positionCaret(textField.getText().length());
						}
					}
				});
	}

	/**
	 * <p>
	 * Formata o textfield com a mascará de data.
	 * </p>
	 * <p>
	 * A função fica atrelada ao fild com um listener.
	 * </p>
	 * <p>
	 * São eles: XX/XX/XX
	 * </p>
	 * 
	 * @param <b>TextField</b> que deseja formatar os dados.
	 * 
	 */
	public static void dateField(final TextField textField) {
		Mascaras.maxField(textField, 10);
		textField.lengthProperty().addListener((ChangeListener<? super Number>) new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if (newValue.intValue() < 11) {
					String value = textField.getText();
					value = value.replaceAll("[^0-9]", "");
					value = value.replaceFirst("(\\d{2})(\\d)", "$1/$2");
					value = value.replaceFirst("(\\d{2})\\/(\\d{2})(\\d)", "$1/$2/$3");
					textField.setText(value);

					if (textField.getText().length() != 0) {
						textField.positionCaret(textField.getText().length());
					}
				}
			}
		});
	}

	/**
	 * <p>
	 * Formata o textfield com a mascará de monetária.
	 * </p>
	 * <p>
	 * A função fica atrelada ao fild com um listener.
	 * </p>
	 * 
	 * @param <b>TextField</b> que deseja formatar os dados.
	 * 
	 */
	public static void monetaryField(final TextField textField) {
		textField.setAlignment(Pos.CENTER_RIGHT);
		textField.lengthProperty().addListener((observable, oldValue, newValue) -> {
			String value = textField.getText();
			value = value.replaceAll("[^0-9]", "");
			value = value.replaceAll("([0-9]{1})([0-9]{14})$", "$1.$2");
			value = value.replaceAll("([0-9]{1})([0-9]{11})$", "$1.$2");
			value = value.replaceAll("([0-9]{1})([0-9]{8})$", "$1.$2");
			value = value.replaceAll("([0-9]{1})([0-9]{5})$", "$1.$2");
			value = value.replaceAll("([0-9]{1})([0-9]{2})$", "$1,$2");
			textField.setText(value);

			if (textField.getText().length() != 0) {
				textField.positionCaret(textField.getText().length());
			}
		});
		textField.textProperty().addListener((ChangeListener<? super String>) new ChangeListener<String>() {

			public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
				if (newValue.length() > 17) {
					textField.setText(oldValue);
				}
			}
		});
		textField.focusedProperty().addListener((observableValue, aBoolean, fieldChange) -> {
			int length;
			if (!(fieldChange || (length = textField.getText().length()) <= 0 || length >= 3)) {
				textField.setText(textField.getText() + "00");
			}
		});
	}

	/**
	 * <p>
	 * Formata o textfield com a mascará de cpf ou cnpj, no qual verifica se o
	 * número de caracteres é menor que 14 para cpf.
	 * </p>
	 * <p>
	 * Formatação: XXX.XXX.XXX-XX ou XX.XXX.XXX/XXXX-XX
	 * </p>
	 * 
	 * @param <b>TextField</b> que deseja formatar os dados.
	 */
	public static void cpfCnpjField(JFXTextField textField) {
		Mascaras.maxField(textField, 18);
		textField.lengthProperty().addListener((observableValue, number, number2) -> {
			String value = textField.getText();
			if (number2.intValue() <= 14) {
				value = value.replaceAll("[^0-9]", "");
				value = value.replaceFirst("(\\d{3})(\\d)", "$1.$2");
				value = value.replaceFirst("(\\d{3})(\\d)", "$1.$2");
				value = value.replaceFirst("(\\d{3})(\\d)", "$1-$2");
			} else {
				value = value.replaceAll("[^0-9]", "");
				value = value.replaceFirst("(\\d{2})(\\d)", "$1.$2");
				value = value.replaceFirst("(\\d{3})(\\d)", "$1.$2");
				value = value.replaceFirst("(\\d{3})(\\d)", "$1/$2");
				value = value.replaceFirst("(\\d{4})(\\d)", "$1-$2");
			}
			textField.setText(value);

			if (textField.getText().length() != 0) {
				textField.positionCaret(textField.getText().length());
			}
		});
	}

	/**
	 * <p>
	 * Formata o textfield com a mascará de cep.
	 * </p>
	 * <p>
	 * Formatação: XX.XXX-XXX
	 * </p>
	 * 
	 * @param <b>TextField</b> que deseja formatar os dados.
	 */
	public static void cepField(TextField textField) {
		Mascaras.maxField(textField, 9);
		textField.lengthProperty().addListener((observableValue, number, number2) -> {
			textField.setText(formatCep(textField.getText()));

			if (textField.getText().length() != 0)
				textField.positionCaret(textField.getText().length());
		});
	}

	public static String formatCep(String cep) {
		String value = cep;
		value = value.replaceAll("[^0-9]", "");
		value = value.replaceFirst("(\\d{5})(\\d)", "$1-$2");
		return value;
	}

	/**
	 * <p>
	 * Formata o textfield com a mascará de telefone, podendo aceitar 8 ou 9
	 * números.
	 * </p>
	 * <p>
	 * Formatação: (XX) XXXX-XXXX ou (XX) XXXXX-XXXX
	 * </p>
	 * 
	 * @param <b>TextField</b> que deseja formatar os dados.
	 */
	public static void foneField(JFXTextField textField) {
		Mascaras.maxField(textField, 15);
		textField.lengthProperty().addListener((observableValue, number, number2) -> {
			Platform.runLater(() -> {
				textField.setText(formatFone(textField.getText()));

				if (textField.getText().length() != 0)
					textField.positionCaret(textField.getText().length());
			});

		});
	}

	public static String formatFone(String fone) {
		String value = fone;
		value = value.replaceAll("[^0-9]", "");
		int tam = value.length();
		value = value.replaceFirst("(\\d{2})(\\d)", "($1) $2");
		value = value.replaceFirst("(\\d{4})(\\d)", "$1-$2");
		if (tam > 10) {
			value = value.replaceAll("-", "");
			value = value.replaceFirst("(\\d{5})(\\d)", "$1-$2");
		}
		return value;
	}

	/**
	 * <p>
	 * Formata o textfield com a mascará de cpf.
	 * </p>
	 * <p>
	 * A função fica atrelada ao fild com um listener.
	 * </p>
	 * <p>
	 * Formatação: XXX.XXX.XXX-XX
	 * </p>
	 * 
	 * @param <b>TextField</b> que deseja formatar os dados.
	 * 
	 */
	public static void cpfField(JFXTextField textField) {
		Mascaras.maxField(textField, 14);
		textField.lengthProperty().addListener((observableValue, number, number2) -> {
			Platform.runLater(() -> {
				textField.setText(formatCpf(textField.getText()));

				if (textField.getText().length() != 0)
					textField.positionCaret(textField.getText().length());
			});
		});
	}

	public static String formatCpf(String cpf) {
		String value = cpf;
		value = value.replaceAll("[^0-9]", "");
		value = value.replaceFirst("(\\d{3})(\\d)", "$1.$2");
		value = value.replaceFirst("(\\d{3})(\\d)", "$1.$2");
		value = value.replaceFirst("(\\d{3})(\\d)", "$1-$2");
		return value;
	}

	/**
	 * <p>
	 * Formata o textfield com a mascará de cnpj.
	 * </p>
	 * <p>
	 * A função fica atrelada ao fild com um listener.
	 * </p>
	 * <p>
	 * São eles: XX.XXX.XXX/XXXX-XX
	 * </p>
	 * 
	 * @param <b>TextField</b> que deseja formatar os dados.
	 * 
	 */
	public static void cnpjField(JFXTextField textField) {
		Mascaras.maxField(textField, 18);
		textField.lengthProperty().addListener((observableValue, number, number2) -> {
			Platform.runLater(() -> {
				textField.setText(formatCnpj(textField.getText()));

				if (textField.getText().length() != 0)
					textField.positionCaret(textField.getText().length());
			});
		});
	}

	public static String formatCnpj(String cnpj) {
		String value = cnpj;
		value = value.replaceAll("[^0-9]", "");
		value = value.replaceFirst("(\\d{2})(\\d)", "$1.$2");
		value = value.replaceFirst("(\\d{3})(\\d)", "$1.$2");
		value = value.replaceFirst("(\\d{3})(\\d)", "$1/$2");
		value = value.replaceFirst("(\\d{4})(\\d)", "$1-$2");
		return value;
	}

	/**
	 * <p>
	 * Formata o com uma quantidade máxima de caracteres.
	 * </p>
	 * <p>
	 * A função fica atrelada ao fild com um listener.
	 * </p>
	 * 
	 * @param <b>TextField</b> que deseja formatar os dados.
	 * @param Tamanho          máximo de caracteres.
	 * 
	 */
	public static void maxField(TextField textField, Integer length) {
		textField.textProperty().addListener((observableValue, oldValue, newValue) -> {
			if (newValue == null || newValue.length() > length) {
				textField.setText(oldValue);
			}
		});
	}

	/**
	 * <p>
	 * Formata o textfield para aceitar apenas letras.
	 * </p>
	 * 
	 * @param <b>TextField</b> que deseja formatar os dados.
	 * @return retorna a string apenas com números.
	 * 
	 */
	public static String onlyAlfaNumericValue(TextField field) {
		String result = field.getText();
		if (result == null)
			return null;
		return result.replaceAll("[^0-9]", "");
	}

	private static List<KeyCode> IGNORE_KEY_CODES = new ArrayList<>();
	static {
		Collections.addAll(IGNORE_KEY_CODES, new KeyCode[] { KeyCode.F1, KeyCode.F2, KeyCode.F3, KeyCode.F4, KeyCode.F5,
				KeyCode.F6, KeyCode.F7, KeyCode.F8, KeyCode.F9, KeyCode.F10, KeyCode.F11, KeyCode.F12 });
	}

}
