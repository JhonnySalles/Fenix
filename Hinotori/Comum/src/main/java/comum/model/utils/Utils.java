package comum.model.utils;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.robot.Robot;

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

}
