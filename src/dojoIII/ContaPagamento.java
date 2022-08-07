package dojoIII;

import java.time.LocalDate;

public class ContaPagamento {
    LocalDate dataPagamento;
    double valorPagamento;

    public ContaPagamento(LocalDate dataPagamento, double valorPagamento) {
        this.dataPagamento = dataPagamento;
        this.valorPagamento = valorPagamento;
        System.out.println(this);
    }
}
