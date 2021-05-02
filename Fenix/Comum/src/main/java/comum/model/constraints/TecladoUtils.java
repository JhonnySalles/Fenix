package comum.model.constraints;

import com.jfoenix.controls.JFXAutoCompletePopup;
import com.jfoenix.controls.JFXTextField;

import comum.model.utils.Utils;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class TecladoUtils {

	public static void onTextEditAutoComplete(JFXTextField textField, String[] sugestoes) {
		JFXAutoCompletePopup<String> autoCompletePopup = new JFXAutoCompletePopup<String>();
		autoCompletePopup.getSuggestions().addAll(sugestoes);

		autoCompletePopup.setSelectionHandler(event -> {
			textField.setText(event.getObject());
		});

		textField.textProperty().addListener(observable -> {
			autoCompletePopup.filter(string -> string.toLowerCase().contains(textField.getText().toLowerCase()));
			if (autoCompletePopup.getFilteredSuggestions().isEmpty() || textField.getText().isEmpty())
				autoCompletePopup.hide();
			else
				autoCompletePopup.show(textField);
		});

	}

	public static void onEnterConfigureTab(TextField textField) {
		textField.setOnKeyPressed(e -> {
			if (e.getCode().toString().equals("ENTER"))
				Utils.clickTab();
		});
	}

	public static <T> void onEnterConfigureTab(ComboBox<T> comboBox) {
		comboBox.setOnKeyPressed(e -> {
			if (e.getCode().toString().equals("ENTER"))
				Utils.clickTab();
		});
	}

}
