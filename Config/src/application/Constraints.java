package application;

import java.util.regex.Pattern;

import javafx.scene.control.TextField;

/*		Constrants s�o limitadores	*/
public class Constraints {
	
	// Funcao pater realiza validacao na forma de expressao.
	public static void validaIp(TextField txt) {
		Pattern p = Pattern.compile("^"
                + "((?!-)[A-Za-z0-9-]{1,63}(?<!-)\\.)+[A-Za-z]{2,6}" // Domain name
                + "|"
                + "localhost" // localhost
                + "|"
                + "(([0-9]{1,3}\\.){3})[0-9]{1,3}"); // Ip
		if (p.matcher(txt.textProperty().get().toString()).matches()) {
			txt.setStyle("-fx-border-color: green;");
		} else {
			txt.setStyle("-fx-border-color: red;");
		}
		
		if (txt.textProperty().get().toString().isEmpty()) {
			txt.setStyle("");
		}
		
	}
	
	// Funcao para validar um campo completo de um ip.
	public static void validaIpCompleto(TextField txt) {
		Pattern p = Pattern.compile("^"
                + "(((?!-)[A-Za-z0-9-]{1,63}(?<!-)\\.)+[A-Za-z]{2,6}" // Domain name
                + "|"
                + "localhost" // localhost
                + "|"
                + "(([0-9]{1,3}\\.){3})[0-9]{1,3})" // Ip
                + ":"
                + "[0-9]{1,5}$"); // Port
	}
	
	/*	Fun��o para que seja validada a porta	*/
	public static void setTxtFieldPort(TextField txt) {
		txt.textProperty().addListener((obs, oldValue, newValue) -> { // O obs � uma referencia para o controlador.
			if(newValue != null && newValue.matches("[0-9]{1,5}$")) { // A express�o serve para validar somente n�meros
				txt.setText(oldValue); //Caso seja digitado algo diferente ir� receber o valor anterior, no caso n�o ter� altera��o.
			}								// d representa digito, * representa qualquer quantidade.
		});
	}
	
	/*	Fun��o para que seja digitado apenas um limite de caracteres	*/
	public static void setTextFieldMaxLenght(TextField txt, int max) {
		txt.textProperty().addListener((obs, oldValue, newValue) -> {
			if(newValue != null && newValue.length() > max) {
				txt.setText(oldValue);
			}
		});
	}
}
