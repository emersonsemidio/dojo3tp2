package dojoIII;

public class Boleto {

    private double preco;
    private String codigo;

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
}
