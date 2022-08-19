package dojoIII;

public class Main {

    public static void main(String[] args) {
        
        CaixaEletronico caixaEletronico = new CaixaEletronico();
        Fabrica fabrica = new Fabrica(caixaEletronico);
        fabrica.fabicarClientes();
        fabrica.fabricarContasCorrente();
        fabrica.fabricarContasPoupanca();
        fabrica.fabricarRelacaoClienteContas();
        Menu.menuInicial(caixaEletronico);
    }
}