module Cadastro {
	exports cadastro.controller.cadastros;
	exports cadastro.controller.frame;
	exports cadastro.controller.lista;
	exports cadastro.controller.pesquisas;
	
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
	requires org.kordamp.ikonli.javafx;
	requires org.kordamp.ikonli.fontawesome5;
	requires java.persistence;

	/* Faz a abertura dos pacotes do javafx para ser utilizado nas clases */
	opens cadastro.controller.cadastros to javafx.fxml;
	opens cadastro.controller.pesquisas to javafx.fxml;
	opens cadastro.controller.frame to javafx.fxml;
	opens cadastro.controller.lista to javafx.fxml;
	opens cadastro.controller.componente to javafx.fxml;
}