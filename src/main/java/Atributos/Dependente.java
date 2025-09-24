package Atributos;

import ENUM.Parentesco;

import java.time.LocalDate;

public class Dependente extends Pessoa{

    private Parentesco parentesco;
    private int funcionario_id;
    private int dependente_id;

    public Dependente(String nome, String cpf, LocalDate dataNascimento, Parentesco parentesco) {
        super(nome, cpf, dataNascimento);
        this.parentesco = parentesco;
    }

    public String getParentesco() {
        return parentesco.getTipo();
    }

    public void setParentesco(Parentesco parentesco) {
        this.parentesco = parentesco;
    }

    public int getDependente_id() {
        return dependente_id;
    }

    public void setDependente_id(int dependente_id) {
        this.dependente_id = dependente_id;
    }

    public int getFuncionario_id() {
        return funcionario_id;
    }

    public void setFuncionario_id(int funcionario_id) {
        this.funcionario_id = funcionario_id;
    }

    @Override
    public String getTipo(){
        return "Dependente";
    }

}
