package Services;

import Atributos.Dependente;
import Atributos.Funcionario;
import ENUM.Parentesco;
import Exceptions.DependenteException;
import java.io.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PersistenciaCSV {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    // --- Salvar em CSV ---
    public void salvarFuncionarios(String caminhoArquivo, List<Funcionario> funcionarios) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            for (Funcionario f : funcionarios) {
                // Linha do funcionário
                writer.write("FUNC;" + f.getNome() + ";" + f.getCpf() + ";" +
                        f.getDataNascimento().format(formatter) + ";" + f.getSalarioBruto());
                writer.newLine();

                // Linhas dos dependentes
                for (Dependente d : f.getDependentes()) {
                    writer.write("DEP;" + d.getNome() + ";" + d.getCpf() + ";" +
                            d.getDataNascimento().format(formatter) + ";" + d.getParentesco());
                    writer.newLine();
                }
                writer.newLine();
            }
        }
    }

    // --- Carregar de CSV ---
    public Set<Funcionario> carregarFuncionarios(String caminhoArquivo) throws IOException {
        Set<Funcionario> funcionarios = new HashSet<>();
        Funcionario funcionarioAtual = null;
        ArrayList<Dependente> dependentes = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                linha = linha.trim();
                if (linha.isEmpty()) {
                    funcionarioAtual = null; // fim do bloco de um funcionário
                    continue;
                }

                String[] partes = linha.split(";");
                if (partes[0].equals("FUNC")) {
                    String nome = partes[1];
                    String cpf = partes[2];
                    String dataNascStr = partes[3];
                    double salarioBruto = Double.parseDouble(partes[4]);

                    funcionarioAtual = new Funcionario(nome, cpf, LocalDate.parse(dataNascStr, formatter), salarioBruto);
                    funcionarios.add(funcionarioAtual);

                } else if (partes[0].equals("DEP") && funcionarioAtual != null) {
                    String nomeDep = partes[1];
                    String cpfDep = partes[2];
                    String dataDepStr = partes[3];
                    Parentesco parentesco = Parentesco.valueOf(partes[4].toUpperCase());

                    try {
                        Dependente dep = new Dependente(nomeDep, cpfDep, LocalDate.parse(dataDepStr, formatter), parentesco);
                    funcionarioAtual.getDependentes().add(dep);
                    if(Period.between(LocalDate.parse(dataDepStr, formatter), LocalDate.now()).getYears() >= 18){
                        throw new DependenteException("O dependente deve ter menos de 18 anos!");
                    }

                    } catch (DependenteException e) {
                        System.err.println("Erro ao adicionar dependente: " + e.getMessage());
                    }
                }
            }
        }
        return funcionarios;
    
    }

    // --- Salvar resultado da folha em CSV ---
    public void salvarFolhaPagamento(String caminhoArquivo, List<Funcionario> funcionarios) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            for (Funcionario f : funcionarios) {
                f.setFolhaPagamento();
                String linha = f.getNome() + ";" +
                        f.getCpf() + ";" +
                        String.format("%.2f", f.getFolhaPagamento().getDescontoINSS()) + ";" +
                        String.format("%.2f", f.getFolhaPagamento().getDescontoIR()) + ";" +
                        String.format("%.2f", f.getFolhaPagamento().getSalarioLiquido());
                writer.write(linha);
                writer.newLine();
            }
        }
    }
}
