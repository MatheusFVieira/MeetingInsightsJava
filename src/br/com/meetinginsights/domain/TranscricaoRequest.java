package br.com.meetinginsights.domain;

public class TranscricaoRequest {
    private String texto;
    private Long idCliente;

    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }

    public Long getIdCliente() { return idCliente; }
    public void setIdCliente(Long idCliente) { this.idCliente = idCliente; }
}