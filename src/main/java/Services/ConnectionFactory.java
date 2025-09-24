package Services;

import Exceptions.DBException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory implements IConnectionFactory {
    private String databaseName = "financeiro";
    private String url = "jdbc:postgresql://localhost:5432/" + databaseName;
    private String user = "postgres";
    private String password = "postgres";
    private Connection connection;

    public Connection getConnection() {
        try {
            connection = DriverManager.getConnection(url, user, password);
            if (connection.isValid(0)) {
                System.out.println("Conexão com o banco de dados estabelecida com êxito.");
            } else
                throw new DBException("Falha ao conectar ao banco de dados.");
        } catch (SQLException e) {
            System.err.print("Ocorreu um erro ao conectar ao banco de dados " + databaseName + "\n" + e.getMessage());
        }
        return connection;
    }
}