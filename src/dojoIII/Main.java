package dojoIII;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        CaixaEletronico conta = new CaixaEletronico();
        int opcaoEscolhida = 1;

        while (1 <= opcaoEscolhida && opcaoEscolhida <= 8) {
            opcaoEscolhida = menuConta();
            escolhaOpcaoDoMenu(opcaoEscolhida, conta);
        }

    }

    public static int menuConta() {
        int valor;
        Scanner scanner = new Scanner(System.in);
        do{
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
            if(valor > 9 || valor < 1){
                System.out.println("Opção Inválida");
            }
        }while(valor > 9 || valor < 1);

        return valor;
    }

    public static void escolhaOpcaoDoMenu(int valor, CaixaEletronico conta){
        switch (valor) {
            case 1:
                conta.iniciarAbrirConta();
                break;
            case 2:
                conta.sacar();
                break;
            case 3:
                conta.depositar();
                break;
            case 4:
                conta.extrato();
                break;
            case 5:
                conta.avancatempo();
                break;
            case 6:
                conta.vincularContaSalario();
                break;
            case 7:
                conta.pagarBoleto();
                break;
            case 8:
                conta.transferirPix();
                break;
            default:
                break;
        }
    }
}