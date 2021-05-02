/**
 * @author Jhonny
 *
 */
module Administrador {
	
	exports restaurante;
	exports restaurante.controller;
	exports restaurante.controller.cadastros;
	exports restaurante.controller.metricas;

	requires transitive Comum;
	requires transitive Servidor;
	requires transitive javafx.fxml;
	requires transitive javafx.graphics;
	requires javafx.base;
	requires javafx.controls;
	requires com.jfoenix;
	requires org.controlsfx.controls;
	requires AnimateFX;
	requires java.desktop;
	requires Cadastro;
	requires java.logging;
	
	/* Faz a abertura dos pacotes do javafx para ser utilizado nas clases */
	opens restaurante.controller to javafx.fxml;
	opens restaurante.controller.cadastros to javafx.fxml;
	opens restaurante.controller.pesquisas to javafx.fxml;
	opens restaurante.controller.metricas to javafx.fxml;
	
}