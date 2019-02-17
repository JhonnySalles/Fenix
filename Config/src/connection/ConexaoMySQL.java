package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConexaoMySQL {

	final public static List<String> bases = new ArrayList<>();

	public static Boolean testaConexaoMySQL(String server, String port, String dataBase, String user, String psswd) {

		Connection connection = null;
		Boolean conecta = false;
		bases.clear();
		try {

			if (dataBase.equals("MySQL")) {
				String driverName = "com.mysql.jdbc.Driver";
				Class.forName(driverName);

				String url = "jdbc:mysql://" + server + ":" + port;
				connection = DriverManager.getConnection(url, user, psswd);

				PreparedStatement pst = connection
						.prepareStatement("SELECT schema_name FROM information_schema.SCHEMATA "
								+ " WHERE schema_name NOT IN (?, ?, ?, ?)");
				pst.setString(1, "mysql");
				pst.setString(2, "performance_schema");
				pst.setString(3, "information_schema");
				pst.setString(4, "sys");
				ResultSet rs = pst.executeQuery();

				while (rs.next()) {
					bases.add(rs.getString(1));
				}
			}

			if (connection != null) {
				conecta = true;
			} else {
				conecta = false;
			}

			// connection.close();

		} catch (ClassNotFoundException e) { // Driver n�o encontrado
			System.out.println("O driver de conex�o expecificado nao foi encontrado.");
			e.printStackTrace();

		} catch (SQLException e) {
			System.out.println("Nao foi possivel conectar ao Banco de Dados.");

		}
		return conecta;
	}
}
