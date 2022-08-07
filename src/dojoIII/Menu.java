package dojoIII;

import java.util.Scanner;

public class Menu {
    public static void menuInicial(CaixaEletronico caixaEletronico) {
        while (true){
            Scanner sc = new Scanner(System.in);
            System.out.println("---- Menu Principal -----");
            System.out.println("(1)Acessar Conta\n(2)Abrir Conta\n(3)Avançar dias\n(4)Encerrar");
            int op = sc.nextInt();
            try {
                switch (op){
                    case 1: caixaEletronico.acessarConta();
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

    private static void acessarConta(CaixaEletronico caixaEletronico) {
        Scanner sc = new Scanner(System.in);
        System.out.println("---- Acessar Conta ----");
        System.out.println("Digite o número da conta:");
        String numeroConta = sc.nextLine();
        System.out.println("Digite sua senha de 4 digítos:");
        String senha = sc.nextLine();

        Conta conta = caixaEletronico.buscarContaPeloNumero(1);
        if(conta != null){
            if(conta.getSenhaDaConta() == 1){
                Cliente cliente = conta.getCliente();
                System.out.println("Acesso Liberado!\n");
                // menuCliente(agencia,conta,banco);
            }else
                System.out.println("Senha inválida");
        }else {
            System.out.println("Conta não encontrada");
        }
    }
}
