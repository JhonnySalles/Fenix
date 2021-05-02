package comum.model.constraints;

import java.util.regex.Pattern;

import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;

import comum.model.enums.TipoPessoa;
import comum.model.mask.Mascaras;
import comum.model.validator.ValidateCNPJ;
import comum.model.validator.ValidateCPF;
import comum.model.validator.ValidateRazaoSocial;

/**
 * <p>
 * Classe que contem métodos estáticos para validações de campos field.
 * </p>
 * 
 * <p>
 * Por padrão esta classe possui diversos regex de validação e as validações dos
 * campos como <b>JFXTextField</b> e <b>JFXPasswordField</b> irá utilizar a
 * função já embutida <b>Validato</b>, onde pode-se utilizar essas funções aqui
 * dentro ou então criar um validador próprio que implementa o
 * <b>IFXValidatableControl </b>.
 * </p>
 * 
 * 
 * @author Jhonny de Salles Noschang
 */
public class Validadores {
	private static final String ERROR = "error";

	public static final String REGEX_APENAS_NUMEROS = "\\d*";
	public static final String REGEX_EMAIL = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
	public static final String REGEX_CNPJ = "^[0-9]{2}[.][0-9]{3}[.][0-9]{3}[/][0-9]{4}[-][0-9]{2}";
	public static final String REGEX_CPF = "^[0-9]{3}[.][0-9]{3}[.][0-9]{3}[-][0-9]{2}";
	public static final String REGEX_CEP = "^\\d{5}[-]\\d{3}";
	public static final String REGEX_TELEFONE = "^.([1-9]{2}.) (?:[2-8]|9[1-9])[0-9]{3}\\-[0-9]{4}$";
	public static final String REGEX_TELEFONE_INTERNACIONAL = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$";
	public static final String REGEX_ENDERECO_IP = "^" + "localhost" + "|"
			+ "(([a-zA-Z]|[a-zA-Z][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*([A-Za-z]|[A-Za-z][A-Za-z0-9\\-]*[A-Za-z0-9])" // Dominio
			+ "|" + "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)(\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)){3}$";

	private static final String OBRIGATORIO = "Campo obrigatório!";

	public static void setTextFieldRegexValidate(final JFXTextField textField, String regex, String menssagem,
			final Boolean validaVazio) {
		RegexValidator valid = new RegexValidator();

		valid.setRegexPattern(regex);
		valid.setMessage(menssagem);

		FontIcon warnIcon = new FontIcon(FontAwesomeSolid.EXCLAMATION_TRIANGLE);
		warnIcon.getStyleClass().add(ERROR);
		valid.setIcon(warnIcon);

		textField.setValidators(valid);
		textField.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (validaVazio) {
				if (!newVal)
					textField.validate();
			} else {
				if (!newVal && !textField.getText().isEmpty())
					textField.validate();
			}
		});
	}

	public static void setTextFieldEmailValidate(final JFXTextField textField, final Boolean validaVazio) {
		setTextFieldRegexValidate(textField, REGEX_EMAIL, "Email informado inválido!", validaVazio);
	}

	public static void setTextFieldTelefoneValidate(final JFXTextField textField, final Boolean validaVazio) {
		setTextFieldRegexValidate(textField, REGEX_TELEFONE, "Telefone informado inválido!", validaVazio);
	}

	public static void setTextFieldCNPJValidate(final JFXTextField textField, final Boolean validaVazio) {
		setTextFieldRegexValidate(textField, REGEX_CNPJ, "CNPJ informado inválido!", validaVazio);
	}

	public static void setTextFieldCNPJValidate(final JFXComboBox<TipoPessoa> comboBox, final JFXTextField textField) {
		ValidateCNPJ validator = new ValidateCNPJ(comboBox, OBRIGATORIO);
		FontIcon warnIcon = new FontIcon(FontAwesomeSolid.EXCLAMATION_TRIANGLE);
		warnIcon.getStyleClass().add("error");
		validator.setIcon(warnIcon);

		textField.getValidators().add(validator);
		textField.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal)
				textField.validate();
		});
	}

	public static void setTextFieldCPFValidate(final JFXTextField textField, final Boolean validaVazio) {
		setTextFieldRegexValidate(textField, REGEX_CPF, "CPF informado inválido!", validaVazio);
	}

	public static void setTextFieldCPFValidate(final JFXComboBox<TipoPessoa> comboBox, final JFXTextField textField) {
		ValidateCPF validator = new ValidateCPF(comboBox, OBRIGATORIO);
		FontIcon warnIcon = new FontIcon(FontAwesomeSolid.EXCLAMATION_TRIANGLE);
		warnIcon.getStyleClass().add("error");
		validator.setIcon(warnIcon);

		textField.getValidators().add(validator);
		textField.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal)
				textField.validate();
		});
	}

	public static void setTextFieldNumberValidate(final JFXTextField textField, final Boolean validaVazio) {
		setTextFieldRegexValidate(textField, REGEX_APENAS_NUMEROS, "Somente números são aceito!", validaVazio);
	}

	public static void setTextFieldNotEmpty(final JFXTextField textField) {
		RequiredFieldValidator validator = new RequiredFieldValidator();
		validator.setMessage("Campo obrigatório!");

		FontIcon warnIcon = new FontIcon(FontAwesomeSolid.EXCLAMATION_TRIANGLE);
		warnIcon.getStyleClass().add(ERROR);
		validator.setIcon(warnIcon);

		textField.getValidators().add(validator);
		textField.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal)
				textField.validate();
		});
	}

	public static void setPasswordFieldNotEmpty(final JFXPasswordField passwordField) {
		RequiredFieldValidator validator = new RequiredFieldValidator();
		validator.setMessage("Campo obrigatório!");

		FontIcon warnIcon = new FontIcon(FontAwesomeSolid.EXCLAMATION_TRIANGLE);
		warnIcon.getStyleClass().add(ERROR);
		validator.setIcon(warnIcon);

		passwordField.getValidators().add(validator);
		passwordField.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal)
				passwordField.validate();
		});
	}

	public static void setComboBoxNotEmpty(final JFXComboBox<?> comboBox) {
		RequiredFieldValidator validator = new RequiredFieldValidator();
		validator.setMessage("Campo obrigatório!");

		FontIcon warnIcon = new FontIcon(FontAwesomeSolid.EXCLAMATION_TRIANGLE);
		warnIcon.getStyleClass().add(ERROR);
		validator.setIcon(warnIcon);

		comboBox.getValidators().add(validator);
		comboBox.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal)
				comboBox.validate();
		});
	}

	public static void setTextFieldRazaoSocialValidate(final JFXComboBox<TipoPessoa> comboBox,
			final JFXTextField textField) {
		ValidateRazaoSocial validator = new ValidateRazaoSocial(comboBox, OBRIGATORIO);
		FontIcon warnIcon = new FontIcon(FontAwesomeSolid.EXCLAMATION_TRIANGLE);
		warnIcon.getStyleClass().add("error");
		validator.setIcon(warnIcon);

		textField.getValidators().add(validator);
		textField.focusedProperty().addListener((o, oldVal, newVal) -> {
			if (!newVal)
				textField.validate();
		});
	}

	public static Boolean validaCpf(final String texto) {
		return Pattern.compile(REGEX_CPF).matcher(Mascaras.formatCpf(texto)).matches();
	}

	public static Boolean validaCnpj(final String texto) {
		return Pattern.compile(REGEX_CNPJ).matcher(Mascaras.formatCnpj(texto)).matches();
	}

	public static Boolean validaCep(final String texto) {
		return Pattern.compile(REGEX_CEP).matcher(Mascaras.formatCep(texto)).matches();
	}

	public static Boolean validaEmail(final String texto) {
		return Pattern.compile(REGEX_EMAIL).matcher(texto).matches();
	}

	public static Boolean validaTelefone(final String texto) {
		return Pattern.compile(REGEX_TELEFONE).matcher(Mascaras.formatFone(texto)).matches();
	}

	public static Boolean validaTelefoneInternacional(final String texto) {
		return Pattern.compile(REGEX_TELEFONE_INTERNACIONAL).matcher(texto).matches();
	}

	// Funcao pater realiza validacao na forma de expressao.
	public static Boolean validaIp(final String texto) {
		return Pattern.compile(REGEX_ENDERECO_IP).matcher(texto).matches(); // Ip
	}

	// Funcao pater realiza validacao na forma de expressao.
	public static Boolean validaPorta(final String texto) {
		return Pattern.compile(REGEX_APENAS_NUMEROS).matcher(texto).matches(); // Ip
	}

	public static Boolean validaEAN(final String codigoBarra) {
		boolean status = false;
		if (!codigoBarra.isEmpty()) {
			// Remove nom numeric characters
			String treatedEanStr = codigoBarra.replaceAll("[^\\d.]", "");
			// Remove leading zeros
			String shortenedEanStr = treatedEanStr.replaceFirst("^0+(?!$)", "");
			// Validate length
			if (shortenedEanStr.length() > 7 && shortenedEanStr.length() < 14) {
				int sum = 0;
				String[] digits = treatedEanStr.split("");
				for (int i = 0; i < (digits.length - 1); i++)
					sum += Integer.parseInt(digits[i]) * ((i % 2 == 0) ? 1 : 3);

				int checksumDigit = (10 - (sum % 10)) % 10;
				status = (Integer.parseInt(digits[digits.length - 1]) == checksumDigit);
			}
		}
		return status;
	}

}
