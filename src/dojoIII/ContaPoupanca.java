package dojoIII;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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


    public void renderPoupanca(LocalDate tempoAtualCaixaEletronico) {
        int dias_passados = (int) ChronoUnit.DAYS.between(this.dataAtualConta,tempoAtualCaixaEletronico);
        int quantiadeDeDepositos = dias_passados/30;
        for(int j=1; j <= quantiadeDeDepositos; j++) {
            double rendimento = this.saldo * (0.3/100);
            this.depositar(rendimento, this.dataAtualConta.plusDays(j*30), "Rendimento poupança");
        }
    }

    @Override
    public void sacar(double valorSaque, LocalDate data, String descricao) {
        if (valorSaque > saldo) {
            System.out.println("Valor maior que o saque possivel");
            return;
        }
        System.out.println("Valor sacado com sucesso");
        System.out.println("Saldo atualizado: " + this.getSaldoContaPoupanca());
        this.saldo -= valorSaque;
        Extrato extratoSaquePoupanca = new Extrato(-valorSaque, data, "saque - poupança", descricao);
        this.extrato.add(extratoSaquePoupanca);
    }

    @Override
    public String toString() {
        String s = super.toString() + "(Conta Poupança)" ;
        return s;
    }

    @Override
    public void emitirExtrato() {
        super.emitirExtrato();
        System.out.println("O saldo da conta poupança é " + this.saldo);
    }
}
