package dojoIII;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ContaPagamento {
    LocalDate dataPagamento;
    double valorPagamento;

    public ContaPagamento(LocalDate dataPagamento, double valorPagamento) {
        this.dataPagamento = dataPagamento;
        this.valorPagamento = valorPagamento;
        System.out.println(this);
    }
    
    @Override
    public String toString() {
        return "R$" + this.valorPagamento + " || " + "Data: " + this.dataPagamento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    } 
}
