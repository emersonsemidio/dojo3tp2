package dojoIII;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Conta {
    protected List<Extrato> extrato = new ArrayList<>();

    protected String senhaDaConta;
    protected String numeroDaConta;
    protected Cliente cliente;
    protected double saldo;
    protected LocalDate dataAtualConta = LocalDate.now();
    protected List<ContaPagamento> depositoPagamentoList = new ArrayList<>();
    private static final String AGENCIA = "0001";
    protected ContaSalario contaSalario = null;


    public Conta(String numeroDaConta, String senhaDaConta) {
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

    public String getSenhaDaConta() {
        return senhaDaConta;
    }

    public void setSenhaDaConta(String senhaDaConta) {
        this.senhaDaConta = senhaDaConta;
    }

    public String getNumeroDaConta() {
        return numeroDaConta;
    }

    public void setNumeroDaConta(String numeroDaConta) {
        this.numeroDaConta = numeroDaConta;
    }

    public void depositar(double valorDeposito, LocalDate data) {
        this.saldo += valorDeposito;
        Extrato extratoDeposito = new Extrato(valorDeposito, data, "depósito");
        this.extrato.add(extratoDeposito);
        this.log(extratoDeposito);
        this.logSaldo();
    }
    
    public void depositar(double valorDeposito, LocalDate data, String descricao) {
        this.saldo += valorDeposito;
        Extrato extratoDeposito = new Extrato(valorDeposito, data, "depósito", descricao);
        this.extrato.add(extratoDeposito);
        this.log(extratoDeposito);
        this.logSaldo();
    }

    public void sacar(double valorSaque, LocalDate data, String descricao) {
        Extrato extratoSaque = new Extrato(valorSaque, data, "saque", descricao);
        this.extrato.add(extratoSaque);
        this.log(extratoSaque);
        this.logSaldo();
    }
    
    public void setContaSalario(ContaSalario contaSalario) {
        this.contaSalario = contaSalario;
    }
    
    public void avancarTempo(LocalDate tempoAtualCaixaEletronico){
        this.dataAtualConta = tempoAtualCaixaEletronico;
    }
    
    public void depositarPagamento(LocalDate tempoAtualCaixaEletronico) {
        if (this.contaSalario == null) return;
        int dias_passados = (int) ChronoUnit.DAYS.between(this.dataAtualConta, tempoAtualCaixaEletronico);
        int mesesAvancados = (dias_passados / 30);
        for (int i = 1; i <= mesesAvancados; i++){
            int ano = dataAtualConta.getYear();
            int mes = dataAtualConta.getMonthValue();
            int dia = this.contaSalario.getDiaPagamento();
            LocalDate dataPagamento = LocalDate.of(ano, mes, dia).plusMonths(i);
            if (!this.recebeuEsteMes(dataPagamento)) {
                this.adicionarContaPagamento(new ContaPagamento(dataPagamento, this.contaSalario.getValorPagamento()));
            }
        }
    }
    
    private boolean recebeuEsteMes(LocalDate esteMes) {
        if (this.depositoPagamentoList.size() == 0) return false;
        ContaPagamento ultimoPagamento = this.depositoPagamentoList.get(this.depositoPagamentoList.size() - 1);
        return ultimoPagamento.dataPagamento.isAfter(esteMes);
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
    public boolean pagarBoleto(Boleto boleto, LocalDate dataPagamento) {
        if (boleto.isPago()) {
            System.out.println("Operação cancelada. Boleto já está pago");
            return false;
        }
        double totalAPagar = boleto.getTotalAPagar(dataPagamento);
        if (this.getSaldoTotal() < totalAPagar) {
            System.out.println("Operação cancelada. Saldo insuficiente");
            System.out.println("Saldo atual: " + this.getSaldoTotal());
            System.out.println("Valor do boleto: " + totalAPagar);
            return false;
        }
        this.decrementarSaldo(boleto.getPreco());
        boleto.associarContaAoBoleto(this);
        boleto.setPago(true);
        
        String descricao = "Descrição pagamento boleto:\n" + boleto.toString();

        Extrato extratoBoleto = new Extrato(-totalAPagar, dataPagamento, "Boleto", descricao);
        this.extrato.add(extratoBoleto);
        this.log(extratoBoleto);
        this.logSaldo();
        return true;
    }

    public void adicionarContaPagamento(ContaPagamento conta) {
        incrementarSaldo(conta.valorPagamento);
        Extrato extratoSalario = new Extrato(conta.valorPagamento, conta.dataPagamento, "Recebimento salário");
        this.extrato.add(extratoSalario);
        depositoPagamentoList.add(conta);
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
