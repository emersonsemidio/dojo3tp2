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
    protected List<DepositoPagamento> depositoPagamentoList = new ArrayList<>();
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

    public void setContaSalario(ContaSalario contaSalario) {
        this.contaSalario = contaSalario;
    }
    
    public double getSaldoTotal() {
        return this.saldo;
    }
    
    public void depositar(double valorDeposito, LocalDate data, String descricao) {
        this.saldo += valorDeposito;
        Extrato extratoDeposito = new Extrato(valorDeposito, data, "depósito", descricao);
        this.extrato.add(extratoDeposito);
    }

    public void sacar(double valorSaque, LocalDate data, String descricao) {
        this.saldo -= valorSaque;
        Extrato extratoSaque = new Extrato(valorSaque, data, "saque", descricao);
        this.extrato.add(extratoSaque);
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
                this.depositarSalario(new DepositoPagamento(dataPagamento, this.contaSalario.getValorPagamento()));
            }
        }
    }
    
    private boolean recebeuEsteMes(LocalDate esteMes) {
        if (this.depositoPagamentoList.size() == 0) return false;
        DepositoPagamento ultimoPagamento = this.depositoPagamentoList.get(this.depositoPagamentoList.size() - 1);
        return ultimoPagamento.dataPagamento.isAfter(esteMes);
    }
    
    public void separadorDeLinhas(){
        System.out.println("---------------------------------\n");
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

    public void transferir(Conta contaDestino, double valorTransferencia, LocalDate data, String descricao) {

        if (getSaldoTotal() < valorTransferencia) {
            System.out.println("Valor da transferência maior do que o saldo em conta");
            System.out.println(this.getSaldoTotal());
            return;
        }

        contaDestino.incrementarSaldo(valorTransferencia);
        this.decrementarSaldo(valorTransferencia);

        Extrato extratoTransferencia = new Extrato(-valorTransferencia, data, "Envio Pix", descricao);
        this.extrato.add(extratoTransferencia);

        Extrato extratoRecebimento = new Extrato(valorTransferencia, data,  "Recebimento Pix", descricao);
        contaDestino.extrato.add(extratoRecebimento);
    }

    public void emitirExtrato() {
        for (int i = 0; i < this.extrato.size(); i++) {
            System.out.println(this.extrato.get(i));
        }
    }
    public boolean pagarBoleto(Boleto boleto, LocalDate dataPagamento) {
        if (boleto.isPago()) {
            System.out.println("Operação cancelada. Boleto já está pago");
            return false;
        }
        double totalAPagar = boleto.totalParaPagarBoleto(dataPagamento);
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
        return true;
    }

    public void depositarSalario(DepositoPagamento conta) {
        incrementarSaldo(conta.valorPagamento);
        Extrato extratoSalario = new Extrato(conta.valorPagamento, conta.dataPagamento, "Depósito", "Recebimento salário");
        this.extrato.add(extratoSalario);
        depositoPagamentoList.add(conta);
    }
}
