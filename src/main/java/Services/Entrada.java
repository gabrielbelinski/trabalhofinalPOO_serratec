package Services;

import Atributos.Dependente;
import Atributos.FolhaPagamento;
import Atributos.Funcionario;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import ENUM.Parentesco;
import Exceptions.DBException;
import model.DependentesDAO;
import model.FolhaPagamentoDAO;
import model.FuncionarioDAO;

public class Entrada {
    public void cadastro() throws DBException, IOException {
        Scanner input = new Scanner(System.in);
        PersistenciaCSV persistencia = new PersistenciaCSV();
        Set<String> cpfs = new HashSet<>();
        Set<String> cpfsDependentes = new HashSet<>();

        // --- 1. Entrada de dados pelo console ---
        System.out.print("Digite o nome do arquivo de entrada: ");
        String arquivoEntrada = input.nextLine();

        System.out.print("Digite o nome do arquivo de saída: ");
        String arquivoSaida = input.nextLine();

        // --- 2. Geração do arquivo de entrada ---
        System.out.print("Quantos funcionários deseja cadastrar? ");
        int qtdFuncionarios = Integer.parseInt(input.nextLine());

        List<Funcionario> listaFuncionarios = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        for (int i = 0; i < qtdFuncionarios; i++) {
            System.out.println("\n--- Funcionário " + (i + 1) + " ---");
            System.out.print("Nome: ");
            String nomeFunc = input.nextLine();

            System.out.print("CPF (11 dígitos): ");
            String cpfFunc = input.nextLine();
            CheckCPFFuncionario.checkCPF(cpfFunc, cpfs);

            System.out.print("Data de nascimento (YYYYMMDD): ");
            String dataFuncStr = input.nextLine();
            LocalDate dataFunc = LocalDate.parse(dataFuncStr, formatter);

            System.out.print("Salário bruto: ");
            double salarioBruto = Double.parseDouble(input.nextLine());

            // Cria o funcionário
            Funcionario funcionario = new Funcionario(nomeFunc, cpfFunc, dataFunc, salarioBruto);
            FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
            funcionarioDAO.insert(funcionario);
            cpfs.add(cpfFunc);

            System.out.print("Quantos dependentes tem este funcionário? ");
            int qtdDep = Integer.parseInt(input.nextLine());

            for (int j = 0; j < qtdDep; j++) {
                System.out.println("Dependente " + (j + 1));
                System.out.print("Nome: ");
                String nomeDep = input.nextLine();

                System.out.print("CPF (11 dígitos): ");
                String cpfDep = input.nextLine();
                CheckCPFFuncionario.checkCPF(cpfDep, cpfsDependentes);

                System.out.print("Data de nascimento (YYYYMMDD): ");
                String dataDepStr = input.nextLine();
                LocalDate dataDep = LocalDate.parse(dataDepStr, formatter);

                System.out.print("Parentesco (FILHO, SOBRINHO, OUTROS): ");
                Parentesco parentesco = Parentesco.valueOf(input.nextLine().toUpperCase());

                Dependente dependente = new Dependente(nomeDep, cpfDep, dataDep, parentesco);
                dependente.setFuncionario_id(funcionario.getFuncionario_id());
                funcionario.adicionarDependente(dependente);

                DependentesDAO dD = new DependentesDAO();
                dD.insert(dependente);
            }

            listaFuncionarios.add(funcionario);
            funcionario.setFolhaPagamento();
            FolhaPagamento fp = funcionario.getFolhaPagamento();
            FolhaPagamentoDAO fpDAO = new FolhaPagamentoDAO();
            fpDAO.insert(fp, funcionario);
        }

        // --- 3. Salvar entrada em CSV ---
        persistencia.salvarFuncionarios(arquivoEntrada, listaFuncionarios);

        // --- 4. Carregar do CSV ---
        Set<Funcionario> funcionariosLidos = persistencia.carregarFuncionarios(arquivoEntrada);

        // --- 5. Salvar folha de pagamento ---
        persistencia.salvarFolhaPagamento(arquivoSaida, new ArrayList<>(funcionariosLidos));


        System.out.println("✅ Arquivo de saída gerado com sucesso: " + arquivoSaida);

        input.close();
    }
}
