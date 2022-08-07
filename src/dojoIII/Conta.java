package dojoIII;

import java.util.ArrayList;
import java.util.List;

public class Conta{
    protected List<Extrato> extrato = new ArrayList<>();

    protected int senhaDaConta;
    protected int numeroDaConta;
    protected Cliente cliente;
    protected double saldo;

    public Conta() { }

    public Conta(int senhaDaConta, int numeroDaConta) {
        this.senhaDaConta = senhaDaConta;
        this.numeroDaConta = numeroDaConta;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public int getSenhaDaConta() {
        return senhaDaConta;
    }

    public void setSenhaDaConta(int senhaDaConta) {
        this.senhaDaConta = senhaDaConta;
    }

    public int getNumeroDaConta() {
        return numeroDaConta;
    }

    public void setNumeroDaConta(int numeroDaConta) {
        this.numeroDaConta = numeroDaConta;
    }

    public void depositar(int valorDeposito, int[] data){
        this.saldo += valorDeposito;
        Extrato extratoDeposito = new Extrato(valorDeposito, data[0], data[1], data[2], "depósito");
        this.extrato.add(extratoDeposito);
        this.log(extratoDeposito);
        this.logSaldo();
    }

    public double getSaldoTotal(){
        return this.saldo;
    }

    public void sacar(double valorSaque, int[] data){
        Extrato extratoSaque = new Extrato(1, 1, 1, 1, "saque");
        this.extrato.add(extratoSaque);
        this.log(extratoSaque);
        this.logSaldo();
    }

    @Override
    public String toString() {
        String s = "Número Conta: " + this.numeroDaConta + " || " + "Saldo: " + this.saldo;
        return s;
    }

    /**
     * O sistema deve permitir configurar o PIX, definindo qual informação será utilizada para transferência (cpf, e-mail e
     * telefone ou criando uma chave nova);
     * */

    protected void incrementarSaldo(double valorTransferencia){
        this.saldo += valorTransferencia;
    }

    protected void decrementarSaldo(double valorTransferencia){
        this.saldo -= valorTransferencia;
    }

    public void transferir(Conta contaDestino, double valorTransferencia, int[] data){

        if(getSaldoTotal() < valorTransferencia){
            System.out.println("Valor da transferência maior do que o saldo em conta");
            System.out.println(this.getSaldoTotal());
            return;
        }

        contaDestino.incrementarSaldo(valorTransferencia);
        this.decrementarSaldo(valorTransferencia);

        Extrato extratoTransferencia = new Extrato(-valorTransferencia, data[0], data[1], data[2], "Envio de transferência via PIX");
        this.extrato.add(extratoTransferencia);

        Extrato extratoRecebimento = new Extrato(valorTransferencia, data[0], data[1], data[2], "transferência recebida");
        contaDestino.extrato.add(extratoRecebimento);

        this.log(extratoTransferencia);
        this.logSaldo();
    }


    // Para cada linha do extrato a data, o tipo de operação, descrição e o valor devem ser exibidos;
    // O sistema deve permitir selecionar um item do extrato para mostrar todos os detalhes. Exemplo: caso o item do extrato seja
    // um pagamento de boleto, sistema deve mostrar todas as suas informações (código de barras, valor, data e multa);
    public void emitirExtrato(){
        for (int i = 0; i < this.extrato.size(); i++) {
            System.out.println(this.extrato.get(i));
        }
    }



    /**
     * O sistema deve permitir pagar boletos (digitando o código de barras de 48 dígitos, valor e data de vencimento);
     * * Caso esteja em atraso, o sistema deve aplicar multa de 0,1% ao dia;
     * */
    private void pagarBoleto() {

    }


    protected void log(Extrato extrato) {
        System.out.println(extrato);
    }
    protected void logSaldo() {
        System.out.println("Novo saldo: " + this.saldo);
    }


    /**
     * Você deve implementar as classes de conta utilizando os conceitos de herança e classe abstrata;
     *
     * Você deve utilizar uma interface TransacaoEmConta para definir o contrato básico de um item do extrato com os
     * métodos getValor(), getDescricao(), getTipoOpercao e getData();
     *
     * O sistema deve guardar o dia atual e permitir “avançar no tempo” (em dias), para poder refletir nos cálculos de
     * rendimento de poupança, cálculo de poupança e salário. O mecanismo de “avanço no tempo” pode ser utilizado várias
     * vezes;
     * * Entrada de conta de salário deve ser simulada mês a mês conforme o valor do salário e data de pagamento;
     * */
}
