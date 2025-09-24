package model;

import Atributos.Funcionario;
import Exceptions.DBException;
import Services.ConnectionFactory;

import java.sql.*;

public class FuncionarioDAO {
    private Connection connection;

    public FuncionarioDAO(){
        connection = new ConnectionFactory().getConnection();
    }

    public void insert(Funcionario funcionario) throws DBException {
    String query = "INSERT INTO funcionario(nome, data_nascimento, cpf, salario_bruto) VALUES(?,?,?,?)";
    try{
        PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        stmt.setString(1, funcionario.getNome());
        stmt.setDate(2, Date.valueOf(funcionario.getDataNascimento()));
        stmt.setString(3, funcionario.getCpf());
        stmt.setDouble(4, funcionario.getSalarioBruto());
        stmt.executeUpdate();

        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            funcionario.setFuncionario_id(rs.getInt("funcionario_id"));
        }

        stmt.close();
        connection.close();
    } catch (SQLException e) {
        throw new DBException("Não foi possível fazer a inserção no banco de dados. " + e.getMessage());
    }
}

}
