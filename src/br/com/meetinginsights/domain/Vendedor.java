package br.com.meetinginsights.domain;

public class Vendedor {
    private Long idVendedor;
    private String nomeVendedor;
    private String email;
    private String senha;
    private String admin; // "S" ou "N"

    public Vendedor() {}

    public Vendedor(Long idVendedor, String nomeVendedor, String email, String senha, String admin) {
        this.idVendedor = idVendedor;
        this.nomeVendedor = nomeVendedor;
        this.email = email;
        this.senha = senha;
        this.admin = admin;
    }

    public Long getIdVendedor() { return idVendedor; }
    public void setIdVendedor(Long idVendedor) { this.idVendedor = idVendedor; }

    public String getNomeVendedor() { return nomeVendedor; }
    public void setNomeVendedor(String nomeVendedor) { this.nomeVendedor = nomeVendedor; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getAdmin() { return admin; }
    public void setAdmin(String admin) { this.admin = admin; }
}