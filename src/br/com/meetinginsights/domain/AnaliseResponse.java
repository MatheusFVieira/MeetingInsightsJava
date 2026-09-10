package br.com.meetinginsights.domain;

// Importando os enums do seu subpacote real
import br.com.meetinginsights.domain.enums.NivelRisco;
import br.com.meetinginsights.domain.enums.TipoOportunidade;
import br.com.meetinginsights.domain.enums.Sentimento;

public class AnaliseResponse {
    private String raciocinio;
    private NivelRisco riscoChurn;
    private TipoOportunidade oportunidadeUpsell;
    private String produtoRecomendado;
    private Sentimento sentimentoGeral;
    private Double orcamentoEstimado;

    public String getRaciocinio() { return raciocinio; }
    public void setRaciocinio(String raciocinio) { this.raciocinio = raciocinio; }

    public NivelRisco getRiscoChurn() { return riscoChurn; }
    public void setRiscoChurn(NivelRisco riscoChurn) { this.riscoChurn = riscoChurn; }

    public TipoOportunidade getOportunidadeUpsell() { return oportunidadeUpsell; }
    public void setOportunidadeUpsell(TipoOportunidade oportunidadeUpsell) { this.oportunidadeUpsell = oportunidadeUpsell; }

    public String getProdutoRecomendado() { return produtoRecomendado; }
    public void setProdutoRecomendado(String produtoRecomendado) { this.produtoRecomendado = produtoRecomendado; }

    public Sentimento getSentimentoGeral() { return sentimentoGeral; }
    public void setSentimentoGeral(Sentimento sentimentoGeral) { this.sentimentoGeral = sentimentoGeral; }

    public Double getOrcamentoEstimado() { return orcamentoEstimado; }
    public void setOrcamentoEstimado(Double orcamentoEstimado) { this.orcamentoEstimado = orcamentoEstimado; }
}