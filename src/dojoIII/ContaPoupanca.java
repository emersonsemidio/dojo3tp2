package dojoIII;

import java.time.LocalDate;
import java.util.*;

public class ContaPoupanca extends Conta {

    public ContaPoupanca(String numeroConta, String senhaConta) {
        
        super(numeroConta, senhaConta);
    }

    public void verificaSaldoPoupanca() {
        
        System.out.println(saldo);
    }

    public double getSaldoContaPoupanca() {
        
        return saldo;
    }

    @Override
    public void sacar(double valorSaque, LocalDate data) {
        if (valorSaque > saldo) {
            System.out.println("Valor maior que o saque possivel");
            return;
        }
        System.out.println("Valor sacado com sucesso");
        System.out.println("Saldo atualizado: " + this.getSaldoContaPoupanca());
        this.saldo -= valorSaque;
        Extrato extratoSaquePoupanca = new Extrato(-valorSaque, data, "saque - poupança");
        this.extrato.add(extratoSaquePoupanca);
        this.log(extratoSaquePoupanca);
        this.logSaldo();
    }

    @Override
    public String toString() {
        String s = super.toString() + "(" + this.getClass().getName() + ")";
        return s;
    }

    @Override
    public void emitirExtrato() {
        super.emitirExtrato();
        System.out.println("O saldo da conta poupança é " + this.saldo);
    }
}
