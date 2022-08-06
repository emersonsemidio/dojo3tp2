package dojoIII;

import java.util.*;

public class ContaCorrente extends Conta{
    private double saldoCorrente = 0;
    private double chequeEspecial = 3000;

    public ContaCorrente(int numeroConta, int senhaConta){
        super(numeroConta, senhaConta);
    }

    public void verificaSaldoCorrente(){
        System.out.println(saldoCorrente);
    }
    public ContaCorrente() {
        this.saldoCorrente = saldoCorrente;
    }

    public double getSaldoCorrente() {
        return saldoCorrente;
    }

    public void setSaldoCorrente(double saldoCorrente) {
        this.saldoCorrente = saldoCorrente;
    }

    public double getChequeEspecial() {
        return chequeEspecial;
    }

    public void setChequeEspecial(double chequeEspecial) {
        this.chequeEspecial = chequeEspecial;
    }

    @Override
    public void sacar(double valorSaque, int[]data){
        double limite = this.getChequeEspecial() + this.getSaldoCorrente();

        if(valorSaque > limite){
            System.out.println("O valor eh maior do que o saque possivel");
        }
        else{
            if(valorSaque >= this.getSaldoCorrente()){
                valorSaque -= saldo;
                saldo = 0;
                chequeEspecial -= valorSaque;
            }
            else{
                saldoCorrente -= valorSaque;
                System.out.println("Valor sacado com sucesso");
                System.out.println("Saldo atualizado: " + saldo);
            }
            Extrato extratoSaquePoupanca = new Extrato(-valorSaque, data[0], data[1], data[2], "saque - conta corrente");
            this.extrato.add(extratoSaquePoupanca);
            this.log(extratoSaquePoupanca);
            this.logSaldo();
        }
    }

    @Override
    public double getSaldoTotal(){
        return this.saldo + chequeEspecial;
    }

    @Override
    public String toString() {
        String s = super.toString() + "(" + this.getClass().getName() + ")";
        return s;
    }

}