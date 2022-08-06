package dojoIII;

import java.util.ArrayList;
import java.util.Date;
import java.text.*;

public class Extrato {

    public int dia, mes, ano;
    private String tipoPagamento;
    private double valor;

    public Extrato(double valor, int dia, int mes, int ano, String tipoPagamento) {
        this.dia = dia;
        this.mes = mes;
        this.ano = ano;
        this.tipoPagamento = tipoPagamento;
        this.valor = valor;
    }

    public Extrato() {
        this.dia = dia;
        this.mes = mes;
        this.ano = ano;
        this.tipoPagamento = tipoPagamento;
        this.valor = valor;
    }

    public String getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(String tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        String s = "";
        s += dia + "/" + mes + "/" + ano + " - ";
        s += tipoPagamento + " - ";
        s += "R$ " + valor;
        return s;
    }
}
