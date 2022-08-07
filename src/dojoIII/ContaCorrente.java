package dojoIII;

import java.time.LocalDate;
import java.util.*;

public class ContaCorrente extends Conta {
    private double chequeEspecial = 3000;

    public ContaCorrente(String numeroConta, String senhaConta) {
        
        super(numeroConta, senhaConta);
    }

    public void verificaSaldoCorrente() {
        
        System.out.println(this.saldo);
    }

    public double getSaldoCorrente() {
        
        return this.saldo;
    }

    public void setSaldoCorrente(double saldoCorrente) {
        
        this.saldo = saldoCorrente;
    }

    public double getChequeEspecial() {
        
        return chequeEspecial;
    }

    public void setChequeEspecial(double chequeEspecial) {
        
        this.chequeEspecial = chequeEspecial;
    }

    @Override
    public void sacar(double valorSaque, LocalDate data) {
        double limite = this.getChequeEspecial() + this.getSaldoCorrente();

        if (valorSaque > limite) {
            System.out.println("O valor eh maior do que o saque possivel");
        } else {
            if (valorSaque >= this.getSaldoCorrente()) {
                valorSaque -= saldo;
                saldo = 0;
                chequeEspecial -= valorSaque;
            } else {
                saldo -= valorSaque;
                System.out.println("Valor sacado com sucesso");
                System.out.println("Saldo atualizado: " + saldo);
            }
            Extrato extratoSaquePoupanca = new Extrato(-valorSaque, data, "saque - conta corrente");
            this.extrato.add(extratoSaquePoupanca);
            this.log(extratoSaquePoupanca);
            this.logSaldo();
        }
    }

    @Override
    public double getSaldoTotal() {
        return this.saldo + chequeEspecial;
    }

    @Override
    public String toString() {
        String s = super.toString() + "(" + this.getClass().getName() + ")";
        return s;
    }

    @Override
    public void emitirExtrato() {
        super.emitirExtrato();
        System.out.println("O saldo total da conta concorrente é " + this.saldo + " + " + this.getChequeEspecial()
                + " = " + this.getSaldoTotal());
    }

}