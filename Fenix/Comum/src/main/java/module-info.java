module Comum {
	exports comum.model.alerts;
	exports comum.model.animation;
	exports comum.model.config;
	exports comum.model.constraints;
	exports comum.model.encode;
	exports comum.model.entities;
	exports comum.model.enums;
	exports comum.model.exceptions;
	exports comum.model.generator;
	exports comum.model.mask;
	exports comum.model.messages;
	exports comum.model.notification;
	exports comum.model.notification.controller;
	exports comum.model.utils;
	exports comum.form;
	exports comum.cep;

	requires java.logging;
	requires javafx.base;
	requires javafx.fxml;
	requires org.controlsfx.controls;
	requires transitive com.jfoenix;
	requires transitive javafx.controls;
	requires transitive javafx.graphics;
	requires transitive java.desktop;
	requires AnimateFX;
	requires org.kordamp.ikonli.javafx;
	requires org.kordamp.ikonli.fontawesome5;
	requires java.persistence;
	requires java.json;
	requires org.apache.httpcomponents.httpcore;
	requires org.apache.httpcomponents.httpclient;

	opens comum.model.notification.controller to javafx.fxml;
	opens comum.model.alerts.controller to javafx.fxml;
	opens comum.form to javafx.fxml;
}