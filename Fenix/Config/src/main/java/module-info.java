module Config {
	exports config.controller;
	exports config.util;
	exports config;

	requires transitive Comum;
	requires java.sql;
	requires javafx.base;
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires com.jfoenix;
	requires Servidor;
	
	/* Faz a abertura dos pacotes do javafx para ser utilizado nas clases */
	opens config.controller to javafx.fxml;
}