package br.com.meetinginsights.domain;

public class Cliente {
    private Long idCliente;
    private Long idVendedor;
    private String razaoSocial;
    private String cnpj;
    private String segmento;
    private String email;

    public Cliente() {}

    public Cliente(Long idCliente, Long idVendedor, String razaoSocial, String cnpj, String segmento, String email) {
        this.idCliente = idCliente;
        this.idVendedor = idVendedor;
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.segmento = segmento;
        this.email = email;
    }

    public Long getIdCliente() { return idCliente; }
    public void setIdCliente(Long idCliente) { this.idCliente = idCliente; }

    public Long getIdVendedor() { return idVendedor; }
    public void setIdVendedor(Long idVendedor) { this.idVendedor = idVendedor; }

    public String getRazaoSocial() { return razaoSocial; }
    public void setRazaoSocial(String razaoSocial) { this.razaoSocial = razaoSocial; }

    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }

    public String getSegmento() { return segmento; }
    public void setSegmento(String segmento) { this.segmento = segmento; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}