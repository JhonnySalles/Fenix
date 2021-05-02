package comum.model.mask;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConverterMascaras {

	/**
	 * <p>
	 * Retorna um valor formatado como cnpj.
	 * </p>
	 * <p>
	 * Formatação: XX.XXX.XXX/XXXX-XX
	 * </p>
	 * 
	 * @param <b>String</b> que deseja extrair os dados formatados.
	 * @return Retorna o valor do campo em <b>String</b>
	 * 
	 */
	public static String formataCNPJ(String texto) {
		texto = texto.replaceAll("[^0-9]", "");
		texto = texto.replaceFirst("(\\d{2})(\\d)", "$1.$2");
		texto = texto.replaceFirst("(\\d{3})(\\d)", "$1.$2");
		texto = texto.replaceFirst("(\\d{3})(\\d)", "$1/$2");
		texto = texto.replaceFirst("(\\d{4})(\\d)", "$1-$2");
		return texto;
	}

	/**
	 * <p>
	 * Retorna um valor formatado como cpf.
	 * </p>
	 * <p>
	 * Formatação: XX.XXX.XXX-XX
	 * </p>
	 * 
	 * @param <b>String</b> que deseja extrair os dados formatados.
	 * @return Retorna o valor do campo em <b>String</b>
	 * 
	 */
	public static String formataCPF(String texto) {
		texto = texto.replaceAll("[^0-9]", "");
		texto = texto.replaceFirst("(\\d{3})(\\d)", "$1.$2");
		texto = texto.replaceFirst("(\\d{3})(\\d)", "$1.$2");
		texto = texto.replaceFirst("(\\d{3})(\\d)", "$1-$2");
		return texto;
	}

	/**
	 * <p>
	 * Retorna um valor formatado como telefone.
	 * </p>
	 * <p>
	 * Formatação: (XX) XXXX-XXXX ou (XX) XXXXX-XXXX
	 * </p>
	 * 
	 * @param <b>String</b> que deseja extrair os dados formatados.
	 * @return Retorna o valor do campo em <b>String</b>
	 * 
	 */
	public static String formataFone(String texto) {
		int tam = texto.length();
		texto = texto.replaceFirst("(\\d{2})(\\d)", "($1) $2");
		texto = texto.replaceFirst("(\\d{4})(\\d)", "$1-$2");
		if (tam > 10) {
			texto = texto.replaceAll("-", "");
			texto = texto.replaceFirst("(\\d{5})(\\d)", "$1-$2");
		}
		return texto;
	}

	/**
	 * <p>
	 * Retorna um valor monetário do fild.
	 * </p>
	 * 
	 * @param <b>TextField</b> que deseja extrair os dados.
	 * @return Retorna o valor do campo em <b>BigDecimal</b>
	 * 
	 */
	public static BigDecimal monetaryValueFromField(String valor) {
		if (valor.isEmpty()) {
			return null;
		}
		BigDecimal retorno = BigDecimal.ZERO;
		NumberFormat nf = NumberFormat.getNumberInstance();
		try {
			Number parsedNumber = nf.parse(valor);
			retorno = new BigDecimal(parsedNumber.toString());
		} catch (ParseException ex) {
			Logger.getLogger(Mascaras.class.getName()).log(Level.SEVERE, null, ex);
		}
		return retorno;
	}

}
