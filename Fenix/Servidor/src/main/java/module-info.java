/**
 * @author Jhonny
 *
 */
module Servidor {

	exports servidor.configuration;
	exports servidor.dao;
	exports servidor.dao.services;
	exports servidor.dto;
	exports servidor.entities;
	exports servidor.converter;
	exports servidor.validations;
	exports servidor.util;

	requires transitive Comum;
	requires transitive java.persistence;
	requires org.hibernate.orm.core;
	requires org.hibernate.commons.annotations;

	requires java.transaction;
	requires java.naming;
	requires transitive java.sql;
	requires org.flywaydb.core;

	opens servidor.entities to org.hibernate.orm.core;
	opens db.migration;
}