package dojoIII;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        CaixaEletronico caixaEletronico = new CaixaEletronico();
        Fabrica fabrica = new Fabrica(caixaEletronico);
        /*fabrica.fabicarClientes();
        fabrica.fabricarContasCorrente();
        fabrica.fabricarContasPoupanca();
        fabrica.fabricarBoleto();
        fabrica.fabricarRelacaoClienteContaPoupanca();
        fabrica.fabricarRelacaoClienteContaCorrente();*/
        
        /*caixaEletronico.criarTresBoletos();

        caixaEletronico.gerarCLiente();
        caixaEletronico.gerarContaCorrente();
        caixaEletronico.gerarContaPoupanca();
        caixaEletronico.associarClienteNaConta();*/
        

        /*int opcaoEscolhida = 1;
        

        while (1 <= opcaoEscolhida && opcaoEscolhida <= 8) {
            opcaoEscolhida = menuConta();
            escolhaOpcaoDoMenu(opcaoEscolhida, caixaEletronico);
        }*/


        Menu.menuInicial(caixaEletronico);

    }

    public static int menuConta() {
        int valor;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("[1] abrir conta");
            System.out.println("[2] sacar");
            System.out.println("[3] depositar");
            System.out.println("[4] emitir extrato");
            System.out.println("[5] avancar tempo");
            System.out.println("[6] vincular conta salario");
            System.out.println("[7] pagar boleto");
            System.out.println("[8] pix");
            System.out.println("[9] sair");
            valor = scanner.nextInt();
            if (valor > 9 || valor < 1) {
                System.out.println("Opção Inválida");
            }
        } while (valor > 9 || valor < 1);

        return valor;
    }

    public static void escolhaOpcaoDoMenu(int valor, CaixaEletronico caixaEletronico) {
        switch (valor) {
            case 1:
                caixaEletronico.iniciarAbrirConta();
                break;
            case 2:
                caixaEletronico.sacar();
                break;
            case 3:
                caixaEletronico.depositar();
                break;
            case 4:
                caixaEletronico.extrato();
                break;
            case 5:
                caixaEletronico.avancartempo();
                break;
            case 6:
                caixaEletronico.vincularContaSalario();
                break;
            case 7:
                caixaEletronico.pagarBoleto();
                break;
            case 8:
                caixaEletronico.transferirPix();
                break;
            default:
                break;
        }
    }
}