package comum.model.validator;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.validation.base.ValidatorBase;

import comum.model.enums.TipoPessoa;
import javafx.beans.DefaultProperty;
import javafx.scene.control.TextInputControl;

@DefaultProperty(value = "icon")
public class ValidateRazaoSocial extends ValidatorBase {

	private JFXComboBox<TipoPessoa> cbTipoPessoa;

	public ValidateRazaoSocial(JFXComboBox<TipoPessoa> comboBox, String message) {
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
					&& (textField.getText() == null || textField.getText().isEmpty()))
				hasErrors.set(true);
			else
				hasErrors.set(false);
		}
	}

}
