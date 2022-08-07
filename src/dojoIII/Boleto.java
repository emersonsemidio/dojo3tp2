package dojoIII;

public class Boleto {

    private double preco;
    private String codigo;
    private boolean isPago = false;
    private Conta conta;

    public boolean isPago() {
        return isPago;
    }

    public void setPago(boolean pago) {
        isPago = pago;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public Boleto() {

    }
    public Boleto(String codigo, double preco) {
        this.codigo = codigo;
        this.preco = preco;
    }

    public void associarContaAoBoleto(Conta conta){
        this.conta = conta;
    }

    @Override
    public String toString(){

        return  "eme" + codigo;
    }
}
