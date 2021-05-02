
/**
 * @author Jhonny
 *
 */
module Loguin {

	exports loguin;
	exports loguin.controller;

	requires Comum;
	requires Servidor;
	requires javafx.base;
	requires javafx.controls;
	requires javafx.fxml;
	requires transitive javafx.graphics;
	requires com.jfoenix;
	requires AnimateFX;
	
	/* Faz a abertura dos pacotes do javafx para ser utilizado nas clases */
	opens loguin.controller to javafx.fxml;

}