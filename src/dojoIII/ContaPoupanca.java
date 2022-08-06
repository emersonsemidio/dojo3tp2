package dojoIII;

import java.util.*;

public class ContaPoupanca extends Conta{

    public ContaPoupanca(int numeroConta, int senhaConta){
        super(numeroConta, senhaConta);
    }


    public void verificaSaldoPoupanca(){
        System.out.println(saldo);
    }

    public double getSaldoContaPoupanca() {
        return saldo;
    }


    @Override
    public void sacar(double valorSaque, int[]data){
        if(valorSaque > saldo){
            System.out.println("Valor maior que o saque possivel");
        }
        else{
            System.out.println("Valor sacado com sucesso");
            System.out.println("Saldo atualizado: " + this.getSaldoContaPoupanca());
            this.saldo -= valorSaque;
            Extrato extratoSaquePoupanca = new Extrato(-valorSaque, data[0], data[1], data[2], "saque - poupança");
            this.extrato.add(extratoSaquePoupanca);
        }
    }
}
