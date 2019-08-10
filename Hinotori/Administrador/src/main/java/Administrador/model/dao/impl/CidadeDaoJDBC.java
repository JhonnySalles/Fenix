package Administrador.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Administrador.model.dao.CidadeDao;
import Administrador.model.entities.Cidade;
import model.enums.Situacao;
import model.log.ManipulaLog;
import model.mysql.DB;

public class CidadeDaoJDBC implements CidadeDao {

	final String insert = "INSERT INTO cidades (Id_Estado, Nome, Ddd, Situacao) VALUES (?, ?, ?, ?);";

	final String update = "UPDATE cidades SET Id_Estado = ?, Nome = ?, Ddd = ?, Situacao = ? WHERE Id = ?;";

	final String delete = "DELETE FROM cidades WHERE Id = ?;";

	final String selectAll = "SELECT Id, Id_Estado, Nome, Ddd, Situacao FROM cidades;";

	final String select = "SELECT Id, Id_Estado, Nome, Ddd, Situacao FROM cidades WHERE ID = ?;";

	private Connection conexao;

	public CidadeDaoJDBC(Connection conexao) {
		this.conexao = conexao;
	}

	@Override
	public void insert(Cidade obj) {

		PreparedStatement st = null;
		try {
			st = conexao.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);

			st.setLong(1, obj.getIdEstado());
			st.setString(2, obj.getNome());
			st.setString(3, obj.getDdd());
			st.setString(4, obj.getSituacao().toString());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected < 1) {
				System.out.println("Erro ao salvar os dados.");
				System.out.println(st.toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(st.toString());
			ManipulaLog.salvar(this.getClass(), "JDBC - INSERT", st.toString(), e.toString());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Cidade obj) {

		PreparedStatement st = null;
		try {
			st = conexao.prepareStatement(update, Statement.RETURN_GENERATED_KEYS);

			st.setLong(1, obj.getIdEstado());
			st.setString(2, obj.getNome());
			st.setString(3, obj.getDdd());
			st.setString(4, obj.getSituacao().toString());
			st.setLong(5, obj.getId());

			int rowsAffected = st.executeUpdate();

			if (rowsAffected < 1) {
				System.out.println("Erro ao salvar os dados.");
				System.out.println(st.toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(st.toString());
			ManipulaLog.salvar(this.getClass(), "JDBC - UPDATE", st.toString(), e.toString());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void delete(Long id) {

		PreparedStatement st = null;
		try {
			st = conexao.prepareStatement(delete, Statement.RETURN_GENERATED_KEYS);

			st.setLong(1, id);

			int rowsAffected = st.executeUpdate();

			if (rowsAffected < 1) {
				System.out.println("Erro ao deletar os dados.");
				System.out.println(st.toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(st.toString());
			ManipulaLog.salvar(this.getClass(), "JDBC - DELETE", st.toString(), e.toString());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Cidade find(Long id) {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conexao.prepareStatement(select);
			st.setLong(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Cidade obj = new Cidade(rs.getLong("Id"), rs.getLong("Id_Estado"), rs.getString("Nome"),
						rs.getString("Ddd"), Situacao.valueOf(rs.getString("Situacao")));
				return obj;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			ManipulaLog.salvar(this.getClass(), "JDBC - FIND", st.toString(), e.toString());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		return null;
	}

	@Override
	public List<Cidade> findAll() {
		
		PreparedStatement st = null;
		ResultSet rs = null;
		try {

			st = conexao.prepareStatement(selectAll);
			rs = st.executeQuery();

			List<Cidade> list = new ArrayList<>();

			while (rs.next()) {
				Cidade obj = new Cidade(rs.getLong("Id"), rs.getLong("Id_Estado"), rs.getString("Nome"),
						rs.getString("Ddd"), Situacao.valueOf(rs.getString("Situacao")));
				list.add(obj);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			ManipulaLog.salvar(this.getClass(), "JDBC - FIND ALL", st.toString(), e.toString());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		return null;
	}

}