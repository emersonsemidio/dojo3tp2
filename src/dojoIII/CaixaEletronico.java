package dojoIII;

import java.util.*;
import java.time.LocalDate;



public class CaixaEletronico {
    Scanner scanner = new Scanner(System.in);
    Random rand = new Random();

    List<Cliente> clientes = new ArrayList<>();
    List<Conta> contas = new ArrayList<>();
    List<Boleto> boletos = new ArrayList<>();


    Cliente clienteA = new Cliente();

    ContaCorrente contaCorrente = new ContaCorrente();
    ContaPoupanca contaPoupanca = new ContaPoupanca(1, 1);
    Calendar c = Calendar.getInstance();
    int tempo = 0;
    int dia, mes, ano;
    ArrayList<Extrato> listaExtratos = new ArrayList<>();


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
        //int numeroConta = 0;

        if (clienteExistente == null) {
            System.out.println("Qual seu CPF?");
            clienteNovo.setCpf(scanner.nextLine());

            System.out.println("Qual seu nome?");
            clienteNovo.setNome(scanner.nextLine());


            /*System.out.println("Qual sua data de nascimento? (digite separado por espacos DD MM AAAA)");

            c.set(Calendar.YEAR, dia = scanner.nextInt());
            c.set(Calendar.MONTH, mes = scanner.nextInt());
            c.set(Calendar.DAY_OF_MONTH, scanner.nextInt());*/

            System.out.println("Qual seu e-mail?");
            clienteNovo.setEmail(scanner.nextLine());

            System.out.println("Qual seu telefone?");
            clienteNovo.setTelefone(scanner.nextLine());

        } else {
            if (!clienteExistente.podeAbrirConta()) {
                System.out.println("Cliente já cadastrado com o CPF e possui contas Corrente e Poupança. Veja abaixo os dados da suas contas");
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
        ContaCorrente contaCorrente = new ContaCorrente(senhaConta, numeroConta);
        contas.add(contaCorrente);
        clienteNovo.setContaCorrente(contaCorrente);
        contaCorrente.setCliente(clienteNovo);
    }

    private void abrirContaPoupanca(int senhaConta, int numeroConta, Cliente clienteNovo) {
        ContaPoupanca contaPoupanca = new ContaPoupanca(senhaConta, numeroConta);
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

    public boolean estaNoIntervalo(int valorMenor, int valor, int valorMaior) {

        return (valorMenor <= valor) && (valor <= valorMaior);
    }

    public int[] lerData() {
        int[] data = new int[3];

        LocalDate dataAtual = pegarDataAtual();

        data[0] = dataAtual.getDayOfMonth();
        data[1] = dataAtual.getMonthValue();
        data[2] = dataAtual.getYear();

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
        int data[] = this.lerData();
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

            int[] data = this.lerData();
            conta.sacar(valorSaque, data);
        }
    }

    LocalDate localDate = LocalDate.now();

    public void avancartempo() {
        System.out.println(localDate);
        System.out.println("Dia da semana: " + localDate.getDayOfWeek().name());
        System.out.println("Dia da semana: " + localDate.getDayOfWeek().ordinal());
        System.out.println("Mes: " + localDate.getMonthValue());
        System.out.println("Mes: " + localDate.getMonth().name());
        System.out.println("Ano: " + localDate.getYear());
    }

    public LocalDate pegarDataAtual(){
        LocalDate dataAtual = LocalDate.now();
        return dataAtual;
    }

    public void vincularContaSalario() {
        if (clienteA.isTemContaCorrente() == false) {
            System.out.println("Voce nao tem conta corrente");
        } else {
            if (clienteA.isVinculoContaSalario() == true) {
                System.out.println("Voce ja possui sua conta-salario vinculada");
            } else {
                System.out.println("Qual o valor do seu salario?");
                clienteA.setValorSalario(scanner.nextInt());
                clienteA.setVinculoContaSalario(true);
            }
        }
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

    public Boleto gerarBoleto(double valor){
        int maximo = 99999999;

        String codigoBarras = padLeftZeros(rand.nextInt(maximo) + "", 8);
        String codigoBarras2 = padLeftZeros(rand.nextInt(maximo) + "", 8);
        String codigoBarras3 = padLeftZeros(rand.nextInt(maximo) + "", 8);
        String codigoBarras4 = padLeftZeros(rand.nextInt(maximo) + "", 8);
        String codigoBarras5 = padLeftZeros(rand.nextInt(maximo) + "", 8);
        String codigoBarras6 = padLeftZeros(rand.nextInt(maximo) + "", 8);

        String codigoBarrasCompleto = codigoBarras + codigoBarras2 + codigoBarras3 + codigoBarras4 + codigoBarras5 + codigoBarras6;

        Boleto boleto = new Boleto(codigoBarrasCompleto, valor);
        boletos.add(boleto);
        System.out.println(boleto);
        return boleto;
    }

    public Boleto pegarBoletoGerado(String codigoDeBarras){
        for(int i=0; i<boletos.size(); i++){
            if(boletos.get(i).getCodigo().equalsIgnoreCase(codigoDeBarras)){
                return boletos.get(i);
            }
        }
        return null;
    }

    public void criarTresBoletos(){ // Fazer alguma coisa aqui depois
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
        int[] data = lerData();
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

        if (tipoConta == 1){
            if(!clientePorCpf.isTemContaCorrente()){
                this.printSemContaCorrente();
                return;
            }

            this.transferirPorCliente(contaOrigem, clientePorCpf.getContaCorrente());

        }else{
            if(!clientePorCpf.isTemContaPoupanca()){
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

            int[] data = lerData();
            contaOrigem.transferir(clientePorTelefone.getContaCorrente(), valorTransferencia, data);
        } else{
            if(!clientePorTelefone.isTemContaPoupanca()){
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

            int[] data = lerData();
            contaOrigem.transferir(clientePorEmail.getContaCorrente(), valorTransferencia, data);
        }
    }


    public void transferirPix() {
        Pix pix = new Pix();
        Conta contaOrigem = buscarContaPeloNumero();

        if (contaOrigem == null) {
            System.out.println("Voce ainda nao possui conta corrente");
            return;
        }

        int tipoChave = 0;
        int chaveDestino = 0;

        System.out.println("Para qual chave a transferencia sera feita");
        opcoesParaChavePix();

        tipoChave = scanner.nextInt();
        scanner.nextLine();

        if (tipoChave == 1) {
            this.transferirPorCpf(contaOrigem);
        } else if (tipoChave == 2) {
            this.transferirPorTelefone(contaOrigem);
        } else if(tipoChave == 3){
            this.transferirPorEmail(contaOrigem);
        }
    }
}




