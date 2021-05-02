package comum.model.utils;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

import comum.model.messages.Mensagens;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.robot.Robot;
import javafx.stage.FileChooser;

/**
 * <p>
 * Classe responssável por conter funções uteis do sistema.
 * </p>
 * 
 * @author Jhonny de Salles Noschang
 */
public class Utils {

	public static String getFileExtension(File file) {
		String name = file.getName();
		int lastIndexOf = name.lastIndexOf(".");
		if (lastIndexOf == -1) {
			return ""; // empty extension
		}
		return name.substring(lastIndexOf).replaceAll("\\.", "");
	}

	public static String removeMascaras(String texto) {
		return texto.replaceAll("[\\.\\-\\(\\)\\s\\/]", "");
	}

	public static void clickTab() {
		Robot robot = new Robot();
		robot.keyPress(KeyCode.TAB);
	}

	/**
	 * <p>
	 * Redimenciona a imagem informada para o tamanho mantendo a proporção.
	 * </p>
	 * 
	 * @param sourceImg Buffer da imagem a ser transformada.
	 * @param Width     Tamanho maximo de largura.
	 * @param Height    Tamanho maximo de altura.
	 * @return retorna a imagem redimencionada.
	 * 
	 * @author Jhonny de Salles Noschang
	 */
	public static BufferedImage resizeImage(BufferedImage origImage, Integer Width, Integer Height) {
		int type = origImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : origImage.getType();

		// *Special* if the width or height is 0 use image src dimensions
		if (Width == 0) {
			Width = origImage.getWidth();
		}
		if (Height == 0) {
			Height = origImage.getHeight();
		}

		int fHeight = Height;
		int fWidth = Width;

		// Work out the resized width/height
		if (origImage.getHeight() > Height || origImage.getWidth() > Width) {
			fHeight = Height;
			int wid = Width;
			float sum = (float) origImage.getWidth() / (float) origImage.getHeight();
			fWidth = Math.round(fHeight * sum);

			if (fWidth > wid) {
				// rezise again for the width this time
				fHeight = Math.round(wid / sum);
				fWidth = wid;
			}
		}

		BufferedImage resizedImage = new BufferedImage(fWidth, fHeight, type);
		Graphics2D g = resizedImage.createGraphics();
		g.setComposite(AlphaComposite.Src);

		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g.drawImage(origImage, 0, 0, fWidth, fHeight, null);
		g.dispose();

		return resizedImage;
	}

	// Redimenciona a imagem para o tab pane
	public static ImageView resizeImageTab(InputStream inputStream) {
		if (inputStream == null)
			return null;

		Image img = new Image(inputStream);
		ImageView imageView = new ImageView();

		imageView.setFitHeight(16);
		imageView.setFitWidth(16);
		imageView.setImage(img);
		return imageView;
	}

	/**
	 * <p>
	 * Função para obter um tom de contraste de uma cor hexadecimal, utilizado
	 * principalmente em contraste de texto.
	 * </p>
	 * 
	 * @param colorHexa String da cor informada, aceita-se uma cor no formato web.
	 * @return retorna uma cor branca ou preta (Color.BLACK ou Color.WHITE).
	 * 
	 * @author Jhonny de Salles Noschang
	 */
	public static Color getContrastColor(String colorHexa) {
		Color cor = Color.web(colorHexa);
		double y = ((cor.getRed() * 76245) + (cor.getGreen() * 149685) + (cor.getBlue() * 29070)) / 1000;
		return y >= 128 ? Color.BLACK : Color.WHITE;
	}

	/**
	 * <p>
	 * Função para obter um tom de contraste de uma cor hexadecimal, utilizado
	 * principalmente em contraste de texto.
	 * </p>
	 * 
	 * @param colorHexa String da cor informada, aceita-se uma cor no formato web.
	 * @return retorna uma cor branca ou preta ("#000000" ou "#ffffff").
	 * 
	 * @author Jhonny de Salles Noschang
	 */
	public static String getContrastHexa(String colorHexa) {
		Color cor = Color.web(colorHexa);
		double y = ((cor.getRed() * 76245) + (cor.getGreen() * 149685) + (cor.getBlue() * 29070)) / 1000;
		return y >= 128 ? "#000000" : "#ffffff";
	}

	/**
	 * <p>
	 * Função padrão para obter um determinado id em um texto com tag. Utilizado
	 * principalmente para o ARRASTA e SOLTA.
	 * </p>
	 * 
	 * <p>
	 * <b>Exemplo:</b> Tag: 'idText'. Texto: 'idText2:1-idText:2'. Separador: '-';
	 * </p>
	 * 
	 * <p>
	 * <b>Retorno:</b> 2;
	 * </p>
	 * 
	 * @param tag    Tag onde se encontra o valor a ser buscado.
	 * @param texto  Texto que contém a tag solicitada.
	 * @param Height Separador opciona, caso esteja vazio utilizará o traço (-) como
	 *               padrão.
	 * @return Retorna um <b>Long</b> do id que se encontra afrente da tag.
	 * 
	 * @author Jhonny de Salles Noschang
	 */
	public static Long getId(String tag, String texto, String separador) {
		if (texto.isEmpty() || tag.isEmpty())
			throw new IllegalArgumentException("Tag ou texto vazios.");

		if (!texto.contains(tag))
			throw new IllegalArgumentException("O texto não contem a tag informada.");

		if (!separador.isEmpty() && texto.contains(separador))
			return Long.valueOf(texto.substring(texto.indexOf(tag) + tag.length(), texto.indexOf(separador)).trim());
		else
			return Long.valueOf(texto.substring(texto.indexOf(tag) + tag.length()).trim());
	}

	private static String format(double val) {
		String in = Integer.toHexString((int) Math.round(val * 255));
		return in.length() == 1 ? "0" + in : in;
	}

	/**
	 * <p>
	 * Função que faz a conversão do tipo <b>Color</b> para o valor hexadecimal.
	 * </p>
	 * 
	 * 
	 * @param value Variável do tipo <b>Color</b>.
	 * @return Retorna uma <b>String</b> em hexadecimal da cor informada.
	 * 
	 * @author Jhonny de Salles Noschang
	 */
	public static String colorToHexString(Color value) {
		return "#" + (format(value.getRed()) + format(value.getGreen()) + format(value.getBlue())
				+ format(value.getOpacity())).toUpperCase();
	}
	
	/**
	 * <p>
	 * Função para abrir a tela de abertura do windows para escolher uma imagem.
	 * </p>
	 * 
	 * @return Retorna um <b>File</b> contendo as propriedades do caminho da imagem
	 *         ou null se não for escolhido nenhuma imagem.
	 * 
	 * @author Jhonny de Salles Noschang
	 */
	public static File procurarImagem() {
		return procurarImagem("", "");
	}

	/**
	 * <p>
	 * Função para abrir a tela de abertura do windows para escolher uma imagem.
	 * </p>
	 * 
	 * 
	 * @param caminhoInicial Campo opcional com o caminho inicial da tela.
	 * @param nomeInicial    Campo opcional com o nome do arquivo inicial.
	 * @return Retorna um <b>File</b> contendo as propriedades do caminho da imagem
	 *         ou null se não for escolhido nenhuma imagem.
	 * 
	 * @author Jhonny de Salles Noschang
	 */
	public static File procurarImagem(String caminhoInicial, String nomeInicial) {
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Imagens", "*.png", "*.jpg");
		FileChooser fileChooser = new FileChooser();

		if (!caminhoInicial.isEmpty())
			fileChooser.setInitialDirectory(new File(caminhoInicial));

		if (!nomeInicial.isEmpty())
			fileChooser.setInitialFileName(nomeInicial);

		fileChooser.getExtensionFilters().add(extFilter);
		fileChooser.setTitle(Mensagens.IMG_SELECIONE);
		return fileChooser.showOpenDialog(null);
	}
}
