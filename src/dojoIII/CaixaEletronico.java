package dojoIII;

import java.util.*;



public class CaixaEletronico {
    Scanner scanner = new Scanner(System.in);
    Random rand = new Random();

    HashMap<String, Cliente> clientes = new HashMap<>();
    List<Conta> contas = new ArrayList<>();


    Cliente clienteA = new Cliente();

    ContaCorrente contaCorrente = new ContaCorrente();
    ContaPoupanca contaPoupanca = new ContaPoupanca(1,1);
    Calendar c = Calendar.getInstance();
    int tempo = 0;
    int dia, mes, ano;
    ArrayList<Extrato> listaExtratos = new ArrayList<>();


    public void iniciarAbrirConta(){
        int tipoConta = tipoDeConta();
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



    public void AbrirConta(int tipoConta){
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

        clientes.put(clienteNovo.getCpf(), clienteNovo);

        if(tipoConta == 1){
            this.abrirContaCorrente(senhaConta, numeroConta, clienteNovo);
        }else{
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

    public int tipoDeConta(){
        int tipoConta;
        System.out.println("Qual conta voce deseja criar?");
        System.out.println("[1] Criar conta corrente");
        System.out.println("[2] Criar conta poupanca");

        do{
            System.out.println("Informe o tipo de conta");
            tipoConta = scanner.nextInt();
            if(tipoConta<1 && tipoConta>2){
                System.out.println("tipo de conta inexistente");
            }
        }while(tipoConta<1 || tipoConta>2);
        scanner.nextLine();

        return tipoConta;
    }

    public boolean verificarExistenciaConta(){
      String cpf;

      System.out.println("Digite seu cpf");
      cpf = scanner.nextLine();

      return clientes.containsKey(cpf);
    }

    public Cliente buscarcontaPorCpf(){
        String cpf;

        System.out.println("Digite seu cpf");
        cpf = scanner.nextLine();

        for(int i=0; i<clientes.size(); i++){
            if(clientes.get(i).getCpf().equalsIgnoreCase(cpf)){
                return clientes.get(i);
            }
        }
        return null;
    }

    public Cliente buscarPorTelefone(){
        String telefone;


        System.out.println("Digite o telefone");
        telefone = scanner.nextLine();

        for(int i=0; i<clientes.size(); i++){
            if(clientes.get(i).getTelefone().equalsIgnoreCase(telefone)){
                return clientes.get(i);
            }
        }
        return null;
    }

    public Cliente buscarPorEmail(){
        String email;

        System.out.println("Digite o email");
        email = scanner.nextLine();

        for(int i=0; i<clientes.size(); i++){
            if(clientes.get(i).getEmail().equalsIgnoreCase(email)){
                return clientes.get(i);
            }
        }
        return null;
    }

    public Conta buscarContaPeloNumero(){
        int numeroConta;

        System.out.println("Digite o número da conta");
        numeroConta = scanner.nextInt();
        return contas.get(numeroConta);
    }

    public boolean estaNoIntervalo(int valorMenor, int valor, int valorMaior){

        return (valorMenor <= valor) && (valor <= valorMaior);
    }

    public int[] lerData(){
        int[] data = new int[3];

        do{
            System.out.println("Dia");
            data[0] = scanner.nextInt();
            if(!estaNoIntervalo(1, data[0], 30 )){
                System.out.println("Dia inválido");
            }
        }while (!estaNoIntervalo(1, data[0], 30 ));

        do{
            System.out.println("mes");
            data[1] = scanner.nextInt();
            if(!estaNoIntervalo(1, data[1], 12)){
                System.out.println("Mês inválido");
            }
        }while (!estaNoIntervalo(1, data[1], 12));

        System.out.println("ano");
        data[2] = scanner.nextInt();

        return data;

    }

    public int lerValorDeposito() {
        int valor;
        int minimo = 1;
        int maximo = 100000000;

        do{
            System.out.println("Valor do deposótio");
            valor = scanner.nextInt();
            if(!estaNoIntervalo(minimo, valor, maximo )){
                System.out.println("Valora fora do intervalo 1 e 100.000.000");
            }
        }while (!estaNoIntervalo(minimo, valor, maximo ));


        return valor;
    }

    public void depositar() {
        Conta conta = buscarContaPeloNumero();
        if (conta == null) {
            System.out.println("Voce ainda nao possui conta");
            return;
        }
        int valorDeposito = this.lerValorDeposito();
        int[] data = this.lerData();
        conta.depositar(valorDeposito, data);
    }
    public void sacar(){

        Conta conta = buscarContaPeloNumero();

        double valorSaque = 0;
        if (conta == null) {
            System.out.println("Voce ainda nao possui conta");
        } else{
            System.out.println("Digite o valor do saque");
            valorSaque = scanner.nextInt();

            int data[] = lerData();
            conta.sacar(valorSaque, data);
        }
    }

    public void avancatempo(){
        double novoSaldo = 0;
        System.out.println("Quantos dias voce deseja avancar?");
        tempo = scanner.nextInt() / 30;
        int mes = listaExtratos.get(0).mes;
        for(int i=0; i<tempo; i++) {
            mes++;
            if (clienteA.isVinculoContaSalario() == true) {
                contaCorrente.setSaldoCorrente(contaCorrente.getSaldoCorrente() + clienteA.getValorSalario());
                Extrato extrato = new Extrato(clienteA.getValorSalario(), listaExtratos.get(0).dia, mes, listaExtratos.get(0).ano, "Conta salario para conta corrente");
                listaExtratos.add(extrato);
            }
            novoSaldo = contaPoupanca.getSaldoContaPoupanca() + contaPoupanca.getSaldoContaPoupanca() * 0.03;
        }
    }
    public void vincularContaSalario(){
        if(clienteA.isTemContaCorrente() == false){
            System.out.println("Voce nao tem conta corrente");
        }
        else {
            if (clienteA.isVinculoContaSalario() == true) {
                System.out.println("Voce ja possui sua conta-salario vinculada");
            }
            else {
                System.out.println("Qual o valor do seu salario?");
                clienteA.setValorSalario(scanner.nextInt());
                clienteA.setVinculoContaSalario(true);
            }
        }
    }
    public void pagarBoleto(){
        Boleto boleto = new Boleto();
        if(clienteA.isTemContaCorrente() == false){
            System.out.println("Voce nao tem conta corrente");
        }
        else {
            System.out.println("Digite o codigo do boleto: ");
            String codigoBoleto = scanner.nextLine();
            boleto.setCodigo(codigoBoleto);
            scanner.nextLine();
            System.out.println("Digite o valor do boleto: ");
            double valorBoleto = scanner.nextInt();
            boleto.setPreco(valorBoleto);
            if (contaCorrente.getSaldoCorrente() + contaCorrente.getChequeEspecial() < valorBoleto) {
                System.out.println("Saldo insuficiente");
            }
            else if (contaCorrente.getSaldoCorrente() < valorBoleto) {
                valorBoleto -= contaCorrente.getSaldoCorrente();
                contaCorrente.setSaldoCorrente(0);
                contaCorrente.setChequeEspecial(contaCorrente.getChequeEspecial() - valorBoleto);
            }
            else if (contaCorrente.getSaldoCorrente() > valorBoleto) {
                contaCorrente.setSaldoCorrente(contaCorrente.getSaldoCorrente() - valorBoleto);
            }
        }
    }

    public void opcoesParaChavePix(){
        System.out.println("[1] - CPF");
        System.out.println("[2] - Telefone");
        System.out.println("[3] - E-mail");
        System.out.println("[4] - Aleatoria");
    }


    public void transferirPix(){
        Pix pix = new Pix();
        if(buscarContaPeloNumero() == null){
            System.out.println("Voce ainda nao possui conta corrente");
            return;
        }

        Conta contaOrigem = buscarContaPeloNumero();

        System.out.println("Escolha por qual chave voce quer fazer a transferencia: ");
        opcoesParaChavePix();

        int tipoChave = 0;
        int chaveDestino = 0;

        System.out.println("Para qual chave a transferencia sera feita");
        opcoesParaChavePix();

        tipoChave = scanner.nextInt();

        if (tipoChave == 1) {
            Cliente clientePorCpf = buscarcontaPorCpf();

            if(clientePorCpf == null){
                System.out.println("Cliente não encontrado nesse cpf");
                return;
            }
            int tipoConta = tipoDeConta();

            if(tipoConta == 1){
                if(!clientePorCpf.isTemContaCorrente()){
                    return;
                }

                double valorTransferencia = scanner.nextDouble();
                System.out.println("Digite o valor a ser transferido");

                contaOrigem.transferir(clientePorCpf.getContaCorrente(), valorTransferencia);

            }else{
                if(!clientePorCpf.isTemContaPoupanca()){
                    return;
                }
            }

        } else if (tipoChave == 2) {
            Cliente clientePorTelefone = buscarPorTelefone();

            if(clientePorTelefone == null){
                System.out.println("Cliente não encontrado nesse telefone");
                return;
            }

        } else if (tipoChave == 3) {
            Cliente clientePorEmail = buscarPorEmail();

            if(clientePorEmail == null){
                System.out.println("Cliente não encontrado nesse telefone");
                return;
            }


        } else if (tipoChave == 4) {
            System.out.println("Para qual chave a transferencia sera feita");
            opcoesParaChavePix();
            chaveDestino = scanner.nextInt();
            scanner.nextLine();
            if (chaveDestino == 1) {
                System.out.println("Digite a chave CPF");
                scanner.nextLine();
                String chaveCPF = scanner.nextLine();
                pix.setCpf(chaveCPF);
            } else if (chaveDestino == 2) {
                System.out.println("Digite a chave telefone");
                scanner.nextLine();
                String chaveTelefone = scanner.nextLine();
                pix.setTelefone(chaveTelefone);
            } else if (chaveDestino == 3) {
                System.out.println("Digite a chave e-mail");
                scanner.nextLine();
                String chaveemail = scanner.nextLine();
                pix.setEmail(chaveemail);;
            } else if (chaveDestino == 4) {
                System.out.println("Digite a chave aleatoria");
                pix.setRandom(scanner.nextInt());
            }
           /* System.out.println("Qual o valor a pagar?");
            valortransferencia = scanner.nextDouble();
            if (contaCorrente.getSaldoCorrente() + contaCorrente.getChequeEspecial() < valortransferencia) {
                System.out.println("Valor na conta insuficiente");
            } else if (contaCorrente.getSaldoCorrente() < valortransferencia) {
                valortransferencia -= contaCorrente.getSaldoCorrente();
                contaCorrente.setSaldoCorrente(0);
                contaCorrente.setChequeEspecial(contaCorrente.getChequeEspecial() - valortransferencia);
            } else if (contaCorrente.getSaldoCorrente() > valortransferencia) {
                contaCorrente.setSaldoCorrente(contaCorrente.getSaldoCorrente() - valortransferencia);

            }

            */
        }

    }
}



