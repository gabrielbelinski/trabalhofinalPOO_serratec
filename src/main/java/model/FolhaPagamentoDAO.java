package model;

import Atributos.FolhaPagamento;
import Atributos.Funcionario;
import Exceptions.DBException;
import Services.ConnectionFactory;

import java.sql.*;

public class FolhaPagamentoDAO {
    private Connection connection;

    public FolhaPagamentoDAO(){
        connection = new ConnectionFactory().getConnection();
    }

    public void insert(FolhaPagamento folha, Funcionario funcionario) throws DBException {
        String query = "INSERT INTO folhapagamento(funcionario_id, data_pagamento, desconto_inss, desconto_ir, salario_liquido) VALUES(?,?,?,?,?)";
        try{
            PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, funcionario.getFuncionario_id());
            stmt.setDate(2, Date.valueOf(folha.getDataPagamento()));
            stmt.setDouble(3, folha.getDescontoINSS());
            stmt.setDouble(4, folha.getDescontoIR());
            stmt.setDouble(5, folha.getSalarioLiquido());
            stmt.executeUpdate();
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            throw new DBException("Não foi possível fazer a inserção no banco de dados.");
        }

    }





}
