package dojoIII;

import java.time.LocalDate;

public class Extrato implements TransacaoEmConta {

    private String tipoPagamento;
    private double valor;
    private LocalDate data;
    private String descricao;

    public Extrato(double valor, LocalDate data, String tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
        this.data = data;
        this.valor = valor;
    }

    public Extrato(double valor, LocalDate data, String tipoPagamento, String descricao) {
        this.tipoPagamento = tipoPagamento;
        this.data = data;
        this.valor = valor;
        this.descricao = descricao;
    }


    public String getTipoPagamento() {
        return tipoPagamento;
    }

    public double getValor() {
        return valor;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipoOperacao() {
        return null;
    }


    public LocalDate getData() {
        return data;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
    
    public void setTipoPagamento(String tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    @Override
    public String toString() {
        int dia = this.data.getDayOfMonth();
        int mes = this.data.getMonthValue();
        int ano = this.data.getYear();

        String s = "";
        s += dia + "/" + mes + "/" + ano + " || ";
        s += tipoPagamento + " || ";
        s += "R$ " + valor + " || ";
        s += "Descrição " + this.descricao;
        return s;
    }
}
