package br.com.meetinginsights.domain;

public class Vendedor extends Pessoa {
    private int idVendedor;
    private String emailCorporativo;
    private String regiaoAtuacao;

    public Vendedor(String nome, int idVendedor, String emailCorporativo, String regiaoAtuacao) {
        super(nome);
        this.idVendedor = idVendedor;
        this.emailCorporativo = emailCorporativo;
        this.regiaoAtuacao = regiaoAtuacao;
    }

    public int getIdVendedor() { return idVendedor; }
    public void setIdVendedor(int idVendedor) { this.idVendedor = idVendedor; }
    public String getEmailCorporativo() { return emailCorporativo; }
    public void setEmailCorporativo(String emailCorporativo) { this.emailCorporativo = emailCorporativo; }
    public String getRegiaoAtuacao() { return regiaoAtuacao; }
    public void setRegiaoAtuacao(String regiaoAtuacao) { this.regiaoAtuacao = regiaoAtuacao; }

    @Override
    public String exibirResumo() {
        return "Vendedor: " + nome + " | Região: " + regiaoAtuacao;
    }
}