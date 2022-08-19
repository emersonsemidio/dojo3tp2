package dojoIII;

public class Fabrica {
    private CaixaEletronico caixaEletronico = null;
    public Fabrica(CaixaEletronico caixaEletronico) {
        this.caixaEletronico = caixaEletronico;
    }
    
    public void fabicarClientes() {
        this.caixaEletronico.clientes.add(new Cliente("a", "0001", "12 07 2001", "a@unirio.com", "9999-0001"));
        this.caixaEletronico.clientes.add(new Cliente("b", "0002", "12 07 2001", "b@unirio.com", "9999-0002"));
        this.caixaEletronico.clientes.add(new Cliente("c", "0003", "12 07 2001", "c@unirio.com", "9999-0003"));
        this.caixaEletronico.clientes.add(new Cliente("d", "0004", "12 07 2001", "d@unirio.com", "9999-0004"));
        this.caixaEletronico.clientes.add(new Cliente("e", "0005", "12 07 2001", "e@unirio.com", "9999-0005"));
    }

    public void fabricarContasCorrente() {
        this.caixaEletronico.contas.add(new ContaCorrente("100", "1234"));
        this.caixaEletronico.contas.add(new ContaCorrente("101", "1234"));
        this.caixaEletronico.contas.add(new ContaCorrente("102", "1234"));
    }
    
    public void fabricarContasPoupanca() {
        this.caixaEletronico.contas.add(new ContaPoupanca("200", "1234"));
        this.caixaEletronico.contas.add(new ContaPoupanca("201", "1234"));
        this.caixaEletronico.contas.add(new ContaPoupanca("202", "1234"));
    }
    
    public void fabricarRelacaoClienteContas() {
        // Cliente 0
        ContaCorrente cc0 = this.getContaCorrenteSemCliente();
        ContaPoupanca cp0 = this.getContaPoupancaSemCliente();
        Cliente c0 = this.caixaEletronico.clientes.get(0);
        cc0.setCliente(c0);
        c0.setContaCorrente(cc0);
        c0.setContaPoupanca(cp0);


        // Cliente 1
        ContaCorrente cc1 = this.getContaCorrenteSemCliente();
        Cliente c1 = this.caixaEletronico.clientes.get(1);
        cc1.setCliente(c1);
        c1.setContaCorrente(cc1);

        // Cliente 2
        ContaCorrente cc2 = this.getContaCorrenteSemCliente();
        Cliente c2 = this.caixaEletronico.clientes.get(2);
        cc2.setCliente(c2);
        c2.setContaCorrente(cc2);


        // Cliente 3
        ContaPoupanca cp3 = this.getContaPoupancaSemCliente();
        Cliente c3 = this.caixaEletronico.clientes.get(3);
        cp3.setCliente(c3);
        c3.setContaPoupanca(cp3);

        // Cliente 4
        ContaPoupanca cp4 = this.getContaPoupancaSemCliente();
        Cliente c4 = this.caixaEletronico.clientes.get(4);
        cp4.setCliente(c2);
        c2.setContaPoupanca(cp4);
    }

    
    private ContaCorrente getContaCorrenteSemCliente() {
        for (Conta cc : this.caixaEletronico.contas) {
            if (cc instanceof ContaCorrente && cc.cliente == null) {
                return (ContaCorrente) cc;
            }
        }
        return null;
    }

    private ContaPoupanca getContaPoupancaSemCliente() {
        for (Conta cc : this.caixaEletronico.contas) {
            if (cc instanceof ContaPoupanca && cc.cliente == null) {
                return (ContaPoupanca) cc;
            }
        }
        return null;
    }

}
