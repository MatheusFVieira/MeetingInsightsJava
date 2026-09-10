package br.com.meetinginsights.domain;

public class TarefaDTO {
    private Long idAcao;
    private String titulo;
    private String razaoSocial;
    private String produto;
    private String tipoAcao;
    private String statusAlerta;
    private String dataAcao;

    // Campos detalhados da análise da IA
    private String ameacaChurn;
    private String oportunidadeUpsell;
    private String sentimentoGeral;
    private Double orcamentoEstimado;

    public TarefaDTO() {}

    public TarefaDTO(Long idAcao, String titulo, String razaoSocial, String produto, String tipoAcao,
                     String statusAlerta, String dataAcao, String ameacaChurn, String oportunidadeUpsell,
                     String sentimentoGeral, Double orcamentoEstimado) {
        this.idAcao = idAcao;
        this.titulo = titulo;
        this.razaoSocial = razaoSocial;
        this.produto = produto;
        this.tipoAcao = tipoAcao;
        this.statusAlerta = statusAlerta;
        this.dataAcao = dataAcao;
        this.ameacaChurn = ameacaChurn;
        this.oportunidadeUpsell = oportunidadeUpsell;
        this.sentimentoGeral = sentimentoGeral;
        this.orcamentoEstimado = orcamentoEstimado;
    }

    public Long getIdAcao() { return idAcao; }
    public void setIdAcao(Long idAcao) { this.idAcao = idAcao; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getRazaoSocial() { return razaoSocial; }
    public void setRazaoSocial(String razaoSocial) { this.razaoSocial = razaoSocial; }

    public String getProduto() { return produto; }
    public void setProduto(String produto) { this.produto = produto; }

    public String getTipoAcao() { return tipoAcao; }
    public void setTipoAcao(String tipoAcao) { this.tipoAcao = tipoAcao; }

    public String getStatusAlerta() { return statusAlerta; }
    public void setStatusAlerta(String statusAlerta) { this.statusAlerta = statusAlerta; }

    public String getDataAcao() { return dataAcao; }
    public void setDataAcao(String dataAcao) { this.dataAcao = dataAcao; }

    public String getAmeacaChurn() { return ameacaChurn; }
    public void setAmeacaChurn(String ameacaChurn) { this.ameacaChurn = ameacaChurn; }

    public String getOportunidadeUpsell() { return oportunidadeUpsell; }
    public void setOportunidadeUpsell(String oportunidadeUpsell) { this.oportunidadeUpsell = oportunidadeUpsell; }

    public String getSentimentoGeral() { return sentimentoGeral; }
    public void setSentimentoGeral(String sentimentoGeral) { this.sentimentoGeral = sentimentoGeral; }

    public Double getOrcamentoEstimado() { return orcamentoEstimado; }
    public void setOrcamentoEstimado(Double orcamentoEstimado) { this.orcamentoEstimado = orcamentoEstimado; }
}