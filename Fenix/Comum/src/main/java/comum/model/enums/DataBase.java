package comum.model.enums;

/**
 * <p>
 * Enuns utilizado para identificar o tipo de banco de dados.
 * </p>
 * 
 * <p>
 * <b>MySQL, SQL Server, Postgre SQL, Firebird</b>
 * </p>
 * 
 * @author Jhonny de Salles Noschang
 */
public enum DataBase {

	MySQL("MySQL"), SQL("SQL Server"), POSTGRE("Postgre SQL"), FIREBIRD("Firebird");

	private String database;

	DataBase(String database) {
		this.database = database;
	}

	public String getDescricao() {
		return database;
	}

	@Override
	public String toString() {
		return this.database;
	}

}
