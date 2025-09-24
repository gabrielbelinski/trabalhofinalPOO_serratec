package Atributos;

import java.time.LocalDate;

public class FolhaPagamento {
    private final double ABATIMENTO = 189.59;
    private double salarioBruto = 0;
    private int qtdDependentes = 0;
    private double salarioLiquido = 0;
    private double descontoINSS = 0;
    private double descontoIR = 0;
    private LocalDate dataPagamento;

    public FolhaPagamento(double salarioBruto, int qtdDependentes){
        this.salarioBruto = salarioBruto;
        this.qtdDependentes = qtdDependentes;
        this.dataPagamento = LocalDate.now();
    }
    
     public double getSalarioBruto() {
        return salarioBruto;
    }

    public int getQtdDependentes() {
        return qtdDependentes;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }
    
    public double getDescontoINSS() {
        if(this.getSalarioBruto() <= 1518){
            this.descontoINSS = this.getSalarioBruto() * 0.075;
        } else if (this.getSalarioBruto() <= 2793.00) {
            this.descontoINSS = this.getSalarioBruto() *  0.09;
        } else if (this.getSalarioBruto() <= 4190.00) {
            this.descontoINSS = this.getSalarioBruto() *  0.12;
        } else if (this.getSalarioBruto() <= 8157.00) {
            this.descontoINSS = this.getSalarioBruto() *  0.14;
        }else{
            this.descontoINSS = 8157.41 * 0.14;
        }

        return this.descontoINSS;
    }

    public double getSalarioLiquido() {
        this.salarioLiquido = this.getSalarioBruto() - this.getDescontoINSS() - this.getDescontoIR();
        return this.salarioLiquido;
    }

    public double getDescontoIR() {
        double baseIR = this.getSalarioBruto() - this.getDescontoINSS() - (this.getQtdDependentes() * ABATIMENTO);
        double aliquota = 0, parcelaDeduzir = 0;
        if (baseIR <= 2259.00) {
            this.descontoIR = 0;
        } else if (baseIR <= 2826.65) {
            aliquota = 0.075;
            parcelaDeduzir = 169.44;
        } else if (baseIR <= 3751.05) {
            aliquota = 0.15;
            parcelaDeduzir = 381.44;
        } else if (baseIR <= 4664.68) {
            aliquota = 0.225;
            parcelaDeduzir = 662.77;
        } else {
            aliquota = 0.275;
            parcelaDeduzir = 896.00;
        }
        this.descontoIR = (baseIR * aliquota) - parcelaDeduzir;
        if (descontoIR < 0) {
            descontoIR = 0;
        }

        return this.descontoIR;
    }
}
