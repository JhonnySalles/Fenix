package comum.model.animation;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class DashboardAnimation {
	static Timeline timeline;

	public static void abrirPaneBotao(SplitPane sp, AnchorPane ap) {
		if (timeline != null)
			timeline.stop();
		
		ap.setMaxWidth(150);
		BooleanProperty collapsed = new SimpleBooleanProperty();
		collapsed.bind(sp.getDividers().get(0).positionProperty().isEqualTo(0, 0.01));

		double target = collapsed.get() ? 0 : 1;
		KeyValue keyValue = new KeyValue(sp.getDividers().get(0).positionProperty(), target);
		timeline = new Timeline(new KeyFrame(Duration.millis(500), keyValue));
		timeline.setOnFinished(t -> {
			ap.setMinWidth(150);
		});
		timeline.setDelay(Duration.seconds(2));
		timeline.play();
	}

	public static void fecharPaneBotao(SplitPane sp, AnchorPane ap) {
		if (timeline != null)
			timeline.stop();

		ap.setMinWidth(47);
		BooleanProperty collapsed = new SimpleBooleanProperty();
		collapsed.bind(sp.getDividers().get(0).positionProperty().isEqualTo(0, 0.01));

		double target = collapsed.get() ? 1 : 0;
		KeyValue keyValue = new KeyValue(sp.getDividers().get(0).positionProperty(), target);
		timeline = new Timeline(new KeyFrame(Duration.millis(500), keyValue));
		timeline.setOnFinished(t -> {
			ap.setMaxWidth(47);
		});
		//timeline.setDelay(Duration.seconds(1));
		timeline.play();
	}
}
