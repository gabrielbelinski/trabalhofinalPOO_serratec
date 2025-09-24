package Services;

import java.sql.Connection;

public interface IConnectionFactory {
    public Connection getConnection();
}
