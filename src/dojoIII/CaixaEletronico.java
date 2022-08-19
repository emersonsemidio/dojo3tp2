package dojoIII;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.LocalDate;

public class CaixaEletronico {
    Scanner scanner = new Scanner(System.in);
    Random rand = new Random();

    List<Cliente> clientes = new ArrayList<>();
    List<Conta> contas = new ArrayList<>();
    List<Boleto> boletos = new ArrayList<>();
    LocalDate tempoAtualCaixaEletronico = LocalDate.now();
    
    public void iniciarAbrirConta() {
        int tipoConta = tipoDeConta("Qual tipo de conta você quer abrir");
        AbrirConta(tipoConta);
    }

    public void configurarContaSalario(Conta conta) {
        System.out.println("Informe somente o dia do pagamento");
        int diaPagamento = scanner.nextInt();

        System.out.println("Informe o valor do pagamento");
        double valorPagamento = scanner.nextDouble();

        conta.setContaSalario(new ContaSalario(diaPagamento, valorPagamento));
        System.out.println("Conta salário cadastrada com sucesso na conta " + conta.getNumeroDaConta() + ". Cliente: " + conta.getCliente().getNome());
    }

    public void AbrirConta(int tipoConta) {
        Cliente clienteNovo;
        
        String cpf = this.lerLinha("Digite o cpf");
        
        Cliente clienteExistente = verificarExistenciaDoCpf(cpf);

        if (clienteExistente == null) {
            String nome = this.lerLinha("Qual seu nome?");
            String dataNascimento = this.lerLinha("Qual sua data de nascimento? (digite separado por espacos DD/MM/AAAA)");
            String email = this.lerLinha("Qual seu e-mail?");
            String telefone = this.lerLinha("Qual seu telefone?");
            clienteNovo = new Cliente(nome, cpf, dataNascimento, email, telefone);
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

        String senhaConta = this.lerLinha("Digite qual senha deseja utilizar para sua conta senha?");
        String numeroConta = this.gerarNumeroConta();

        clientes.add(clienteNovo);
        Conta conta;

        if (tipoConta == 1) {
            conta = this.abrirContaCorrente(senhaConta, numeroConta, clienteNovo);
        } else {
            conta = this.abrirContaPoupanca(senhaConta, numeroConta, clienteNovo);
        }
        System.out.println("Conta criada com sucesso " + numeroConta);

        System.out.println("Deseja configurar conta salário?");
        System.out.println("[1] - Sim");
        System.out.println("[2] - Não");
        String contaSalarioOpcao = scanner.nextLine();
        if (contaSalarioOpcao.equalsIgnoreCase("1")) {
            this.configurarContaSalario(conta);
        }
        conta.avancarTempo(this.pegarDataAtual());
    }
    
    // Método auxiliar para evitar os casos de nextAlgumTipo antes de um nextLine.
    private String lerLinha(String msg) {
        System.out.println(msg);
        String linha = scanner.nextLine();
        while(linha.length() == 0) {
            linha = scanner.nextLine();
        }
        return linha;
    }

    private String gerarNumeroConta() {
        return rand.nextInt(10000) + "";
    }

    private Conta abrirContaCorrente(String senhaConta, String numeroConta, Cliente clienteNovo) {
        ContaCorrente contaCorrente = new ContaCorrente(numeroConta, senhaConta);
        contas.add(contaCorrente);
        clienteNovo.setContaCorrente(contaCorrente);
        contaCorrente.setCliente(clienteNovo);
        return contaCorrente;
    }

    private Conta abrirContaPoupanca(String senhaConta, String numeroConta, Cliente clienteNovo) {
        ContaPoupanca contaPoupanca = new ContaPoupanca(numeroConta, senhaConta);
        contas.add(contaPoupanca);
        clienteNovo.setContaPoupanca(contaPoupanca);
        contaPoupanca.setCliente(clienteNovo);
        return contaPoupanca;
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

    public Cliente verificarExistenciaDoCpf(String cpf) {
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getCpf().equalsIgnoreCase(cpf)) {
                return clientes.get(i);
            }
        }
        return null;
    }

    public Cliente buscarContaPorCpfLido() {
        String cpf;
        
        System.out.println("Digite o cpf");
        cpf = scanner.next();

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
        telefone = scanner.next();

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
        email = scanner.next();

        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getEmail().equalsIgnoreCase(email)) {
                return clientes.get(i);
            }
        }
        return null;
    }
    
    public Conta buscarContaPeloNumero(String numeroConta) {
        
        for (int i = 0; i < contas.size(); i++) {
            if (contas.get(i).getNumeroDaConta().equalsIgnoreCase(numeroConta)) {
                return contas.get(i);
            }
        }
        return null;
    }

    public void emitirExtrato(Conta conta) {
        conta.emitirExtrato();
    }

    public boolean estaNoIntervalo(int valorMenor, int valor, int valorMaior) {
        return (valorMenor <= valor) && (valor <= valorMaior);
    }


    public void depositar(Conta conta) {
        int valorDeposito = this.lerValorDeposito();
        LocalDate data = pegarDataAtual();
        String descricao = this.lerLinha("Digite uma descrição para o depósito");
        conta.depositar(valorDeposito, data, descricao);
    }

    public void sacar(Conta conta) {
        double valorSaque = this.lerValorPositivo("Digite o valor do saque");
        String descricao = this.lerLinha("Digite uma descrição para o saque");
        LocalDate data = pegarDataAtual();
        conta.sacar(valorSaque, data, descricao);
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
        for(Conta conta : contas){
            conta.depositarPagamento(tempoAtualCaixaEletronico);
            if (conta instanceof ContaPoupanca) {
                ContaPoupanca contaPoupanca = (ContaPoupanca) conta;
                contaPoupanca.renderPoupanca(tempoAtualCaixaEletronico);
            }
            conta.avancarTempo(this.pegarDataAtual());
        }
    }

    public LocalDate pegarDataAtual() {
        
        return this.tempoAtualCaixaEletronico;
    }
    
    // Boleto
    
    private String lerCodigoBarras() {
        String codigoBarras = this.lerLinha("Digite os 48 dígitos do código de barras do boleto");
        while(codigoBarras.length() != 48) {
            System.out.println("Código de barras inválido. Digite novamente");
            codigoBarras = this.lerLinha("Digite os 48 dígitos do código de barras do boleto");
        }
        return codigoBarras;
    }
    
    private LocalDate lerDataVencimento() {
        System.out.println("Digite a data no formato DD/MM/AAAA");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataPagamento = LocalDate.parse(scanner.nextLine(), dateTimeFormatter);
        return dataPagamento;
    }
    
    private double lerValorBoleto() {
        System.out.println("Digite o valor do boleto");
        double valor = scanner.nextDouble();
        scanner.nextLine();
        return valor;
    }

    public void pagarBoleto(Conta conta) {
        String codigoDeBarrasBoleto = this.lerCodigoBarras();
        LocalDate dataVencimento = this.lerDataVencimento();
        double valorBoleto = this.lerValorBoleto();
        Boleto boletoAPaga = new Boleto(codigoDeBarrasBoleto, valorBoleto, dataVencimento);
        
        boolean pagoComSucesso = conta.pagarBoleto(boletoAPaga, this.tempoAtualCaixaEletronico);
        if (pagoComSucesso) {
            this.boletos.add(boletoAPaga);
        }
    }

    // Transferencia PIX
    
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
        String descricao = this.lerLinha("Informe uma descrição para a transferência");
        contaOrigem.transferir(contaDestino, valorTransferencia, data, descricao);
    }


    public void transferirPorCpf(Conta contaOrigem) {
        Cliente clientePorCpf = buscarContaPorCpfLido();

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
            String descricao = this.lerLinha("Informe uma descrição para a transferência por telefone");
            contaOrigem.transferir(clientePorTelefone.getContaCorrente(), valorTransferencia, data, descricao);
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
            String descricao = this.lerLinha("Informe uma descrição para a transferência por e-mail");
            contaOrigem.transferir(clientePorEmail.getContaCorrente(), valorTransferencia, data, descricao);
        }
    }

    private void printSemContaCorrente() {
        System.out.println("O cliente não possui conta corrente");
    }

    private void printSemContaPoupanca() {
        System.out.println("O cliente não possui conta corrente");
    }

    public void transferirPix(Conta contaOrigem) {
        int tipoChave = 0;
        System.out.println("Para qual chave a transferencia sera feita");
        opcoesParaChavePix();

        tipoChave = scanner.nextInt();

        if (tipoChave == 1) {
            this.transferirPorCpf(contaOrigem);
        } else if (tipoChave == 2) {
            this.transferirPorTelefone(contaOrigem);
        } else if (tipoChave == 3) {
            this.transferirPorEmail(contaOrigem);
        } else {
            System.out.println("Opção " + tipoChave + "Inválida");
        }
    }
    
    // Métodos de Leitura
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

    private double lerValorPositivo(String msg) {
        System.out.println(msg);
        double valor = scanner.nextDouble();
        while (valor < 0.0) {
            System.out.println("O valor deve ser maior que zero");
            valor = scanner.nextDouble();
        }
        return valor;
    }
}
