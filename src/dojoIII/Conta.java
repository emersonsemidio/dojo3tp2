package dojoIII;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Conta {
    protected List<Extrato> extrato = new ArrayList<>();
    protected List<Conta> contasPreCadastradas = new ArrayList<>();

    protected int senhaDaConta;
    protected int numeroDaConta;
    protected final String agencia = "0001"; 
    protected Cliente cliente;
    protected double saldo;
    protected LocalDate dataAtualConta = LocalDate.now();
    protected List<ContaPagamento> contasPagamento = new ArrayList<>();


    public Conta(int numeroDaConta, int senhaDaConta) {
        this.senhaDaConta = senhaDaConta;
        this.numeroDaConta = numeroDaConta;
        System.out.println(this);
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

    public void depositar(double valorDeposito, LocalDate data) {
        this.saldo += valorDeposito;
        Extrato extratoDeposito = new Extrato(valorDeposito, data, "depósito");
        this.extrato.add(extratoDeposito);
        this.log(extratoDeposito);
        this.logSaldo();
    }

    public void sacar(double valorSaque, LocalDate data) { // Verificar porque o saque zerou o saldo
        Extrato extratoSaque = new Extrato(valorSaque, data, "saque");
        this.extrato.add(extratoSaque);
        this.log(extratoSaque);
        this.logSaldo();
    }
    
    public void avancarTempo(LocalDate tempoAtualCaixaEletronico){
        if (this.extrato.isEmpty()) return;
        
        long meses = mesesPassados(tempoAtualCaixaEletronico);
        
        this.dataAtualConta = tempoAtualCaixaEletronico;
        
        for(int i=0; i<this.extrato.size(); i++){
            if(this.extrato.get(i).getValor() < 0){
                continue;
            }
            if (this instanceof  ContaPoupanca) {
                this.renderPoupanca(this.extrato.get(i), tempoAtualCaixaEletronico);   
            }
        }
    }
    
    public void renderPoupanca(Extrato extratoDeposito, LocalDate tempoAtualCaixaEletronico) {
        System.out.println("Número conta: " + this.numeroDaConta);
        long dias = (int) extratoDeposito.getData().until(tempoAtualCaixaEletronico, ChronoUnit.DAYS);
        int quantiadeDeDepositos = ((int) dias)/30;
        for(int j=1; j <= quantiadeDeDepositos; j++) {
            double rendimento = extratoDeposito.getValor() * (0.3/100);
            this.depositar(rendimento, extratoDeposito.getData().plusDays(j*30));
        }
        System.out.println("Dias mudados");
        System.out.println(dias);
        System.out.println(extratoDeposito);
        separadorDeLinhas();
    }
    
    public void depositarPagamento() {
        
    }
    
    public long mesesPassados(LocalDate tempoAtualCaixaEletronico){
        long meses;
        meses = this.dataAtualConta.until(tempoAtualCaixaEletronico, ChronoUnit.MONTHS);
        
        return meses;
    }
    
    public void separadorDeLinhas(){
        System.out.println("---------------------------------\n");
    }

    public double getSaldoTotal() {
        return this.saldo;
    }

    @Override
    public String toString() {
        String s = "Número Conta: " + this.numeroDaConta + " || " + "Saldo: " + this.saldo;
        return s;
    }

    /**
     * O sistema deve permitir configurar o PIX, definindo qual informação será
     * utilizada para transferência (cpf, e-mail e
     * telefone ou criando uma chave nova);
     */

    protected void incrementarSaldo(double valorTransferencia) {
        this.saldo += valorTransferencia;
    }

    protected void decrementarSaldo(double valorTransferencia) {
        this.saldo -= valorTransferencia;
    }

    public void transferir(Conta contaDestino, double valorTransferencia, LocalDate data) {

        if (getSaldoTotal() < valorTransferencia) {
            System.out.println("Valor da transferência maior do que o saldo em conta");
            System.out.println(this.getSaldoTotal());
            return;
        }

        contaDestino.incrementarSaldo(valorTransferencia);
        this.decrementarSaldo(valorTransferencia);

        Extrato extratoTransferencia = new Extrato(-valorTransferencia, data, "Envio de transferência via PIX");
        this.extrato.add(extratoTransferencia);

        Extrato extratoRecebimento = new Extrato(valorTransferencia, data, "transferência recebida");
        contaDestino.extrato.add(extratoRecebimento);

        this.log(extratoTransferencia);
        this.logSaldo();
    }

    // Para cada linha do extrato a data, o tipo de operação, descrição e o valor
    // devem ser exibidos;
    // O sistema deve permitir selecionar um item do extrato para mostrar todos os
    // detalhes. Exemplo: caso o item do extrato seja
    // um pagamento de boleto, sistema deve mostrar todas as suas informações
    // (código de barras, valor, data e multa);
    public void emitirExtrato() {
        for (int i = 0; i < this.extrato.size(); i++) {
            System.out.println(this.extrato.get(i));
        }
    }

    /**
     * O sistema deve permitir pagar boletos (digitando o código de barras de 48
     * dígitos, valor e data de vencimento);
     * * Caso esteja em atraso, o sistema deve aplicar multa de 0,1% ao dia;
     */
    public void pagarBoleto(Boleto boleto) {
        if (boleto.isPago()) {
            System.out.println("Boleto já está pago");
            return;
        }
        if (this.getSaldoTotal() < boleto.getPreco()) {
            System.out.println("Impossível pagar esse boleto" + " Saldo: " + this.getSaldoTotal() + " Valor boleto: "
                    + boleto.getPreco());
            return;
        }
        this.decrementarSaldo(boleto.getPreco());
        boleto.associarContaAoBoleto(this);
        boleto.setPago(true);

        LocalDate data = LocalDate.now();

        Extrato extratoBoleto = new Extrato(-boleto.getPreco(), data, "Boleto");
        this.extrato.add(extratoBoleto);
        this.log(extratoBoleto);
        this.logSaldo();
    }

    public void adicionarContaPagamento(ContaPagamento conta) {
        incrementarSaldo(conta.valorPagamento);
        Extrato extratoSalario = new Extrato(conta.valorPagamento, conta.dataPagamento, "Recebimento salário");
        this.extrato.add(extratoSalario);
        contasPagamento.add(conta);
    }

    protected void log(Extrato extrato) {
        System.out.println(extrato);
    }

    protected void logSaldo() {
        System.out.println("Novo saldo: " + this.saldo);
    }

    /**
     * Você deve implementar as classes de conta utilizando os conceitos de herança
     * e classe abstrata;
     *
     * Você deve utilizar uma interface TransacaoEmConta para definir o contrato
     * básico de um item do extrato com os
     * métodos getValor(), getDescricao(), getTipoOpercao e getData();
     *
     * O sistema deve guardar o dia atual e permitir “avançar no tempo” (em dias),
     * para poder refletir nos cálculos de
     * rendimento de poupança, cálculo de poupança e salário. O mecanismo de “avanço
     * no tempo” pode ser utilizado várias
     * vezes;
     * * Entrada de conta de salário deve ser simulada mês a mês conforme o valor do
     * salário e data de pagamento;
     */
}
