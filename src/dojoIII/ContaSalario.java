package dojoIII;

public class ContaSalario {
    private int diaPagamento;
    private double valorPagamento;

    public ContaSalario(int diaPagamento, double valorPagamento) {
        this.diaPagamento = diaPagamento;
        this.valorPagamento = valorPagamento;
        System.out.println(this);
    }

    public int getDiaPagamento() {
        return diaPagamento;
    }

    public void setDiaPagamento(int diaPagamento) {
        this.diaPagamento = diaPagamento;
    }

    public double getValorPagamento() {
        return valorPagamento;
    }

    public void setValorPagamento(double valorPagamento) {
        this.valorPagamento = valorPagamento;
    }

    private String detalharValor() {
        return "R$" + this.valorPagamento;
    }
    
    private String detalharData() {
        return "Dia pagamento: " + this.diaPagamento;
    }
    
    private String className() {
        return "(ContaSalario)";
    }

    @Override
    public String toString() {
        return  this.detalharValor() + " || " +  this.detalharData() + this.className();
    }
}
