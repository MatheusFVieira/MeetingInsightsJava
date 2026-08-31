package br.com.meetinginsights.domain;

import java.time.LocalDate;

public class Transcricao {

    private int id;
    private int clienteId;
    private String texto;
    private LocalDate data;

    public Transcricao(int clienteId, String texto) {
        this.clienteId = clienteId;
        this.texto = texto;
        this.data = LocalDate.now();
    }

    public boolean contemPalavra(String palavra) {
        return texto.toLowerCase().contains(palavra.toLowerCase());
    }

    public int getId() {
        return id;
    }

    public int getClienteId() {
        return clienteId;
    }

    public String getTexto() {
        return texto;
    }

    public LocalDate getData() {
        return data;
    }

    public void setId(int id) {
        this.id = id;
    }
}