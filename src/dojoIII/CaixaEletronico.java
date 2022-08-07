package dojoIII;

import java.sql.SQLSyntaxErrorException;
import java.util.*;
import java.time.LocalDate;

public class CaixaEletronico {
    Scanner scanner = new Scanner(System.in);
    Random rand = new Random();

    List<Cliente> clientes = new ArrayList<>();
    List<Conta> contas = new ArrayList<>();
    List<Boleto> boletos = new ArrayList<>();
    LocalDate tempoAtualCaixaEletronico = LocalDate.now();
    
    ContaCorrente contaCorrente = new ContaCorrente();
    ContaPoupanca contaPoupanca = new ContaPoupanca(1, 1);
    Calendar c = Calendar.getInstance();
    int tempo = 0;
    int dia, mes, ano;
    ArrayList<Extrato> listaExtratos = new ArrayList<>();
    
    public void gerarCLiente(){
        Cliente clienteGerado1 = new Cliente("Ana", "123", "12 07 2001", "ana@gmail.com");
        Cliente clienteGerado2 = new Cliente("Bruna", "1234", "12 07 2001", "bruna@gmail.com");
        Cliente clienteGerado3 = new Cliente("Emerson", "12345", "12 07 2001", "emerson@gmail.com");
        Cliente clienteGerado4 = new Cliente("Henrique", "123456", "12 07 2001", "henrique@gmail.com");
        Cliente clienteGerado5 = new Cliente("Vitor", "1234567", "12 07 2001", "vitor@gmail.com");
        
        clientes.add(clienteGerado1);
        clientes.add(clienteGerado2);
        clientes.add(clienteGerado3);
        clientes.add(clienteGerado4);
        clientes.add(clienteGerado5);
    }
    
    public void gerarContaCorrente(){
        ContaCorrente contaGerada1 = new ContaCorrente(10, 10000);
        ContaCorrente contaGerada2 = new ContaCorrente(11, 1000);
        ContaCorrente contaGerada3 = new ContaCorrente(12, 100);
        
        contas.add(contaGerada1);
        contas.add(contaGerada2);
        contas.add(contaGerada3);
    }
    
    public void gerarContaPoupanca(){
        ContaPoupanca contaGerada1 = new ContaPoupanca(20, 10000);
        ContaPoupanca contaGerada2 = new ContaPoupanca(21, 1000);
        ContaPoupanca contaGerada3 = new ContaPoupanca(22, 100);

        contas.add(contaGerada1);
        contas.add(contaGerada2);
        contas.add(contaGerada3);
    }
    
    public void associarClienteNaConta(){
        clientes.get(0).setContaCorrente((ContaCorrente) buscarContaPeloNumero(10));
        clientes.get(0).setContaPoupanca((ContaPoupanca) buscarContaPeloNumero(20));
        clientes.get(1).setContaCorrente((ContaCorrente) buscarContaPeloNumero(11));
        clientes.get(2).setContaCorrente((ContaCorrente) buscarContaPeloNumero(12));
        clientes.get(3).setContaPoupanca((ContaPoupanca) buscarContaPeloNumero(21));
        clientes.get(4).setContaPoupanca((ContaPoupanca) buscarContaPeloNumero(22));
    }
    
    public void iniciarAbrirConta() {
        int tipoConta = tipoDeConta("Qual tipo de conta você quer abrir");
        AbrirConta(tipoConta);
    }

    public void extrato() {
        Conta conta = buscarContaPeloNumero();
        if (conta == null) {
            System.out.println("Conta não encontrada");
            return;
        }
        conta.emitirExtrato();
    }

    public boolean isClienteNovo() {
        return true;
    }

    public void AbrirConta(int tipoConta) {
        Cliente clienteNovo = new Cliente();
        Cliente clienteExistente = buscarcontaPorCpf();
        // int senhaConta = 0;
        // int numeroConta = 0;

        if (clienteExistente == null) {
            System.out.println("Qual seu CPF?");
            clienteNovo.setCpf(scanner.nextLine());

            System.out.println("Qual seu nome?");
            clienteNovo.setNome(scanner.nextLine());
            
            System.out.println("Qual sua data de nascimento? (digite separado por espacos DD MM AAAA)");
            clienteNovo.setDataNascimento(scanner.nextLine());

            System.out.println("Qual seu e-mail?");
            clienteNovo.setEmail(scanner.nextLine());

            System.out.println("Qual seu telefone?");
            clienteNovo.setTelefone(scanner.nextLine());
            
            

        } else {
            if (!clienteExistente.podeAbrirConta()) {
                System.out.println(
                        "Cliente já cadastrado com o CPF e possui contas Corrente e Poupança. Veja abaixo os dados da suas contas");
                clienteExistente.mostrarDetalhesContaCorrente();
                clienteExistente.mostrarDetalhesContaPoupanca();
                return;
            }
            System.out.println("Cliente já cadastrado com o CPF");

            if (clienteExistente.isTemContaCorrente()) {
                System.out.println("Cliente já possui conta Corrente. Será continuado com conta Poupança");
                tipoConta = 2;
                clienteExistente.mostrarDetalhesContaCorrente();
            }
            if (clienteExistente.isTemContaPoupanca()) {
                System.out.println("Cliente já possui conta Poupança. Será continuado com conta Corrente");
                tipoConta = 1;
                clienteExistente.mostrarDetalhesContaPoupanca();
            }
            clienteNovo = clienteExistente;
        }

        System.out.println("Qual sua senha?");
        int senhaConta = scanner.nextInt();
        int numeroConta = rand.nextInt(10000);

        clientes.add(clienteNovo);

        if (tipoConta == 1) {
            this.abrirContaCorrente(senhaConta, numeroConta, clienteNovo);
        } else {
            this.abrirContaPoupanca(senhaConta, numeroConta, clienteNovo);
        }
        System.out.println("Conta criada com sucesso " + numeroConta);
    }

    private void abrirContaCorrente(int senhaConta, int numeroConta, Cliente clienteNovo) {
        ContaCorrente contaCorrente = new ContaCorrente(numeroConta, senhaConta);
        contas.add(contaCorrente);
        clienteNovo.setContaCorrente(contaCorrente);
        contaCorrente.setCliente(clienteNovo);
    }

    private void abrirContaPoupanca(int senhaConta, int numeroConta, Cliente clienteNovo) {
        ContaPoupanca contaPoupanca = new ContaPoupanca(numeroConta, senhaConta);
        contas.add(contaPoupanca);
        clienteNovo.setContaPoupanca(contaPoupanca);
        contaPoupanca.setCliente(clienteNovo);
    }

    public int tipoDeConta(String texto) {
        int tipoConta;
        System.out.println(texto);
        System.out.println("[1] Criar conta corrente");
        System.out.println("[2] Criar conta poupanca");

        do {
            System.out.println("Informe o tipo de conta");
            tipoConta = scanner.nextInt();
            if (tipoConta < 1 && tipoConta > 2) {
                System.out.println("tipo de conta inexistente");
            }
        } while (tipoConta < 1 || tipoConta > 2);
        scanner.nextLine();

        return tipoConta;
    }

    public boolean verificarExistenciaConta() {
        String cpf;

        System.out.println("Digite seu cpf");
        cpf = scanner.nextLine();

        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getCpf().equalsIgnoreCase(cpf)) {
                return true;
            }
        }
        return false;
    }

    public Cliente buscarcontaPorCpf() {
        String cpf;

        System.out.println("Digite o cpf");
        cpf = scanner.nextLine();

        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getCpf().equalsIgnoreCase(cpf)) {
                return clientes.get(i);
            }
        }
        return null;
    }

    public Cliente buscarPorTelefone() {
        String telefone;

        System.out.println("Digite o telefone");
        telefone = scanner.nextLine();

        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getTelefone().equalsIgnoreCase(telefone)) {
                return clientes.get(i);
            }
        }
        return null;
    }

    public Cliente buscarPorEmail() {
        String email;

        System.out.println("Digite o email");
        email = scanner.nextLine();

        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getEmail().equalsIgnoreCase(email)) {
                return clientes.get(i);
            }
        }
        return null;
    }

    public Conta buscarContaPeloNumero() {
        int numeroConta;

        System.out.println("Digite o número da conta");
        numeroConta = scanner.nextInt();

        for (int i = 0; i < contas.size(); i++) {
            if (contas.get(i).getNumeroDaConta() == numeroConta) {
                return contas.get(i);
            }
        }
        return null;
    }
    
    public Conta buscarContaPeloNumero(int numeroConta) {
        
        for (int i = 0; i < contas.size(); i++) {
            if (contas.get(i).getNumeroDaConta() == numeroConta) {
                return contas.get(i);
            }
        }
        return null;
    }

    public boolean estaNoIntervalo(int valorMenor, int valor, int valorMaior) {

        return (valorMenor <= valor) && (valor <= valorMaior);
    }

    public int[] lerData() {
        int[] data = new int[3];

        do {
            System.out.println("Dia");
            data[0] = scanner.nextInt();
            if (!estaNoIntervalo(1, data[0], 30)) {
                System.out.println("Dia inválido");
            }
        } while (!estaNoIntervalo(1, data[0], 30));
        do {
            System.out.println("mes");
            data[1] = scanner.nextInt();
            if (!estaNoIntervalo(1, data[1], 12)) {
                System.out.println("Mês inválido");
            }
        } while (!estaNoIntervalo(1, data[1], 12));
        System.out.println("ano");
        data[2] = scanner.nextInt();

        return data;
    }

    public int lerValorDeposito() {
        int valor;
        int minimo = 1;
        int maximo = 100000000;

        do {
            System.out.println("Valor do deposótio");
            valor = scanner.nextInt();
            if (!estaNoIntervalo(minimo, valor, maximo)) {
                System.out.println("Valora fora do intervalo 1 e 100.000.000");
            }
        } while (!estaNoIntervalo(minimo, valor, maximo));

        return valor;
    }

    public void depositar() {
        Conta conta = buscarContaPeloNumero();
        if (conta == null) {
            System.out.println("Voce ainda nao possui conta");
            return;
        }
        int valorDeposito = this.lerValorDeposito();
        LocalDate data = pegarDataAtual();
        conta.depositar(valorDeposito, data);
    }

    public void sacar() {

        Conta conta = buscarContaPeloNumero();

        double valorSaque = 0;
        if (conta == null) {
            System.out.println("Voce ainda nao possui conta");
        } else {
            System.out.println("Digite o valor do saque");
            valorSaque = scanner.nextInt();

            LocalDate data = pegarDataAtual();
            conta.sacar(valorSaque, data);
        }
    }
    
    public void avancartempo() {
        System.out.println("Quantos dias deseja avançar? ");
        int quantosDiasDesejaPassar = scanner.nextInt();

        this.tempoAtualCaixaEletronico = tempoAtualCaixaEletronico.plusDays(quantosDiasDesejaPassar);
        System.out.println(tempoAtualCaixaEletronico);
        System.out.println("Dia da semana: " + tempoAtualCaixaEletronico.getDayOfWeek().ordinal());
        System.out.println("Mes: " + tempoAtualCaixaEletronico.getMonthValue());
        System.out.println("Mes: " + tempoAtualCaixaEletronico.getMonth().name());
        System.out.println("Ano: " + tempoAtualCaixaEletronico.getYear());
        
        this.emitirAvancoTempoParaContas();
    }
    
    public void emitirAvancoTempoParaContas(){
        for(int i=0; i<contas.size(); i++){
            contas.get(i).avancarTempo(tempoAtualCaixaEletronico);
        }
    }

    public LocalDate pegarDataAtual() {
        return this.tempoAtualCaixaEletronico;
    }

    public void vincularContaSalario() {

        Conta conta = buscarContaPeloNumero();

        if (conta == null) {
            System.out.println("Conta Inexistente");
            return;
        }

        System.out.println("Ler valor do salário");
        double valorSalario = scanner.nextDouble();

        int[] dataPagamentoEmArray = lerData();
        LocalDate dataPagamentoLocalDate = LocalDate.of(dataPagamentoEmArray[2], dataPagamentoEmArray[1],
                dataPagamentoEmArray[0]);

        ContaPagamento contaPagamento = new ContaPagamento(dataPagamentoLocalDate, valorSalario);

        conta.adicionarContaPagamento(contaPagamento);
    }

    public String padLeftZeros(String inputString, int length) {
        if (inputString.length() >= length) {
            return inputString;
        }
        StringBuilder sb = new StringBuilder();
        while (sb.length() < length - inputString.length()) {
            sb.append('0');
        }
        sb.append(inputString);

        return sb.toString();
    }

    public Boleto gerarBoleto(double valor) {
        int maximo = 99999999;

        String codigoBarras = padLeftZeros(rand.nextInt(maximo) + "", 8);
        String codigoBarras2 = padLeftZeros(rand.nextInt(maximo) + "", 8);
        String codigoBarras3 = padLeftZeros(rand.nextInt(maximo) + "", 8);
        String codigoBarras4 = padLeftZeros(rand.nextInt(maximo) + "", 8);
        String codigoBarras5 = padLeftZeros(rand.nextInt(maximo) + "", 8);
        String codigoBarras6 = padLeftZeros(rand.nextInt(maximo) + "", 8);

        String codigoBarrasCompleto = codigoBarras + codigoBarras2 + codigoBarras3 + codigoBarras4 + codigoBarras5
                + codigoBarras6;

        Boleto boleto = new Boleto(codigoBarrasCompleto, valor);
        boletos.add(boleto);
        System.out.println(boleto);
        return boleto;
    }

    public Boleto pegarBoletoGerado(String codigoDeBarras) {
        for (int i = 0; i < boletos.size(); i++) {
            if (boletos.get(i).getCodigo().equalsIgnoreCase(codigoDeBarras)) {
                return boletos.get(i);
            }
        }
        return null;
    }

    public void criarTresBoletos() { // Fazer alguma coisa aqui depois
        gerarBoleto(51);
        gerarBoleto(787);
        gerarBoleto(987);
    }

    public void pagarBoleto() {
        Conta conta = buscarContaPeloNumero();

        System.out.println("Digite o código de barras do boleto");
        scanner.nextLine();

        String codigoDeBarrasBoleto = scanner.nextLine();
        Boleto boleto = pegarBoletoGerado(codigoDeBarrasBoleto);

        conta.pagarBoleto(boleto);

    }

    public void opcoesParaChavePix() {
        System.out.println("[1] - CPF");
        System.out.println("[2] - Telefone");
        System.out.println("[3] - E-mail");
        System.out.println("[4] - Aleatoria");
    }

    public void transferirPorCliente(Conta contaOrigem, Conta contaDestino) {
        System.out.println("Digite o valor a ser transferido");
        double valorTransferencia = scanner.nextDouble();
        LocalDate data = pegarDataAtual();
        contaOrigem.transferir(contaDestino, valorTransferencia, data);
    }

    private void printSemContaCorrente() {
        System.out.println("O cliente não possui conta corrente");
    }

    private void printSemContaPoupanca() {
        System.out.println("O cliente não possui conta corrente");
    }

    public void transferirPorCpf(Conta contaOrigem) {
        Cliente clientePorCpf = buscarcontaPorCpf();

        if (clientePorCpf == null) {
            System.out.println("Cliente não encontrado nesse cpf");
            return;
        }

        int tipoConta = tipoDeConta("Digite o tipo de conta");

        if (tipoConta == 1) {
            if (!clientePorCpf.isTemContaCorrente()) {
                this.printSemContaCorrente();
                return;
            }

            this.transferirPorCliente(contaOrigem, clientePorCpf.getContaCorrente());

        } else {
            if (!clientePorCpf.isTemContaPoupanca()) {
                this.printSemContaPoupanca();
                return;
            }

            this.transferirPorCliente(contaOrigem, clientePorCpf.getContaPoupanca());
        }
    }

    public void transferirPorTelefone(Conta contaOrigem) {
        Cliente clientePorTelefone = buscarPorTelefone();

        if (clientePorTelefone == null) {
            System.out.println("Cliente não encontrado nesse telefone");
            return;
        }

        int tipoConta = tipoDeConta("Digite o tipo de conta");

        if (tipoConta == 1) {
            if (!clientePorTelefone.isTemContaCorrente()) {
                System.out.println("Cliente não possui Conta Corrente");
                return;
            }

            System.out.println("Digite o valor a ser transferido");
            double valorTransferencia = scanner.nextDouble();

            LocalDate data = pegarDataAtual();
            contaOrigem.transferir(clientePorTelefone.getContaCorrente(), valorTransferencia, data);
        } else {
            if (!clientePorTelefone.isTemContaPoupanca()) {
                this.printSemContaPoupanca();
                return;
            }

            this.transferirPorCliente(contaOrigem, clientePorTelefone.getContaPoupanca());
        }
    }

    public void transferirPorEmail(Conta contaOrigem) {
        Cliente clientePorEmail = buscarPorEmail();

        if (clientePorEmail == null) {
            System.out.println("Cliente não encontrado nesse email");
            return;
        }

        int tipoConta = tipoDeConta("Digite o tipo de conta");

        if (tipoConta == 1) {
            if (!clientePorEmail.isTemContaCorrente()) {
                System.out.println("Cliente não possui Conta Corrente");
                return;
            }

            System.out.println("Digite o valor a ser transferido");
            double valorTransferencia = scanner.nextDouble();

            LocalDate data = pegarDataAtual();
            contaOrigem.transferir(clientePorEmail.getContaCorrente(), valorTransferencia, data);
        }
    }

    public void transferirPix() {
        Conta contaOrigem = buscarContaPeloNumero();

        if (contaOrigem == null) {
            System.out.println("Voce ainda nao possui conta corrente");
            return;
        }

        int tipoChave = 0;

        System.out.println("Para qual chave a transferencia sera feita");
        opcoesParaChavePix();

        tipoChave = scanner.nextInt();
        scanner.nextLine();

        if (tipoChave == 1) {
            this.transferirPorCpf(contaOrigem);
        } else if (tipoChave == 2) {
            this.transferirPorTelefone(contaOrigem);
        } else if (tipoChave == 3) {
            this.transferirPorEmail(contaOrigem);
        }
    }
}
