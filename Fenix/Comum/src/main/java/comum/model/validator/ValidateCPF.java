package comum.model.validator;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.validation.base.ValidatorBase;

import comum.model.constraints.Validadores;
import comum.model.enums.TipoPessoa;
import javafx.beans.DefaultProperty;
import javafx.scene.control.TextInputControl;

@DefaultProperty(value = "icon")
public class ValidateCPF extends ValidatorBase {

	private JFXComboBox<TipoPessoa> cbTipoPessoa;

	public ValidateCPF(JFXComboBox<TipoPessoa> comboBox, String message) {
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
			if (!cbTipoPessoa.getValue().equals(TipoPessoa.JURIDICO)
					&& (textField.getText() == null || !Validadores.validaCpf(textField.getText())))
				hasErrors.set(true);
			else
				hasErrors.set(false);
		}
	}

}
