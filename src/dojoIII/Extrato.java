package dojoIII;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.text.*;

public class Extrato implements TransacaoEmConta{

    //public int dia, mes, ano;
    private String tipoPagamento;
    private double valor;
    private LocalDate data;

    public Extrato(double valor, LocalDate data, String tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
        this.data = data;
        this.valor = valor;

    }

    /*public Extrato(double valor, int dia, int mes, int ano, String tipoPagamento) {
        this.dia = dia;
        this.mes = mes;
        this.ano = ano;
        this.tipoPagamento = tipoPagamento;
        this.valor = valor;
    }*/



    public String getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(String tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public double getValor() {
        return valor;
    }

    @Override
    public String getDescricao() {
        return null;
    }

    @Override
    public String getTipoOperacao() {
        return null;
    }

    @Override
    public int[] getData() {
        return new int[0];
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        int dia = this.data.getDayOfMonth();
        int mes = this.data.getMonthValue();
        int ano = this.data.getYear();


        String s = "";
        s += dia + "/" + mes + "/" + ano + " - ";
        s += tipoPagamento + " - ";
        s += "R$ " + valor;
        return s;
    }
}
