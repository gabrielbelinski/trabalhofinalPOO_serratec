package Services;

import Exceptions.CPFException;

import java.util.Set;

public class CheckCPFFuncionario {
    public static void checkCPF(String cpfAtual, Set<String> cpfs){
        if(cpfs.contains(cpfAtual)){
            throw new CPFException("\nJá existe um funcionário com este CPF!");
        }
    }
}
