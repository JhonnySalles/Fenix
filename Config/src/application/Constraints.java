package application;

import java.util.regex.Pattern;

import javafx.scene.control.TextField;

/*		Constrants s�o limitadores	*/
public class Constraints {
	
	// Funcao pater realiza validacao na forma de expressao.
	public static Boolean validaIp(TextField txt) {
		Boolean result = false;
		Pattern p = Pattern.compile("^"
                + "(([a-zA-Z]|[a-zA-Z][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z]|[A-Za-z][A-Za-z0-9\\-]*[A-Za-z0-9])" // Domain name
                + "|"
                + "localhost" // localhost
                + "|"
                + "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)(\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)){3}$"); // Ip
		if (p.matcher(txt.textProperty().get().toString()).matches()) {
			result = true;
		}
		
		return result;
	}
	
	/*	Fun��o para que seja validada a porta	*/
	public static void setTxtFieldPort(TextField txt) {
		txt.textProperty().addListener((obs, oldValue, newValue) -> { // O obs � uma referencia para o controlador.
			if(newValue != null && (!newValue.matches("\\d*") || newValue.length() > 6)) { // A express�o serve para validar somente n�meros
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
