package br.com.meetinginsights.domain;

import java.time.LocalDateTime;

public class AcaoAgente {
    private int idAcao;
    private int idAnalise;
    private Integer idProduto;
    private String tipoAcao;
    private LocalDateTime dataAcao;
    private String statusAlerta;

    public AcaoAgente(int idAcao, int idAnalise, Integer idProduto, String tipoAcao, LocalDateTime dataAcao, String statusAlerta) {
        this.idAcao = idAcao;
        this.idAnalise = idAnalise;
        this.idProduto = idProduto;
        this.tipoAcao = tipoAcao;
        this.dataAcao = dataAcao;
        this.statusAlerta = statusAlerta;
    }

    public int getIdAcao() { return idAcao; }
    public void setIdAcao(int idAcao) { this.idAcao = idAcao; }
    public int getIdAnalise() { return idAnalise; }
    public void setIdAnalise(int idAnalise) { this.idAnalise = idAnalise; }
    public Integer getIdProduto() { return idProduto; }
    public void setIdProduto(Integer idProduto) { this.idProduto = idProduto; }
    public String getTipoAcao() { return tipoAcao; }
    public void setTipoAcao(String tipoAcao) { this.tipoAcao = tipoAcao; }
    public LocalDateTime getDataAcao() { return dataAcao; }
    public void setDataAcao(LocalDateTime dataAcao) { this.dataAcao = dataAcao; }
    public String getStatusAlerta() { return statusAlerta; }
    public void setStatusAlerta(String statusAlerta) { this.statusAlerta = statusAlerta; }
}