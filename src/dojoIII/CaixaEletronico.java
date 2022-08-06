package dojoIII;

import java.util.*;



public class CaixaEletronico {
    Scanner scanner = new Scanner(System.in);
    Random rand = new Random();

    HashMap<String, Cliente> clientes = new HashMap<>();
    HashMap<Integer, Conta> contas = new HashMap<>();


    Cliente cliente = new Cliente();
    Conta conta = new Conta();

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

    public void extrato() {}

    public void AbrirConta(int tipoConta){
        Cliente clienteNovo = new Cliente();

        System.out.println("Qual seu CPF?");
        clienteNovo.setCpf(scanner.nextLine());

        System.out.println("Qual seu nome?");
        clienteNovo.setNome(scanner.nextLine());


        System.out.println("Qual sua data de nascimento? (digite separado por espacos DD MM AAAA)");

        c.set(Calendar.YEAR, dia = scanner.nextInt());
        c.set(Calendar.MONTH, mes = scanner.nextInt());
        c.set(Calendar.DAY_OF_MONTH, scanner.nextInt());

        System.out.println("Qual seu e-mail?");
        cliente.setEmail(scanner.nextLine());

        clienteNovo.setEmail(scanner.nextLine());
        System.out.println("Qual seu telefone?");

        clienteNovo.setTelefone(scanner.nextLine());

        System.out.println("Qual sua senha?");

        int senhaConta = scanner.nextInt();
        int numeroConta = rand.nextInt(10000);

        clientes.put(cliente.getCpf(), clienteNovo);

        if(tipoConta == 1){
            ContaCorrente contaCorrente = new ContaCorrente(senhaConta, numeroConta);
            contas.put(contaCorrente.getNumeroDaConta(), contaCorrente);
            clienteNovo.setContaCorrente(contaCorrente);
            contaCorrente.setCliente(clienteNovo);

        }else{
            ContaPoupanca contaPoupanca = new ContaPoupanca(senhaConta, numeroConta);
            contas.put(contaPoupanca.getNumeroDaConta(), contaPoupanca);
            clienteNovo.setContaPoupanca(contaPoupanca);
            contaPoupanca.setCliente(clienteNovo);
        }
        System.out.println("Conta criada com sucesso " + numeroConta);
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
        return clientes.get(cpf);

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

        /*
            if (cliente.isTemContaCorrente() == true && cliente.isTemContaPoupanca() == true) {
                System.out.println("Em qual conta voce deseja depositar?");
                System.out.println("[1] Conta corrente");
                System.out.println("[2] Conta poupanca");
                int tipoConta = tipoDeConta();
                if (tipoConta == 1) {

                    System.out.println("Quanto voce deseja depositar?");
                    valorDeposito = scanner.nextInt();

                    int data[] = lerData();

                    contaCorrente.setSaldoCorrente(contaCorrente.getSaldoCorrente() + valorDeposito);

                } else if (tipoConta == 2) {
                    System.out.println("Quanto voce deseja depositar?");
                    valorDeposito = scanner.nextInt();

                    int data[] = lerData();


                } else {
                    System.out.println("Valor Invalido");
                }
            } else if (cliente.isTemContaCorrente() == false && cliente.isTemContaPoupanca() == true) {
                System.out.println("Quanto voce deseja depositar?");
                valorDeposito = scanner.nextInt();

                int data[] = lerData();


            } else if (cliente.isTemContaCorrente() == true && cliente.isTemContaPoupanca() == false) {
                System.out.println("Quanto voce deseja depositar?");
                valorDeposito = scanner.nextInt();

                int data[] = lerData();

                contaCorrente.setSaldoCorrente(contaCorrente.getSaldoCorrente() + valorDeposito);
            }*/

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

        /*
        int qualConta;

        qualConta = tipoDeConta();
        if(qualConta == 1){
            double limite = contaCorrente.getChequeEspecial() + contaCorrente.getSaldoCorrente();
            System.out.println("Digite quanto voce quer sacar: ");
            valorSaque = scanner.nextInt();
            if(valorSaque > limite){
                System.out.println("O valor eh maior do que o saque possivel");
            }
            else{
                if(valorSaque >= contaCorrente.getSaldoCorrente()){

                    int data[] = lerData();

                    valorSaque -= contaCorrente.getSaldoCorrente();
                    contaCorrente.setSaldoCorrente(0);
                    contaCorrente.setChequeEspecial(contaCorrente.getChequeEspecial() - valorSaque);
                    System.out.println("Saldo atualizado: " + contaCorrente.getSaldoCorrente());
                    System.out.println("Valor restante do cheque especial: " + contaCorrente.getChequeEspecial());
                    Extrato extrato = new Extrato(valorSaque, dia, mes, ano,"Saque da conta Corrente");
                    listaExtratos.add(extrato);

                }
                else{
                    contaCorrente.setSaldoCorrente(contaCorrente.getSaldoCorrente() - valorSaque);

                    int data[] = lerData();

                    System.out.println("Valor sacado com sucesso");
                    System.out.println("Saldo atualizado: " + contaCorrente.getSaldoCorrente());
                    Extrato extrato = new Extrato(valorSaque, dia, mes, ano,"Saque da conta Poupanca");
                    listaExtratos.add(extrato);
                }
            }
        }
        else if(qualConta == 2){
            System.out.println("Digite quanto voce quer sacar: ");
            valorSaque = scanner.nextInt();
            if(valorSaque > contaPoupanca.getSaldoContaPoupanca()){
                System.out.println("Valor maior que o saque possivel");
            }
            else if(valorSaque <= contaPoupanca.getSaldoContaPoupanca()){

                int data[] = lerData();

                System.out.println("Valor sacado com sucesso");
                System.out.println("Saldo atualizado: " + contaPoupanca.getSaldoContaPoupanca());
                Extrato extrato = new Extrato(valorSaque, dia, mes, ano,"Saque da conta poupanca");
                listaExtratos.add(extrato);
            }
            else{
                System.out.println("Valor invalido");
            }
        }*/
    }




    public void avancatempo(){
        double novoSaldo = 0;
        System.out.println("Quantos dias voce deseja avancar?");
        tempo = scanner.nextInt() / 30;
        int mes = listaExtratos.get(0).mes;
        for(int i=0; i<tempo; i++) {
            mes++;
            if (cliente.isVinculoContaSalario() == true) {
                contaCorrente.setSaldoCorrente(contaCorrente.getSaldoCorrente() + cliente.getValorSalario());
                Extrato extrato = new Extrato(cliente.getValorSalario(), listaExtratos.get(0).dia, mes, listaExtratos.get(0).ano, "Conta salario para conta corrente");
                listaExtratos.add(extrato);
            }
            novoSaldo = contaPoupanca.getSaldoContaPoupanca() + contaPoupanca.getSaldoContaPoupanca() * 0.03;
            Extrato extrato = new Extrato(contaPoupanca.getSaldoContaPoupanca() * 0.03, listaExtratos.get(0).dia, mes, listaExtratos.get(0).ano, "Rendimento poupanca");
            listaExtratos.add(extrato);
        }
    }
    public void vincularContaSalario(){
        if(cliente.isTemContaCorrente() == false){
            System.out.println("Voce nao tem conta corrente");
        }
        else {
            if (cliente.isVinculoContaSalario() == true) {
                System.out.println("Voce ja possui sua conta-salario vinculada");
            }
            else {
                System.out.println("Qual o valor do seu salario?");
                cliente.setValorSalario(scanner.nextInt());
                cliente.setVinculoContaSalario(true);
            }
        }
    }
    public void pagarBoleto(){
        Boleto boleto = new Boleto();
        if(cliente.isTemContaCorrente() == false){
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
                Extrato extrato = new Extrato(valorBoleto, listaExtratos.get(0).dia, listaExtratos.get(0).mes, listaExtratos.get(0).ano, "Pagamento Boleto");
                listaExtratos.add(extrato);
            }
            else if (contaCorrente.getSaldoCorrente() > valorBoleto) {
                contaCorrente.setSaldoCorrente(contaCorrente.getSaldoCorrente() - valorBoleto);
                Extrato extrato = new Extrato(valorBoleto, listaExtratos.get(0).dia, listaExtratos.get(0).mes, listaExtratos.get(0).ano, "Pagamento Boleto");
                listaExtratos.add(extrato);
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
        if(cliente.isTemContaCorrente() == false){
            System.out.println("Voce ainda nao possui conta corrente");
        }
        else {

            System.out.println("Escolha por qual chave voce quer fazer a transferencia: ");
            opcoesParaChavePix();

            int tipoChave = scanner.nextInt();
            int chaveDestino = 0;
            double valortransferencia = 0;
            if (tipoChave == 1) {

                System.out.println("Para qual chave a transferencia sera feita");
                opcoesParaChavePix();

                chaveDestino = scanner.nextInt();
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
                    pix.setEmail(chaveemail);
                } else if (chaveDestino == 4) {
                    System.out.println("Digite a chave aleatoria");
                    pix.setRandom(scanner.nextInt());
                }
                System.out.println("Qual o valor a pagar?");
                valortransferencia = scanner.nextDouble();
                if (contaCorrente.getSaldoCorrente() + contaCorrente.getChequeEspecial() < valortransferencia) {
                    System.out.println("Valor na conta insuficiente");
                } else if (contaCorrente.getSaldoCorrente() < valortransferencia) {
                    valortransferencia -= contaCorrente.getSaldoCorrente();
                    contaCorrente.setSaldoCorrente(0);
                    contaCorrente.setChequeEspecial(contaCorrente.getChequeEspecial() - valortransferencia);
                    Extrato extrato = new Extrato(valortransferencia, listaExtratos.get(0).dia, listaExtratos.get(0).mes, listaExtratos.get(0).ano, "Pagamento por pix - CPF");
                    listaExtratos.add(extrato);
                } else if (contaCorrente.getSaldoCorrente() > valortransferencia) {
                    contaCorrente.setSaldoCorrente(contaCorrente.getSaldoCorrente() - valortransferencia);
                    Extrato extrato = new Extrato(valortransferencia, listaExtratos.get(0).dia, listaExtratos.get(0).mes, listaExtratos.get(0).ano, "Pagamento por pix - CPF");
                    listaExtratos.add(extrato);
                }
            } else if (tipoChave == 2) {
                System.out.println("Para qual chave a transferencia sera feita");
                opcoesParaChavePix();

                chaveDestino = scanner.nextInt();
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
                    pix.setEmail(chaveemail);
                } else if (chaveDestino == 4) {
                    System.out.println("Digite a chave aleatoria");
                    pix.setRandom(scanner.nextInt());
                }
                System.out.println("Qual o valor a pagar?");
                valortransferencia = scanner.nextDouble();
                if (contaCorrente.getSaldoCorrente() + contaCorrente.getChequeEspecial() < valortransferencia) {
                    System.out.println("Valor na conta insuficiente");
                } else if (contaCorrente.getSaldoCorrente() < valortransferencia) {
                    valortransferencia -= contaCorrente.getSaldoCorrente();
                    contaCorrente.setSaldoCorrente(0);
                    contaCorrente.setChequeEspecial(contaCorrente.getChequeEspecial() - valortransferencia);
                    Extrato extrato = new Extrato(valortransferencia, listaExtratos.get(0).dia, listaExtratos.get(0).mes, listaExtratos.get(0).ano, "Pagamento por pix - Telefone");
                    listaExtratos.add(extrato);
                } else if (contaCorrente.getSaldoCorrente() > valortransferencia) {
                    contaCorrente.setSaldoCorrente(contaCorrente.getSaldoCorrente() - valortransferencia);
                    Extrato extrato = new Extrato(valortransferencia, listaExtratos.get(0).dia, listaExtratos.get(0).mes, listaExtratos.get(0).ano, "Pagamento por pix - Telefone");
                    listaExtratos.add(extrato);
                }
            } else if (tipoChave == 3) {
                System.out.println("Para qual chave a transferencia sera feita");
                opcoesParaChavePix();
                chaveDestino = scanner.nextInt();
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
                    pix.setEmail(chaveemail);
                } else if (chaveDestino == 4) {
                    System.out.println("Digite a chave aleatoria");
                    pix.setRandom(scanner.nextInt());
                }
                System.out.println("Qual o valor a pagar?");
                valortransferencia = scanner.nextDouble();
                if (contaCorrente.getSaldoCorrente() + contaCorrente.getChequeEspecial() < valortransferencia) {
                    System.out.println("Valor na conta insuficiente");
                } else if (contaCorrente.getSaldoCorrente() < valortransferencia) {
                    valortransferencia -= contaCorrente.getSaldoCorrente();
                    contaCorrente.setSaldoCorrente(0);
                    contaCorrente.setChequeEspecial(contaCorrente.getChequeEspecial() - valortransferencia);
                    Extrato extrato = new Extrato(valortransferencia, listaExtratos.get(0).dia, listaExtratos.get(0).mes, listaExtratos.get(0).ano, "Pagamento por pix - E-mail");
                    listaExtratos.add(extrato);
                } else if (contaCorrente.getSaldoCorrente() > valortransferencia) {
                    contaCorrente.setSaldoCorrente(contaCorrente.getSaldoCorrente() - valortransferencia);
                    Extrato extrato = new Extrato(valortransferencia, listaExtratos.get(0).dia, listaExtratos.get(0).mes, listaExtratos.get(0).ano, "Pagamento por pix - E-mail");
                    listaExtratos.add(extrato);
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
                System.out.println("Qual o valor a pagar?");
                valortransferencia = scanner.nextDouble();
                if (contaCorrente.getSaldoCorrente() + contaCorrente.getChequeEspecial() < valortransferencia) {
                    System.out.println("Valor na conta insuficiente");
                } else if (contaCorrente.getSaldoCorrente() < valortransferencia) {
                    valortransferencia -= contaCorrente.getSaldoCorrente();
                    contaCorrente.setSaldoCorrente(0);
                    contaCorrente.setChequeEspecial(contaCorrente.getChequeEspecial() - valortransferencia);
                    Extrato extrato = new Extrato(valortransferencia, listaExtratos.get(0).dia, listaExtratos.get(0).mes, listaExtratos.get(0).ano, "Pagamento por pix - Aleatorio");
                    listaExtratos.add(extrato);
                } else if (contaCorrente.getSaldoCorrente() > valortransferencia) {
                    contaCorrente.setSaldoCorrente(contaCorrente.getSaldoCorrente() - valortransferencia);
                    Extrato extrato = new Extrato(valortransferencia, listaExtratos.get(0).dia, listaExtratos.get(0).mes, listaExtratos.get(0).ano, "Pagamento por pix - Aleatorio");
                    listaExtratos.add(extrato);
                }
            }
        }
    }
}



