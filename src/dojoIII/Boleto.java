package dojoIII;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Boleto {

    private double preco;
    private double valorPago;
    private String codigo;
    private boolean isPago = false;
    private Conta conta;
    private LocalDate dataVencimento;
    private final double taxaJuros = 0.1/100;


    public Boleto(String codigo, double preco, LocalDate dataVencimento) {
        this.codigo = codigo;
        this.preco = preco;
        this.dataVencimento = dataVencimento;
    }
    
    public double totalParaPagarBoleto(LocalDate dataPagamento) {
        int dias_passados = (int) ChronoUnit.DAYS.between(this.dataVencimento,dataPagamento);
        if (dias_passados < 0) {
            return this.preco;
        }
        double juros = 1 + this.taxaJuros * dias_passados;
        this.valorPago = this.preco * juros;
     
        return this.valorPago;
    }


    public void associarContaAoBoleto(Conta conta) {
        this.conta = conta;
    }

    @Override
    public String toString() {
        String s = "";
        s += "Código de Barras: " + this.codigo + " || ";
        s += "Preço: " + this.valorPago + " || ";
        return s;
    }

    public boolean isPago() {
        return isPago;
    }

    public void setPago(boolean pago) {
        isPago = pago;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
}
