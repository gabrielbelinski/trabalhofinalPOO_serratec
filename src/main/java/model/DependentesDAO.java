package model;

import Atributos.Dependente;
import Atributos.Funcionario;
import Exceptions.DBException;
import Services.ConnectionFactory;

import java.sql.*;

public class DependentesDAO {
    private Connection connection;

    public DependentesDAO(){
        connection = new ConnectionFactory().getConnection();
    }

    public void insert(Dependente dependente) throws DBException {
        String query = "INSERT INTO dependente(funcionario_id, nome, data_nascimento, cpf, parentesco) VALUES(?,?,?,?,?)";
        try{
            PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, dependente.getFuncionario_id());
            stmt.setString(2, dependente.getNome());
            stmt.setDate(3, Date.valueOf(dependente.getDataNascimento()));
            stmt.setString(4, dependente.getCpf());
            stmt.setString(5, dependente.getParentesco());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if(rs.next()){
                dependente.setDependente_id(rs.getInt("dependente_id"));
            }
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            //throw new DBException("Não foi possível fazer a inserção no banco de dados." + e.printStackTrace());
        }

    }





}
