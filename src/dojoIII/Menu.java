package dojoIII;

import java.util.Scanner;

public class Menu {
    public static void menuInicial(CaixaEletronico caixaEletronico) {
        while (true){
            Scanner sc = new Scanner(System.in);
            System.out.println("---- Menu Principal -----");
            System.out.println("[1] Acessar Conta");
            System.out.println("[2] Abrir Conta");
            System.out.println("[3] Avançar dias");
            System.out.println("[4] Encerrar");
            int op = sc.nextInt();
            try {
                switch (op){
                    case 1: Menu.acessarConta(caixaEletronico);
                        break;
                    case 2: caixaEletronico.iniciarAbrirConta();
                        break;
                    case 3: caixaEletronico.avancartempo();
                        break;
                    default: return;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void acessarConta(CaixaEletronico caixaEletronico) {
        Scanner sc = new Scanner(System.in);
        System.out.println("---- Acessar Conta ----");
        System.out.println("Digite o número da conta:");
        String numeroConta = sc.nextLine();
        System.out.println("Digite sua senha de 4 digítos:");
        String senha = sc.nextLine();

        Conta conta = caixaEletronico.buscarContaPeloNumero(numeroConta);
        if(conta != null){
            if(conta.getSenhaDaConta().equalsIgnoreCase(senha)){
                menuContaAutenticada(caixaEletronico,conta);
            }else
                System.out.println("Senha inválida");
        }else {
            System.out.println("Conta não encontrada");
        }
    }
    
    public static void menuContaAutenticada(CaixaEletronico caixaEletronico, Conta conta) {
        Scanner sc = new Scanner(System.in);
        System.out.println("---- Menu Conta Autenticada -----");
        System.out.println("[1] Sacar");
        System.out.println("[2] Depositar");
        System.out.println("[3] Emitir extrato");
        System.out.println("[4] Pagar boleto");
        System.out.println("[5] Pix");
        System.out.println("[6] Conta salario");
        System.out.println("[7] Sair");

        String opcao = sc.nextLine();
        switch (Integer.parseInt(opcao)){
            case 1: caixaEletronico.sacar(conta);
                break;
            case 2: caixaEletronico.depositar(conta);
                break;
            case 3: caixaEletronico.emitirExtrato(conta);
                break;
            case 4: caixaEletronico.pagarBoleto(conta);
                break;
            case 5: caixaEletronico.transferirPix(conta);
                break;
            case 6: caixaEletronico.configurarContaSalario(conta);
                break;
            default: return;
        }
    }
}
