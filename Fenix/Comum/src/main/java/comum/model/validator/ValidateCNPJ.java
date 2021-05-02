package comum.model.validator;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.validation.base.ValidatorBase;

import comum.model.constraints.Validadores;
import comum.model.enums.TipoPessoa;
import javafx.beans.DefaultProperty;
import javafx.scene.control.TextInputControl;

@DefaultProperty(value = "icon")
public class ValidateCNPJ extends ValidatorBase {

	private JFXComboBox<TipoPessoa> cbTipoPessoa;

	public ValidateCNPJ(JFXComboBox<TipoPessoa> comboBox, String message) {
		super(message);
		cbTipoPessoa = comboBox;
	}

	@Override
	protected void eval() {
		evalTextInputField();
	}

	private void evalTextInputField() {
		TextInputControl textField = (TextInputControl) srcControl.get();
		if (cbTipoPessoa == null)
			hasErrors.set(true);
		else {
			if (!cbTipoPessoa.getValue().equals(TipoPessoa.FISICO)
					&& (textField.getText() == null || !Validadores.validaCnpj(textField.getText())))
				hasErrors.set(true);
			else
				hasErrors.set(false);
		}
	}

}
