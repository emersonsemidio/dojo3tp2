package dojoIII;

import java.util.*;

public class Cliente {

    private String nome;
    private String cpf;
    private String dataNascimento;
    private String email;
    private String telefone;
    private int valorSalario = 0;
    private static final String AGENCIA = "0001";
    private boolean temContaCorrente = false;
    private boolean temContaPoupanca = false;
    private boolean vinculoContaSalario = false;
    private ContaCorrente contaCorrente;
    private ContaPoupanca contaPoupanca;

    public Cliente() {
        
    }

    public Cliente(String nome, String cpf, String dataNascimento, String email, String telefone) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.email = email;
        this.telefone = telefone;
        System.out.println(this);
    }
    

    public void mostrarDetalhesContaCorrente() {
        System.out.println(this.contaCorrente);
    }

    public void mostrarDetalhesContaPoupanca() {
        System.out.println(this.contaPoupanca);
    }

    public boolean podeAbrirConta() {
        return this.temContaCorrente || this.temContaPoupanca;
    }

    public void vincularContaSalario() {
    }

    public ContaCorrente getContaCorrente() {
        return contaCorrente;
    }

    public void setContaCorrente(ContaCorrente contaCorrente) {
        this.contaCorrente = contaCorrente;
        this.temContaCorrente = true;
    }

    public ContaPoupanca getContaPoupanca() {
        return contaPoupanca;
    }

    public void setContaPoupanca(ContaPoupanca contaPoupanca) {
        this.contaPoupanca = contaPoupanca;
        this.temContaPoupanca = true;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }

    public boolean isTemContaCorrente() {
        return temContaCorrente;
    }

    public void setTemContaCorrente(boolean temContaCorrente) {
        this.temContaCorrente = temContaCorrente;
    }

    public boolean isTemContaPoupanca() {
        return temContaPoupanca;
    }

    public void setTemContaPoupanca(boolean temContaPoupanca) {
        this.temContaPoupanca = temContaPoupanca;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public boolean isVinculoContaSalario() {
        return vinculoContaSalario;
    }

    public void setVinculoContaSalario(boolean vinculoContaSalario) {
        this.vinculoContaSalario = vinculoContaSalario;
    }

    public int getValorSalario() {
        return valorSalario;
    }

    public void setValorSalario(int valorSalario) {
        this.valorSalario = valorSalario;
    }
    
    @Override
    public String toString() {
        String s = "";
        s += "Nome: " + this.getNome() + " || ";
        s += "Cpf: " + this.getCpf() + " || ";
        s += "Email: " + this.getEmail() + " || ";
        s += "Telefone: " + this.getTelefone() + " || ";
        s += "Data Nascimento: " + this.getDataNascimento();
        return s;
    }
}
