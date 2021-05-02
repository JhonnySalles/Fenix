module PDV {
	exports pdv;
	exports pdv.controller;
	exports pdv.controller.cadastros;
	exports pdv.controller.pesquisas;
	exports pdv.controller.metricas;

	requires transitive Comum;
	requires transitive Servidor;
	requires transitive javafx.fxml;
	requires transitive javafx.graphics;
	requires com.jfoenix;
	requires javafx.base;
	requires javafx.controls;
	requires java.desktop;
	requires org.controlsfx.controls;
	requires AnimateFX;
	requires java.sql;
	requires Cadastro;
	requires Loguin;

	/* Faz a abertura dos pacotes do javafx para ser utilizado nas clases */
	opens pdv.controller to javafx.fxml;
	opens pdv.controller.cadastros to javafx.fxml;
	opens pdv.controller.pesquisas to javafx.fxml;
	opens pdv.controller.metricas to javafx.fxml;
}