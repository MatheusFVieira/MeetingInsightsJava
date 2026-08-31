package br.com.meetinginsights.domain;

public class Produto {
    private int idProduto;
    private String nomeProduto;
    private String categoria;
    private int statusAtivo;

    public Produto(int idProduto, String nomeProduto, String categoria, int statusAtivo) {
        this.idProduto = idProduto;
        this.nomeProduto = nomeProduto;
        this.categoria = categoria;
        this.statusAtivo = statusAtivo;
    }

    public int getIdProduto() { return idProduto; }
    public void setIdProduto(int idProduto) { this.idProduto = idProduto; }
    public String getNomeProduto() { return nomeProduto; }
    public void setNomeProduto(String nomeProduto) { this.nomeProduto = nomeProduto; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public int getStatusAtivo() { return statusAtivo; }
    public void setStatusAtivo(int statusAtivo) { this.statusAtivo = statusAtivo; }
}