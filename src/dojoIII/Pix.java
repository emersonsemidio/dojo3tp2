package dojoIII;

public class Pix{
    protected String email;
    protected String telefone;
    protected String cpf;
    protected int valorPix;
    protected int random;

    public Pix(String email, String telefone, String cpf, int random) {
        this.email = email;
        this.telefone = telefone;
        this.cpf = cpf;
        this.random = random;
    }
    public Pix() {
        this.email = email;
        this.telefone = telefone;
        this.cpf = cpf;
        this.random = random;
        this.valorPix = valorPix;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public int getRandom() {
        return random;
    }

    public void setRandom(int random) {
        this.random = random;
    }


    public int getValorPix() {
        return valorPix;
    }

    public void setValorPix(int valorPix) {
        this.valorPix = valorPix;
    }
}
