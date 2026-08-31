package br.com.meetinginsights.domain;

public class Cliente extends Pessoa {
    private int idCliente;
    private int idVendedor;
    private String empresa;
    private String cnpj;
    private String cargo;
    private String segmento;

    public Cliente(String nome, int idVendedor, String empresa, String cnpj, String cargo, String segmento) {
        super(nome);
        this.idVendedor = idVendedor;
        this.empresa = empresa;
        this.cnpj = cnpj;
        this.cargo = cargo;
        this.segmento = segmento;
    }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }
    public int getIdVendedor() { return idVendedor; }
    public String getEmpresa() { return empresa; }
    public String getCnpj() { return cnpj; }
    public String getCargo() { return cargo; }
    public String getSegmento() { return segmento; }

    @Override
    public String exibirResumo() {
        return "Cliente: " + nome + " | Empresa: " + empresa + " | CNPJ: " + cnpj;
    }
}